/*
Controller class for initial login, account registration and IO
methods create account types (not admin as yet)
TODO upon login transfer control to relevant subclass controller

 */
import java.util.ArrayList;
import java.util.Arrays;

public class JSS
{
    private static ArrayList<String> categories;
    private static int nextJobID;
    private final File_Control fileControl = new File_Control();
    private ArrayList<User> userList = new ArrayList<>();
    private static Start StartControl;

    public JSS(Start myParent) {
        StartControl = myParent;
        //  JFrame frame = new JFrame("LoginGUI");
        //  frame.setContentPane(new LoginGUI().loginPanel);
        //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  frame.pack();
        //  frame.setVisible(true);
        importUserList("JobSeekingSystem/users.csv");
    }

    //Verifies username/password & logs the user in.
    //TODO: can split this method into two. One for validation, one for logging in.
    public void login(String username, char[] password) throws Exception
    {
        // 1. Verify username
        boolean Exists = false;
        int userIndex = 0;
        for (User tmpUser: userList) {
            if (tmpUser.getUserName().equals(username)) {
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
        char[] pwd = userList.get(userIndex).getPassword();
        if (Arrays.equals(password, pwd)) {
            //Match on password
            passwordMatch = true;
        }

        if (!passwordMatch) {
            //This user's password did not match their stored password
            throw new Exception("passwords do not match!");
        }

        // 3. Let's check what kind of account this user should have
        String accountType = userList.get(userIndex).getUserType();
        switch (accountType) {
            case "Admin":
                //Do something
                throw new Exception("Success! Logging you in as " + accountType + "...");

            case "Jobseeker":
                //Do something else
                JobseekerControl jobSeekerControl = new JobseekerControl (userList.get(userIndex));
                break;

            case "Recruiter":
                //Launch Recruiter Control
                RecruiterControl recruiterControl = new RecruiterControl (userList.get(userIndex));
                break;

            default:
                throw new Exception("error logging user in!");
        }
    }


    //Method to read in the user list into memory
    public void importUserList(String fileName)
    {
        File_Control io = new File_Control();
        String userContent = "";
        try
        {
            userContent = io.readFile(fileName);
        }
        catch (Exception e)
        {
            System.out.println("Error failed to read file.");
        }

        //Grab the users from the csv file as a String
        if (userContent.trim().length() > 0)
        {
            //split each user into a string array
            String[] user = userContent.split("\n");

            try
            {
                for (int i = 0; i < user.length; i++)
                {
                    //split each user into another array of userDetails
                    String[] userDetails = user[i].split(",");

                    //if userType is Jobseeker
                    if (userDetails[5].trim().equalsIgnoreCase("jobseeker"))
                    {
                        //store password as char[]
                        char[] password = new char[userDetails[4].length()];
                        for (int j = 0; j < password.length; j++)
                        {
                            password[j] = userDetails[4].charAt(j);
                        }

                        //each userDetail array: userID,firstName,LastName,username,password,userType
                        importUser(Integer.parseInt(userDetails[0]), userDetails[1], userDetails[2], userDetails[3],password, userDetails[5]);
                    }

                    //if userType is a Recruiter
                    else if (userDetails[5].trim().equalsIgnoreCase("recruiter"))
                    {
                        //store password as char[]
                        char[] password = new char[userDetails[4].length()];
                        for (int j = 0; j < password.length; j++)
                        {
                            password[j] = userDetails[4].charAt(j);
                        }

                        //each userDetail array: userID,firstName,LastName,username,password,userType
                        importUser(Integer.parseInt(userDetails[0]), userDetails[1], userDetails[2], userDetails[3],password, userDetails[5]);
                    }

                    else if (userDetails[5].trim().equalsIgnoreCase("admin"))
                    {
                        //store password as char[]
                        char[] password = new char[userDetails[4].length()];
                        for (int j = 0; j < password.length; j++)
                        {
                            password[j] = userDetails[4].charAt(j);
                        }

                        //each array: userID,firstName,LastName,username,password,userType
                        importUser(Integer.parseInt(userDetails[0]), userDetails[1], userDetails[2], userDetails[3],password, userDetails[5]);
                    }

                    else
                    {
                        System.out.println("Error invalid userType, failed to import user. Check line: " + i);
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Error unable to import users, check " + fileName + "format");
            }


            //DEBUG: Quick test to print the results to the terminal
            for (User tmpUser: userList) {
                tmpUser.display();
            }

            /* Result: JSS_Control's userList ArrayList is now a list of String[]s,
             * with each one representing a user. Can be indexed into to retrieve specific
             * info (index 0 = userID, index 1 = username, index 2 = password).
             * index 3 up to index n are skills.
             * This method seems a bit messy, but works. Might need to be refactored later.
             */
        }
        else
            System.out.println("Error filename cannot be empty");
    }

    public int countUsers()
    {
        int count = 0;
        for (User tmpUser: userList)
        {
            count++;
        }
        return count;
    }

    //import existing user from .csv file
    public void importUser(int userID, String firstName, String lastName, String userName, char[] password, String userType)
    {
        if (userType.trim().equalsIgnoreCase("jobseeker"))
        {
            try
            {
                Jobseeker jobseeker = new Jobseeker(userID, firstName, lastName, userName, password);
                userList.add(jobseeker);
            }
            catch (Exception e)
            {
                System.out.println("Error failed to create Jobseeker, check your parameters!");
            }
        }
        else if (userType.trim().equalsIgnoreCase("recruiter"))
        {
            try
            {
                Recruiter recruiter = new Recruiter(userID,firstName, lastName, userName, password);
                userList.add(recruiter);
            }
            catch (Exception e)
            {
                System.out.println("Error failed to create Recruiter, check your parameters!");
            }
        }
        else if (userType.trim().equalsIgnoreCase("admin"))
        {
            try
            {
                Administrator admin = new Administrator(userID,firstName, lastName, userName, password);
                userList.add(admin);
            }
            catch (Exception e)
            {
                System.out.println("Error failed to create admin, check your parameters!");
            }
        }
        else
            System.out.println("Error failed to import user, invalid userType");
    }

    //Create User
    public void createUser(String firstName, String lastName, String userName, char[] password, String userType)
    {
        //create jobseeker
        if (userType.trim().equalsIgnoreCase("jobseeker"))
        {
            try
            {
                int userID = countUsers() + 1;
                Jobseeker newJobseeker = new Jobseeker(userID, firstName, lastName, userName, password);
                userList.add(newJobseeker);

                //write new user to users.csv
                newJobseeker.saveUser("JobSeekingSystem/users.csv");

            }
            catch (Exception e)
            {
                System.out.println("Error failed to create Jobseeker, check your parameters!");
            }
        }
        //create Recruiter
        else if (userType.trim().equalsIgnoreCase("recruiter"))
        {
            try
            {
                int userID = countUsers() + 1;
                Recruiter newRecruiter = new Recruiter(userID,firstName, lastName, userName, password);
                userList.add(newRecruiter);

                //write new recruiter to users.csv
                newRecruiter.saveUser("JobSeekingSystem/users.csv");
            }
            catch (Exception e)
            {
                System.out.println("Error failed to create Recruiter, check your parameters!");
            }
        }
        //create admin
        else if (userType.trim().equalsIgnoreCase("admin"))
        {
            try
            {
                int userID = countUsers() + 1;
                Administrator admin = new Administrator(userID,firstName, lastName, userName, password);
                userList.add(admin);

                //write new recruiter to users.csv
                admin.saveUser("JobSeekingSystem/users.csv");
            }
            catch (Exception e)
            {
                System.out.println("Error failed to create Recruiter, check your parameters!");
            }
        }
    }
}
