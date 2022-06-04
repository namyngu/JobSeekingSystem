/*
Error GUI to display message to user
 */

import javax.swing.*;

public class ErrorGUI
{

    private JButton proceedButton;
    private JLabel messageLabel;
    private JLabel detailsLabel;
    private JPanel errorPanel;

    private String message;
    private String details;

//default constructor if no message sent.  TODO add appropriate message
    public ErrorGUI()
    {
        JFrame frame = new JFrame("ErrorGUI");
        frame.setContentPane(this.errorPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.message = "";
        this.details = "";
    }
//message only without stack trace to send to admin for situations the user should resolve
    //eg blank text where input required
    public ErrorGUI(String message)
    {
        JFrame frame = new JFrame("ErrorGUI");
        frame.setContentPane(this.errorPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.message = message;
        this.messageLabel.setText(this.message);
    }

    //detials added for admin to pass on to developer
    // such as stack trace
    public ErrorGUI(String message, String details)
    {
        JFrame frame = new JFrame("ErrorGUI");
        frame.setContentPane(this.errorPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        this.message = message;
        this.details = details;

        this.messageLabel.setText(this.message);
        this.detailsLabel.setText("Please send the following to Admin: " + this.details);
        this.messageLabel.setVisible(true);
        this.detailsLabel.setVisible(true);
    }
}
