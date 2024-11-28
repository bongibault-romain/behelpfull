package lt.bongibau.behelpfull.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabaseConnector implements Database {
    private Connection connection;

    private final String file;

    public TestDatabaseConnector(String file) {
        this.file = file;
    }

    private void createConnection() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:sqlite:" + this.file
        );
    }

    private void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
            this.connection = null;
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (this.connection == null) {
            this.createConnection();
        }

        return this.connection;
    }
}
