package lt.bongibau.behelpfull;

import lt.bongibau.behelpfull.ui.LoginWindow;

import javax.swing.*;
import java.sql.SQLException;

public class BeHelpfull {
    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();

            loginWindow.show();
        });
    }
}
