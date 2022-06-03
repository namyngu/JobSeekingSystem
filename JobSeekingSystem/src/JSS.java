import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class JSS
{
    private static ArrayList<String> categories;
    private static int currentUserID;
    private static int nextJobID;
    private final File_Control fileControl = new File_Control();
    private ArrayList<String[]> userList = new ArrayList<String[]>();

    public JSS()
    {
    //  JFrame frame = new JFrame("LoginGUI");
    //  frame.setContentPane(new LoginGUI().loginPanel);
    //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //  frame.pack();
    //  frame.setVisible(true);
        readUserList();
    }

    public void login(String username, char[] password)
    {

       //TODO verify username here
        //TODO verify password here
        //TODO check account type (use instanceof to check subclass)
        //TODO call relevant control class
    }

    //Method to read in the user list into memory
    public void readUserList() {
        String users = "";

        //Grab the users from the csv file as a String
        try {
            users = fileControl.readFile("JobSeekingSystem/src/users.csv");
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
         * info (index 0 = userID, index 1 = username, index 2 = password.
         * index 3 up to index n are skills.
         * This method seems a bit messy, but works. Might need to be refactored later.
         */
    }

    public void createJobseeker(String firstName, String lastName, String username, char[] password)
    {
        String name = firstName + " " + lastName;
        Jobseeker newJobseeker = new Jobseeker(this.currentUserID,name,username,password);
        currentUserID +=1;

        System.out.println("new jobseeker created: " + newJobseeker.getName());
    }

    public void createRecruiter(String firstName, String lastName, String username, char[] password)
    {
        String name = firstName + " " + lastName;
        Recruiter newRecruiter = new Recruiter(this.currentUserID,name,username,password);
        currentUserID +=1;

        System.out.println("new recruiter created: " + newRecruiter.getName());
    }

//    public static void main(String[] args)
//    {
//        JSS obj = new JSS();
//
//    }


}
