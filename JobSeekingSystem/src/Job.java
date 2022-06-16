/**
 * This class represents a Job, which can be advertised and applied for.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import java.util.ArrayList;

public class Job
{
    private int jobID;
    private String jobTitle;
    private String employer;
    private int recruiterID;
    private String jobType;
    private String jobStatus;
    private int salary;
    private int locationID;
    private String jobDescription;
    private ArrayList<String> skills;
    private ArrayList<Application> applications;
    private ArrayList<String> keywords;


    /**
     * This is the Default constructor for the class.
     */
    public Job()
    {
        jobID = -1;
        jobTitle = "jobTitle";
        employer = "employer";
        recruiterID = -1;
        jobType = "jobType";
        jobStatus = "Draft";
        salary = 0;
        locationID = 0;
        jobDescription = "jobDescription";
        skills = new ArrayList<>();
        applications = new ArrayList<>();
        keywords = new ArrayList<>();
    }

    /**
     * This is a Non-default constructor for the class.
     * @param jobID         an Integer containing the ID number for the Job.
     * @param jobTitle      a String containing the Job title.
     * @param employer      a String containing the Employer of the Job.
     * @param recruiterID   an Integer containing the ID number of the Recruiter
     *                      which created this Job.
     * @param jobType       a String containing the type for this Job.
     * @param jobStatus     a String containing the status for this Job.
     * @param salary        an Integer containing the salary for this Job.
     * @param locationID    an Integer containing the ID number for the Location
     *                      this Job takes place in.
     * @param jobDescription a String containing the description of this Job.
     */
    public Job(int jobID, String jobTitle, String employer, int recruiterID, String jobType, String jobStatus, int salary, int locationID, String jobDescription) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.employer = employer;
        this.recruiterID = recruiterID;
        this.jobType = jobType;
        this.jobStatus = jobStatus;
        this.salary = salary;
        this.locationID = locationID;
        this.jobDescription = jobDescription;
        skills = new ArrayList<>();
        applications = new ArrayList<>();
        keywords = new ArrayList<>();
    }

    /**
     * This is a Non-default constructor for this class.
     * @param jobID         an Integer containing the ID number for the Job.
     * @param jobTitle      a String containing the Job title.
     * @param employer      a String containing the Employer of the Job.
     * @param recruiterID   an Integer containing the ID number of the Recruiter
     *                      which created this Job.
     * @param jobType       a String containing the type for this Job.
     * @param jobStatus     a String containing the status for this Job.
     * @param salary        an Integer containing the salary for this Job.
     * @param locationID    an Integer containing the ID number for the Location
     *                      this Job takes place in.
     * @param jobDescription a String containing the description of this Job.
     * @param skills        an ArrayList of Strings containing all the skills which
     *                      are required to complete this Job.
     * @param applications  an ArrayList of Applications containing all the
     *                      Applications which have been filed for this Job.
     * @param keywords      an ArrayList of Strings representing a list of the
     *                      keywords related to this Job.
     */
    public Job(int jobID, String jobTitle, String employer, int recruiterID, String jobType, String jobStatus, int salary,
               int locationID, String jobDescription, ArrayList<String> skills, ArrayList<Application> applications, ArrayList<String> keywords)
    {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.employer = employer;
        this.recruiterID = recruiterID;
        this.jobType = jobType;
        this.jobStatus = jobStatus;
        this.salary = salary;
        this.locationID = locationID;
        this.jobDescription = jobDescription;
        this.skills = skills;
        this.applications = applications;
        this.keywords = keywords;
    }

    /**
     * This method adds a new Application to the selected Job.
     * @param application an Application to be added to this Job's list of Applications.
     */
    public void appendApplication(Application application)
    {
        this.applications.add(application);
    }

    /**
     * This method adds a new keyword for this Job.
     * @param keyword a String to be added to this Job's list of keywords.
     */
    public void appendKeyword(String keyword)
    {
        this.keywords.add(keyword);
    }

    /**
     * This method adds a new skill for this Job.
     * @param skill a String to be added to this Job's list of skills.
     */
    public void appendSkill(String skill)
    {
        this.skills.add(skill);
    }

    /**
     * This is the display method for this class.
     */
    public void display(){
        System.out.println("Job:" + jobID);
        System.out.println("Title: " + jobTitle);
        System.out.println("Employer: " + employer);
        System.out.println("Recruiter ID: " + recruiterID);
        System.out.println("Type: " + jobType);
        System.out.println("Status: " + jobStatus);
        System.out.println("Salary: " + salary);
        System.out.println("Location ID: " + locationID);
        System.out.println("Description: " + jobDescription);
        System.out.println("Skills: {");
        for (String skill : skills) {
            System.out.println(skill + "\n");
        }
        System.out.println("}");
        System.out.println("Applications: {");
        for (Application app : applications) {
            System.out.println(app.toString() + "\n");
        }
        System.out.println("}");
        System.out.println("Keywords: {");
        for (String keyword : keywords) {
            System.out.println(keyword + "\n");
        }
        System.out.println("}");
    }

    /**
     * This is the Accessor method for the applications field.
     * @return an ArrayList of Applications for this Job.
     */
    public ArrayList<Application> getApplications() {
        return applications;
    }

    /**
     * This is the Accessor method for the employer field.
     * @return a String containing the employer for this Job.
     */
    public String getEmployer() {
        return employer;
    }

    /**
     * This is the Accessor method for the jobDescription field.
     * @return a String containing this Job's description.
     */
    public String getJobDescription() {
        return jobDescription;
    }

    /**
     * This is the Accessor method for the jobID field.
     * @return an Integer containing the ID number for this Job.
     */
    public int getJobID() {
        return jobID;
    }

    /**
     * This is the Accessor method for the jobStatus field.
     * @return a String representing the current status of this Job.
     */
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * This is the Accessor method for the jobTitle field.
     * @return a String containing the title of this Job.
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * This is the Accessor method for the jobType field.
     * @return a String containing this Job's type.
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * This is the Accessor method for the keywords field.
     * @return  an ArrayList of Strings representing a list of all the related
     *          keywords for this Job.
     */
    public ArrayList<String> getKeywords() {
        return keywords;
    }

    /**
     * This is the Accessor method for the locationID field.
     * @return  an Integer containing the ID number of the Location in which
     *          this Job takes place.
     */
    public int getLocationID() {
        return locationID;
    }

    /**
     * This is the Accessor method for the salary field.
     * @return an Integer containing the salary for this Job.
     */
    public int getSalary() {
        return salary;
    }

    /**
     * This is the Accessor method for the skill field.
     * @return  an ArrayList of Strings representing a list of the skills
     *          required to complete this Job.
     */
    public ArrayList<String> getSkills() {
        return skills;
    }

    /**
     * This is the Accessor method for the recruiterID field.
     * @return  an Integer containing the ID number of the Recruiter which
     *          created this Job.
     */
    public int getRecruiterID() {
        return recruiterID;
    }

    /**
     * This is the Mutator method for the applications field.
     * @param applications an ArrayList of Applications for this Job.
     */
    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }

    /**
     * This is the Mutator method for the employer field.
     * @param employer a String containing the employer for this Job.
     */
    public void setEmployer(String employer) {
        this.employer = employer;
    }

    /**
     * This is the Mutator method for the jobDescription field.
     * @param jobDescription a String containing the description of this Job.
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    /**
     * This is the Mutator method for the jobID field.
     * @param jobID an Integer containing the ID number of this Job.
     */
    public void setJobID(int jobID) {this.jobID = jobID;}

    /**
     * This is the Mutator method for the jobStatus field.
     * @param jobStatus a String containing the status of this Job.
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * This is the Mutator method for the jobTitle field.
     * @param jobTitle a String containing the title for this Job.
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * This is the Mutator method for the jobType field.
     * @param jobType a String containing the type of this Job.
     */
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    /**
     * This is the Mutator method for the keywords field.
     * @param keywords an ArrayList of Strings representing a list of all the
     *                 keywords relevant to this Job.
     */
    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * This is the Mutator method for the locationID field.
     * @param locationID an Integer containing the ID number of the Location which
     *                   should be associated with this Job.
     */
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    /**
     * This is the Mutator method for the salary field.
     * @param salary an Integer containing the salary for this Job.
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * This is the Mutator method for the skills field.
     * @param skills an ArrayList of Strings representing the list of skills which
     *               are required to complete this Job.
     */
    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    /**
     * This is the Mutator method for the recruiterID field.
     * @param recruiterID an Integer representing the ID number of the Recruiter
     *                    which created this Job.
     */
    public void setRecruiterID(int recruiterID) {
        this.recruiterID = recruiterID;
    }

    /**
     * This method returns information about this Job as a String.
     * @return a String containing all the information about this Job.
     */
    @Override
    public String toString() {
        return "Job{" +
                "jobID=" + jobID +
                ", jobTitle='" + jobTitle + '\'' +
                ", employer='" + employer + '\'' +
                ", recruiterID=" + recruiterID +
                ", jobType='" + jobType + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", salary=" + salary +
                ", locationID=" + locationID +
                ", jobDescription='" + jobDescription + '\'' +
                ", skills=" + skills +
                ", applications=" + applications +
                ", keywords=" + keywords +
                '}';
    }
}
