/**
 * This class contains methods used to validate various kinds of inputs
 * against required parameters.
 * @author  Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @param username  a String containing the desired username to be checked.
     * @return          a Boolean describing if the chosen username already exists.
     */
    public static int usernameIndex(ArrayList<User> users, String username) {
        int userIndex = -1;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equalsIgnoreCase(username))
            {
                userIndex = i;
                break;
            }
        }

        return userIndex;

    }

    /**
     * This method checks an input string (email) and compares to a regex pattern.
     * @param email     a String of the email to be changed against the regex pattern
     * @param warning  a Jlabel that is used to flash a warning if the email is invalid
     * @return          a Boolean describing if the string matches the email format.
     */
    public static boolean isValidEmail(String email, JLabel warning)
    {
        boolean validEmail = true;
        String emailFormat = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailFormat);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            Validation.invalidInputWarning(warning, "You have not entered a valid email.");
            validEmail = false;
        }

        return validEmail;
    }

    /**
     * This method checks an input string (phone number) and compares to a regex pattern.
     * @param phone     a String of the phone to be changed against the regex pattern
     * @param warning  a Jlabel that is used to flash a warning if the phone number is invalid
     * @return          a Boolean describing if the string matches the phone number format.
     */
    public static boolean isValidPhoneNo(String phone, JLabel warning)
    {
        boolean validNumber = true;
        String phoneFormat = "^(\\+?\\(61\\)|\\(\\+?61\\)|\\+?61|\\(0[1-9]\\)|0[1-9])?( ?-?[0-9]){7,9}$";
        Pattern pattern = Pattern.compile(phoneFormat);
        Matcher matcher = pattern.matcher(phone);

        if (!matcher.matches()) {
            Validation.invalidInputWarning(warning, "You have not entered a valid phone number");
            validNumber = false;
        }

        return validNumber;
    }

    /**
     * This method loops through an array of JTextFields to check if they are empty, updating the relevant warning label.
     * @param inputs     an Array of JTextFields that are checked to see if they are empty
     * @param warningLabels an Array of JLabels that are displayed if the associated input is empty
     * @param inputLabels an Array of JLabels for the checked inputs used to form the warning message
     * @return          a Boolean describing if all of the inputs are valid (not empty)
     */
    public static Boolean validInputs(JTextField[] inputs, JLabel[] warningLabels, JLabel[] inputLabels)
    {
        boolean valid = true;

        for (int i = 0; i < inputs.length; i++) {

            if((Validation.isInputBlank(inputs[i])))
            {
                Validation.invalidInputWarning(warningLabels[i], inputLabels[i].getText() + " cannot be blank");
                valid =  false;
            }
        }

        return valid;
    }

}

