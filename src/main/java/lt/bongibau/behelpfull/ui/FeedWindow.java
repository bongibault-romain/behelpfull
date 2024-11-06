package lt.bongibau.behelpfull.ui;

import javax.swing.*;
import java.awt.*;

public class FeedWindow {
    private JFrame frame;

    public FeedWindow() {
        frame = new JFrame("Login");
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
}
