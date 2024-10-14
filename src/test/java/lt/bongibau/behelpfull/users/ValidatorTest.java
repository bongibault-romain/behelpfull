package lt.bongibau.behelpfull.users;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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
}