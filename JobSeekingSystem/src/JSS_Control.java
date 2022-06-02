import javax.swing.*;
import java.io.IOException;

public class JSS_Control {

    private final File_Control fileControl = new File_Control();

    //Begin main program execution
    public static void main(String[] args) {
        //Instantiate the control class
        JSS_Control mainSystem = new JSS_Control();
    }

    public JSS_Control() {
        //Upon building, system should read list of users
        //We really need to break these out into separate methods
        String users = "";
        try {
            users = fileControl.readFile("JobSeekingSystem/src/users.csv");
        }
        catch (IOException e) {
            System.out.println("No users.csv file exists!");
        }

        //Test the users.csv file
        System.out.println(users);
        //TODO: Do stuff with the users String
        //Call login screen
        JFrame frame = new JFrame("LoginGUI");
        frame.setContentPane(new LoginGUI().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
