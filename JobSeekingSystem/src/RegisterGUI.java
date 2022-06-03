import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI
{
    private JPanel registerPanel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JLabel accountTypeLabel;
    private JPasswordField passwordField;
    private JTextField usernameTextTextField;
    private JButton registerButton;
    private JRadioButton radioButtonJobseeker;
    private JRadioButton radioButtonRecruiter;
    private JSS program;


    public RegisterGUI(JSS program)
    {
        this.program = program;
        JFrame frame = new JFrame("RegisterGUI");
        frame.setContentPane(this.registerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        registerButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try{
                    if (radioButtonJobseeker.isContentAreaFilled())
                    {
                        program.createJobseeker(firstNameText.getText(),lastNameText.getText(),usernameTextTextField.getText(),passwordField.getPassword());
                    }
                    else if (radioButtonRecruiter.isContentAreaFilled())
                    {
//                        User newRecruiter = new Recruiter();
                    }
                }
                catch (Exception x)
                {
                    System.out.println("Error - contact Admin with message: \n");
                    x.printStackTrace();
                }
            }
        });
    }


}
