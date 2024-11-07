package lt.bongibau.behelpfull.ui.authentication;

import lt.bongibau.behelpfull.authentication.Authentication;
import lt.bongibau.behelpfull.exceptions.CreateUserErrorException;
import lt.bongibau.behelpfull.exceptions.UserAlreadyExistsException;
import lt.bongibau.behelpfull.ui.FeedWindow;
import lt.bongibau.behelpfull.ui.Window;
import lt.bongibau.behelpfull.ui.WindowManager;
import lt.bongibau.behelpfull.users.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;

public class RegisterAsVolunteerWindow implements Window {

    private JFrame frame;

    public RegisterAsVolunteerWindow() {
        frame = new JFrame("Register As Validator");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JLabel hasDrivingLicenseLabel = new JLabel("Has driving license:");
        JCheckBox hasDrivingLicenseCheckBox = new JCheckBox();

        JLabel birthOnLabel = new JLabel("Birth on:");
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);

        JLabel hasPSC1Label = new JLabel("Has PSC1:");
        JCheckBox hasPSC1CheckBox = new JCheckBox();

        JLabel errorLabel = new JLabel("Unable to register as validator");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        JButton loginButton = new JButton("Register as Validator");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField
                    .getPassword());
            Date birthOn = new Date(((SpinnerDateModel) dateSpinner.getModel()).getDate().getTime());
            boolean hasDrivingLicense = hasDrivingLicenseCheckBox.isSelected();
            boolean hasPSC1 = hasPSC1CheckBox.isSelected();


            try {
                User user = Authentication.registerVolunteer(username, password, birthOn, hasDrivingLicense, hasPSC1);

                WindowManager.getInstance().setCurrentWindow(new FeedWindow(user));
            } catch (UserAlreadyExistsException | CreateUserErrorException |
                     SQLException ex) {
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

        panel.add(hasDrivingLicenseLabel);
        panel.add(hasDrivingLicenseCheckBox);

        panel.add(birthOnLabel);
        panel.add(dateSpinner);

        panel.add(hasPSC1Label);
        panel.add(hasPSC1CheckBox);

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
