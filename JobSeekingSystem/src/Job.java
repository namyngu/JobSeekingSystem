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
    private String locationState;
    private String postCode;

    public Job(int jobID, String jobTitle, int recruiterID, String employer, String jobType, Boolean isAdvertised, int salary, ArrayList<String> skills, ArrayList<Application> applications, String locationState, String postCode)
    {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.recruiterID = recruiterID;
        this.employer = employer;
        this.jobType = jobType;
        this.isAdvertised = isAdvertised;
        this.salary = salary;
        this.skills = skills;
        this.applications = applications;
        this.locationState = locationState;
        this.postCode = postCode;
    }
}
