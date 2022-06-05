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
    private JButton loginButton;
    private JSS program;


    public RegisterGUI(JSS program)
    {

        this.program = program;
        JFrame frame = new JFrame("RegisterGUI");
        frame.setContentPane(this.registerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setResizable(false);
        frame.setVisible(true);


        registerButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try{
                    if (radioButtonJobseeker.isContentAreaFilled())
                    {
                        //TODO: need to check if username already exists.
                        program.createUser(firstNameText.getText(),lastNameText.getText(),usernameTextTextField.getText(),passwordField.getPassword(),"Jobseeker");

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
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginGUI loginGUI = new LoginGUI(program);
            }
        });
    }


}
