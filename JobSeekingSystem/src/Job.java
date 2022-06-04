import java.util.ArrayList;

public class Job
{
    private int jobID;
    private String jobTitle;
    private int recruiterID;
    private String employer;
    private String jobType;
    private Boolean isAdvertised;
    private int salary;
    private ArrayList<String> skills;
    private ArrayList<Application> applications;
    private ArrayList<String> keywords;
    private String locationState;
    private String postCode;
    private String jobDescription;
    private String jobCategory;
    private String workingHours;
    private boolean advertised;
    private boolean archived;

    public Job(int jobID, String jobTitle, int recruiterID, String employer, String jobType, Boolean isAdvertised, int salary, ArrayList<String> skills, ArrayList<Application> applications, ArrayList<String> keywords, String locationState, String postCode, String jobDescription, String jobCategory, String workingHours, boolean advertised, boolean archived) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.recruiterID = recruiterID;
        this.employer = employer;
        this.jobType = jobType;
        this.isAdvertised = isAdvertised;
        this.salary = salary;
        this.skills = skills;
        this.applications = applications;
        this.keywords = keywords;
        this.locationState = locationState;
        this.postCode = postCode;
        this.jobDescription = jobDescription;
        this.jobCategory = jobCategory;
        this.workingHours = workingHours;
        this.advertised = advertised;
        this.archived = archived;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getRecruiterID() {
        return recruiterID;
    }

    public void setRecruiterID(int recruiterID) {
        this.recruiterID = recruiterID;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Boolean getAdvertised() {
        return isAdvertised;
    }

    public void setAdvertised(Boolean advertised) {
        isAdvertised = advertised;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public String getLocationState() {
        return locationState;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public boolean isAdvertised() {
        return advertised;
    }

    public void setAdvertised(boolean advertised) {
        this.advertised = advertised;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobID=" + jobID +
                ", jobTitle='" + jobTitle + '\'' +
                ", recruiterID=" + recruiterID +
                ", employer='" + employer + '\'' +
                ", jobType='" + jobType + '\'' +
                ", isAdvertised=" + isAdvertised +
                ", salary=" + salary +
                ", skills=" + skills +
                ", applications=" + applications +
                ", keywords=" + keywords +
                ", locationState='" + locationState + '\'' +
                ", postCode='" + postCode + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", jobCategory='" + jobCategory + '\'' +
                ", workingHours='" + workingHours + '\'' +
                ", advertised=" + advertised +
                ", archived=" + archived +
                '}';
    }
}
