package lt.bongibau.behelpfull.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductionDatabaseConnectorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void getConnectionWithValidCredentials() {
        Database productionDatabaseConnector = new TestDatabaseConnector(
                DatabaseCredentials.Test.FILE
        );

        assertDoesNotThrow(productionDatabaseConnector::getConnection);
    }

    @Test
    public void getConnectionMultipleTimes() throws SQLException {
        Database productionDatabaseConnector = new TestDatabaseConnector(
                DatabaseCredentials.Test.FILE
        );

        assertDoesNotThrow(productionDatabaseConnector::getConnection);
        assertEquals(productionDatabaseConnector.getConnection(), productionDatabaseConnector.getConnection());
    }
}