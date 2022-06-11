import java.util.ArrayList;

public class JobCategory
{
    private String jobTitle;
    private String jobPrimaryCategory;
    private ArrayList<String> jobSubCategory;

    public JobCategory()
    {
        System.out.println("Error: JobCategory class accepts two parameters at least.");
    }

    public JobCategory(String title)
    {
        this.jobTitle = title;
        jobSubCategory = new ArrayList<>();
    }

    public JobCategory(String title, String jobPrimaryCategory)
    {
        this.jobTitle = title;
        this.jobPrimaryCategory = jobPrimaryCategory;
    }

    public JobCategory(String title, String jobPrimaryCategory, ArrayList<String> jobSubCategory)
    {
        this.jobTitle = title;
        this.jobPrimaryCategory = jobPrimaryCategory;
        this.jobSubCategory = jobSubCategory;
    }

    public void appendSubCategory(String subCategory)
    {
        this.jobSubCategory.add(subCategory);
    }


    //setters and getters
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobPrimaryCategory() {
        return jobPrimaryCategory;
    }

    public void setJobPrimaryCategory(String jobPrimaryCategory) {
        this.jobPrimaryCategory = jobPrimaryCategory;
    }

    public ArrayList<String> getJobSubCategory() {
        return jobSubCategory;
    }

    public void setJobSubCategory(ArrayList<String> jobSubCategory) {
        this.jobSubCategory = jobSubCategory;
    }
}
