import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

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
//                    frame.dispose();
//                 TestGUI test = new TestGUI();
                   RegisterGUI register = new RegisterGUI(program);
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

    /*public static void main(String[] args) {
        JFrame frame = new JFrame("LoginGUI");
        frame.setContentPane(new LoginGUI().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }*/



}
