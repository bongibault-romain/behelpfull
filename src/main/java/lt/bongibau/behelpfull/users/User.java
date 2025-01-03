package lt.bongibau.behelpfull.users;

import lt.bongibau.behelpfull.database.DatabaseManager;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {

    private int id;
    private String username;
    private String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Create a new user in the database
     *
     * @param username username of the user
     * @param password password of the user
     * @return the id of the created user
     * @throws SQLException if an error occurs while creating the user
     */
    protected static Integer createAndGetId(String username, String password) throws SQLException {
        PreparedStatement userInsertStatement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

        userInsertStatement.setString(1, username);
        userInsertStatement.setString(2, password);

        userInsertStatement.execute();

        ResultSet result = userInsertStatement.getGeneratedKeys();

        if (!result.next()) return null;
        
        return result.getInt(1);
    }


    @Nullable
    public static User getById(int id) throws SQLException {
        User result = Asker.getById(id);

        if (result == null) {
            result = Volunteer.getById(id);
        }

        if (result == null) {
            result = Validator.getById(id);
        }

        return result;
    }

    public static User getByUsername(String username) throws SQLException {
        User result = Asker.getByUsername(username);

        if (result == null) {
            result = Volunteer.getByUsername(username);
        }

        if (result == null) {
            result = Validator.getByUsername(username);
        }

        return result;
    }

    public void save() throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector().getConnection()
                .prepareStatement("UPDATE users SET username = ?, password = ? WHERE id = ?");

        statement.setString(1, this.username);
        statement.setString(2, this.password);
        statement.setInt(3, this.id);

        statement.execute();
    }
}
