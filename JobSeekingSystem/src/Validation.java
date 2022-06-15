/**
 * This class contains methods used to validate various kinds of inputs
 * against required parameters.
 * @author  Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Validation {

    /**
     * This is the Default constructor.
     */
    public Validation(){

    }

    /**
     * This method creates a warning label to be displayed when the user has
     * entered invalid input.
     * @param warningLabel  a JLabel onto which the warning will be displayed.
     * @param message       a String contaning the warning message text.
     */
    public static void invalidInputWarning(JLabel warningLabel, String message) {
        warningLabel.setText(message);
        warningLabel.setVisible(true);

        new Timer().schedule(new TimerTask() {

            public void run() {
                warningLabel.setVisible(false);
            }
        }, 2000L); // 300 is the delay in millis
    }

    /**
     * This method checks to see if the input of a text field is empty or not.
     * @param input a JTextField to be checked.
     * @return      a Boolean describing if the JTextField is empty or not.
     */
    public static boolean isInputBlank(JTextField input)
    {
        return input.getText().isEmpty();
    }

    /**
     * This method checks to see if a chosen username already exists in the system.
     * @param users     an ArrayList of Users, containing all the Users in the system.
     * @param username  a String contaning the desired username to be checked.
     * @return          a Boolean describing if the chosen username already exists.
     */
    public static boolean usernameExists(ArrayList<User> users, String username) {
        boolean exists = false;
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(username)) {
                exists = true;
                break;
            }
        }
        return exists;
    }
}

