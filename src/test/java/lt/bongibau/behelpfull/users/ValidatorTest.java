package lt.bongibau.behelpfull.users;

import lt.bongibau.behelpfull.database.DatabaseManager;
import lt.bongibau.behelpfull.requests.Request;
import lt.bongibau.behelpfull.requests.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ValidatorTest extends UserTest {
    @BeforeEach
    void setUp() throws SQLException {
        super.setUp();

        PreparedStatement statement = DatabaseManager.getInstance().getConnector().getConnection()
                .prepareStatement("DELETE FROM requests");

        statement.execute();
    }

    @AfterEach
    void tearDown() throws SQLException {
        super.tearDown();

        PreparedStatement statement = DatabaseManager.getInstance().getConnector().getConnection()
                .prepareStatement("DELETE FROM requests");

        statement.execute();
    }

    @Override
    public User createUser(int id, String username, String password) {
        return new Validator(id, username, password);
    }

    @Override
    @Test
    public void getById() throws SQLException {
        Validator validator = Validator.create(TEST_USERNAME, TEST_PASSWORD);

        assertNotNull(validator);
        assertEquals(TEST_USERNAME, validator.getUsername());
        assertEquals(TEST_PASSWORD, validator.getPassword());

        Validator gotValidator = Validator.getById(validator.getId());

        assertNotNull(gotValidator);
        assertEquals(validator.getId(), gotValidator.getId());
        assertEquals(TEST_USERNAME, gotValidator.getUsername());
        assertEquals(TEST_PASSWORD, gotValidator.getPassword());
    }

    @Override
    @Test
    public void getByUsername() throws SQLException {
        Validator validator = Validator.create(TEST_USERNAME, TEST_PASSWORD);

        assertNotNull(validator);
        assertEquals(TEST_USERNAME, validator.getUsername());
        assertEquals(TEST_PASSWORD, validator.getPassword());

        Validator gotValidator = Validator.getByUsername(TEST_USERNAME);

        assertNotNull(gotValidator);
        assertEquals(validator.getId(), gotValidator.getId());
        assertEquals(TEST_USERNAME, gotValidator.getUsername());
        assertEquals(TEST_PASSWORD, gotValidator.getPassword());
    }

    @Test
    public void getAllValidators() throws SQLException {
        List<Validator> validators = Arrays.asList(
                Validator.create("username1", "password1"),
                Validator.create("username2", "password2"),
                Validator.create("username3", "password3"),
                Validator.create("username4", "password4")
        );

        List<Validator> allValidators = Validator.getAll();

        assertEquals(validators.size(), allValidators.size());

        for (int i = 0; i < validators.size(); i++) {
            Validator validator = validators.get(i);
            Validator allValidator = allValidators.get(i);

            assertEquals(validator.getId(), allValidator.getId());
            assertEquals(validator.getUsername(), allValidator.getUsername());
            assertEquals(validator.getPassword(), allValidator.getPassword());
        }
    }

    @Test
    public void acceptRequest() throws SQLException {
        Validator validator = Validator.create(TEST_USERNAME, TEST_PASSWORD);
        Asker asker = Asker.create("asker", "password", new Date(System.currentTimeMillis()));

        assertNotNull(validator);
        assertNotNull(asker);

        Request request = Request.create("title", "description", asker.getId(), validator.getId(), new Date(System.currentTimeMillis()), 10);

        assertNotNull(request);

        request.setStatus(Status.WAITING_FOR_APPROVAL);
        request.save();

        validator.validateRequest(request);

        assertEquals(Status.PUBLISHED, request.getStatus());
    }

    @Test
    public void refuseRequest() throws SQLException {
        Validator validator = Validator.create(TEST_USERNAME, TEST_PASSWORD);
        Asker asker = Asker.create("asker", "password", new Date(System.currentTimeMillis()));

        assertNotNull(validator);
        assertNotNull(asker);

        Request request = Request.create("title", "description", asker.getId(), validator.getId(), new Date(System.currentTimeMillis()), 10);

        assertNotNull(request);

        request.setStatus(Status.WAITING_FOR_APPROVAL);
        request.save();

        validator.refuseRequest(request, "reason");

        assertEquals(Status.BANNED_HAMMER, request.getStatus());
        assertEquals("reason", request.getFeedback());
    }

}