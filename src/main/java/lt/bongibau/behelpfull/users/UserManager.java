package lt.bongibau.behelpfull.users;

import lt.bongibau.behelpfull.database.DatabaseManager;
import lt.bongibau.behelpfull.models.Asker;
import lt.bongibau.behelpfull.models.User;
import lt.bongibau.behelpfull.models.Validator;
import lt.bongibau.behelpfull.models.Volunteer;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        //Prepare une requete SQL pour la BDD à laquelle nous sommes connectés
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM askers JOIN users ON users.id = askers.user_id WHERE user_id = ?");

        //Remplace le ? dans la requête précédente (minimise les attaques SQL)
        statement.setString(1, String.valueOf(id));

        //Execute la requete : stocke le resultat dans result
        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            return null;
        }

        return new Asker(
                result.getInt("id"),
                result.getString("username"),
                result.getString("password"),
                result.getDate("birth_on"),
                result.getInt("validator_id")
        );
    }

    @Nullable
    public Asker getAsker(String username) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM askers JOIN users ON users.id = askers.user_id WHERE username = ?");

        statement.setString(1, username);

        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            return null;
        }

        return new Asker(
                result.getInt("id"),
                result.getString("username"),
                result.getString("password"),
                result.getDate("birth_on"),
                result.getInt("validator_id")
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
    public Volunteer getVolunteer(String username) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM volunteers JOIN users ON users.id = askers.user_id WHERE username = ?");

        statement.setString(1, username);

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

    @Nullable
    public Validator getValidator(String username) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM volunteers JOIN users ON users.id = askers.user_id WHERE username = ?");

        statement.setString(1, username);

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
