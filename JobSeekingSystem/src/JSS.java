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
    private ArrayList<Job> jobList = new ArrayList<>();

    public JSS()
    {
        importUserList("users.csv");
        importJobs("JobSeekingSystem/Jobs.csv");
    }

    // TODO: Method for reading in all the jobs and creating the ArrayList<Job>
    public void importJobs(String fileName) {

        // Read the Jobs.csv file.
        String jobContent = "";
        try {
            jobContent = fileControl.readFile(fileName);
        } catch (Exception e) {
            System.out.println("Error failed to read jobs!");
        }

        // Split the read String into many Jobs to be created.
        if (jobContent.trim().length() > 0) {
            // Split each Job into a string array.
            String[] jobs = jobContent.split("\n");

            try {
                for (int i = 0; i < jobs.length; i++) {
                    // If Jobs.csv contains an empty line, then skip it.
                    if (jobs[i].isEmpty()) {
                        System.out.println("Warning: empty line in Jobs.csv at line: " + i + ", skipping...");
                        continue;
                    }

                    // Split each Job into another array of jobDetails
                    // Since each Job can have a variable number of skills, applications and
                    // keywords, we need to check each index of the jobDetails to make sure
                    // we construct the Jobs appropriately.

                    // Let's store all this job's data in an Arraylist.
                    ArrayList<String> jobDetails = new ArrayList<>();
                    String[] job = jobs[i].split(",");
                    for (String jobElement : job) {
                        jobDetails.add(jobElement);
                    }

                    // Let's setup some Arraylists to store only skills,
                    // applications and keywords.
                    ArrayList<String> jobSkills = new ArrayList<>();
                    ArrayList<String> jobApps = new ArrayList<>();
                    ArrayList<String> jobKeywords = new ArrayList<>();

                    // Check all the job Elements, and if it is a skill,
                    // put it in the skill list, and so on. Remove that
                    // element from the general list so we know that list
                    // has been sanitised.
                    for (String jobElement : jobDetails) {
                        if (jobElement.startsWith("skill:")) {
                            // This element is a skill.
                            jobSkills.add(jobElement);
                            jobDetails.remove(jobElement);
                        } else if (jobElement.startsWith("app:")) {
                            // This element is an application ID.
                            jobApps.add(jobElement);
                            jobDetails.remove(jobElement);
                        } else if (jobElement.startsWith("keyword:")) {
                            // This element is a keyword.
                            jobKeywords.add(jobElement);
                            jobDetails.remove(jobElement);
                        }
                    }

                    // Result: jobDetails contains all job elements which are NOT
                    // skills, keywords or Applications (and it is ordered).
                    // jobSkills contains all skills.
                    // jobApps contains all Application IDs.
                    // jobKeywords contains all keywords.

                    // PROBLEM! jobDetails contains all Strings! These need to be cast
                    // as the appropriate type so that the Job constructor can be called.
                    // To solve, let's check each element in jobDetails and cast it back
                    // as the appropriate type.
                    ArrayList<Object> castJobDetails = new ArrayList<>();
                    for (int x = 0; x < jobDetails.size(); x++) {
                        if (jobDetails.get(x).equals("true")) {
                            // This element is a true bool.
                            boolean castMe = true;
                            castJobDetails.add(castMe);
                        } else if (jobDetails.get(x).equals("false")) {
                            // This element is a false bool.
                            boolean castMe = false;
                            castJobDetails.add(castMe);
                        } else if (jobDetails.get(x).matches("-?\\d+")) {
                            // This element is a number.
                            int castMe = Integer.parseInt(jobDetails.get(x));
                            castJobDetails.add(castMe);
                        } else {
                            // This element is a String and can be added straight from the can.
                            castJobDetails.add(jobDetails.get(x));
                        }
                    }

                    // Get all of the Applications which related to this job.
                    // Convert our ArrayList of Application IDs to an ArrayList
                    // of actual Applications.
                    // TODO: This needs fixing, it is just mocked for now.
                    ArrayList<Application> jobCreatedApps = new ArrayList<>();
                    for (String appID : jobApps) {
                        // TODO: Create a real application goes here.
                        jobCreatedApps.add(new Application());
                    }

                    // Construct a job.
                    Job newJob = new Job((int)castJobDetails.get(0), (String)castJobDetails.get(1), (int)castJobDetails.get(2), (String)castJobDetails.get(3),
                            (String)castJobDetails.get(4), (boolean)castJobDetails.get(5), (String)castJobDetails.get(6), jobSkills,
                            jobCreatedApps, jobKeywords, (String)castJobDetails.get(7),
                            (String)castJobDetails.get(8), (String)castJobDetails.get(9), (String)castJobDetails.get(10), (String)castJobDetails.get(11),
                            (String)castJobDetails.get(12), (boolean)castJobDetails.get(13), (boolean)castJobDetails.get(14));

                }
            } catch (Exception e) {
                System.out.println("Error unable to import Jobs, check " + fileName + " format!");
            }

        }
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

        //2.1 check if the account is locked

        if (!userList.get(userIndex).isActive())
        {
            PromptGUI locked = new PromptGUI("This account has been locked.  Contact Administrator");
            throw new Exception("Account Locked!");
        }

        // 2.2 check if user has any messages?

//        this.checkMessages(userIndex);

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
                        importUser(Integer.parseInt(userDetails[0]), userDetails[1], userDetails[2], userDetails[3], userDetails[4], userDetails[5], Boolean.parseBoolean(userDetails[6]));
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
    public void importUser(int userID, String firstName, String lastName, String userName, String password, String userType, boolean active)
    {
        if (userType.trim().equalsIgnoreCase("jobseeker"))
        {
            try
            {
                Jobseeker jobseeker = new Jobseeker(userID, firstName, lastName, userName, password,active);
                userList.add(jobseeker);
            } catch (Exception e)
            {
                System.out.println("Error failed to create Jobseeker, check your parameters!");
            }
        } else if (userType.trim().equalsIgnoreCase("recruiter"))
        {
            try
            {
                Recruiter recruiter = new Recruiter(userID, firstName, lastName, userName, password,active);
                userList.add(recruiter);
            } catch (Exception e)
            {
                System.out.println("Error failed to create Recruiter, check your parameters!");
            }
        } else if (userType.trim().equalsIgnoreCase("admin"))
        {
            try
            {
                Administrator admin = new Administrator(userID, firstName, lastName, userName, password,active);
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
                this.saveUser(userID, firstName, lastName, userName, encryptPW,userType,true);

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
                this.saveUser(userID, firstName, lastName, userName, encryptPW,userType,true);
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
                Administrator admin = new Administrator(userID, firstName, lastName, userName, encryptPW,true);

                //write new recruiter to users.csv
                this.saveUser(userID, firstName, lastName, userName, encryptPW,userType,true);
            } catch (Exception e)
            {
                System.out.println("Error failed to create admin, check your parameters!");
            }
        }
    }


    //Method to save user to users.csv
    //TODO adapt to include active status
    public void saveUser(int userID, String firstName, String lastName, String userName, String password, String userType, boolean active)
    {
        try
        {
            String userData = userID + "," + firstName + "," + lastName + "," + userName + "," + password + "," + userType + "," + active;
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
//method to lock/unlock account comes via admin controller.
    //TODO this should come from admin control I think but there must be a neater way of doing this

    public void alterActive(int index)
    {
        User temp = userList.get(index);
        if(temp.isActive() == true)
        {
            temp.setActive(false);
        }
        else
        {
            temp.setActive(true);
        }
   this.refreshUserSavedList();
    }
    //method updates saved user list
    private void refreshUserSavedList()
    {
        try
        {
            fileControl.clearFile("users.csv");
            for (User temp: userList)
            {
                this.saveUser(temp.getUserID(), temp.getFirstName(), temp.getLastName(), temp.getUserName(), temp.getPassword(),temp.getUserType(),temp.isActive());
            }
        } catch (Exception e)
        {
            System.out.println("Error failed REFRESH save user into csv.");
        }


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

    //message sorter??

//    public void messageSend(int senderID, int receiverID)
//    {
//        User sender = userList.get(senderID);
//
//        if (sender instanceof Administrator)
//        {
//            Message alert = new AdminAlert(senderID, receiverID);
//        }
//    }
    public User getUserByID(int userID)
    {
        User inQuestion = userList.get(userID);
        return inQuestion;
    }

    public void storeMessage(int senderID, int receiverID, String text)
    {
        String message = "pending" + "," + senderID + "," + receiverID + "," + text;
        try
        {
            File_Control io = new File_Control();
            io.writeFile("messages.csv", message);
        } catch (Exception e)
        {
            System.out.println("Error failed to save user into csv.");
        }
    }

    public boolean checkMessages(int userIndex)
    {
        boolean hasMail = false;

        ArrayList<Message> messages = new ArrayList<>();

        String rawInput = "";
        try {
            rawInput = fileControl.readFile("messages.csv");
        } catch (Exception e) {
            System.out.println("Error failed to read messages!");
        }

//        System.out.println("these are the messages: " + rawInput);
        String[] messageString = rawInput.split("\n");

        try
        {
            for (int i = 0; i < messageString.length; i++)
            {
                //If messages.csv contains an empty line skip it.
                if (messageString[i].isEmpty())
                {
                    System.out.println("Warning: empty line in messages.csv at line: " + i + ", skipping...");
                    continue;
                }

                //split each user into another array of userDetails
                String[] messageDetails = messageString[i].split(",");

                //find out who the message is for

                int messageTo = Integer.parseInt(messageDetails[2]);

                //if it is for the user checking, add it to their list
                if (messageTo == userIndex)
                {
                    Message message = new AdminAlert(Integer.parseInt(messageDetails[1]),Integer.parseInt(messageDetails[2]),messageDetails[3]);
                   this.userList.get(userIndex).addMessage(message);
                   //TODO alter messages arraylist so this is received if true
                }
                //TODO check user type and determine message type accordingly
                //TODO deal with \n -- try replace with String methods? -- do this at point of writing
                //TODO refresh message.csv list when done

                //checking message is getting there....
                User one = userList.get(userIndex);

                System.out.println(one.messagesToString());
                //this isn't arriving

            }
        } catch (Exception e)
        {
            System.out.println("message read error" + e.toString());
        }

        return hasMail;
    }
}

