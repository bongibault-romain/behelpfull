package lt.bongibau.behelpfull.ui;

import lt.bongibau.behelpfull.authentication.Authentication;
import lt.bongibau.behelpfull.exceptions.AuthenticationFailedException;

import javax.swing.*;
import java.awt.*;

public class LoginWindow {

    private JFrame frame;

    public LoginWindow() {
        frame = new JFrame("Login");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JLabel errorLabel = new JLabel("Invalid username or password");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField
                    .getPassword());

            try {
                Authentication.login(username, password);

                this.dispose();
            } catch (AuthenticationFailedException ex) {
                errorLabel.setText(ex.getMessage());
                errorLabel.setVisible(true);
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);

        panel.add(passwordField);
        panel.add(errorLabel);
        panel.add(loginButton);

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
