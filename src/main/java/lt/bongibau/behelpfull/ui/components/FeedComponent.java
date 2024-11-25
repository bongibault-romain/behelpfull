package lt.bongibau.behelpfull.ui.components;

import lt.bongibau.behelpfull.requests.Request;
import lt.bongibau.behelpfull.ui.CreateRequestWindow;
import lt.bongibau.behelpfull.ui.WindowManager;
import lt.bongibau.behelpfull.users.Asker;
import lt.bongibau.behelpfull.users.User;
import lt.bongibau.behelpfull.users.Validator;
import lt.bongibau.behelpfull.users.Volunteer;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class FeedComponent extends JPanel implements Request.Observer {
    private final User user;

    private int page = 0;

    public FeedComponent(User user) {
        this.user = user;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Request.addObserver(this);
        this.update();
    }

    public void update() {
        this.removeAll();
        
        if (user instanceof Asker) {
            JButton createRequestButton = new JButton("Create request");
            createRequestButton.addActionListener(e -> {
                WindowManager.getInstance().setCurrentWindow(new CreateRequestWindow((Asker) user));
            });
            this.add(createRequestButton);
        }

        this.add(new JLabel("Feed of " + user.getUsername() + ", here is the list of requests:"));

        try {
            if (user instanceof Asker) {
                List<Request> requests = ((Asker) user).getRequests();

                for (Request request : requests) {
                    this.add(new AskerRequestComponent(user, request));
                }
            }

            if (user instanceof Validator) {
                List<Request> requests = ((Validator) user).getRequests();

                for (Request request : requests) {
                    this.add(new ValidatorRequestComponent((Validator) user, request));
                }
            }

            if (user instanceof Volunteer) {
                List<Request> requests = ((Volunteer) user).getRequests();

                this.add(new JLabel("My requests:"));

                for (Request request : requests) {
                    this.add(new VolunteerRequestComponent(user, request));
                }
            }
        } catch (SQLException e) {
            // Create popup
            JOptionPane.showMessageDialog(this, "Failed to load requests: " + e.getMessage());
        }
    }

    @Override
    public void onRequestCreate(Request request) {
        this.update();

    }

    @Override
    public void onRequestUpdate(Request request) {
        this.update();

    }
}
