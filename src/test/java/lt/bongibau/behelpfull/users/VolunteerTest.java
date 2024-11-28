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

public class VolunteerTest extends UserTest {
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
        return new Volunteer(id, username, password, new Date(0), true, false);
    }

    @Override
    @Test
    public void getById() throws SQLException {
        Volunteer volunteer = Volunteer.create(TEST_USERNAME, TEST_PASSWORD, new Date(0), true, false);

        assertNotNull(volunteer);
        assertEquals(TEST_USERNAME, volunteer.getUsername());
        assertEquals(TEST_PASSWORD, volunteer.getPassword());

        Volunteer gotValidator = Volunteer.getById(volunteer.getId());

        assertNotNull(gotValidator);
        assertEquals(volunteer.getId(), gotValidator.getId());
        assertEquals(TEST_USERNAME, gotValidator.getUsername());
        assertEquals(TEST_PASSWORD, gotValidator.getPassword());
    }

    @Override
    @Test
    public void getByUsername() throws SQLException {
        Volunteer volunteer = Volunteer.create(TEST_USERNAME, TEST_PASSWORD, new Date(0), true, false);

        assertNotNull(volunteer);
        assertEquals(TEST_USERNAME, volunteer.getUsername());
        assertEquals(TEST_PASSWORD, volunteer.getPassword());

        Volunteer gotValidator = Volunteer.getByUsername(TEST_USERNAME);

        assertNotNull(gotValidator);
        assertEquals(volunteer.getId(), gotValidator.getId());
        assertEquals(TEST_USERNAME, gotValidator.getUsername());
        assertEquals(TEST_PASSWORD, gotValidator.getPassword());
    }

    @Test
    public void getRequests() throws SQLException {
        Volunteer volunteer = Volunteer.create(TEST_USERNAME, TEST_PASSWORD, new Date(0), true, false);
        Asker asker = Asker.create("asker", "password", new Date(0));

        assertNotNull(asker);
        assertNotNull(volunteer);

        List<Request> requests = Arrays.asList(
                Request.create("title", "description", asker.getId(), null, new Date(0), 1),
                Request.create("title2", "description2", asker.getId(), null, new Date(0), 1),
                Request.create("title3", "description3", asker.getId(), null, new Date(0), 1),
                Request.create("title4", "description4", asker.getId(), null, new Date(0), 1)
        );

        for (Request request : requests) {
            volunteer.acceptRequest(request);

            assertEquals(volunteer.getId(), request.getVolunteerId());
            assertEquals(Status.ASSIGNED, request.getStatus());
        }

        List<Request> gotRequests = volunteer.getRequests();

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