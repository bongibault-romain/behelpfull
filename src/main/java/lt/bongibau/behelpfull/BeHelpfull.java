package lt.bongibau.behelpfull;

import lt.bongibau.behelpfull.ui.panels.RequestsPanel;

import javax.swing.*;

public class BeHelpfull {
    public static void main(String[] args) {
        System.out.println("Hello, BeHelpfull!");

        JFrame frame = new JFrame("BeHelpfull");

        frame.setContentPane(new RequestsPanel());

        frame.setSize(200, 200);

        frame.setVisible(true);
    }
}
