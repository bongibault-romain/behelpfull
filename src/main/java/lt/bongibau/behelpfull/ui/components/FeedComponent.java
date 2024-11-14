package lt.bongibau.behelpfull.ui.components;

import lt.bongibau.behelpfull.requests.Request;
import lt.bongibau.behelpfull.users.User;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class FeedComponent extends JPanel {
    private final User user;

    private int page = 0;

    public FeedComponent(User user) {
        this.user = user;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel("Feed of " + user.getUsername() + ", here is the list of requests:"));

        try {
            List<Request> requests = Request.getAll(page, 20);

            for (Request request : requests) {
                this.add(new VolunteerRequestComponent(user, request));
            }
        } catch (SQLException e) {
            // Create popup
            JOptionPane.showMessageDialog(this, "Failed to load requests: " + e.getMessage());
        }
    }
}
