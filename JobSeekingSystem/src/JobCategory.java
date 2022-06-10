public class JobCategory
{
    private String jobTitle;
    private String jobCategoryPrimary;
    private String jobCategorySecondary;
    private String jobCategoryTertiary;

    public JobCategory()
    {
    }

    public JobCategory(String title, String jobCategoryPrimary)
    {
        this.jobTitle = title;
        this.jobCategoryPrimary = jobCategoryPrimary;
    }

    public JobCategory(String title, String jobCategoryPrimary, String jobCategorySecondary)
    {
        this.jobTitle = title;
        this. jobCategoryPrimary = jobCategoryPrimary;
        this.jobCategorySecondary = jobCategorySecondary;
    }

    public JobCategory(String title, String jobCategoryPrimary, String jobCategorySecondary, String jobCategoryTertiary)
    {
        this.jobTitle = title;
        this.jobCategoryPrimary = jobCategoryPrimary;
        this.jobCategorySecondary = jobCategorySecondary;
        this.jobCategoryTertiary = jobCategoryTertiary;
    }

    public String getJobTitle()
    {
        return jobTitle;
    }

    public String getJobCategoryPrimary()
    {
        return jobCategoryPrimary;
    }

    public String getJobCategorySecondary()
    {
        return jobCategorySecondary;
    }

    public String getJobCategoryTertiary()
    {
        return jobCategoryTertiary;
    }

    public void setJobTitle(String title)
    {
        this.jobTitle = title;
    }

    public void setJobCategoryPrimary(String jobCategoryPrimary) {
        this.jobCategoryPrimary = jobCategoryPrimary;
    }

    public void setJobCategorySecondary(String jobCategorySecondary) {
        this.jobCategorySecondary = jobCategorySecondary;
    }

    public void setJobCategoryTertiary(String jobCategoryTertiary) {
        this.jobCategoryTertiary = jobCategoryTertiary;
    }
}
