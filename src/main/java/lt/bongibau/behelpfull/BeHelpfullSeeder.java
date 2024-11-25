package lt.bongibau.behelpfull;

import lt.bongibau.behelpfull.database.seeders.DatabaseSeeder;

import java.sql.SQLException;

public class BeHelpfullSeeder {
    public static void main(String[] args) throws SQLException {
        DatabaseSeeder.clear();

        DatabaseSeeder.seed();
    }
}
