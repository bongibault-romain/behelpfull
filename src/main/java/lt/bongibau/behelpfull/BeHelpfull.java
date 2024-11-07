package lt.bongibau.behelpfull;

import lt.bongibau.behelpfull.ui.WindowManager;
import lt.bongibau.behelpfull.ui.authentication.ChooseAuthenticationMethodWindow;

import java.sql.SQLException;

public class BeHelpfull {
    public static void main(String[] args) throws SQLException {
        WindowManager.getInstance().start(new ChooseAuthenticationMethodWindow());
    }
}
