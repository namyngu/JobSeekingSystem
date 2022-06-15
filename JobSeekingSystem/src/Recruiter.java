/**
 * This class represents the Recruiter User role in the system.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */
import java.util.ArrayList;

public class Recruiter extends User
{
    private ArrayList<Job> jobs;
    private ArrayList<Invitation> invitations;
    private int totalAdverts;
    private int totalApplications;

    /**
     * This is the Default constructor for the class.
     */
    public Recruiter(){
        jobs = new ArrayList<>();
        invitations = new ArrayList<>();
        totalAdverts = -1;
        totalApplications = -1;

        updateRecruiterJobs(jobs);
    }

    /**
     * This is a Non-default constructor for the class.
     * @param userID    an Integer representing the Recruiter's User ID number.
     * @param firstName a String containing the User's first name.
     * @param lastName  a String containing the User's last name.
     * @param userName  a String containing the User's user name.
     * @param password  a String containing the User's chosen password.
     */
    public Recruiter(int userID, String firstName, String lastName, String userName, String password)
    {
        super(userID, firstName, lastName, userName, password, "Recruiter");
        this.jobs = new ArrayList<>();
        this.invitations = new ArrayList<>();
        this.totalAdverts = 0;
        this.totalApplications = 0;

        updateRecruiterJobs(jobs);
    }

    /**
     * This is a Non-default constructor for the class.
     * @param userID    an Integer representing the Recruiter's User ID number.
     * @param firstName a String containing the User's first name.
     * @param lastName  a String containing the User's last name.
     * @param userName  a String containing the User's user name.
     * @param password  a String containing the User's chosen password.
     * @param active    a Boolean describing if the User is currently active in
     *                  the system or not.
     */
    public Recruiter(int userID, String firstName, String lastName, String userName, String password, boolean active)
    {
        super(userID, firstName, lastName, userName, password, "Recruiter",active);
        this.jobs = new ArrayList<>();
        this.invitations = new ArrayList<>();
        this.totalAdverts = 0;
        this.totalApplications = 0;

        updateRecruiterJobs(jobs);
    }

    /**
     * This is the display method for the class.
     */
    public void display() {
        System.out.println("Jobs: " + jobs);
        System.out.println("Invitations: " + invitations);
        System.out.println("Advertised Jobs: " + totalAdverts);
        System.out.println("Total Applications: " + totalApplications);
    }

    /**
     * This is the Accessor method for the invitations field.
     * @return  an ArrayList of Invitations containing all of this Recruiter's
     *          invitations.
     */
    public ArrayList<Invitation> getInvitations() {
        return invitations;
    }

    /**
     * This is the Accessor method for the jobs field.
     * @return  an ArrayList of Jobs containing all the Jobs this Recruiter
     *          has created.
     */
    public ArrayList<Job> getJobs() {
        return jobs;
    }

    /**
     * This is the Accessor method for the totalAdverts field.
     * @return  an Integer representing the number of advertisements created
     *          by this Recruiter.
     */
    public int getTotalAdverts() {
        return totalAdverts;
    }

    /**
     * This is the Accessor method for the totalApplications field.
     * @return  an Integer representing the number of Applications which have
     *          been received for Jobs created by this Recruiter.
     */
    public int getTotalApplications() {
        return totalApplications;
    }

    /**
     * This is the Mutator method for the invitations field.
     * @param invites   An ArrayList of Invitations.
     */
    public void setInvitations(ArrayList<Invitation> invites) {
        invitations = invites;
    }

    /**
     * This is the Mutator method for the jobs field.
     * @param newJobs   An ArrayList of Jobs.
     */
    public void setJobs(ArrayList<Job> newJobs) {
        jobs = newJobs;
    }

    /**
     * This is the Mutator method for the totalAdverts field.
     * @param adverts   An Integer representing the new number of total
     *                  advertisements for this Recruiter.
     */
    public void setTotalAdverts(int adverts) {
        totalAdverts = adverts;
    }

    /**
     * This is the Mutator method for the totalApplications field.
     * @param apps  An Integer representing the new number of total Applications
     *              for this Recruiter.
     */
    public void setTotalApplications(int apps) {
        totalApplications = apps;
    }

    public void updateRecruiterJobs (ArrayList<Job> jobList)
    {
        ArrayList<Job> myJob = new ArrayList<>();
        //search through all jobs for the recruiter's job
        for (Job tmpJob : jobList)
        {
            if (tmpJob.getRecruiterID() == getUserID())
            {
                myJob.add(tmpJob);
            }
        }
    }
}
