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
    private ArrayList<Location> locationList = new ArrayList<>();
    private ArrayList<JobCategory> jobCategoryList = new ArrayList<>();

    public JSS()
    {
        try
        {
            importUserList("users.csv");

        } catch (Exception e)
        {
            System.out.println("FATAL ERROR!! Failed to import users!!");
        }

        try
        {
            importJobList("JobList.csv");

        } catch (Exception e)
        {
            System.out.println("FATAL ERROR!! Failed to import JobList!!");
        }
        try
        {
            importLocationList("Location.csv");

        } catch (Exception e)
        {
            System.out.println("FATAL ERROR!! Failed to import Location!!");
        }
        try
        {
            importJobCategoryList("JobCategories.csv");

        } catch (Exception e)
        {
            System.out.println("FATAL ERROR!! Failed to import JobCategories!!");
        }
        try
        {
            updateJobs("JobKeywords.csv", "JobSkills.csv");

        } catch (Exception e)
        {
            System.out.println("FATAL ERROR!! Failed to updateJobs!!");
        }

        display();
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
        }
        else
            System.out.println("Error filename cannot be empty");
    }

    //Method to read job from csv's into memory
    public void importJobList(String jobListFile) throws Exception
    {
        File_Control io = new File_Control();
        String jobPostingContent = "";
        try
        {
            jobPostingContent = io.readFile(jobListFile);
        }
        catch (Exception e)
        {
            System.out.println("Error failed to read files");
        }
        if (jobPostingContent.trim().isEmpty())
                throw new Exception("File cannot be empty");

        //Split each job into an array
        String[] job = jobPostingContent.split("\n");

        //iterate through each job, ignore first line!!
        for (int i = 1; i < job.length; i++)
        {
            //if line is empty, skip it.
            if (job[i].isEmpty())
            {
                System.out.println("Warning: empty line in " + jobListFile + " at line: " + i + ", skipping...");
                continue;
            }

            //split each job into jobDetails
            //jobID, jobTitle, employer, recruiterID, jobType, jobStatus, salary, locationID, jobDescription
            String[] jobDetails = job[i].split(",");
            importJob(Integer.parseInt(jobDetails[0]), jobDetails[1], jobDetails[2], Integer.parseInt(jobDetails[3]), jobDetails[4],
                    jobDetails[5], Integer.parseInt(jobDetails[6]), Integer.parseInt(jobDetails[7]), jobDetails[8]);
        }
    }

    //Method to import job category from csv into memory
    public void importJobCategory(String jobTitle, String jobCategoryPrimary, String jobCategorySecondary, String jobCategoryTertiary)
    {
        try
        {
            JobCategory category = new JobCategory(jobTitle, jobCategoryPrimary, jobCategorySecondary, jobCategoryTertiary);
            jobCategoryList.add(category);

        } catch (Exception e)
        {
            System.out.println("Error failed to import a category, check your parameters!");
        }
    }

    //Method to import job category list from csv into memory.
    public void importJobCategoryList(String jobCategoriesFile) throws Exception
    {
        File_Control io = new File_Control();
        String jobCategoriesContent = "";
        try
        {
            jobCategoriesContent = io.readFile(jobCategoriesFile);
        }
        catch (Exception e)
        {
            System.out.println("Error failed to read JobCategory.csv");
        }
        if (jobCategoriesContent.trim().isEmpty())
            throw new Exception("Error File cannot be empty");

        //Split each jobcategory into an array
        String[] jobCategory = jobCategoriesContent.split("\n");

        //iterate through each array, ignore first line!!
        for (int i = 1; i < jobCategory.length; i++)
        {
            //if line is empty, skip it.
            if (jobCategory[i].isEmpty())
            {
                System.out.println("Warning: empty line in " + jobCategoriesFile + ".csv at line: " + i + ", skipping...");
                continue;
            }

            //split each category into jobCategoryDetails
            //jobTitle, jobCategory 1, jobCategory 2, jobCategory 3
            String[] jobCategoryDetails = jobCategory[i].split(",");
            importJobCategory(jobCategoryDetails[0], jobCategoryDetails[1], jobCategoryDetails[2], jobCategoryDetails[3]);
        }
    }

    //importing location from .csv file
    public void importLocationList(String locationFile) throws Exception
    {
        File_Control io = new File_Control();
        String locationContent = "";
        try
        {
            locationContent = io.readFile(locationFile);
        }
        catch (Exception e)
        {
            System.out.println("Error failed to read files");
        }
        if (locationContent.trim().isEmpty())
            throw new Exception("File cannot be empty");

        //split each location into an array
        String[] location = locationContent.split("\n");

        //iteration through each location array
        for (int i = 1; i < location.length; i++)
        {
            //if line is empty, skip it.
            if (location[i].isEmpty())
            {
                System.out.println("Warning: empty line in Location.csv at line: " + i + ", skipping...");
                continue;
            }

            //split each location into locationDetails
            //locationID, locationState, PostCode
            String[] locationDetails = location[i].split(",");
            importLocation(Integer.parseInt(locationDetails[0]), locationDetails[1], Integer.parseInt(locationDetails[2]), locationDetails[3]);
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
                JobseekerControl jobSeekerControl = new JobseekerControl(userList.get(userIndex), jobList, locationList, jobCategoryList);
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

    public int countUsers()
    {
        int count = 0;
        for (User tmpUser : userList)
        {
            count++;
        }
        return count;
    }

    //Method to import location from csv file
    public void importLocation(int locationID, String locationState, int postCode, String city)
    {
        try
        {
            Location location = new Location(locationID, locationState, postCode, city);
            locationList.add(location);
        } catch (Exception e)
        {
            System.out.println("Error failed to import a location, check your parameters!");
        }
    }


    //import existing job in .csv file
    public void importJob(int jobID, String jobTitle, String employer, int recruiterID, String jobType, String jobStatus, int salary, int locationID, String jobDescription)
    {
        try
        {
            //create job skeleton
            Job job = new Job(jobID, jobTitle, employer, recruiterID, jobType, jobStatus, salary, locationID, jobDescription);
            jobList.add(job);
        } catch (Exception e)
        {
            System.out.println("Error failed to create Job, check your parameters!");
        }
    }

    //create new job and write to .csv

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

    //Method to link all the job databases together
    public void updateJobs(String jobKeywordFile, String jobSkillsFile) throws Exception
    {
        File_Control reader = new File_Control();

        //update job keywords
        String jobKeywordsContent = "";
        try
        {
            jobKeywordsContent = reader.readFile(jobKeywordFile);
        } catch (Exception e)
        {
            System.out.println("Failed to read JobKeywords.csv");
        }

        String[] jobKeyword = jobKeywordsContent.split("\n");

        //ignore first line!!
        for (int i = 1; i < jobKeyword.length; i++)
        {
            String[] jobKeywordDetails = jobKeyword[i].split(",");

            //Search through the jobList for the job then append keywords to that job
            //TODO can optimize search by first sorting out the database - not required extra functionality.
            int index = 0;
            for (Job tmpJob : jobList)
            {
                if (tmpJob.getJobTitle().equalsIgnoreCase(jobKeywordDetails[0]))
                {

                    jobList.get(index).appendKeyword(jobKeywordDetails[1]);
                }
                index++;
            }
        }

        //update job skills
        String jobSkillsContent = "";
        try
        {
            jobSkillsContent = reader.readFile(jobSkillsFile);
        } catch (Exception e)
        {
            System.out.println("Failed to read JobSkills.csv");
        }

        String[] jobSkill = jobSkillsContent.split("\n");

        //ignore first line!!
        for (int i = 1; i < jobSkill.length; i++)
        {
            String[] jobSkillDetails = jobSkill[i].split(",");

            //Search through the jobList for the job then append skills to that job
            int index = 0;
            for (Job tmpJob : jobList)
            {
                if (tmpJob.getJobID() == Integer.parseInt(jobSkillDetails[0]))
                {

                    jobList.get(index).appendSkill(jobSkillDetails[1]);
                }
                index++;
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

    // Check Messages method provided by Gerard.
    public boolean checkMessages(int userIndex)
    {
        //decreasing userIndex!!!
//        userIndex -=1;
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
                    hasMail =true;
                    int sender = Integer.parseInt(messageDetails[1]);
                    //TODO fix this to reflect changes to message class
                    Message message = new Message(sender,messageTo,messageDetails[3],messageDetails[4]);


                    User temp = this.userList.get(userIndex);

                    temp.addMessage(message);
                    //TODO alter messages arraylist so this is received if true
                }
                //TODO check user type and determine message type accordingly
                //TODO deal with \n -- try replace with String methods? -- do this at point of writing
                //TODO refresh message.csv list when done
                //checking message is getting there....
//                User one = userList.get(userIndex);
//                System.out.println("Nessafe: " + one.messagesToString());
            }
        } catch (Exception e)
        {
            System.out.println("message read error" + e.toString());
        }

        return hasMail;
    }

    // Store message method as copied from Gerard's branch.
    public void storeMessage(int senderID, int receiverID, String header, String body)
    {
        String message = "pending" + "," + senderID + "," + receiverID + "," + header+"," + body;
        try
        {
            File_Control io = new File_Control();
            io.writeFile("messages.csv", message);
        } catch (Exception e)
        {
            System.out.println("Error failed to save user into csv.");
        }
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

    //DEBUG: Test to see if jobs have been imported
    public void display()
    {
        for (Job tmpJob : jobList)
        {
            System.out.println(tmpJob.toString());
        }
    }

}

