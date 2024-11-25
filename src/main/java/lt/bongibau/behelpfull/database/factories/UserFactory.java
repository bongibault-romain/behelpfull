package lt.bongibau.behelpfull.database.factories;

import lt.bongibau.behelpfull.users.Asker;
import lt.bongibau.behelpfull.users.Validator;
import lt.bongibau.behelpfull.users.Volunteer;

import java.sql.Date;
import java.sql.SQLException;

public class UserFactory {

    public static Validator createValidator(String username, String password) throws SQLException {
        return Validator.create(username, password);
    }

    public static Asker createAsker(String username, String password, Date birthOn) throws SQLException {
        return Asker.create(username, password, birthOn);
    }

    public static Volunteer createVolunteer(String username, String password, Date birthOn, boolean hasDrivingLicense, boolean hasPSC1) throws SQLException {
        return Volunteer.create(username, password, birthOn, hasDrivingLicense, hasPSC1);
    }

}
