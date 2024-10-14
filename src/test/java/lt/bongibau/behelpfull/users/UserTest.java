package lt.bongibau.behelpfull.users;

import lt.bongibau.behelpfull.database.DatabaseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class UserTest {

    public static String TEST_USERNAME = "username";

    public static String TEST_PASSWORD = "password";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseManager.getInstance().getConnector().getConnection()
                .prepareStatement("DELETE FROM users WHERE username = ?");

        statement.setString(1, TEST_USERNAME);

        statement.execute();
    }

    public abstract User createUser(int id, String username, String password);

    @Test
    public void createUserAndGetId() {
        assertDoesNotThrow(() -> {
            Integer userId = User.createAndGetId(TEST_USERNAME, TEST_PASSWORD);

            assertNotNull(userId);
        });
    }

    @Test
    public void getById() throws SQLException {
        Integer userId = User.createAndGetId(TEST_USERNAME, TEST_PASSWORD);

        assertNotNull(userId);

        User user = User.getById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals(TEST_USERNAME, user.getUsername());
        assertEquals(TEST_PASSWORD, user.getPassword());
    }

    @Test
    public void getByUsername() throws SQLException {
        Integer userId = User.createAndGetId(TEST_USERNAME, TEST_PASSWORD);

        assertNotNull(userId);

        User user = User.getByUsername(TEST_USERNAME);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals(TEST_USERNAME, user.getUsername());
        assertEquals(TEST_PASSWORD, user.getPassword());
    }

    @Test
    public void testGetters() {
        User user = this.createUser(10, TEST_USERNAME, TEST_PASSWORD);

        assertEquals(10, user.getId());
        assertEquals(TEST_USERNAME, user.getUsername());
        assertEquals(TEST_PASSWORD, user.getPassword());
    }

    @Test
    public void testSetters() {
        User user = this.createUser(10, TEST_USERNAME, TEST_PASSWORD);

        user.setId(20);
        user.setUsername("newUsername");
        user.setPassword("newPassword");

        assertEquals(20, user.getId());
        assertEquals("newUsername", user.getUsername());
        assertEquals("newPassword", user.getPassword());
    }
}