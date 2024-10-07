package lt.bongibau.behelpfull;

import lt.bongibau.behelpfull.users.UserManager;

import java.sql.Date;
import java.sql.SQLException;

public class BeHelpfull {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello, BeHelpfull!");

        UserManager.getInstance().createAsker("bat", "mmll", new Date(1), null);
    }
}
