/**
 * This class is the Control class for the Jobseeker User Object.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class JobseekerControl implements Communication
{
    private Search mainSearch;
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;
    private Jobseeker jobseeker;
    private JSS program;

    /**
     * This is the Default constructor for the class.
     */
    public JobseekerControl() {
    }

    /**
     * This is a Non-default constructor for the class.
     * @param user      a User to be controlled by this control class.
     * @param jobs      an ArrayList of Jobs which contains all the Jobs currently
     *                  in the system.
     * @param locations an ArrayList of Locations which contains all the Locations
     *                  in the system.
     * @param categories an ArrayList of JobCategories which contains all the Job
     *                  Categories in the system.
     */
    public JobseekerControl(User user, ArrayList<Job> jobs, ArrayList<Location> locations,
                            ArrayList<JobCategory> categories) {

        jobseeker = new Jobseeker(user.getUserID(), user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword(),user.isActive(), true);
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        mainSearch = new Search(this, jobList, locationList, jobCategoryList);
        JobSeekerHomeGUI jobSeekerHomeGUI = new JobSeekerHomeGUI(this, jobCategoryList, locationList, jobseeker);
    }

    /**
     * This is a Non-default constructor for the class.
     * @param program    a JSS Object which represents the main system class.
     * @param user       a User to be controlled by this control class.
     * @param jobs       an ArrayList of Jobs which contains all the Jobs currently
     *                   in the system.
     * @param locations  an ArrayList of Locations which contains all the Locations
     *                   in the system.
     * @param categories an ArrayList of JobCategories which contains all the Job
     *                   Categories in the system.
     */
    public JobseekerControl(JSS program, User user, ArrayList<Job> jobs, ArrayList<Location> locations,
                            ArrayList<JobCategory> categories) {

        jobseeker = new Jobseeker(user.getUserID(), user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword(),user.isActive(), true);
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        mainSearch = new Search(this, jobList, locationList, jobCategoryList);
        JobSeekerHomeGUI jobSeekerHomeGUI = new JobSeekerHomeGUI(this, jobCategoryList, locationList, jobseeker);
        this.program = program;
    }

    /**
     * This method creates a new Application for this Jobseeker.
     * @param jobID an Integer containing the ID number of the Job this Jobseeker
     *              wishes to apply for.
     * @param text  a String containing the body of the Application message.
     * @return      a Boolean representing success or failure of the Application operation.
     */
    public boolean apply(int jobID, String text)
    {
        boolean sent = false;
        JSS program = this.relayProgram();
        Job applyFor = jobList.get(jobID);
        int recruiterID = applyFor.getRecruiterID();
        LocalDate date = LocalDate.now();
        try
        {
            int messageID = program.issueMessageID();
            Application application = new Application(messageID,this.jobseeker.getUserID(), recruiterID, "Application", text, date);
            application.setJobRef(applyFor.getJobID());

            sent = this.sendMessage(program,application);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sent;
    }

    /**
     * This is the display method for the class.
     */
    public void display(){
        System.out.println("Main Program: " + program);
        System.out.println("Jobseeker: " + jobseeker.toString());
        System.out.println("Main Search: " + mainSearch);
        System.out.println("Jobs: {");
        for (Job job : jobList) {
            System.out.println(job.toString());
        }
        System.out.println("}");
        System.out.println("Locations: {");
        for (Location place : locationList) {
            System.out.println(place.toString());
        }
        System.out.println("}");
        System.out.println("Job Categories: {");
        for (JobCategory jobCat : jobCategoryList) {
            System.out.println(jobCat.toString());
        }
        System.out.println("}");
    }

    /**
     * This method obtains the email address for the controlled Jobseeker.
     * @return a String containing the Jobseeker's email address.
     */
    public String getEmail()
    {
        return jobseeker.getEmail();
    }

    /**
     * This method obtains the full name of the controlled Jobseeker.
     * @return a String containing the Jobseeker's full name.
     */
    public String getFullName()
    {
        return jobseeker.getFullName();
    }

    /**
     * This is the Accessor method for the jobCategoryList field.
     * @return  an ArrayList of JobCategories representing all the Job
     *          Categories in the system.
     */
    public ArrayList<JobCategory> getJobCategoryList() {
        return jobCategoryList;
    }

    /**
     * This is the Accessor method for the jobList field.
     * @return  an ArrayList of Jobs representing all the Jobs in the system.
     */
    public ArrayList<Job> getJobList() {
        return jobList;
    }

    /**
     * This is the Accessor method for the jobseeker field.
     * @return a Jobseeker which is controlled by this class.
     */
    public Jobseeker getJobseeker() {
        return jobseeker;
    }

    /**
     * This method obtains the Location of the controlled jobseeker.
     * @return a Location which is the jobseeker's current location.
     */
    public Location getLocation()
    {
        return jobseeker.getLocation();
    }

    /**
     * This is the Accessor method for the locationList field.
     * @return  an ArrayList of Locations representing all the Locations currently
     *          in the system.
     */
    public ArrayList<Location> getLocationList() {
        return locationList;
    }

    /**
     * This is the Accessor method for the mainSearch field.
     * @return a Search Object which is used to execute job searches.
     */
    public Search getMainSearch() {
        return mainSearch;
    }

    /**
     * This method obtains the phone number for the controlled Jobseeker.
     * @return a String containing the controlled Jobseeker's phone number.
     */
    public String getPhone()
    {
        return jobseeker.getPhone();
    }

    /**
     * This is the Accessor method for the program field.
     * @return a JSS Object which represents the main system.
     */
    public JSS getProgram() {
        return program;
    }

    /**
     * This method obtains the list of skills for the controlled Jobseeker.
     * @return an ArrayList of Strings containing all the Jobseeker's skills.
     */
    public ArrayList getSkills()
    {
        ArrayList<String> skills = jobseeker.getSkills();
        return skills;
    }

    /**
     * This method executes a Job Search for the user according to specified
     * parameters and returns matching Jobs as search results.
     * @param jobDesc           a String containing the search description the
     *                          user has searched for.
     * @param categoryPrimary   a String containing the primary category the
     *                          user has searched for.
     * @param categorySecondary a String containing the sub category the
     *                          user has searched for.
     * @param location          a String containing the location the user has
     *                          searched for.
     * @param fullTime          a Boolean representing if the user would like to
     *                          search for Jobs of this type.
     * @param partTime          a Boolean representing if the user would like to
     *                          search for Jobs of this type.
     * @param casual            a Boolean representing if the user would like to
     *                          search for Jobs of this type.
     * @param salMin            an Integer representing the minimum salary the
     *                          user would like to search for.
     * @param salMax            an Integer representing the maximum salary the
     *                          user would like to search for.
     * @param seekerSkills      an ArrayList of Strings representing a list of
     *                          the Jobseeker's skills.
     * @return                  a TreeMap of Integer Keys with ArrayList<Job> values.
     *                          The TreeMap will be sorted in descending order
     *                          when obtained, so higher Integer keys represent
     *                          lists of Jobs that match search parameters more highly.
     */
    public TreeMap<Integer, ArrayList<Job>> jobSearch(String jobDesc, String categoryPrimary,
                          String categorySecondary, String location, boolean fullTime, boolean partTime,
                          boolean casual, int salMin, int salMax, ArrayList<String> seekerSkills)
    {
        System.out.println("Searching...");
        TreeMap<Integer, ArrayList<Job>> searchResults = new TreeMap<>(Collections.reverseOrder());
        try {
            searchResults = mainSearch.jobSearch(jobDesc, categoryPrimary, categorySecondary, location, fullTime, partTime,
            casual, salMin, salMax, seekerSkills);
        }
        catch (Exception e) {
            PromptGUI notification = new PromptGUI(e.getMessage());
        }
        return searchResults;
    }

    /**
     * This method finds Jobs in the system which can be recommended to the
     * current Jobseeker.
     * @return  a TreeMap of Integer keys with ArrayList<Job> values. The TreeMap
     *          will be sorted in descending order when obtained, so higher Integer
     *          keys represent lists of Jobs that match search parameters more highly.
     */
    public TreeMap<Integer, ArrayList<Job>> recommendedSearch() {
        Location seekerLocation = jobseeker.getLocation();
        TreeMap<Integer, ArrayList<Job>> searchResults = new TreeMap<>();
        try {
            searchResults = mainSearch.recommendedJobs(seekerLocation, jobseeker.getSkills());
        }
        catch (Exception e) {
            PromptGUI notification = new PromptGUI(e.getMessage());
        }
        return searchResults;
    }

    /**
     * This method saves the User's current contact information to the database.
     */
    public void saveContactInfo()
    {

        String file = "user-contact.csv";
        try {

            String newLocationString = Integer.toString(jobseeker.getUserID()) + "," + String.valueOf(getLocation().getLocationID()) + "," + getEmail() + "," + getPhone();

            File_Control fc = new File_Control();

            //get all contact details except for current jobseeker
            ArrayList<String> all = fc.removeById(jobseeker.getUserID(), file);

            //add updated contact info to full contact array
            all.add(newLocationString);

            //clear contact csv
            fc.clearFile(file);

            //rewrite contact info
            fc.writeListToFile(all, file);
            System.out.println("Updated contact information has been saved to user-contact.csv");
        }
        catch(Exception e)
        {

        }
    }

    /**
     * This method saves the User's current list of skills into the database.
     */
    public void saveSkills()
    {

        String file = "jobseeker-skills.csv";
        try {

            ArrayList<String> newskills = getSkills();
            newskills.add(0,Integer.toString(jobseeker.getUserID()));
            String skillsString = "";
            for (int i = 0; i <newskills.size(); i++) {

                if(i != newskills.size()-1)
                {
                    skillsString = skillsString + newskills.get(i) +",";
                }
                else
                {
                    skillsString = skillsString + newskills.get(i);
                }
            }

            //save all skills from skills csv
            File_Control fc = new File_Control();

            //get all skills except for current user
            ArrayList<String> all = fc.removeById(jobseeker.getUserID(), file);

            //add current users new skill set
            all.add(skillsString);

            /*for(String line : all){
                System.out.println(line);
            }*/

            // clear skills csv
            fc.clearFile(file);

            // rewrite skill set to skills csv
            fc.writeListToFile(all, file);
            System.out.println("Updated skill information has been saved to jobseeker-skill.csv");
        }
        catch(Exception e)
        {

        }
    }

    /**
     * This is an overrided Accessor method for the jobseeker field.
     * @return a Jobseeker which is controlled by this control class.
     */
    @Override
    public User relayUser()
    {
        return this.jobseeker;
    }

    /**
     * This is an overrided Accessor method for the program field.
     * @return a JSS class which represents the main system.
     */
    @Override
    public JSS relayProgram()
    {
        return this.program;
    }

    /**
     * This method sets the email for the controlled Jobseeker.
     * @param email a String containing the Jobseeker's email address.
     */
    public void setEmail(String email)
    {
        jobseeker.setEmail(email);
    }

    /**
     * This is the Mutator method for the jobCategoryList field.
     * @param jobCategories an ArrayList of JobCategories containing all of the
     *                      Job Categories in the system.
     */
    public void setJobCategoryList(ArrayList<JobCategory> jobCategories) {
        jobCategoryList = jobCategories;
    }

    /**
     * This is the Mutator method for the jobList field.
     * @param jobs an ArrayList of Jobs representing all the Jobs in the system.
     */
    public void setJobList(ArrayList<Job> jobs) {
        jobList = jobs;
    }

    /**
     * This is the Mutator method for the jobseeker field.
     * @param user a Jobseeker Object representing the User being controlled
     *             by this control class.
     */
    public void setJobseeker(Jobseeker user) {
        jobseeker = user;
    }

    /**
     * This method sets the Jobseeker's location.
     * @param location a Location which the Jobseeker should be set at.
     */
    public void setLocation(Location location)
    {
        jobseeker.setLocation(location);
    }

    /**
     * This is the Mutator method for the locationList field.
     * @param locations an ArrayList of Locations representing all the Locations
     *                  in the system.
     */
    public void setLocationList(ArrayList<Location> locations) {
        locationList = locations;
    }

    /**
     * This is the Mutator method for the mainSearch field.
     * @param search a Search Object which will execute all searches for this class.
     */
    public void setMainSearch(Search search) {
        mainSearch = search;
    }

    /**
     * This method sets the Jobseeker's phone number.
     * @param phone a String containing the Jobseeker's phone number.
     */
    public void setPhone(String phone)
    {
        jobseeker.setPhone(phone);
    }

    /**
     * This is the Mutator method for the program field.
     * @param main a JSS Object which represents the mains system.
     */
    public void setProgram(JSS main) {
        program = main;
    }

    /**
     * This method sets the Jobseeker's skills.
     * @param skills an ArrayList of Strings containing all the Jobseeker's skills.
     */
    public void setSkills(ArrayList<String> skills)
    {
        jobseeker.setSkills(skills);
    }

}
