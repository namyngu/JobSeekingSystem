/*
Controller class for initial login, account registration and IO
methods create account types (not admin as yet)
TODO upon login transfer control to relevant subclass controller

 */

import com.sun.source.tree.TryTree;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class JSS
{

    private ArrayList<Application> applicationList = new ArrayList<>();
    private ArrayList<Message> allMessages;
    private static int nextMessageID;
    private final File_Control fileControl = new File_Control();

    public ArrayList<User> getUserList() {
        return userList;
    }

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Job> jobList = new ArrayList<>();

    public ArrayList<Location> getLocationList() {
        return locationList;
    }

    private ArrayList<Location> locationList = new ArrayList<>();
    private ArrayList<JobCategory> jobCategoryList = new ArrayList<>();

    boolean gerardWork;

    /**
     * This is the Default constructor for the class.
     */
    public JSS()
    {
        /* This Boolean makes some test GUIs appear for testing purposes.
         * Should be set to false in the production version of the system.
         */
        this.gerardWork = false;

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
            importJobCategoryList("JobCategory.csv");

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

        try
        {
            this.messageIDloader();

            this.allMessages = new ArrayList<Message>();
        }
        catch (Exception e)
        {
            System.out.println("Could not set message ID counter");
            e.printStackTrace();
        }

        if (gerardWork)
        {
            System.out.println(LocalDate.now());
            Job job = jobList.get(1);
            Jobseeker jobseeker = (Jobseeker) userList.get(10);
            JobseekerControl control = new JobseekerControl(this,jobseeker, this.jobList,this.locationList,this.jobCategoryList);
            TestGUI test = new TestGUI(this,control);
            // JobSeekerApplication testJob = new JobSeekerApplication();
        }

        try
        {
            importApplicationList("Application.csv");
        } catch (Exception e)
        {
            System.out.println("FATAL ERROR!! Failed to import applications!!");
        }

    }
    /**
    non default constructor takes reason for needing constructor
     loads without normal boot processes
     **/
    public JSS (String reason)
    {
        System.out.println("reason non default JSS object created");
    }

    public int authenticateUser(String username, String password)
    {
        int userIndex = Validation.usernameIndex(userList, username);

        if(userIndex < 0)
        {
            return userIndex;

        }

        String encryptedPW = EncryptMe.encryptThisString(password);
        if(!encryptedPW.equals(userList.get(userIndex).getPassword()))
        {
            userIndex = -1;
            return userIndex;
        }

        if(!userList.get(userIndex).isActive())
        {
            userIndex = -2;
            return userIndex;
        }

        return userIndex;

    };

    //Verifies username/password & logs the user in.
    public void login(int userIndex) throws Exception
    {
        // 1. Verify username



//        boolean Exists = false;
//        int userIndex = 0;
//        for (User tmpUser : userList)
//        {
//            if (tmpUser.getUserName().equals(username))
//            {
//                //Match on username
//                Exists = true;
//                break;
//            }
//            userIndex++;
//        }
//
//        if (!Exists)
//        {
//            //We did not find a username matching the entered name
//            throw new Exception("Username doesn't exist!");
//        }
//
//        // 2. Verify password
//        boolean passwordMatch = false;
//
//        //hash user's password
//        String encryptedPW = EncryptMe.encryptThisString(password);
//        if (encryptedPW.equals(userList.get(userIndex).getPassword()))
//            passwordMatch = true;
//
//        if (!passwordMatch)
//        {
//            //This user's password did not match their stored password
//            throw new Exception("passwords do not match!");
//        }
//
//        //2.1 check if the account is locked
//
//        if (!userList.get(userIndex).isActive())
//        {
//            PromptGUI locked = new PromptGUI("This account has been locked.  Contact Administrator");
//            throw new Exception("Account Locked!");
//        }

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

                    PromptGUI error = new PromptGUI("Contact Administrator", e.getMessage());

                }

            case "Jobseeker":
                //Do something else
                JobseekerControl jobSeekerControl = new JobseekerControl(this,userList.get(userIndex), jobList, locationList, jobCategoryList);
                break;

            case "Recruiter":
                //Launch Recruiter Control
                RecruiterControl recruiterControl = new RecruiterControl(this,userList.get(userIndex), jobList, locationList, jobCategoryList, userList);
                break;

            default:
                throw new Exception("error logging user in!");
        }
    }

    //Method to import application from csv to memory
    public void importApplicationList(String applicationFile) throws Exception
    {
        File_Control io = new File_Control();
        String applicationContent = "";
        try
        {
            applicationContent = io.readFile(applicationFile);
        }
        catch (Exception e)
        {
            System.out.println("Error failed to read files");
        }
        if (applicationContent.trim().isEmpty())
            throw new Exception("File cannot be empty");

        //split each application into an array
        String[] application = applicationContent.split("\n");

        //iteration through each application array
        for (int i = 1; i < application.length; i++)
        {
            //if line is empty, skip it.
            if (application[i].isEmpty())
            {
                System.out.println("Warning: empty line in Application.csv at line: " + i + ", skipping...");
                continue;
            }

            //split each application into applicationDetails
            //messageID, jobID, status
            String[] applicationDetails = application[i].split(",");

            //find messages for applications in csv then import application to applicationList
            String msgContent = "";
            try
            {
                msgContent = io.readFile("messages.csv");
            }
            catch (Exception e)
            {
                System.out.println("Error failed to read files");
            }

            //Split each message into an array
            //
            String[] message = msgContent.split("\n");
            for (int j = 0; j < message.length; j++)
            {
                //if line is empty, skip it.
                if (message[j].isEmpty())
                {
                    System.out.println("Warning: empty line in messages.csv at line: " + i + ", skipping...");
                    continue;
                }

                String[] messageDetails = message[j].split(",");
                //check if message is an application
                if (Integer.parseInt(messageDetails[0]) == Integer.parseInt(applicationDetails[0]))
                {

                    //Parses newlines and commas for message header
                    String messageHead = messageDetails[4];
                    messageHead = messageHead.replaceAll("`",",");
                    messageHead = messageHead.replaceAll("~","\n");

                    //Parses newlines and commas for message body
                    String messageBody = messageDetails[5];
                    messageBody = messageBody.replaceAll("`",",");
                    messageBody = messageBody.replaceAll("~","\n");

                    //split date into three integers
                    String dateStr = messageDetails[7];

                    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    //formatter = formatter.withLocale(Locale.US);
                    LocalDate date = LocalDate.parse(dateStr);


                    //messageID, hasReceived, senderID, receiverID, header, body, jobID, sentDate
                    importApplication(Integer.parseInt(messageDetails[0]), false, Integer.parseInt(messageDetails[2]),
                            Integer.parseInt(messageDetails[3]), messageHead, messageBody, Integer.parseInt(applicationDetails[1]), date);
                }
            }
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
            String[] jobDetails = job[i].split(",",9);
            //replaces all "|" in the jobDescription with a new line "\n"
            String jobDescription = jobDetails[8].replaceAll("\\|","\n");

            importJob(Integer.parseInt(jobDetails[0]), jobDetails[1], jobDetails[2], Integer.parseInt(jobDetails[3]), jobDetails[4],
                        jobDetails[5], Integer.parseInt(jobDetails[6]), Integer.parseInt(jobDetails[7]), jobDescription);
        }
    }

    //Method to import job category list from csv into memory.
    public void importJobCategoryList(String JobCategoryFile) throws Exception
    {
        File_Control io = new File_Control();
        String jobCategoryContent = "";
        try
        {
            jobCategoryContent = io.readFile(JobCategoryFile);
        }
        catch (Exception e)
        {
            System.out.println("Error failed to read JobCategory.csv");
        }
        if (jobCategoryContent.trim().isEmpty())
            throw new Exception("Error File cannot be empty");

        //Split each jobcategory into an array
        String[] jobCategory = jobCategoryContent.split("\n");

        //iterate through each array, ignore first line!!
        for (int i = 1; i < jobCategory.length; i++)
        {
            //if line is empty, skip it.
            if (jobCategory[i].isEmpty())
            {
                System.out.println("Warning: empty line in " + JobCategoryFile + ".csv at line: " + i + ", skipping...");
                continue;
            }

            //split each category into jobCategoryDetails
            //jobTitle, jobCategory 1
            String[] jobCategoryDetails = jobCategory[i].split(",");
            try
            {
                JobCategory category = new JobCategory(Integer.parseInt(jobCategoryDetails[0]), jobCategoryDetails[1], jobCategoryDetails[2]);
                jobCategoryList.add(category);

            } catch (Exception e)
            {
                System.out.println("Error failed to import a category, check your parameters!");
            }
        }
    }

    /* NO LONGER USED
    public void updateSubCategory(String JobSubCategoryFile) throws Exception
    {
        File_Control reader = new File_Control();

        //update sub categories
        String subCategoryContent = "";
        try
        {
            subCategoryContent = reader.readFile(JobSubCategoryFile);
        } catch (Exception e)
        {
            System.out.println("Failed to read JobKeywords.csv");
        }

        String[] subCategory = subCategoryContent.split("\n");

        //ignore first line!!
        for (int i = 1; i < subCategory.length; i++)
        {
            String[] subCategoryDetails = subCategory[i].split(",");

            //Search through the jobCategoryList for the jobTitle then append keywords to that category
            //NOTE can optimize search by first sorting out the database - not required extra functionality.
            int index = 0;
            for (JobCategory tmpCategory : jobCategoryList)
            {
                if (tmpCategory.getJobTitle().equalsIgnoreCase(subCategoryDetails[0]))
                {

                    jobCategoryList.get(index).appendSubCategory(subCategoryDetails[1]);
                }
                else
                {
                    System.out.println("Error: cannot find job title for that sub category. Skipping...");
                }
                index++;
            }
        }
    }
    */

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

    //arraylist starts at 0 but usernames start at 1

    public String retrieveUsername(int userID)
    {
        userID -=1;
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

    //Method to import application to applicationList arrayList
    public void importApplication(int messageID, boolean hasReceived, int senderID, int receiverID, String header, String text, int jobID, LocalDate sentDate)
    {
        try
        {
            Application application = new Application(messageID, hasReceived, senderID, receiverID, header, text, jobID, sentDate);
            applicationList.add(application);
            //link application to jobs
            Job myJob = findJob(jobList, jobID);
            myJob.getApplications().add(application);
        } catch (Exception e)
        {
            System.out.println("Error failed to import application, check your parameters!");
        }
    }

    //Method to import location from csv to memory
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

    /**
     * Imports existing job from a .csv file
     * @param jobID  the ID of the job to be imported
     * @param jobTitle the title of the job
     * @param employer the employer
     * @param recruiterID the ID of the recruiter who created the job post
     * @param jobType the type of the job
     * @param jobStatus the status of the job: advertised, draft or archived
     * @param salary the proposed salary
     * @param locationID the ID that links to the jobs location
     * @param jobDescription a brief description of the job
     */

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


    /**
     *  Imports the user for the existing .csv file and creates an object of Administrator, Recruiter or Jobseeker
     * @param userID the user's user ID
     * @param firstName their first name
     * @param lastName their surname
     * @param userName their username
     * @param password their password
     * @param userType their user type
     * @param active their status as being active or not - generally if active is false an administrator will have
     *               locked the account
     */
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

    //Create application and links it to the job
    public Application createApplication(boolean hasReceived, int senderID, int receiverID, String header, String text, int jobID, LocalDate sentDate) throws Exception
    {
        //Create application

        //Gerard's way of incrementing messageID
        int messageID = issueMessageID();
        //Nam's way of incrementing messageID - only works if allMessages is loaded on JSS launch
        //int messageID = allMessages.size() + 1;
        try
        {
            Application application = new Application(messageID, hasReceived, senderID, receiverID, header, text, jobID, sentDate);

            //Link application to job
            Job job = findJob(jobList, application.getJobRef());
            job.getApplications().add(application);

            //convert newlines and commas in message body and header to store in csv
            header = header.replaceAll("\n","~");
            header = header.replaceAll(",","`");
            text = text.replaceAll("\n","~");
            text = text.replaceAll(",","`");

            //write application to Application csv.
            this.saveApplication(messageID, hasReceived, senderID, receiverID, header, text, jobID, sentDate);
            return application;
        }
        catch (Exception e)
        {
            System.out.println("Error failed to create application.");
        }
        throw new Exception("Error failed to create application!");
    }


    /**
     * Method that creates a new user, either a recruiter or job seeker
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param userName the users chosen username
     * @param password the users selected password
     * @param userType being recruiter or jobseeker
     * @param userLocation the location of the user
     * @param userEmail the users email
     * @param userPhone the users phone number
     * @throws Exception thrown if the username already exists or there is an issue with creating the user
    */
    public void createUser(String firstName, String lastName, String userName, String password, String userType, Location userLocation, String userEmail, String userPhone) throws Exception
    {
        //Validate username
        for (User tmpUser : userList)
        {
            if (tmpUser.getUserName().equalsIgnoreCase(userName))
            {
                throw new Exception("Username already exists!");
            }
        }
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

                //write new user contact info to user-contact.csv
                this.saveContactInfo(userID, userLocation, userEmail, userPhone);
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

                //write new user contact info to user-contact.csv
                this.saveContactInfo(userID, userLocation, userEmail, userPhone);
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
                userList.add(admin);

                //write new recruiter to users.csv
                this.saveUser(userID, firstName, lastName, userName, encryptPW,userType,true);

                //write new user contact info to user-contact.csv
                this.saveContactInfo(userID, userLocation, userEmail, userPhone);
            } catch (Exception e)
            {
                System.out.println("Error failed to create admin, check your parameters!");
            }
        }
    }

    /** Method to link all the job databases together
     *
     * @param jobKeywordFile
     * @param jobSkillsFile
     * @throws Exception
     */

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
            //NOTE can optimize search by first sorting out the database - not required extra functionality.
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

    //Method to save Application to Application.csv
    public void saveApplication(int messageID, boolean hasReceived, int senderID, int receiverID, String header, String text, int jobID, LocalDate sentDate)
    {
        try
        {
            //write to application.csv
            String applicationData = messageID + "," + jobID + ",Pending";
            File_Control io = new File_Control();
            io.writeFile("Application.csv", applicationData);

            //write to messages.csv
            String messageData = messageID + "," + "pending" + "," + senderID + "," + receiverID + "," + header + "," + text + "," + jobID + "," + sentDate;
            io.writeFile("messages.csv", messageData);

        } catch (Exception e)
        {
            System.out.println("Error failed to save application into csv.");
        }

    }

    //Method to save user to users.csv
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
        System.out.println();
        boolean hasMail = false;

