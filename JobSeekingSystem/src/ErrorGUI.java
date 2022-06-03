import javax.swing.*;

public class ErrorGUI
{
    private JButton proceedButton;
    private JLabel errorMessage;
    private JLabel errorDetails;
    private JPanel errorTitle;


    public ErrorGUI()
    {
        JFrame frame = new JFrame("ErrorGUI");
        frame.setContentPane(new ErrorGUI().errorTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
