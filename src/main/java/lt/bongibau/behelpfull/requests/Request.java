package lt.bongibau.behelpfull.requests;

import lt.bongibau.behelpfull.database.DatabaseManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class Request {


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

    public static Request createRequest(String title, String description, int askerId, @Nullable Integer validatorId, @Nullable Integer volunteerId, @NotNull Date date, int duration) throws SQLException {

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
        requestInsertStatement.setString(5, Status.IN_WAITING.toString());
        requestInsertStatement.setInt(6, duration);
        requestInsertStatement.setDate(7, date);

        requestInsertStatement.execute();

        ResultSet result = requestInsertStatement.getGeneratedKeys();

        if (!result.next()) return null;

        return new Request(
                result.getInt(1),
                title,
                description,
                validatorId,
                askerId,
                result.getInt(6),
                Status.IN_WAITING,
                result.getDate(8),
                duration,
                null,
                date
        );
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

    public Integer getVolunteerId() {
        return volunteerId;
    }

    public Integer getValidatorId() {
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

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