//        ArrayList<Message> messages = new ArrayList<>();

        String rawInput = "";
        try {
            rawInput = fileControl.readFile("messages.csv");
        } catch (Exception e) {
            System.out.println("Error failed to read messages!");
        }
        rawInput = rawInput.replaceAll("~", "n");
        rawInput = rawInput.replaceAll("`", ",");
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
                int messageTo = Integer.parseInt(messageDetails[3]);
                int sender = Integer.parseInt(messageDetails[2]);
                int messageID = Integer.parseInt(messageDetails[0]);
                String dateStr = messageDetails[7];
                LocalDate date = LocalDate.parse(dateStr);
                //if it is for the user checking, add it to their list
                if (messageTo == userIndex)
                {
                    hasMail =true;
                    User temp = this.userList.get(userIndex-1);
                    boolean hasReceived = false;

                    if (messageDetails[1].equalsIgnoreCase("sent"))
                    {
                        hasReceived =true;
                    }

                    if (messageDetails[4].equalsIgnoreCase("Application"))
                    {
                        Application application = new Application(messageID, hasReceived, sender, messageTo, messageDetails[4], messageDetails[5], date);
                        int jobRef = Integer.parseInt(messageDetails[6]);
                        application.setJobRef(jobRef);
                        temp.addMessage(application);
                        this.allMessages.add(application);

                    }
                    else if (messageDetails[4].contains("INVITATION"))
                    {
                        //int messageID, int senderID, int receiverID, String header, String text, LocalDate sentDate, int jobID)
                        int jobRef = Integer.parseInt(messageDetails[6]);
                        Invitation invitation = new Invitation(messageID,sender,messageTo,messageDetails[4], messageDetails[5], date,jobRef);
                        temp.addMessage(invitation);
                        this.allMessages.add(invitation);
                    }
                    else
                    {

                        Message message = new Message(messageID, hasReceived, sender, messageTo, messageDetails[4], messageDetails[5], date);
                        temp.addMessage(message);
                        this.allMessages.add(message);

                    }

                }

            }
        } catch (Exception e)
        {
            System.out.println("message read error" + e.getMessage());
            e.printStackTrace();
        }

        return hasMail;
    }

    /**
     * This method creates a new Message Object and saves it to the database.
     * @param messageID   an Integer containing the ID number of the Message.
     * @param hasReceived a Boolean describing if the Message has been received or not.
     * @param senderID    an Integer containing the User ID number of the sender.
     * @param receiverID  an Integer containing the User ID number of the recipient.
     * @param header      a String containing the subject line of the Message.
     * @param body        a String containing the body of the Message.
     * @param jobRef      an Integer containing the ID number of the Job referred to
     *                    in this Message.
     * @param date        a LocalDate describing the date on which this Message was created.
     */
    public void storeMessage(int messageID, boolean hasReceived, int senderID, int receiverID, String header, String body, int jobRef, LocalDate date)
    {
        //TODO: newline is "\n" not n - check with Gerard
        header = header.replaceAll(",","`");
        body = body.replaceAll(",", "`");
        header = header.replaceAll("n","~");
        body = body.replaceAll("n","~");


        String status = "pending";
        if (hasReceived == true)
        {
            status = "sent";
        }
        String message = messageID + "," + status + "," + senderID + "," + receiverID + "," + header+"," + body+ "," + jobRef + "," + date;

        try
        {
            File_Control io = new File_Control();
            io.writeFile("messages.csv", message);
        } catch (Exception e)
        {
            System.out.println("Error failed to save user into csv.");
        }
    }

    /**
     * This method creates a new list of Messages and marks them as sent appropriately.
     * @param message the Message Object to be added to the message list.
     */
    public void markAsSent(Message message)
    {
        // create ArrayList of all messages
        ArrayList<Message> messageList = this.allMessages;
        int messageIndex = message.getMessageID()-1;
        // System.out.println("message index is " + messageIndex);
        // remove the message to update the status of and replace with updated status

        // essageList.remove(messageIndex);
        // System.out.println("JSS800 messageID to remove " + messageList.get(messageIndex).getMessageID());
        // messageList.add(messageIndex,message);

        messageList.set(messageIndex,message);

        //clear the old file
            try
            {
                File_Control fileControl = new File_Control();
                fileControl.clearFile("messages.csv");
            } catch (IOException e)
            {
                System.out.println("MARK AS SENT ISSUE");
                e.printStackTrace();
            }

            //write new message list to file

    for (Message each:messageList)
    {
        int ID = each.getMessageID();
        boolean status = each.isHasReceived();
        int sender = each.getSenderID();
        int destination = each.getReceiverID();
        String header = each.getHeader();
        String body = each.getBody();
        LocalDate date = each.getSentDate();
        int jobRef = -1;
        if (each instanceof Application)
        {
            jobRef = ((Application) each).getJobRef();
        }
        this.storeMessage(ID, status, sender,destination,header,body, jobRef, date);

    }
    this.allMessages = messageList;
    }



    /**
     * This method locks or unlocks a User's account
     * @param index an Integer containing the index in the User list of the
     *              User to lock/unlock.
     */
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

    /**
     * This method saves a new list of Users into the database.
     */
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

    /**
     * This method checks to see if a particular User is currently locked out.
     * @param userNumber an Integer containing the ID number of the User to be checked.
     * @return a Boolean representing if this User is currently locked out or not.
     */
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


    /**
     * This method finds a User in the list of Users using their ID number.
     * @param userID an Integer containing the ID number of the User to be found.
     * @return the User Object relating to the specified ID number.
     */
    public User getUserByID(int userID)
    {
        User inQuestion = userList.get(userID);
        return inQuestion;
    }

    /**
     * This method displays each Job in the list of Jobs. Used for testing purposes.
     */
    public void displayJob()
    {
        for (Job tmpJob : jobList)
        {
            System.out.println(tmpJob.toString());
        }
    }

    /**
     * This method increments the nextMessageID field appropriately and returns
     * a MessageID for a new Message.
     * @return
     */
    public int issueMessageID()
    {
        int messageID = nextMessageID;

        nextMessageID+=1;

        return messageID;
    }

    /**
     * This method counts the Messages in the database and sets the nextMessageID field
     * appropriately.
     */
    private void messageIDloader()
    {
        String rawInput = "";
        try
        {
            rawInput = fileControl.readFile("messages.csv");
        } catch (Exception e)
        {
            System.out.println("Error failed to read messages!");
        }
        rawInput = rawInput.replaceAll("~", "n");
        rawInput = rawInput.replaceAll("`", ",");
        String[] messageString = rawInput.split("\n");
        int currentMessageID = 0;
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
               currentMessageID = Integer.parseInt(messageDetails[0]);

            }
            nextMessageID = currentMessageID+1;
            if (nextMessageID == 0)
            {
                nextMessageID =1;
            }
        }
        catch (Exception e)
        {
            System.out.println("message read error" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method retrieves Messages from the database.
     * @param messageID an Integer containing the ID number of the Message to be
     *                  read from the database.
     * @return the Message Object created from the database.
     */
    public Message retrieveMessage(int messageID)
    {

        Message message = new Message();

        String rawInput = "";
        try
        {
            rawInput = fileControl.readFile("messages.csv");
        } catch (Exception e)
        {
            System.out.println("Error failed to read messages!");
        }
        rawInput = rawInput.replaceAll("~", "n");
        rawInput = rawInput.replaceAll("`", ",");

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
                int readID = Integer.parseInt(messageDetails[0]);

                //if it is for the user checking, add it to their list
                if (readID == messageID)
                {

                    int sender = Integer.parseInt(messageDetails[1]);
                    int messageTo = Integer.parseInt(messageDetails[2]);



                    message.setMessageID(readID);
                    message.setSenderID(sender);
                    message.setReceiverID(messageTo);
                    message.setHeader(messageDetails[3]);
                    message.setBody(messageDetails[4]);
                    //check whether message is pending or sent!
                    if (messageDetails[0].equalsIgnoreCase("sent"))
                    {
                        message.setHasReceived(true);
                    }
                }

                this.allMessages.add(message);
            }
        }
        catch (Exception e)
        {
            System.out.println("message read error" + e.getMessage());
            e.printStackTrace();
        }

        return message;
    }

    /**
     * This method saves contact information for a particular User.
     * @param userId        an Integer containing the ID number of the User.
     * @param userLocation  a Location which has been set as the User's Location.
     * @param userEmail     a String containing the User's email address.
     * @param userPhone     a String containing the User's phone number.
     */
    public void saveContactInfo(int userId, Location userLocation, String userEmail, String userPhone )
    {

        String file = "user-contact.csv";
        try {

            String newLocationString = Integer.toString(userId) + "," + String.valueOf(userLocation.getLocationID()) + "," + userEmail + "," + userPhone;
//
            File_Control fc = new File_Control();

            //get all contacts except current (not relevant for new user)
            ArrayList<String> all = fc.removeById(userId, file);

            //add current users new skill set
            all.add(newLocationString);

            //clear existing csv
            fc.clearFile(file);

            //re-write new contact info
            fc.writeListToFile(all, file);
            System.out.println("Updated contact information has been saved to user-contact.csv");
        }
        catch(Exception e)
        {

        }
    }

    public ArrayList<Job> getJobList()
    {
        return jobList;
    }

    public void setJobList(ArrayList<Job> jobList)
    {
        this.jobList = jobList;
    }


    /**
     * Method that switches job to archived for the administrators
     * @param jobID
     */
    public void switchJobStatus(int jobID)
    {
        File_Control io = new File_Control();
        try
        {
            Job job = findJob(jobList, jobID);


        System.out.println("this is the job to be deleted: " + jobID);
        job.setJobStatus("Archived");
        //setting the job category to a default so it will save

        JobCategory category = new JobCategory(jobID);


        try {
            io.updateJob(job.getJobID(), job.getJobTitle(), job.getEmployer(), job.getRecruiterID(),
                    job.getJobType(), job.getJobStatus(), job.getSalary(), job.getLocationID(), job.getJobDescription(), job.getSkills(), category);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        //update JobList
        jobList.remove(jobID-1);
        jobList.add(job);
        this.setJobList(jobList);

        this.removeJobAlert(job);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method provides an alert to Users that a specific Job has been de-listed.
     * @param job the Job which has been de-listed.
     */
    protected void removeJobAlert(Job job)
    {

        int recruiterID = job.getRecruiterID();
        ArrayList<Application> applications = job.getApplications();
        int messageID = this.issueMessageID();
        String header = "Job Ref: " + job.getJobID() + ". " + job.getJobTitle() +" has been withdrawn";
        String body = "This job has been removed and applications rejected";
        Message recruiterAlert = new Message(messageID,recruiterID,recruiterID,header,body,LocalDate.now());
        allMessages.add(recruiterAlert);
        this.storeMessage(messageID,false,recruiterID,recruiterID,header,body,-1,LocalDate.now());

        for (Application each: applications)
        {
            int jobseekerID = each.getSenderID();
            messageID = this.issueMessageID();
            body = "Dear Jobseeker: this job has been withdrawn and therefore your application rejected";
            Message alert = new Message(messageID,recruiterID,jobseekerID,header,body,LocalDate.now());
           allMessages.add(alert);
           //jobRef is -1 as this is a message, although it does concern a job this is identified in the header
           this.storeMessage(messageID,false,recruiterID,jobseekerID,header,body,-1,LocalDate.now());
        }
    }

    /**
     * This method looks for a specific Job in the list of Jobs and returns it.
     * @param jobList   an ArrayList of Jobs in the system.
     * @param ID        an Integer containing the Job ID number to be searched for.
     * @return          a Job which matches the specified ID number.
     * @throws Exception Exceptions are thrown if the specified Job cannot be found.
     */
    public Job findJob(ArrayList<Job> jobList, int ID) throws Exception
    {
        Job myJob = null;
        for (Job tmpJob : jobList)
        {
            if (tmpJob.getJobID() == ID)
            {
                myJob = tmpJob;
                return myJob;
            }
        }
        throw new Exception("Error: Job doesn't exist!");
    }

    public Message findMessage(ArrayList<Message> allMessages, int messageID) throws Exception
    {
        Message myMessage = null;
        for (Message tmpMsg : allMessages)
        {
            if (tmpMsg.getMessageID() == messageID)
            {
                myMessage = tmpMsg;
                return myMessage;
            }
        }
        throw new Exception("Error: Message doesn't exist");
    }


    /**
     * A method that retrieves from file and returns the messages sent to an individual user
     * @param userID the user ID for whos messages to return
     * @return an ArrayList of Messages
     */
    public ArrayList<Message> listUserMessages(int userID)
    {
        ArrayList<Message> userMessages = new ArrayList<Message>();

        File_Control fileControl = new File_Control();
        String rawInput= "";
        try
        {
            rawInput += fileControl.readFile("messages.csv");
        }
        catch (Exception e)
        {
            System.out.println("failed to read file fetching user messages");
            e.printStackTrace();
        }

        rawInput = rawInput.replaceAll("~", "n");
        rawInput = rawInput.replaceAll("`", ",");
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

                int destination = Integer.parseInt(messageDetails[3]);

                if (destination == userID)
                {

                    int messageID = Integer.parseInt(messageDetails[0]);
                    boolean hasReceived = false;
                    if (messageDetails[1].equalsIgnoreCase("sent"))
                    {
                        hasReceived = true;
                    }
                    int sender = Integer.parseInt(messageDetails[2]);

                    String header = messageDetails[4];
                    String body = messageDetails[5];
                    LocalDate date = LocalDate.parse(messageDetails[7]);

                    if (header.equalsIgnoreCase("Application"))
                    {
                        Message toAdd = new Application(messageID, sender, destination, header, body, date);
                        toAdd.setHasReceived(hasReceived);
                        userMessages.add(toAdd);
                    } else if (header.equalsIgnoreCase("Invitation"))
                    {
                        Message toAdd = new Invitation();
                        PromptGUI a = new PromptGUI("come look at JSS 1349 and set up invitation");
                        toAdd.setHasReceived(hasReceived);
                        userMessages.add(toAdd);
                    } else
                    {
                        Message toAdd = new Message(messageID, sender, destination, header, body, date);
                        toAdd.setHasReceived(hasReceived);
                        userMessages.add(toAdd);
                    }

                }
            }
        }
        catch (Exception e)
        {
            System.out.println("message read error" + e.getMessage());
        }


        return userMessages;
    }
}

