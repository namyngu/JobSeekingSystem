import java.util.ArrayList;

public class JobseekerControl
{

    private Search mainSearch;

    public JobseekerControl() {
    }
    public JobseekerControl(User jobseeker) {
        JobSeekerHomeGUI jobSeekerHomeGUI = new JobSeekerHomeGUI(jobseeker, this);
        mainSearch = new Search();
    }

    public void apply()
    {

    }

    public void createSkill()
    {

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
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
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





}
