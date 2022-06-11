import java.util.ArrayList;

public class JobCategory
{
    private int jobID;
    private String jobPrimaryCategory;
    private String jobSubCategory;

    public JobCategory()
    {
        System.out.println("Error: JobCategory class accepts two parameters at least.");
    }

    public JobCategory(int jobID)
    {
        this.jobID = jobID;
        this.jobPrimaryCategory = "Other";
    }

    public JobCategory(int jobID, String jobPrimaryCategory)
    {
        this.jobID = jobID;
        this.jobPrimaryCategory = jobPrimaryCategory;
        this.jobSubCategory = "Other";
    }

    public JobCategory(int jobID, String jobPrimaryCategory, String jobSubCategory)
    {
        this.jobID = jobID;
        this.jobPrimaryCategory = jobPrimaryCategory;
        this.jobSubCategory = jobSubCategory;
    }

    //setters and getters
    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getJobPrimaryCategory() {
        return jobPrimaryCategory;
    }

    public void setJobPrimaryCategory(String jobPrimaryCategory) {
        this.jobPrimaryCategory = jobPrimaryCategory;
    }

    public String getJobSubCategory() {
        return jobSubCategory;
    }

    public void setJobSubCategory(String jobSubCategory) {
        this.jobSubCategory = jobSubCategory;
    }
}
