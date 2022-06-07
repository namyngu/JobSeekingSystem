/*
Controller class for initial login, account registration and IO
methods create account types (not admin as yet)
TODO upon login transfer control to relevant subclass controller

 */
import java.util.ArrayList;

public class JSS
{
    private static ArrayList<String> categories;
    private static int nextJobID;
    private final File_Control fileControl = new File_Control();
    private ArrayList<User> userList = new ArrayList<>();

    public JSS()
    {
        importUserList("users.csv");
    }

    //Verifies username/password & logs the user in.
    //TODO: can split this method into two. One for validation, one for logging in.
    public void login(String username, String password) throws Exception
    {
        // 1. Verify username
        boolean Exists = false;
        int userIndex = 0;
        for (User tmpUser : userList)
        {
            if (tmpUser.getUserName().equals(username))
            {
                //Match on username
                Exists = true;
                break;
            }
            userIndex++;
        }

        if (!Exists)
        {
            //We did not find a username matching the entered name
            throw new Exception("could not find a user with this username!");
        }

        // 2. Verify password
        boolean passwordMatch = false;

        //hash user's password
        String encryptedPW = EncryptMe.encryptThisString(password);
        if (encryptedPW.equals(userList.get(userIndex).getPassword()))
            passwordMatch = true;

        if (!passwordMatch)
        {
            //This user's password did not match their stored password
            throw new Exception("passwords do not match!");
        }

        // 3. Let's check what kind of account this user should have
        String accountType = userList.get(userIndex).getUserType();
        switch (accountType)
        {
            case "Admin":
                try
                {
                    AdminControl adminControl = new AdminControl((Administrator) userList.get(userIndex), this);
                    AdminGUI adminGUI = new AdminGUI(adminControl, this);
//                    throw new Exception("Success! Logging you in as " + accountType + "...");
                    break;
                }
                catch (Exception e)
                {

                    PromptGUI error = new PromptGUI("Contact Administrator", e.toString());

                }

            case "Jobseeker":
                //Do something else
                JobseekerControl jobSeekerControl = new JobseekerControl(userList.get(userIndex));
                break;

            case "Recruiter":
                //Launch Recruiter Control
                RecruiterControl recruiterControl = new RecruiterControl(userList.get(userIndex));
                break;

            default:
                throw new Exception("error logging user in!");
        }
    }

    public String retrieveUsername(int userID)
    {
        User temp = userList.get(userID);
        String username = temp.getUserName();
        return username;
    }

