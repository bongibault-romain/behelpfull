package lt.bongibau.behelpfull.database;

public class DatabaseManager {

    public static enum Environment {
        PRODUCTION,
        TEST
    }

    private static DatabaseManager instance;

    private final Database productionConnector;

    private final Database testConnector;

    public DatabaseManager() {
        this.productionConnector = new ProductionDatabaseConnector(
                DatabaseCredentials.Production.HOST,
                DatabaseCredentials.Production.PORT,
                DatabaseCredentials.Production.DATABASE,
                DatabaseCredentials.Production.USERNAME,
                DatabaseCredentials.Production.PASSWORD
        );

        this.testConnector = new TestDatabaseConnector(DatabaseCredentials.Test.FILE);
    }

    public Database getConnector() {
        return getConnector(Environment.PRODUCTION);
    }

    public Database getConnector(Environment environment) {
        return switch (environment) {
            case PRODUCTION -> productionConnector;
            case TEST -> testConnector;
        };
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }
}
