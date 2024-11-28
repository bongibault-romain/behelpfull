package lt.bongibau.behelpfull.users;

import lt.bongibau.behelpfull.database.DatabaseManager;
import lt.bongibau.behelpfull.requests.Request;
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

public class AskerTest extends UserTest {
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
        return new Asker(id, username, password, new Date(0));
    }

    @Override
    @Test
    public void getById() throws SQLException {
        Asker asker = Asker.create(TEST_USERNAME, TEST_PASSWORD, new Date(0));

        assertNotNull(asker);
        assertEquals(TEST_USERNAME, asker.getUsername());
        assertEquals(TEST_PASSWORD, asker.getPassword());

        Asker gotValidator = Asker.getById(asker.getId());

        assertNotNull(gotValidator);
        assertEquals(asker.getId(), gotValidator.getId());
        assertEquals(TEST_USERNAME, gotValidator.getUsername());
        assertEquals(TEST_PASSWORD, gotValidator.getPassword());
        assertEquals(asker.getBirthOn(), gotValidator.getBirthOn());
    }

    @Override
    @Test
    public void getByUsername() throws SQLException {
        Asker asker = Asker.create(TEST_USERNAME, TEST_PASSWORD, new Date(0));

        assertNotNull(asker);
        assertEquals(TEST_USERNAME, asker.getUsername());
        assertEquals(TEST_PASSWORD, asker.getPassword());

        Asker gotValidator = Asker.getByUsername(TEST_USERNAME);

        assertNotNull(gotValidator);
        assertEquals(asker.getId(), gotValidator.getId());
        assertEquals(TEST_USERNAME, gotValidator.getUsername());
        assertEquals(TEST_PASSWORD, gotValidator.getPassword());
        assertEquals(asker.getBirthOn(), gotValidator.getBirthOn());
    }

    @Test
    public void getRequests() throws SQLException {
        Asker asker = Asker.create(TEST_USERNAME, TEST_PASSWORD, new Date(0));

        assertNotNull(asker);

        List<Request> requests = Arrays.asList(
                Request.create("title", "description", asker.getId(), null, new Date(0), 1),
                Request.create("title2", "description2", asker.getId(), null, new Date(0), 1),
                Request.create("title3", "description3", asker.getId(), null, new Date(0), 1),
                Request.create("title4", "description4", asker.getId(), null, new Date(0), 1)
        );

        List<Request> gotRequests = asker.getRequests();

        assertEquals(requests.size(), gotRequests.size());

        for (int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);
            Request gotRequest = gotRequests.get(i);

            assertEquals(request.getId(), gotRequest.getId());
            assertEquals(request.getTitle(), gotRequest.getTitle());
            assertEquals(request.getDescription(), gotRequest.getDescription());
            assertEquals(request.getValidatorId(), gotRequest.getValidatorId());
            assertEquals(request.getAskerId(), gotRequest.getAskerId());
            assertEquals(request.getVolunteerId(), gotRequest.getVolunteerId());
            assertEquals(request.getStatus(), gotRequest.getStatus());
        }
    }
}