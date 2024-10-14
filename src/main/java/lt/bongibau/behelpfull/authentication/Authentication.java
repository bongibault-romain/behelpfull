package lt.bongibau.behelpfull.authentication;

import lt.bongibau.behelpfull.exceptions.AuthenticationFailedException;
import lt.bongibau.behelpfull.exceptions.CreateUserErrorException;
import lt.bongibau.behelpfull.exceptions.UserAlreadyExistsException;
import lt.bongibau.behelpfull.users.Asker;
import lt.bongibau.behelpfull.users.User;
import lt.bongibau.behelpfull.users.Validator;
import lt.bongibau.behelpfull.users.Volunteer;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.sql.SQLException;

public class Authentication {

    public static User login(String username, String password) throws AuthenticationFailedException {
        User user = null;
        try {
            user = User.getByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (user == null || !user.getPassword().equals(password)) {
            throw (new AuthenticationFailedException("Username or password is incorrect"));
        }

        return user;
    }

    public static Asker registerAsker(String username, String password, Date birthOn, @Nullable Integer validatorId) throws UserAlreadyExistsException, CreateUserErrorException, SQLException {
        User user = User.getByUsername(username);

        if (user != null) {
            throw (new UserAlreadyExistsException("This username already exists"));
        }

        Asker asker = Asker.create(username, password, birthOn, validatorId);

        if (asker == null) {
            throw (new CreateUserErrorException("An error occurred while creating the user"));
        }

        return asker;
    }

    public static Asker registerAsker(String username, String password, Date birthOn) throws SQLException, UserAlreadyExistsException, CreateUserErrorException {
        return Authentication.registerAsker(username, password, birthOn, null);
    }

    public static Validator registerValidator(String username, String password) throws UserAlreadyExistsException, CreateUserErrorException, SQLException {
        User user = User.getByUsername(username);

        if (user != null) {
            throw (new UserAlreadyExistsException("This username already exists"));
        }

        Validator validator = Validator.create(username, password);

        if (validator == null) {
            throw (new CreateUserErrorException("An error occurred while creating the user"));
        }

        return validator;
    }

    public static Volunteer registerVolunteer(String username, String password, Date birthOn, boolean hasDrivingLicense, boolean hasPSC1) throws UserAlreadyExistsException, CreateUserErrorException, SQLException {
        User user = User.getByUsername(username);

        if (user != null) {
            throw (new UserAlreadyExistsException("This username already exists"));
        }

        Volunteer volunteer = Volunteer.create(username, password, birthOn, hasDrivingLicense, hasPSC1);

        if (volunteer == null) {
            throw (new CreateUserErrorException("An error occurred while creating the user"));
        }

        return volunteer;
    }
}
