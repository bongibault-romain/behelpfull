package lt.bongibau.behelpfull.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assumptions.*;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void getConnectionWithValidCredentials() {
        DatabaseConnector databaseConnector = new DatabaseConnector(
                DatabaseCredentials.HOST,
                DatabaseCredentials.PORT,
                DatabaseCredentials.DATABASE,
                DatabaseCredentials.USERNAME,
                DatabaseCredentials.PASSWORD
        );

        assertDoesNotThrow(databaseConnector::getConnection);
    }

    @Test
    public void getConnectionMultipleTimes() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector(
                DatabaseCredentials.HOST,
                DatabaseCredentials.PORT,
                DatabaseCredentials.DATABASE,
                DatabaseCredentials.USERNAME,
                DatabaseCredentials.PASSWORD
        );

        assertDoesNotThrow(databaseConnector::getConnection);
        assertEquals(databaseConnector.getConnection(), databaseConnector.getConnection());
    }

    @Test
    public void getConnectionWithInvalidCredentials() {
        DatabaseConnector databaseConnector = new DatabaseConnector("localhost", 3306, "test", "username", "password");

        assertThrows(SQLException.class, databaseConnector::getConnection);
    }

    @Test
    public void testGetters() {
        DatabaseConnector databaseConnector = new DatabaseConnector("localhost", 3306, "test", "username", "password");

        assertEquals("localhost", databaseConnector.getHost());
        assertEquals(3306, databaseConnector.getPort());
        assertEquals("test", databaseConnector.getDatabase());
        assertEquals("username", databaseConnector.getUsername());
        assertEquals("password", databaseConnector.getPassword());
    }
}