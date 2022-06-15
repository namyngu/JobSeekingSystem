/**
 * This class represents the Control class which will control the Recruiter Object.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import java.util.ArrayList;
import java.util.TreeMap;

public class RecruiterControl {

    private Recruiter recruiter;
    private Search mainSearch;
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;
    private ArrayList<User> userList;
    private ArrayList<Jobseeker> jobseekerList;

    /**
     * This is the Default constructor for the class.
     */
    public RecruiterControl() {
        mainSearch = new Search();
        recruiter = new Recruiter();
        jobList = new ArrayList<>();
        locationList = new ArrayList<>();
        jobCategoryList = new ArrayList<>();
        userList = new ArrayList<>();
        jobseekerList = new ArrayList<>();
    }

    /**
     * This is a Non-default constructor for the class.
     * @param recruiter a User which describes the Recruiter this class is controlling.
     */
    public RecruiterControl(Recruiter recruiter) {
        RecruiterHomeGUI recruiterHomeGUI = new RecruiterHomeGUI(this, locationList);
        this.recruiter = recruiter;
        mainSearch = new Search();

        updateRecruiterJobs(jobList);
    }

    /**
     * This is a Non-default constructor for the class.
     * @param user a User which describes the Recruiter this class is controlling.
     * @param jobs      an ArrayList containing all the Jobs in the system.
     * @param locations an ArrayList containing all the Locations in the system.
     * @param categories an ArrayList containing all the Job Categories in the system.
     * @param userList  an ArrayList containing all the Users in the system.
     */
    public RecruiterControl(User user, ArrayList<Job> jobs, ArrayList<Location> locations,
                            ArrayList<JobCategory> categories, ArrayList<User> userList) {
        this.userList = userList;
        jobseekerList = buildSeekerList();
        this.recruiter = new Recruiter(user.getUserID(), user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword(),user.isActive());;
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        mainSearch = new Search(this, jobList, locationList, jobCategoryList, jobseekerList);

        //update recruiter's jobs
        updateRecruiterJobs(jobList);
        //Launch recruiter HomeGUI
        RecruiterHomeGUI recruiterHomeGUI = new RecruiterHomeGUI(this, locationList);
    }

    /**
     * This method populates the jobseekerList field using data from the
     * userList field.
     * @return an ArrayList of all active Jobseekers in the system.
     */
    public ArrayList<Jobseeker> buildSeekerList() {
        ArrayList<Jobseeker> returnList = new ArrayList<>();
        for (User user : userList) {
            if (user.getUserType().equals("Jobseeker") && user.isActive()) {
                // This user is a jobseeker and they are active in the system.
                Jobseeker seeker = new Jobseeker(user.getUserID(), user.getFirstName(),
                        user.getLastName(), user.getUserName(), user.getPassword(),
                        user.isActive());
                returnList.add(seeker);
            }
        }
        return returnList;
    }

    /**
     * This is the display method for this class.
     */
    public void display() {
        System.out.println("MainSearch: " + mainSearch);
        System.out.println("Recruiter: " + recruiter);
        System.out.println("Job List: " + jobList);
        System.out.println("Location List: " + locationList);
        System.out.println("Job Category List: " + jobCategoryList);
        System.out.println("User List: " + userList);
        System.out.println("Jobseeker List:" + jobseekerList);
    }

    /**
     * This is the Accessor method for the jobCategoryList field.
     * @return  an ArrayList of JobCategories representing all the Job Categories
     *          in the system.
     */
    public ArrayList<JobCategory> getJobCategoryList() {
        return jobCategoryList;
    }

    /**
     * This is the Accessor method for the jobList field.
     * @return an ArrayList of Jobs representing all the Jobs in the system.
     */
    public ArrayList<Job> getJobList() {
        return jobList;
    }

    /**
     * This is the Accessor method for the jobseekerList field.
     * @return  an ArrayList of Jobseekers representing all the Users in
     *          the system which are of Type "Jobseeker".
     */
    public ArrayList<Jobseeker> getJobseekerList () { return jobseekerList;}

    /**
     * This is the Accessor method for the locationList field.
     * @return  an ArrayList of Locations representing all the Locations in the system.
     */
    public ArrayList<Location> getLocationList() {
        return locationList;
    }

    /**
     * This is the Accessor method for the mainSearch field.
     * @return a Search Object which is used by this Control class for executing searches.
     */
    public Search getMainSearch() {return mainSearch;}

    /**
     * This is the Accessor method for the recruiter field.
     * @return the Recruiter which is controlled by this control class.
     */
    public Recruiter getRecruiter() {
        return recruiter;
    }

    /**
     * This is the Accessor method for the userList field.
     * @return an ArrayList of Users representing all of the Users in the system.
     */
    public ArrayList<User> getUserList() {
        return userList;
    }

    /**
     * This method passes search information received from the GUI to the mainSearch
     * Object and executes a search for Jobseekers who match the parameters.
     * @param location      a String describing the location the Recruiter has
     *                      searched in.
     * @param requiredSkills an ArrayList of Strings describing the skills the
     *                       Recruiter would like to search for.
     * @return an ArrayList of Jobseekers which match the search parameters.
     */
    public TreeMap<Integer, ArrayList<Jobseeker>> seekerSearch(String location, ArrayList<String> requiredSkills)
    {
        System.out.println("Searching...");
        TreeMap<Integer, ArrayList<Jobseeker>> searchList = new TreeMap<>();
        try
        {
            searchList = mainSearch.seekerSearch(location, requiredSkills);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return searchList;
    }

    /**
     * This is the Mutator method for the jobCategoryList field.
     * @param jobCategoryList an ArrayList of Job Categories representing all the
     *                        Job Categories in the system.
     */
    public void setJobCategoryList(ArrayList<JobCategory> jobCategoryList) {
        this.jobCategoryList = jobCategoryList;
    }

    /**
     * This is the Mutator method for the jobList field.
     * @param jobList an ArrayList of Jobs representing all the Jobs in the system.
     */
    public void setJobList(ArrayList<Job> jobList) {
        this.jobList = jobList;
    }

    /**
     * This is the Mutator method for the jobseekerList field.
     * @param seekers an ArrayList of Jobseekers representing all the Jobseekers
     *                in the system.
     */
    public void setJobseekerList(ArrayList<Jobseeker> seekers) {
        jobseekerList = seekers;
    }

    /**
     * This is the Mutator method for the locationList field.
     * @param locationList an ArrayList of Locations representing all the Locations
     *                     in the system.
     */
    public void setLocationList(ArrayList<Location> locationList) {
        this.locationList = locationList;
    }

    /**
     * This is the Mutator method for the mainSearch field.
     * @param search a Search Object to be used by this Control class for search execution.
     */
    public void setMainSearch(Search search) {
        this.mainSearch = search;
    }

    /**
     * This is the Mutator method for the recruiter field.
     * @param recruiter a Recruiter Object which is the User to be controlled
     *                  by this control class.
     */
    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    /**
     * This is the Mutator method for the userList field.
     * @param users an ArrayList of Users which represents all the Users
     *              in the system.
     */
    public void setUserList(ArrayList<User> users) {
        userList = users;
    }

    //Method to add all recruiter's job in.
    public void updateRecruiterJobs (ArrayList<Job> jobList)
    {
        ArrayList<Job> myJob = new ArrayList<>();
        //search through all jobs for the recruiter's job
        for (Job tmpJob : jobList)
        {
            if (tmpJob.getRecruiterID() == recruiter.getUserID())
            {
                myJob.add(tmpJob);
            }
        }
        recruiter.setJobs(myJob);
    }

}
