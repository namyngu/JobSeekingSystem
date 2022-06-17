/**
 * This class is the GUI form for when a User is registering a new account.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

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
    private JLabel fnameWarning;
    private JLabel lnameWarning;
    private JLabel emailWarning;
    private JLabel phoneWarning;
    private JLabel usernameWarning;
    private JLabel passwordWarning;
    private JSS program;
    private ArrayList<Location> locations;
    private Location userLocation;

    /**
     * This is the Default constructor for the class.
     */
    public RegisterGUI() {
        program = new JSS();
        locations = new ArrayList<>();
        userLocation = new Location();
    }

    /**
     * This is a Non-default constructor for the class.
     * @param program a JSS program which represents the main system.
     */
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
                JTextField[] inputs = {firstNameText, lastNameText, userEmailText, userPhoneText, usernameTextTextField,passwordField};
                JLabel[] warningLabels = {fnameWarning,lnameWarning,emailWarning,phoneWarning, usernameWarning, passwordWarning};
                JLabel[] inputLabels = {firstNameLabel,lastNameLabel,emailLabel,phoneLabel,userNameLabel, passwordLabel};
                //input validation
                if(!Validation.validInputs(inputs, warningLabels, inputLabels) || !this.validPassword())
                {
                    allowRegistration = false;
                }

                //email validation
                allowRegistration = Validation.isValidEmail(userEmailText.getText(), emailWarning);

                //check if username exists
                if(Validation.usernameIndex(program.getUserList(), usernameTextTextField.getText()) >= 0)
                {
                    Validation.invalidInputWarning(usernameWarning, "That username already exists");
                    allowRegistration = false;
                }

                if (allowRegistration)
                {
                    try
                    {
                        if (radioButtonJobseeker.isSelected())
                        {
                            program.createUser(firstNameText.getText(), lastNameText.getText(), usernameTextTextField.getText(), String.valueOf(passwordField.getPassword()), "Jobseeker", userLocation, userEmailText.getText(), userPhoneText.getText());
                            PromptGUI confirm = new PromptGUI("Account created! Log in to continue");
                            frame.dispose();

                        } else if (radioButtonRecruiter.isSelected())
                        {
                            program.createUser(firstNameText.getText(), lastNameText.getText(), usernameTextTextField.getText(), String.valueOf(passwordField.getPassword()), "Recruiter",userLocation, userEmailText.getText(), userPhoneText.getText());
                            PromptGUI confirm = new PromptGUI("Account created! Log in to continue");
                            frame.dispose();
                        }
                        else if (radioButtonAdmin.isSelected())
                        {
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

            private boolean validPassword()
            {
                boolean valid = true;
                if (passwordField.getPassword().length<8)
                {
                    Validation.invalidInputWarning(passwordWarning,  "Password must 8 or more characters");
                    valid = false;
                }
                return valid;
            }

            //method that loops through all inputs (excluding location & radio), if any are empty a warning message is displayed

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

    /**
     * This method loads  all the Locations from the system into the locationSelect
     * Combo Box.
     */
    public void buildLocationSelector()
    {
        for (Location location : locations) {
            locationSelect.addItem(location.toString());
        }
    }

    /**
     * This is the display method for this class.
     */
    public void display(){
        System.out.println("Register GUI: " + this);
    }

    /**
     * This is the Accessor method for the accountTypeLabel field.
     * @return a JLabel describing the account type.
     */
    public JLabel getAccountTypeLabel() {
        return accountTypeLabel;
    }

    /**
     * This is the Accessor method for the emailLabel field.
     * @return a JLabel describing the email address.
     */
    public JLabel getEmailLabel() {
        return emailLabel;
    }

    /**
     * This is the Accessor method for the emailWarning field.
     * @return a JLabel containing the validation warning for the email address.
     */
    public JLabel getEmailWarning() {
        return emailWarning;
    }

    /**
     * This is the Accessor method for the firstNameLabel field.
     * @return a JLabel containing the first name label.
     */
    public JLabel getFirstNameLabel() {
        return firstNameLabel;
    }

    /**
     * This is the Accessor method for the firstNameText field.
     * @return a JTextField containing the User's entered first name.
     */
    public JTextField getFirstNameText() {
        return firstNameText;
    }

    /**
     * This is the Accessor method for the fnameWarning field.
     * @return a JLabel containing the validation warning for the first name.
     */
    public JLabel getFnameWarning() {
        return fnameWarning;
    }

    /**
     * This is the Accessor method for the lastNameLabel field.
     * @return a JLabel containing the last name label.
     */
    public JLabel getLastNameLabel() {
        return lastNameLabel;
    }

    /**
     * This is the Accessor method for the lastNameText field.
     * @return a JTextField containing the User's entered last name.
     */
    public JTextField getLastNameText() {
        return lastNameText;
    }

    /**
     * This is the Accessor method for the lnameWarning field.
     * @return a JLabel containing the warning for the last name.
     */
    public JLabel getLnameWarning() {
        return lnameWarning;
    }

    /**
     * This is the Accessor method for the locationSelect field.
     * @return a JComboBox used to select the User's Location.
     */
    public JComboBox getLocationSelect() {
        return locationSelect;
    }

    /**
     * This is the Accessor method for the locations field.
     * @return an ArrayList of Locations in the system.
     */
    public ArrayList<Location> getLocations() {
        return locations;
    }

    /**
     * This is the Accessor method for the loginButton field.
     * @return a JButton used to login to the system.
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * This is the Accessor method for the passwordField field.
     * @return a JPasswordField used to get the User's password.
     */
    public JPasswordField getPasswordField() {
        return passwordField;
    }

    /**
     * This is the Accessor method for the passwordLabel field.
     * @return a JLabel containing the password label.
     */
    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    /**
     * This is the Accessor method for the passwordWarning field.
     * @return a JLabel containing the validation warning for the password.
     */
    public JLabel getPasswordWarning() {
        return passwordWarning;
    }

    /**
     * This is the Accessor method for the phoneLabel field.
     * @return a JLabel containing the label for the phone number.
     */
    public JLabel getPhoneLabel() {
        return phoneLabel;
    }

    /**
     * This is the Accessor method for the phoneWarning field.
     * @return a JLabel for the validation warning for the phone number.
     */
    public JLabel getPhoneWarning() {
        return phoneWarning;
    }

    /**
     * This is the Accessor method for the program field.
     * @return a JSS Object representing the system.
     */
    public JSS getProgram() {
        return program;
    }

    /**
     * This is the Accessor method for the radioButtonAdmin field.
     * @return a JRadioButton describing if the User is an admin.
     */
    public JRadioButton getRadioButtonAdmin() {
        return radioButtonAdmin;
    }

    /**
     * This is the Accessor method for the radioButtonJobseeker field.
     * @return a JRadioButton describing if the User is a Jobseeker.
     */
    public JRadioButton getRadioButtonJobseeker() {
        return radioButtonJobseeker;
    }

    /**
     * This is the Accessor method for the radioButtonRecruiter field.
     * @return a JRadioButton describing if the User is a Recruiter.
     */
    public JRadioButton getRadioButtonRecruiter() {
        return radioButtonRecruiter;
    }

    /**
     * This is the Accessor method for the registerButton field.
     * @return a JButton used to open the register GUI.
     */
    public JButton getRegisterButton() {
        return registerButton;
    }

    /**
     * This is the Accessor method for the registerPanel field.
     * @return a JPanel containing the register panel.
     */
    public JPanel getRegisterPanel() {
        return registerPanel;
    }

    /**
     * This is the Accessor method for the userEmailText field.
     * @return a JTextField containing the User's entered email address.
     */
    public JTextField getUserEmailText() {
        return userEmailText;
    }

    /**
     * This is the Accessor method for the userLocation field.
     * @return a Location which is the User's chosen Location.
     */
    public Location getUserLocation() {
        return userLocation;
    }

    /**
     * This is the Accessor method for the userNameLabel field.
     * @return a JLabel containing the username label.
     */
    public JLabel getUserNameLabel() {
        return userNameLabel;
    }

    /**
     * This is the Accessor method for the userPhoneText field.
     * @return a JTextField containing the User's entered phone number.
     */
    public JTextField getUserPhoneText() {
        return userPhoneText;
    }

    /**
     * This is the Accessor method for the usernameTextTextField field.
     * @return a JTextField containing the User's entered username.
     */
    public JTextField getUsernameTextTextField() {
        return usernameTextTextField;
    }

    /**
     * This is the Accessor method for the usernameWarning field.
     * @return a JLabel containing the validation warning for the username.
     */
    public JLabel getUsernameWarning() {
        return usernameWarning;
    }

    /**
     * This is the Mutator method for the accountTypeLabel field.
     * @param accountTypeLabel a JLabel for the account type.
     */
    public void setAccountTypeLabel(JLabel accountTypeLabel) {
        this.accountTypeLabel = accountTypeLabel;
    }

    /**
     * This is the Mutator method for the emailLabel field.
     * @param emailLabel a JLabel for the email address.
     */
    public void setEmailLabel(JLabel emailLabel) {
        this.emailLabel = emailLabel;
    }

    /**
     * This is the Mutator method for the emailWarning field.
     * @param emailWarning a JLabel containing the validation warning for the
     *                     email address.
     */
    public void setEmailWarning(JLabel emailWarning) {
        this.emailWarning = emailWarning;
    }

    /**
     * This is the Mutator method for the firstNameLabel field.
     * @param firstNameLabel a JLabel for the first name.
     */
    public void setFirstNameLabel(JLabel firstNameLabel) {
        this.firstNameLabel = firstNameLabel;
    }

    /**
     * This is the Mutator method for the firstNameText field.
     * @param firstNameText a JTextField for the User to enter in their first name.
     */
    public void setFirstNameText(JTextField firstNameText) {
        this.firstNameText = firstNameText;
    }

    /**
     * This is the Mutator method for the fnameWarning field.
     * @param fnameWarning a JLabel containing the validation warning for the
     *                     first name.
     */
    public void setFnameWarning(JLabel fnameWarning) {
        this.fnameWarning = fnameWarning;
    }

    /**
     * This is the Mutator method for the lastNameLabel field.
     * @param lastNameLabel a JLabel containing the last name label.
     */
    public void setLastNameLabel(JLabel lastNameLabel) {
        this.lastNameLabel = lastNameLabel;
    }

    /**
     * This is the Mutator method for the lastNameText field.
     * @param lastNameText a JTextField for the User to enter their last name.
     */
    public void setLastNameText(JTextField lastNameText) {
        this.lastNameText = lastNameText;
    }

    /**
     * This is the Mutator method for the lnameWarning field.
     * @param lnameWarning a JLabel containing the validation warning for
     *                     last name.
     */
    public void setLnameWarning(JLabel lnameWarning) {
        this.lnameWarning = lnameWarning;
    }

    /**
     * This is the Mutator method for the locationSelect field.
     * @param locationSelect a JComboBox used for the Location select.
     */
    public void setLocationSelect(JComboBox locationSelect) {
        this.locationSelect = locationSelect;
    }

    /**
     * This is the Mutator methid for the locations field.
     * @param locations an ArrayList of Locations in the system.
     */
    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    /**
     * This is the Mutator method for the loginButton field.
     * @param loginButton a JButton the User can use to login.
     */
    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    /**
     * This is the Mutator method for the passwordField field.
     * @param passwordField a JPasswordField used to collect the User's password.
     */
    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    /**
     * This is the Mutator method for the passwordLabel field.
     * @param passwordLabel a JLabel for the password.
     */
    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    /**
     * This is the Mutator method for the passwordWarning field.
     * @param passwordWarning a JLabel for the validation warning for the password.
     */
    public void setPasswordWarning(JLabel passwordWarning) {
        this.passwordWarning = passwordWarning;
    }

    /**
     * This is the Mutator method for the phoneLabel field.
     * @param phoneLabel a JLabel for the phone number.
     */
    public void setPhoneLabel(JLabel phoneLabel) {
        this.phoneLabel = phoneLabel;
    }

    /**
     * This is the Mutator method for the phoneWarning field.
     * @param phoneWarning a JLabel containing the validation warning for the
     *                     phone number.
     */
    public void setPhoneWarning(JLabel phoneWarning) {
        this.phoneWarning = phoneWarning;
    }

    /**
     * This is the Mutator method for the program field.
     * @param program a JSS Object describing the main system.
     */
    public void setProgram(JSS program) {
        this.program = program;
    }

    /**
     * This is the Mutator method for the radioButtonAdmin field.
     * @param radioButtonAdmin a JRadioButton to be used if the User is an Admin.
     */
    public void setRadioButtonAdmin(JRadioButton radioButtonAdmin) {
        this.radioButtonAdmin = radioButtonAdmin;
    }

    /**
     * This is the Mutator method for the radioButtonJobseeker field.
     * @param radioButtonJobseeker a JRadioButton to be used if the User is a
     *                             Jobseeker.
     */
    public void setRadioButtonJobseeker(JRadioButton radioButtonJobseeker) {
        this.radioButtonJobseeker = radioButtonJobseeker;
    }

    /**
     * This is the Mutator method for the radioButtonRecruiter field.
     * @param radioButtonRecruiter a JRadioButton to be used if the User is a
     *                             Recruiter.
     */
    public void setRadioButtonRecruiter(JRadioButton radioButtonRecruiter) {
        this.radioButtonRecruiter = radioButtonRecruiter;
    }

    /**
     * This is the Mutator method for the registerButton field.
     * @param registerButton a JButton the User can use to open the register GUI.
     */
    public void setRegisterButton(JButton registerButton) {
        this.registerButton = registerButton;
    }

    /**
     * This is the Mutator method for the registerPanel field.
     * @param registerPanel a JPanel containing the register GUI.
     */
    public void setRegisterPanel(JPanel registerPanel) {
        this.registerPanel = registerPanel;
    }

    /**
     * This is the Mutator method for the userEmailText field.
     * @param userEmailText a JTextField for the User to enter their email address.
     */
    public void setUserEmailText(JTextField userEmailText) {
        this.userEmailText = userEmailText;
    }

    /**
     * This is the Mutator method for the userLocation field.
     * @param userLocation a Location for this User.
     */
    public void setUserLocation(Location userLocation) {
        this.userLocation = userLocation;
    }

    /**
     * This is the Mutator method for the userNameLabel field.
     * @param userNameLabel a JLabel containing the username label.
     */
    public void setUserNameLabel(JLabel userNameLabel) {
        this.userNameLabel = userNameLabel;
    }

    /**
     * This is the Mutator method for the userPhoneText field.
     * @param userPhoneText a JTextField for the User to enter their phone number.
     */
    public void setUserPhoneText(JTextField userPhoneText) {
        this.userPhoneText = userPhoneText;
    }

    /**
     * This is the Mutator method for the usernameTextTextField field.
     * @param usernameTextTextField a JTextField containing the username text.
     */
    public void setUsernameTextTextField(JTextField usernameTextTextField) {
        this.usernameTextTextField = usernameTextTextField;
    }

    /**
     * This is the Mutator method for the usernameWarning field.
     * @param usernameWarning a JLabel containing the validation warning for
     *                        the username.
     */
    public void setUsernameWarning(JLabel usernameWarning) {
        this.usernameWarning = usernameWarning;
    }
}
