/**
 * This class is used to display a prompt to the user (such as an error message or instructions).
 * @author  Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromptGUI
{

    private JButton proceedButton;
    private JLabel messageLabel;
    private JLabel detailsLabel;
    private JPanel promptPanel;

    private String message;
    private String details;

    /**
     * This is the Default constructor for the class.
     */
    public PromptGUI()
    {
        JFrame frame = new JFrame("USER PROMPT");
        frame.setContentPane(this.promptPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.message = "Error!";
        this.details = "Please contact an Administrator.";
        frame.pack();
        frame.setVisible(true);
        proceedButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               frame.dispose();
            }
        });
    }

    /**
     * This is the Non-default constructor for the class.
     * @param message a String containing the error or warning message to be
     *                displayed on the prompt.
     */
    public PromptGUI(String message)
    {
        JFrame frame = new JFrame("USER PROMPT");
        frame.setContentPane(this.promptPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.message = message;
        this.messageLabel.setText(this.message);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(600, 100);

        proceedButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
            }
        });
    }

    /**
     * This is a Non-default constructor for the class.
     * @param message a String containing the error or warning message to be
     *                displayed on the prompt.
     * @param details a String containing error or warning message details to
     *                be displayed on the prompt.
     */
    public PromptGUI(String message, String details)
    {
        JFrame frame = new JFrame("USER PROMPT");
        frame.setContentPane(this.promptPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(600, 100);

        this.message = message;
        this.details = details;

        this.messageLabel.setText(this.message);
        this.detailsLabel.setText(this.details);
        this.messageLabel.setVisible(true);
        this.detailsLabel.setVisible(true);
        proceedButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
            }
        });
    }

    /**
     * This is the display method for the class.
     */
    public void display() {
        System.out.println("Prompt GUI: " + this);
        System.out.println("Message: " + message);
        System.out.println("Details: " + details);
    }

    // TODO: Setters and getters go here.

}
