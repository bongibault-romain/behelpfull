package lt.bongibau.behelpfull.database.seeders;

import com.github.javafaker.Faker;
import lt.bongibau.behelpfull.database.DatabaseManager;
import lt.bongibau.behelpfull.database.factories.UserFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Locale;

public class DatabaseSeeder {

    private static Faker faker = new Faker(new Locale("en-US"));


    public static void clear() {
        try {
            DatabaseManager.getInstance().getConnector().getConnection().createStatement().execute("DELETE FROM askers");
            DatabaseManager.getInstance().getConnector().getConnection().createStatement().execute("DELETE FROM volunteers");
            DatabaseManager.getInstance().getConnector().getConnection().createStatement().execute("DELETE FROM validators");
            DatabaseManager.getInstance().getConnector().getConnection().createStatement().execute("DELETE FROM users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void seed() throws SQLException {
        for (int i = 0; i < 79; i++) {
            UserFactory.createValidator(
                    faker.name().username(),
                    faker.internet().password()
            );

            UserFactory.createAsker(
                    faker.name().username(),
                    faker.internet().password(),
                    new Date(faker.date().birthday().getTime())
            );

            UserFactory.createVolunteer(
                    faker.name().username(),
                    faker.internet().password(),
                    new Date(faker.date().birthday().getTime()),
                    faker.bool().bool(),
                    faker.bool().bool()
            );
        }

        UserFactory.createValidator("validator", "validator");
    }

}
