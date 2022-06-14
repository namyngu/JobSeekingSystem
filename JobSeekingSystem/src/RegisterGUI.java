import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JRadioButton radioButtonAdmin;
    private JTextField userEmailText;
    private JTextField userPhoneText;
    private JLabel emailLabel;
    private JLabel phoneLabel;
    private JComboBox locationSelect;
    private JSS program;

    private ArrayList<Location> locations;

    private Location userLocation;


    public RegisterGUI(JSS program)
    {
        this.locations = program.getLocationList();
        buildLocationSelector();
        this.program = program;
        JFrame frame = new JFrame("RegisterGUI");
        frame.setContentPane(this.registerPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();

        frame.setResizable(false);
        frame.setVisible(true);


        registerButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boolean allowRegistration = true;

                try
                {

                    if (this.checkBlanks() == true)
                    {
                    PromptGUI error = new PromptGUI("Error: Invalid input.","All fields must be complete and either JOBSEEKER or RECRUITER selected");
                    allowRegistration = false;
                    }
                    if (this.checkPassword() == false)
                    {
                        PromptGUI error = new PromptGUI("Password must be at least 8 characters long");
                        allowRegistration = false;
                    }
                }
                catch (Exception x)
                {
                    PromptGUI error = new PromptGUI("Couldn't validate", x.getMessage());
                    allowRegistration = false;
                }
                if (allowRegistration == true)
                {
                    try
                    {
                        if (radioButtonJobseeker.isSelected())
                        {
                            //TODO: need to check if username already exists.
                            program.createUser(firstNameText.getText(), lastNameText.getText(), usernameTextTextField.getText(), String.valueOf(passwordField.getPassword()), "Jobseeker", userLocation, userEmailText.getText(), userPhoneText.getText());
                            PromptGUI confirm = new PromptGUI("Account created! Log in to continue");
                            frame.dispose();

                        } else if (radioButtonRecruiter.isSelected())
                        {
                            //TODO: need to check if username already exists.
                            program.createUser(firstNameText.getText(), lastNameText.getText(), usernameTextTextField.getText(), String.valueOf(passwordField.getPassword()), "Recruiter",userLocation, userEmailText.getText(), userPhoneText.getText());
                            PromptGUI confirm = new PromptGUI("Account created! Log in to continue");
                            frame.dispose();
                        }
                        else if (radioButtonAdmin.isSelected())
                        {
                            //TODO: need to check if username already exists.
                            program.createUser(firstNameText.getText(), lastNameText.getText(), usernameTextTextField.getText(), String.valueOf(passwordField.getPassword()), "Admin",userLocation, userEmailText.getText(), userPhoneText.getText());
                            PromptGUI confirm = new PromptGUI("Account created! Log in to continue");
                            frame.dispose();
                        }
                    } catch (Exception x)
                    {
                        PromptGUI error = new PromptGUI("Error", x.getMessage());
                    }
                }
            }

            private boolean checkPassword()
            {
                boolean valid = true;
                if (passwordField.getPassword().length<8)
                {
                    valid = false;
                }
                return valid;
            }

            private Boolean checkBlanks()
            {
                boolean blank = false;

                if (firstNameText.getText().isEmpty()||lastNameText.getText().isEmpty()||usernameTextTextField.getText().isEmpty())
                {
                    blank = true;
                }
                else if (!radioButtonJobseeker.isSelected()&&!radioButtonRecruiter.isSelected()&&!radioButtonAdmin.isSelected())
                {
                    blank = true;
                }
               return blank;
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            frame.dispose();
                LoginGUI loginGUI = new LoginGUI(program);
            }
        });

        locationSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                userLocation = locations.get(locationSelect.getSelectedIndex());
            }
        });
    }

    public void buildLocationSelector()
    {
        for (int i = 0; i < locations.size(); i++)
        {
            locationSelect.addItem(locations.get(i).toString());
        }
    }


}
