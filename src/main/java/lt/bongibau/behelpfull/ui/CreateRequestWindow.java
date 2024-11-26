package lt.bongibau.behelpfull.ui;

import lt.bongibau.behelpfull.requests.Request;
import lt.bongibau.behelpfull.users.Asker;
import lt.bongibau.behelpfull.users.User;
import lt.bongibau.behelpfull.users.Validator;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;

public class CreateRequestWindow extends JFrame implements Window {

    private final Asker user;

    public CreateRequestWindow(Asker user) {
        this.user = user;

        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Title");
        JTextField titleField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description");
        JTextArea descriptionArea = new JTextArea();

        JLabel durationLabel = new JLabel("Duration");
        JTextField durationField = new JTextField();

        JLabel validatorLabel = new JLabel("Validator (optional)");
        // select
        JComboBox<Validator> validatorComboBox = new JComboBox<>();
        // load all validators
        try {
            validatorComboBox.addItem(null);
            for (Validator validator : Validator.getAll()) {
                validatorComboBox.addItem(validator);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            Validator validator = (Validator) validatorComboBox.getSelectedItem();
            
            try {
                Request.create(
                        titleField.getText(),
                        descriptionArea.getText(),
                        user.getId(),
                        validator == null ? null : validator.getId(),
                        new Date(System.currentTimeMillis()),
                        Integer.parseInt(durationField.getText())
                );

                WindowManager.getInstance().setCurrentWindow(new FeedWindow(user));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to create request: " + ex.getMessage());
            }
        });

        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(descriptionLabel);
        panel.add(descriptionArea);
        panel.add(durationLabel);
        panel.add(durationField);
        panel.add(validatorLabel);
        panel.add(validatorComboBox);
        panel.add(createButton);

        this.add(panel);

        this.setVisible(true);
    }

    public User getUser() {
        return user;
    }
}
