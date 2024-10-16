package lt.bongibau.behelpfull.users;

import lt.bongibau.behelpfull.database.DatabaseManager;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class Volunteer extends User {
    private boolean hasDriverLicense;
    private boolean hasPSC1;
    private LocalDate dateDeNaissance;

    public Volunteer(int id, String username, String password, Date date, boolean hasDriverLicense, boolean hasPSC1) {
        super(id, username, password);
        this.dateDeNaissance = date.toLocalDate();
        this.hasDriverLicense = hasDriverLicense;
        this.hasPSC1 = hasPSC1;
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
    public static Volunteer create(String username, String password, Date birthOn, boolean hasDrivingLicense, boolean hasPSC1) throws SQLException {
        Integer id = User.createAndGetId(username, password);
        if (id == null) return null;

        PreparedStatement askerInsertStatement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("INSERT INTO volunteers(user_id, has_driving_license, birth_on, has_psc1) VALUES (?,?,?,?)");

        askerInsertStatement.setInt(1, id);
        askerInsertStatement.setBoolean(2, hasDrivingLicense);
        askerInsertStatement.setDate(3, birthOn);
        askerInsertStatement.setBoolean(4, hasPSC1);

        askerInsertStatement.execute();

        return new Volunteer(
                id,
                username,
                password,
                birthOn,
                hasDrivingLicense,
                hasPSC1
        );
    }

    @Nullable
    public static Volunteer getById(int id) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM volunteers JOIN users ON users.id = volunteers.user_id WHERE user_id = ?");

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
    public static Volunteer getByUsername(String username) throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector()
                .getConnection()
                .prepareStatement("SELECT * FROM volunteers JOIN users ON users.id = volunteers.user_id WHERE username = ?");

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

    @Override
    public void save() throws SQLException {
        super.save();

        PreparedStatement statement = DatabaseManager.getInstance().getConnector().getConnection()
                .prepareStatement("UPDATE volunteers SET has_driving_license = ?, birth_on = ?, has_psc1 = ? WHERE user_id = ?");

        statement.setBoolean(1, this.hasDriverLicense);
        statement.setDate(2, Date.valueOf(this.dateDeNaissance));
        statement.setBoolean(3, this.hasPSC1);
        statement.setInt(4, this.getId());

        statement.execute();
    }
}
