/*
Error GUI to display message to user
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

//default constructor if no message sent.  TODO add appropriate message
    public PromptGUI()
    {
        JFrame frame = new JFrame("USER PROMPT");
        frame.setContentPane(this.promptPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.message = "";
        this.details = "";
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
//message only without stack trace to send to admin for situations the user should resolve
    //eg blank text where input required
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

    //detials added for admin to pass on to developer
    // such as stack trace
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
}
