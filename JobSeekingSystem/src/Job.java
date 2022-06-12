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

    public void appendApplication(Application application)
    {
        this.applications.add(application);
    }

    public void appendKeyword(String keyword)
    {
        this.keywords.add(keyword);
    }

    public void appendSkill(String skill)
    {
        this.skills.add(skill);
    }

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

    //getters and setters
    public int getJobID() {
        return jobID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getEmployer() {
        return employer;
    }

    public int getRecruiterID() {
        return recruiterID;
    }

    public String getJobType() {
        return jobType;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public int getSalary() {
        return salary;
    }

    public int getLocationID() {
        return locationID;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public void setRecruiterID(int recruiterID) {
        this.recruiterID = recruiterID;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public void setJobID(int jobID) {this.jobID = jobID;}
}
