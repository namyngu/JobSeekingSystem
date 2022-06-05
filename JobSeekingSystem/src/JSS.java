/*
Controller class for initial login, account registration and IO
methods create account types (not admin as yet)
TODO upon login transfer control to relevant subclass controller

 */

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class JSS
{
    private static ArrayList<String> categories;
    private static int currentUserID;
    private static int nextJobID;
    private final File_Control fileControl = new File_Control();
    private ArrayList<String[]> userList = new ArrayList<>();
    private static Start StartControl;

    public JSS(Start myParent) {
        StartControl = myParent;
    //  JFrame frame = new JFrame("LoginGUI");
    //  frame.setContentPane(new LoginGUI().loginPanel);
    //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //  frame.pack();
    //  frame.setVisible(true);
        readUserList();
    }

    public void login(String username, char[] password) throws Exception
    {
        // 1. Verify username
        boolean Exists = false;
        int userIndex = 0;
        for (String[] user: userList) {
            if (username.equals(user[1])) {
                //Match on username
                Exists = true;
                break;
            }
            userIndex++;
        }

        if (!Exists) {
            //We did not find a username matching the entered name
            throw new Exception("could not find a user with this username!");
        }

        // 2. Verify password
        boolean passwordMatch = false;
        char[] pwd = userList.get(userIndex)[2].toCharArray();
        if (Arrays.equals(password, pwd)) {
            //Match on password
            passwordMatch = true;
        }

        if (!passwordMatch) {
            //This user's password did not match their stored password
            throw new Exception("passwords do not match!");
        }

        // 3. Let's check what kind of account this user should have
        String accountType = userList.get(userIndex)[3];
        switch (accountType) {
            case "Admin":
                //Do something
                throw new Exception("Success! Logging you in as " + accountType + "...");
            case "Job-Seeker":
                //Do something else
                throw new Exception("Success! Logging you in as " + accountType + "...");
            case "Recruiter":
                //Do something else
                throw new Exception("Success! Logging you in as " + accountType + "...");
            default:
                throw new Exception("error logging user in!");
        }
    }

    //Method to read in the user list into memory
    public void readUserList() {
        String users = "";

        //Grab the users from the csv file as a String
        try {
            users = fileControl.readFile("JobSeekingSystem/data/users.csv");
        }
        catch (IOException e) {
            System.out.println("No users.csv file exists!");
            //TODO: Need to fancy this up a bit - what error should present to the user?
        }

        //Split our string into individual users
        String[] allUsers = users.split(";");

        //For each user in allUsers, add relevant info to ArrayList
        for (String temp: allUsers) {
            String[] tempUser = temp.split(",");
            userList.add(tempUser);
        }

        //DEBUG: Quick test to print the results to the terminal
        for (String[] temp: userList) {
            System.out.println(Arrays.toString(temp));
        }

        /* Result: JSS_Control's userList ArrayList is now a list of String[]s,
         * with each one representing a user. Can be indexed into to retrieve specific
         * info (index 0 = userID, index 1 = username, index 2 = password).
         * index 3 up to index n are skills.
         * This method seems a bit messy, but works. Might need to be refactored later.
         */
    }

    public void createJobseeker(String firstName, String lastName, String username, char[] password)
    {
        String name = firstName + " " + lastName;
        Jobseeker newJobseeker = new Jobseeker(this.currentUserID,name,username,password);
        currentUserID +=1;

        System.out.println("new jobseeker created: " + newJobseeker.toString());
    }

    public void createRecruiter(String firstName, String lastName, String username, char[] password)
    {
        String name = firstName + " " + lastName;
        Recruiter newRecruiter = new Recruiter(this.currentUserID,name,username,password);
        currentUserID +=1;

        System.out.println("new recruiter created: " + newRecruiter.toString());
    }




}
