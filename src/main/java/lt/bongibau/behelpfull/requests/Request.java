package lt.bongibau.behelpfull.requests;

import lt.bongibau.behelpfull.database.DatabaseManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Request {

    public interface Observer {
        void onRequestCreate(Request request);

        void onRequestUpdate(Request request);
    }

    private static List<Observer> observers = new ArrayList<>();

    public static void addObserver(Observer observer) {
        observers.add(observer);
    }

    public static void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private int id;

    private String title;
    private int duration;
    private Date createdAt;
    private String description;
    private Status status;
    private int askerId;
    @Nullable
    private Integer volunteerId;
    @Nullable
    private Integer validatorId;
    private String feedback;

    private Date date;

    public Request(int id, String title, String description, Integer validatorId, Integer askerId, Integer volunteerId, Status status, Date createdAt, int duration, String feedback, Date date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.validatorId = validatorId;
        this.askerId = askerId;
        this.volunteerId = volunteerId;
        this.status = status;
        this.createdAt = createdAt;
        this.duration = duration;
        this.feedback = feedback;
        this.date = date;
    }

    public static Request create(String title, String description, int askerId, @Nullable Integer validatorId, @NotNull Date date, int duration) throws SQLException {

        PreparedStatement requestInsertStatement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("INSERT INTO requests(title, description, validator_id, asker_id, status, duration, date) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        requestInsertStatement.setString(1, title);
        requestInsertStatement.setString(2, description);
        if (validatorId == null) {
            requestInsertStatement.setNull(3, Types.INTEGER);
        } else {
            requestInsertStatement.setInt(3, validatorId);
        }
        requestInsertStatement.setInt(4, askerId);
        if (validatorId == null) {
            requestInsertStatement.setString(5, Status.PUBLISHED.toString());
        } else {
            requestInsertStatement.setString(5, Status.WAITING_FOR_APPROVAL.toString());
        }
        requestInsertStatement.setInt(6, duration);
        requestInsertStatement.setDate(7, date);

        requestInsertStatement.execute();

        ResultSet result = requestInsertStatement.getGeneratedKeys();

        if (!result.next()) return null;

        Request request = new Request(
                result.getInt(1),
                title,
                description,
                validatorId,
                askerId,
                null,
                validatorId == null ? Status.PUBLISHED : Status.WAITING_FOR_APPROVAL,
                new Date(System.currentTimeMillis()),
                duration,
                null,
                date
        );

        observers.forEach(observer -> observer.onRequestCreate(request));

        return request;
    }

    public void deleteRequest() throws SQLException {
        PreparedStatement deleteRequestStatement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("DELETE FROM requests WHERE id = (?)");

        deleteRequestStatement.setInt(1, this.getId());
        deleteRequestStatement.execute();
    }

    public int getId() {
        return id;
    }

    public int getTime() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public int getAskerId() {
        return askerId;
    }

    public @Nullable Integer getVolunteerId() {
        return volunteerId;
    }

    public @Nullable Integer getValidatorId() {
        return validatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(int time) {
        this.duration = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAskerId(int askerId) {
        this.askerId = askerId;
    }

    public void setVolunteerId(Integer volunteerId) {
        this.volunteerId = volunteerId;
    }

    public void setValidatorId(Integer validatorId) {
        this.validatorId = validatorId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) throws SQLException {
        this.feedback = feedback;
    }

    public void save() throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector().getConnection()
                .prepareStatement("UPDATE requests SET title = ?, description = ?, validator_id = ?, asker_id = ?, volunteer_id = ?, status = ?, created_at = ?, duration = ?, feedback = ?, date = ? WHERE id = ?");

        statement.setString(1, this.title);
        statement.setString(2, this.description);

        if (this.validatorId == null) {
            statement.setNull(3, Types.INTEGER);
        } else {
            statement.setInt(3, this.validatorId);
        }

        statement.setInt(4, this.askerId);

        if (this.volunteerId == null) {
            statement.setNull(5, Types.INTEGER);
        } else {
            statement.setInt(5, this.volunteerId);
        }

        statement.setString(6, this.status.toString());
        statement.setDate(7, this.createdAt);
        statement.setInt(8, this.duration);

        if (this.feedback == null) {
            statement.setNull(9, Types.VARCHAR);
        } else {
            statement.setString(9, this.feedback);
        }

        statement.setDate(10, this.date);
        statement.setInt(11, this.id);

        statement.execute();

        observers.forEach(observer -> observer.onRequestUpdate(this));
    }

    public static List<Request> getAll(int page, int perPage) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector().getConnection()
                .prepareStatement("SELECT * from requests order by created_at desc limit ? offset ?");

        statement.setInt(1, perPage);
        statement.setInt(2, page * perPage);
        statement.execute();

        ResultSet result = statement.getResultSet();

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
