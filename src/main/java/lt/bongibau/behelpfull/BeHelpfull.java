package lt.bongibau.behelpfull;

import lt.bongibau.behelpfull.users.Asker;

import java.sql.Date;
import java.sql.SQLException;

public class BeHelpfull {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello, BeHelpfull!");

        Asker.create("bat", "mmll", new Date(1), null);
    }
}
