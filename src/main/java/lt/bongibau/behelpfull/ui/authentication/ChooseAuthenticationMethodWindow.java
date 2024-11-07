package lt.bongibau.behelpfull.ui.authentication;

import lt.bongibau.behelpfull.ui.Window;
import lt.bongibau.behelpfull.ui.WindowManager;

import javax.swing.*;
import java.awt.*;

public class ChooseAuthenticationMethodWindow implements Window {

    private JFrame frame;

    public ChooseAuthenticationMethodWindow() {
        frame = new JFrame("Login");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            WindowManager.getInstance().setCurrentWindow(new LoginWindow());
        });

        JButton registerButton = new JButton("Register as Asker");
        JButton registerValidatorButton = new JButton("Register as Validator");
        JButton regiserVolunteerButton = new JButton("Register as Volunteer");

        panel.add(loginButton);

        panel.add(registerButton);
        panel.add(registerValidatorButton);
        panel.add(regiserVolunteerButton);

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
