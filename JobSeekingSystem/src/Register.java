import javax.swing.*;

public class Register {

    private JSS program;

    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JPasswordField usernamePasswordField;
    private JButton registerButton;
    private JRadioButton jobSeekerRadioButton;
    private JRadioButton recruiterRadioButton;

    public JPanel registerPanel;

    public Register()
    {

    }

    public Register(JSS program)
    {
        this.program = program;
        JFrame frame2 = new JFrame("Register");
        frame2.setContentPane(this.registerPanel);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.pack();
        frame2.setVisible(true);
    }

}
