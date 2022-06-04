
/*
Registration GUI called by selecting register on the LoginGUI
Does not create users directly but calls method in JSS controller to do so
TODO validation
TODO fix radio buttons, both can be selected
TODO after registration return to login screen
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

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
    private JTextField usernameTextField;
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
                    String firstName = firstNameText.getText();
                    String lastName = lastNameText.getText();
                    String userName = usernameTextField.getText();
                    char[] password = passwordField.getPassword();
                    if (radioButtonJobseeker.isSelected())
                    {
                        program.createJobseeker(firstName,lastName,userName,password);
                    }
                    else if (radioButtonRecruiter.isSelected())
                    {
                        program.createRecruiter(firstName,lastName,userName,password);
                    }
                }
                catch (Exception x)
                {
                    System.out.println("Error - contact Admin with message: \n");
                    x.printStackTrace();
                }
            }
        });
        radioButtonJobseeker.addComponentListener(new ComponentAdapter()
        {
        });
        radioButtonJobseeker.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                radioButtonRecruiter.setSelected(false);
            }
        });


        radioButtonRecruiter.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                radioButtonJobseeker.setSelected(false);
            }
        });
    }


}
