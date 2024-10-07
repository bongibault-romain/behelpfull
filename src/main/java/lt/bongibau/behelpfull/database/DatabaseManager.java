package lt.bongibau.behelpfull.database;

public class DatabaseManager {

    private static DatabaseManager instance;

    private final DatabaseConnector connector;

    public DatabaseManager() {
        this.connector = new DatabaseConnector(
                DatabaseCredentials.HOST,
                DatabaseCredentials.PORT,
                DatabaseCredentials.DATABASE,
                DatabaseCredentials.USERNAME,
                DatabaseCredentials.PASSWORD
        );
    }

    public DatabaseConnector getConnector() {
        return connector;
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }
}
