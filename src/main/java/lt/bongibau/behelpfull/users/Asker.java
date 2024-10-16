package lt.bongibau.behelpfull.users;

import lt.bongibau.behelpfull.database.DatabaseManager;
import lt.bongibau.behelpfull.requests.Request;
import lt.bongibau.behelpfull.requests.Status;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

public class Asker extends User {

    private LocalDate dateDeNaissance;

    @Nullable
    private Integer validatorId;

    public Asker(int id, String username, String password, Date date, @Nullable Integer validatorId) {
        super(id, username, password);
        this.dateDeNaissance = date.toLocalDate();
        this.validatorId = validatorId;
    }

    public int getAge() {
        LocalDate today = LocalDate.now();
        if ((dateDeNaissance != null) && (dateDeNaissance.isBefore(today))) {
            return Period.between(dateDeNaissance, today).getYears();
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

    public Request createRequest(int id, String title, String description, Integer validatorId, Integer volunteerId, Status status, Date createdAt, int duration, String feedback, Date date) {
        Request request = Request.createRequest(id, title, description, validatorId, this.getId(), volunteerId, status, createdAt, duration, feedback, date);
    }
}
