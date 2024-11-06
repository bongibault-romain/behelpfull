package lt.bongibau.behelpfull.users;

import lt.bongibau.behelpfull.database.DatabaseManager;
import lt.bongibau.behelpfull.requests.Request;
import lt.bongibau.behelpfull.requests.Status;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Asker extends User {

    private LocalDate birthOn;

    @Nullable
    private Integer validatorId;

    public Asker(int id, String username, String password, Date date, @Nullable Integer validatorId) {
        super(id, username, password);
        this.birthOn = date.toLocalDate();
        this.validatorId = validatorId;
    }

    public LocalDate getBirthOn() {
        return birthOn;
    }

    public int getAge() {
        LocalDate today = LocalDate.now();
        if ((birthOn != null) && (birthOn.isBefore(today))) {
            return Period.between(birthOn, today).getYears();
        } else {
            return 0; // si la date de naissance est incorrecte
        }
    }

    @Nullable
    public Integer getValidatorId() {
        return validatorId;
    }

    public void setValidatorId(@Nullable Integer validatorId) {
        this.validatorId = validatorId;
    }

    /**
     * Create a new Asker in the database
     *
     * @param username    username of the user
     * @param password    password of the user
     * @param birthOn     birthdate of the user
     * @param validatorId id of the validator
     * @return the created Asker, or null if an error occurred
     * @throws SQLException if an error occurs while creating the Asker
     */
    @Nullable
    public static Asker create(String username, String password, Date birthOn, @Nullable Integer validatorId) throws SQLException {
        Integer id = User.createAndGetId(username, password);
        if (id == null) return null;

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
                birthOn,
                validatorId
        );
    }

    @Nullable
    public static Asker getById(int id) throws SQLException {
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
    public static Asker getByUsername(String username) throws SQLException {
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

    public Request createRequest(String title, String description, Integer validatorId, int duration, Date date) {
        try {
            Request request = Request.createRequest(title, description, this.getId(), validatorId, date, duration);
            return request;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRequest(Request request) {
        try {
            request.deleteRequest();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: bouger dans la classe Volunteer
    public ArrayList<String> getFeedbacksOf(Volunteer volunteer) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM requests WHERE volunteer_id = ?");

        statement.setInt(1, volunteer.getId());

        ResultSet result = statement.executeQuery();

        ArrayList<String> feedbacksOf = new ArrayList<>();

        while (result.next()) {
            feedbacksOf.add(result.getString("feedback"));
        }

        return feedbacksOf;
    }

    public void giveFeedback(Request request, String feedback) {
        try {
            request.setFeedback(feedback);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save() throws SQLException {
        super.save();

        PreparedStatement statement = DatabaseManager.getInstance().getConnector().getConnection()
                .prepareStatement("UPDATE askers SET birth_on = ?, validator_id = ? WHERE user_id = ?");

        statement.setDate(1, Date.valueOf(birthOn));
        if (validatorId == null) {
            statement.setNull(2, Types.INTEGER);
        } else {
            statement.setInt(2, validatorId);
        }
        statement.setInt(3, this.getId());

        statement.execute();
    }

    public List<Request> getRequests() throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM requests WHERE asker_id = ?");

        statement.setInt(1, this.getId());

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
