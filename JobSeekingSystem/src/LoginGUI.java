import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {

    private JSS program;
    private JLabel loginTitle;
    public JPanel loginPanel;
    private JLabel loginUsername;
    private JTextField usernameField;
    private JPasswordField welcomeToJSSPasswordField;
    private JLabel loginPassword;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel errorLabel;
    private JLabel usernameWarning;
    private JLabel passwordWarning;
    private JLabel mainWarning;


    public LoginGUI(JSS program) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        this.program = program;
        JFrame frame = new JFrame("LoginGUI");
        frame.setContentPane(this.loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocation(700, 40);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = welcomeToJSSPasswordField.getPassword();

                if(validInputs())
                {
                    int userIndex = program.authenticateUser(username, String.valueOf(password));
                    if (userIndex == -1)
                    {
                        Validation.invalidInputWarning(mainWarning, "Invalid username or password");
                    }
                    else if (userIndex == -2) {

                        mainWarning.setText("Your account has been locked. Please contact JSS for more information.");
                    }
                    else
                    {
                        try
                        {
                            program.login(userIndex);
                            frame.dispose();

                        }
                        catch (Exception x)
                        {

                            x.printStackTrace();
                        }
                    }



                    //Clear the password box
                    clearPassword();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try
                {
                   RegisterGUI register = new RegisterGUI(program);
                }
                catch (Exception x)
                {
                    x.printStackTrace();
                }
            }
        });
    }

    // Method to clear the password box
    // Having trouble calling this from a static context at the moment...
    public void clearPassword() {
        welcomeToJSSPasswordField.setText("");
    }

    private Boolean validInputs()
    {
        boolean valid = true;
        JTextField[] inputs = {usernameField, welcomeToJSSPasswordField};
        JLabel[] warningLabels = {usernameWarning, passwordWarning};
        JLabel[] inputLabels = {loginUsername, loginPassword};

        for (int i = 0; i < inputs.length; i++) {

            if((Validation.isInputBlank(inputs[i])))
            {
                Validation.invalidInputWarning(warningLabels[i], inputLabels[i].getText()+ " cannot be blank");
                valid =  false;
            }
        }
        return valid;
    }


}
