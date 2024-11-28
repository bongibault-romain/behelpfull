package lt.bongibau.behelpfull.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProductionDatabaseConnector implements Database {
    private Connection connection;

    private final String host;

    private final int port;

    private final String database;

    private final String username;

    private final String password;

    public ProductionDatabaseConnector(String host, int port, String database, String username, String password) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    private void createConnection() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://" + this.host + "/" + this.database,
                this.username,
                this.password
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

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
