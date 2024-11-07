package lt.bongibau.behelpfull.ui;

import lt.bongibau.behelpfull.users.User;

import javax.swing.*;
import java.awt.*;

public class FeedWindow implements Window {
    private final User user;

    private JFrame frame;

    public FeedWindow(User user) {
        this.user = user;
        frame = new JFrame("Feed of " + user.getUsername());
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));


        frame.add(panel);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

    public void dispose() {
        frame.dispose();
    }

    public User getUser() {
        return user;
    }
}
