package lt.bongibau.behelpfull.ui;

import lt.bongibau.behelpfull.ui.components.FeedComponent;
import lt.bongibau.behelpfull.users.User;

import javax.swing.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class FeedWindow implements Window, AdjustmentListener {
    private final User user;

    private JFrame frame;

    public FeedWindow(User user) {
        this.user = user;
        frame = new JFrame("Feed of " + user.getUsername());
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JScrollPane scrollBar = new JScrollPane(new FeedComponent(user));
        scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollBar);

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

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        System.out.println("Adjustment value changed");
    }
}
