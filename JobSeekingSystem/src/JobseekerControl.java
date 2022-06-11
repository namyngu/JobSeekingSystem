import java.util.ArrayList;

public class JobseekerControl
{

    private Search mainSearch;
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;

    private Jobseeker jobseeker;
    public JobseekerControl() {
    }
    public JobseekerControl(User user, ArrayList<Job> jobs, ArrayList<Location> locations,
                            ArrayList<JobCategory> categories) {

        jobseeker = new Jobseeker(user.getUserID(), user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword(),user.isActive());
        JobSeekerHomeGUI jobSeekerHomeGUI = new JobSeekerHomeGUI(this);

        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        mainSearch = new Search(this, jobList, locationList, jobCategoryList);
    }


    public void jobSearch(String jobDesc, String categoryPrimary,
                          String categorySecondary, String location, boolean fullTime, boolean partTime,
                          boolean casual, float salMin, float salMax, ArrayList<String> seekerSkills)
    {
        System.out.println("Searching...");
        try
        {
            ArrayList<Job> someList = mainSearch.jobSearch(jobDesc, categoryPrimary, categorySecondary, location, fullTime, partTime,
            casual, salMin, salMax, seekerSkills);

            // Debug search results:
            System.out.println("Results: \n");
            for (Job job : someList) {
                System.out.println(job.toString());
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public float matchScore(ArrayList<String> keywords, ArrayList skills)
    {
        float matchPercentage = 0;
        return matchPercentage;
    };

    public void modifySkill()
    {
        System.out.println("Searching...");
    }


    public void viewApplications()
    {

    }

    public void viewJobDetails(int jobId)
    {

    }

    public void viewInvitations()
    {

    }

    public String getFullName()
    {
        return jobseeker.getFullName();
    }

    public ArrayList getSkills()
    {
        ArrayList<String> skills = jobseeker.getSkills();
        System.out.println("in controller");
        return skills;
    }






}
