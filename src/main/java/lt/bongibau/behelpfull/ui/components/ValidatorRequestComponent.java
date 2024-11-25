package lt.bongibau.behelpfull.ui.components;

import lt.bongibau.behelpfull.requests.Request;
import lt.bongibau.behelpfull.users.User;

import javax.swing.*;

public class ValidatorRequestComponent extends JPanel {
    private final User user;
    private final Request request;

    public ValidatorRequestComponent(User user, Request request) {
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

        content.add(textComponent);
        content.add(approveButton);
        content.add(disapproveButton);

        this.add(requestLabel);
        this.add(content);
    }
}
