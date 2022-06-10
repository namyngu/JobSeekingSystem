import java.util.ArrayList;

public class RecruiterControl extends BaseController
{
    private Search mainSearch;

    public RecruiterControl()
    {
        mainSearch = new Search();
    }

    public RecruiterControl(User recruiter)
    {
        RecruiterHomeGUI recruiterHomeGUI = new RecruiterHomeGUI(this);
        mainSearch = new Search();
    }

    public void homePage()
    {

    }

    public void seekerSearch(String location, ArrayList<String> requiredSkills)
    {
        System.out.println("Searching...");
        try
        {
            ArrayList<Jobseeker> someList = mainSearch.seekerSearch(location, requiredSkills);

            // Debug search results:
            System.out.println("Results: \n");
            for (Jobseeker seeker : someList) {
                System.out.println(seeker.toString());
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
