package lt.bongibau.behelpfull.users;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest extends UserTest {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Override
    public User createUser(int id, String username, String password) {
        return new Validator(id, username, password);
    }

    @Override
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

    @Override
    @Test
    public void getByUsername() throws SQLException {
        User user = Validator.getByUsername(TEST_USERNAME);

        assertNotNull(user);
        assertEquals(TEST_USERNAME, user.getUsername());
        assertEquals(TEST_PASSWORD, user.getPassword());
        assertInstanceOf(Validator.class, user);
    }
}