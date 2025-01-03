package lt.bongibau.behelpfull.users;

import lt.bongibau.behelpfull.database.DatabaseManager;
import lt.bongibau.behelpfull.requests.Request;
import lt.bongibau.behelpfull.requests.Status;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Validator extends User {

    public Validator(int id, String username, String password) {
        super(id, username, password);
    }

    @Nullable
    public static Validator create(String username, String password) throws SQLException {
        Integer id = User.createAndGetId(username, password);
        if (id == null) return null;

        PreparedStatement askerInsertStatement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("INSERT INTO validators(user_id) VALUES (?)");

        askerInsertStatement.setInt(1, id);

        askerInsertStatement.execute();

        return new Validator(
                id,
                username,
                password
        );
    }

    @Nullable
    public static Validator getById(int id) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM validators JOIN users ON users.id = validators.user_id WHERE user_id = ?");

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
    public static Validator getByUsername(String username) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM validators JOIN users ON users.id = validators.user_id WHERE username = ?");

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

    public static List<Validator> getAll() throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM validators JOIN users ON users.id = validators.user_id");

        ResultSet result = statement.executeQuery();

        List<Validator> validators = new ArrayList<>();

        while (result.next()) {
            Validator validator = new Validator(
                    result.getInt("id"),
                    result.getString("username"),
                    result.getString("password")
            );

            validators.add(validator);
        }

        return validators;
    }

    public void validateRequest(Request request) throws SQLException {
        if (request.getStatus() == Status.WAITING_FOR_APPROVAL) {
            request.setStatus(Status.PUBLISHED);
        }
        request.save();
    }

    public void refuseRequest(Request request, String reason) throws SQLException {
        if (request.getStatus() == Status.WAITING_FOR_APPROVAL) {
            request.setFeedback(reason);
            request.setStatus(Status.BANNED_HAMMER);
        }
        request.save();
    }

    @Override
    public String toString() {
        return this.getUsername() + " (id: " + this.getId() + ")";
    }

    public List<Request> getRequests() throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM requests WHERE validator_id = ? AND status = ?");

        statement.setInt(1, this.getId());
        statement.setString(2, Status.WAITING_FOR_APPROVAL.toString());

        ResultSet result = statement.executeQuery();
        List<Request> requests = new ArrayList<>();

        while (result.next()) {
            int id = result.getInt("id");
            String title = result.getString("title");
            String description = result.getString("description");
            Integer validatorId = result.getInt("validator_id");

            if (result.wasNull()) validatorId = null;

            int askerId = result.getInt("asker_id");
            Integer volunteerId = result.getInt("volunteer_id");

            if (result.wasNull()) volunteerId = null;

            Status status = Status.valueOf(result.getString("status"));
            Date createdAt = result.getDate("created_at");
            int duration = result.getInt("duration");
            String feedback = result.getString("feedback");

            if (result.wasNull()) feedback = null;

            Date date = result.getDate("date");

            requests.add(new Request(
                    id,
                    title,
                    description,
                    validatorId,
                    askerId,
                    volunteerId,
                    status,
                    createdAt,
                    duration,
                    feedback,
                    date
            ));
        }

        return requests;
    }
}
