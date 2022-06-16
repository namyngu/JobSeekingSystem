/**
 * This class represents a JobCategory, which is a key/value pairing of a Job and a Category.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

public class JobCategory
{
    private int jobID;
    private String jobPrimaryCategory;
    private String jobSubCategory;

    /**
     * This is the Default constructor for the class. Note the error message -
     * Job Categories should not contain blank information.
     */
    public JobCategory()
    {
        System.out.println("Error: JobCategory class accepts two parameters at least.");
    }

    /**
     * This is a Non-default constructor for the class.
     * @param jobID an Integer containing the ID number of the Job this Category
     *              is for.
     */
    public JobCategory(int jobID)
    {
        this.jobID = jobID;
        this.jobPrimaryCategory = "Other";
    }

    /**
     * This is a Non-default constructor for this class.
     * @param jobID              an Integer containing the ID number of a Job this
     *                           Category is for.
     * @param jobPrimaryCategory a String containing the primary category for this
     *                           Job Category.
     */
    public JobCategory(int jobID, String jobPrimaryCategory)
    {
        this.jobID = jobID;
        this.jobPrimaryCategory = jobPrimaryCategory;
        this.jobSubCategory = "Other";
    }

    /**
     * This is a Non-default constructor for this class.
     * @param jobID              an Integer containing the ID number of a Job this
     *                           Category is for.
     * @param jobPrimaryCategory a String containing the primary category for this
     *                           Job Category.
     * @param jobSubCategory     a String containing the sub category for this
     *                           Job Category.
     */
    public JobCategory(int jobID, String jobPrimaryCategory, String jobSubCategory)
    {
        this.jobID = jobID;
        this.jobPrimaryCategory = jobPrimaryCategory;
        this.jobSubCategory = jobSubCategory;
    }

    /**
     * This is the display method for this class.
     */
    public void display(){
        System.out.println("Job ID: " + jobID);
        System.out.println("Primary Category: " + jobPrimaryCategory);
        System.out.println("Sub Category: " + jobSubCategory);
    }

    /**
     * This is the Accessor method for the jobID field.
     * @return  an Integer containing the ID number of the Job this Job Category
     *          represents.
     */
    public int getJobID() {
        return jobID;
    }

    /**
     * This is the Accessor method for the jobPrimaryCategory field.
     * @return a String containing the primary category for this Job Category.
     */
    public String getJobPrimaryCategory() {
        return jobPrimaryCategory;
    }

    /**
     * This is the Accessor method for the jobSubCategory field.
     * @return a String containing the sub category for this Job Category.
     */
    public String getJobSubCategory() {
        return jobSubCategory;
    }

    /**
     * This is the Mutator method for the jobID field.
     * @param jobID an Integer containing the ID number of the Job this Job
     *              Category represents.
     */
    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    /**
     * This is the Mutator method for the jobPrimaryCategory field.
     * @param jobPrimaryCategory a String containing the primary category this
     *                           Job Category should contain.
     */
    public void setJobPrimaryCategory(String jobPrimaryCategory) {
        this.jobPrimaryCategory = jobPrimaryCategory;
    }

    /**
     * This is the Mutator method for the jobSubCategory field.
     * @param jobSubCategory a String containing the sub category this Job
     *                       Category should contain.
     */
    public void setJobSubCategory(String jobSubCategory) {
        this.jobSubCategory = jobSubCategory;
    }
}
