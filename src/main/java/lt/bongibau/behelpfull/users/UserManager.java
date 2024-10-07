package lt.bongibau.behelpfull.users;

import lt.bongibau.behelpfull.database.DatabaseManager;
import lt.bongibau.behelpfull.models.Asker;
import lt.bongibau.behelpfull.models.User;
import lt.bongibau.behelpfull.models.Validator;
import lt.bongibau.behelpfull.models.Volunteer;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class UserManager {

    private static UserManager instance;

    @Nullable
    public User getUser(int id) throws SQLException {
        User result = this.getAsker(id);

        if (result == null) {
            result = this.getVolunteer(id);
        }

        if (result == null) {
            result = this.getValidator(id);
        }

        return result;
    }

    @Nullable
    public Asker getAsker(int id) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM askers JOIN users ON users.id = askers.user_id WHERE user_id = ?");

        statement.setString(1, String.valueOf(id));

        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            return null;
        }

        return new Asker(
                result.getInt("id"),
                result.getString("username"),
                result.getString("password"),
                result.getDate("birth_on")
        );
    }

    public Asker createAsker(String username, String password, Date birthOn, @Nullable Integer validatorId) throws SQLException {
        PreparedStatement userInsertStatement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

        userInsertStatement.setString(1, username);
        userInsertStatement.setString(2, password);

        userInsertStatement.execute();

        ResultSet result = userInsertStatement.getGeneratedKeys();

        if (!result.next()) return null;

        int id = result.getInt(1);

        PreparedStatement askerInsertStatement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("INSERT INTO askers(user_id, birth_on, validator_id) VALUES (?,?,?)");

        askerInsertStatement.setInt(1, id);
        askerInsertStatement.setDate(2, birthOn);
        if (validatorId == null) {
            askerInsertStatement.setNull(3, Types.INTEGER);
        } else {
            askerInsertStatement.setInt(3, validatorId);
        }


        askerInsertStatement.execute();

        return new Asker(
                id,
                username,
                password,
                birthOn
        );
    }

    @Nullable
    public Volunteer getVolunteer(int id) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM volunteers JOIN users ON users.id = askers.user_id WHERE user_id = ?");

        statement.setString(1, String.valueOf(id));

        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            return null;
        }

        return new Volunteer(
                result.getInt("id"),
                result.getString("username"),
                result.getString("password"),
                result.getDate("birth_on"),
                result.getBoolean("has_driving_license"),
                result.getBoolean("has_psc1")
        );
    }

    @Nullable
    public Validator getValidator(int id) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM volunteers JOIN users ON users.id = askers.user_id WHERE user_id = ?");

        statement.setString(1, String.valueOf(id));

        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            return null;
        }

        return new Validator(
                result.getInt("id"),
                result.getString("username"),
                result.getString("password")
        );
    }


    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }

        return instance;
    }
}
