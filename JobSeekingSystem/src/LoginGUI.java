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


    public LoginGUI(JSS program) {

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

                try
                {
                    program.login(username, String.valueOf(password));
                    frame.dispose();
                }
                catch (Exception x)
                {
                    errorLabel.setText("System Error! " + x.getMessage());
                    errorLabel.setVisible(true);
                    x.printStackTrace();
                }

                //Clear the password box
                clearPassword();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try
                {
                   RegisterGUI register = new RegisterGUI(program);
                   frame.dispose();
                }
                catch (Exception x)
                {
                    errorLabel.setText("System Error contact Admin with message " + x.toString());
                    errorLabel.setVisible(true);
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
