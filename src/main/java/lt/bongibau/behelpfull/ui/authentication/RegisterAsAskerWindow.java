package lt.bongibau.behelpfull.ui.authentication;

import lt.bongibau.behelpfull.authentication.Authentication;
import lt.bongibau.behelpfull.exceptions.AuthenticationFailedException;
import lt.bongibau.behelpfull.ui.Window;
import lt.bongibau.behelpfull.ui.WindowManager;

import javax.swing.*;
import java.awt.*;

public class RegisterAsAskerWindow implements Window {

    private JFrame frame;

    public RegisterAsAskerWindow() {
        frame = new JFrame("Register As Asker");
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

        JButton loginButton = new JButton("Register");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField
                    .getPassword());

            try {
                Authentication.login(username, password);

                // TODO: un truc
            } catch (AuthenticationFailedException ex) {
                errorLabel.setText(ex.getMessage());
                errorLabel.setVisible(true);
            }
        });

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            WindowManager.getInstance().setCurrentWindow(new ChooseAuthenticationMethodWindow());
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);

        panel.add(passwordField);
        panel.add(errorLabel);
        panel.add(back);
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