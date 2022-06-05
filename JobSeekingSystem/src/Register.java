import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame {

    private JSS program;

    private JTextField firstName;
    private JTextField lastName;
    private JPasswordField password;
    private JPasswordField userName;
    private JButton registerButton;
    private JRadioButton jobSeekerRadioButton;
    private JRadioButton recruiterRadioButton;

    public JPanel registerPanel;

    public Register()
    {

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("you clicked me");
            }
        });
    }

}