    //Method to read in the user list into memory
    public void importUserList(String fileName)
    {
        File_Control io = new File_Control();
        String userContent = "";
        try
        {
            userContent = io.readFile(fileName);
        } catch (Exception e)
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
                    //If users.csv contains an empty line skip it.
                    if (user[i].isEmpty())
                    {
                        System.out.println("Warning: empty line in users.csv at line: " + i + ", skipping...");
                        continue;
                    }

                    //split each user into another array of userDetails
                    String[] userDetails = user[i].split(",");
                    String userType = userDetails[5].trim().toLowerCase();

                    //if userType is Jobseeker, recruiter or admin
                    if (userType.equals("jobseeker") || userType.equals("recruiter") || userType.equals("admin"))
                        importUser(Integer.parseInt(userDetails[0]), userDetails[1], userDetails[2], userDetails[3], userDetails[4], userDetails[5]);
                    else
                        System.out.println("Error invalid userType, failed to import user. Check line: " + i);
                }
            } catch (Exception e)
            {
                System.out.println("Error unable to import users, check " + fileName + " format");
            }

            /*
            //DEBUG: Quick test to print the results to the terminal
            for (User tmpUser: userList) {
                tmpUser.display();
            }
            */
        } else
            System.out.println("Error filename cannot be empty");
    }

    public int countUsers()
    {
        int count = 0;
        for (User tmpUser : userList)
        {
            count++;
        }
        return count;
    }

    //import existing user from .csv file
    public void importUser(int userID, String firstName, String lastName, String userName, String password, String userType)
    {
        if (userType.trim().equalsIgnoreCase("jobseeker"))
        {
            try
            {
                Jobseeker jobseeker = new Jobseeker(userID, firstName, lastName, userName, password);
                userList.add(jobseeker);
            } catch (Exception e)
            {
                System.out.println("Error failed to create Jobseeker, check your parameters!");
            }
        } else if (userType.trim().equalsIgnoreCase("recruiter"))
        {
            try
            {
                Recruiter recruiter = new Recruiter(userID, firstName, lastName, userName, password);
                userList.add(recruiter);
            } catch (Exception e)
            {
                System.out.println("Error failed to create Recruiter, check your parameters!");
            }
        } else if (userType.trim().equalsIgnoreCase("admin"))
        {
            try
            {
                Administrator admin = new Administrator(userID, firstName, lastName, userName, password);
                userList.add(admin);
            } catch (Exception e)
            {
                System.out.println("Error failed to create admin, check your parameters!");
            }
        } else
            System.out.println("Error failed to import user, invalid userType");
    }

    //Create User
    public void createUser(String firstName, String lastName, String userName, String password, String userType)
    {
        //encrypt password
        String encryptPW = EncryptMe.encryptThisString(password);
        //create jobseeker
        if (userType.trim().equalsIgnoreCase("jobseeker"))
        {
            try
            {
                int userID = countUsers() + 1;
                Jobseeker newJobseeker = new Jobseeker(userID, firstName, lastName, userName, encryptPW);
                userList.add(newJobseeker);

                //write new user to users.csv
                this.saveUser(userID, firstName, lastName, userName, encryptPW,userType);

            } catch (Exception e)
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
                Recruiter newRecruiter = new Recruiter(userID, firstName, lastName, userName, encryptPW);
                userList.add(newRecruiter);

                //write new recruiter to users.csv
                this.saveUser(userID, firstName, lastName, userName, encryptPW,userType);
            } catch (Exception e)
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
                Administrator admin = new Administrator(userID, firstName, lastName, userName, encryptPW);

                //write new recruiter to users.csv
                this.saveUser(userID, firstName, lastName, userName, encryptPW,userType);
            } catch (Exception e)
            {
                System.out.println("Error failed to create admin, check your parameters!");
            }
        }
    }


    //Method to save user to users.csv
    public void saveUser(int userID, String firstName, String lastName, String userName, String password, String userType)
    {
        try
        {
            String userData = userID + "," + firstName + "," + lastName + "," + userName + "," + password + "," + userType;
            File_Control io = new File_Control();
            io.writeFile("users.csv", userData);
        } catch (Exception e)
        {
            System.out.println("Error failed to save user into csv.");
        }

    }
//method to return relevant User details to AdminGUI for review
    public String retrieveUserDetails(int userNumber)
    {
        User userSelected = userList.get(userNumber);
        String userDetails = "";
        userDetails += "Username: " + userSelected.getUserName() + "\n";
        userDetails += "Account type: " + userSelected.getUserType() + "\n";
        userDetails += "First name: " + userSelected.getFirstName() + "\n";
        userDetails += "Surname: " + userSelected.getLastName() + "\n";




        return userDetails;
    }
//method to lock account comes via admin controller.
    //TODO this should come from admin control I think but there must be a neater way of doing this

    public void alterActive(int index)
    {
        boolean success = false;

        User temp = userList.get(index);
        temp.setActive(false);
    }

    public boolean checkLocked(int userNumber)
    {
        boolean locked = false;
        User temp = this.userList.get(userNumber);
        if (temp.isActive()==false)
        {
            locked = true;
        }
        return locked;
    }
//temp method to check lock mechanism
//    public void checkLock(int userIndex)
//    {
//        User temp = userList.get(userIndex);
//        System.out.println("Username " + temp.getUserName());
//        System.out.println("Is active: " + temp.isActive());
//    }
}

