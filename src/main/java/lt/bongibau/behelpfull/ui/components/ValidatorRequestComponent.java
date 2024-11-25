package lt.bongibau.behelpfull.ui.components;

import lt.bongibau.behelpfull.requests.Request;
import lt.bongibau.behelpfull.users.Validator;

import javax.swing.*;
import java.sql.SQLException;

public class ValidatorRequestComponent extends JPanel {
    private final Validator user;
    private final Request request;

    public ValidatorRequestComponent(Validator user, Request request) {
        this.user = user;
        this.request = request;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel requestLabel = new JLabel(request.getTitle());

        this.setBorder(
                BorderFactory.createLoweredBevelBorder()
        );

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));

        JTextArea textComponent = new JTextArea(request.getDescription());
        textComponent.setEditable(false);
        textComponent.setLineWrap(true);
        textComponent.setRows(2);

        JButton disapproveButton = new JButton("Disapprove");
        JButton approveButton = new JButton("Approve");

        approveButton.addActionListener(e -> {
            try {
                user.validateRequest(request);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Failed to validate request: " + ex.getMessage());
            }
        });

        disapproveButton.addActionListener(e -> {
            // ask for a reason in a dialog
            String reason = JOptionPane.showInputDialog(this, "Enter the reason for disapproving the request");

            try {
                user.refuseRequest(request, reason);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Failed to disapprove request: " + ex.getMessage());
            }
        });

        content.add(textComponent);
        content.add(approveButton);
        content.add(disapproveButton);

        this.add(requestLabel);
        this.add(content);
    }
}
