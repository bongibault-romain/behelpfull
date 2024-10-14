package lt.bongibau.behelpfull.authentication;

import lt.bongibau.behelpfull.exceptions.AuthenticationFailedException;
import lt.bongibau.behelpfull.exceptions.CreateAskerErrorException;
import lt.bongibau.behelpfull.exceptions.UserExistsException;
import lt.bongibau.behelpfull.users.Asker;
import lt.bongibau.behelpfull.users.User;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.sql.SQLException;

public class Authentication {

    public User login(String username, String password) throws AuthenticationFailedException {
        User user = null;
        try {
            user = User.getByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (user == null || !user.getPassword().equals(password)) {
            throw (new AuthenticationFailedException("Authentication Failed"));
        }

        return user;
    }

    public Asker registerAsker(String username, String password, Date date, @Nullable Integer validatorId) {
        User user = null;
        Asker asker = null;
        try {
            user = User.getByUsername(username);
            if (user != null) {
                throw (new UserExistsException("Already existing user"));
            }
            asker = Asker.create(username, password, date, validatorId);
            if (asker == null) {
                throw (new CreateAskerErrorException("Create Asker Error"));
            }
        } catch (SQLException | UserExistsException | CreateAskerErrorException e) {
            throw new RuntimeException(e);
        }

        return asker;
    }

    public Asker registerAsker(String username, String password, Date date) {
        return this.registerAsker(username, password, date, null);
    }

}
