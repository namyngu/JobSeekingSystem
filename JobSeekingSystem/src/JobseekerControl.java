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
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        JobSeekerHomeGUI jobSeekerHomeGUI = new JobSeekerHomeGUI(this, jobCategoryList, locationList, jobseeker);
        mainSearch = new Search(this, jobList, locationList, jobCategoryList);
    }


    public ArrayList<Job> jobSearch(String jobDesc, String categoryPrimary,
                          String categorySecondary, String location, boolean fullTime, boolean partTime,
                          boolean casual, int salMin, int salMax, ArrayList<String> seekerSkills)
    {
        System.out.println("Searching...");
        ArrayList<Job> searchResults = new ArrayList<>();
        try
        {
            searchResults = mainSearch.jobSearch(jobDesc, categoryPrimary, categorySecondary, location, fullTime, partTime,
            casual, salMin, salMax, seekerSkills);
        }
        catch (Exception e)
        {
            PromptGUI notification = new PromptGUI(e.getMessage());
        }
        return searchResults;
    }

    public ArrayList<Job> recommendedSearch() {
        // TODO: Get the jobSeeker's location somehow.
        // TODO: Mocked in for now.
        Location seekerLocation = new Location();
        ArrayList<Job> searchResults = new mainSearch.seekerSearch(seekerLocation, jobseeker.getSkills());
        return searchResults;
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
        return skills;
    }

    public void setSkills(ArrayList<String> skills)
    {
        jobseeker.setSkills(skills);

    }

    public void saveSkills()
    {


        String file = "jobseeker-skills.csv";
        try {

            ArrayList<String> newskills = getSkills();
            newskills.add(0,Integer.toString(jobseeker.getUserID()));
            String skillsString = "";
            for (int i = 0; i <newskills.size(); i++) {

                if(i != newskills.size()-1)
                {
                    skillsString = skillsString + newskills.get(i) +",";
                }
                else
                {
                    skillsString = skillsString + newskills.get(i);
                }
            }

                //save all skills from skills csv
                File_Control fc = new File_Control();

                //get all skills except for current user
                ArrayList<String> all = fc.removeById(jobseeker.getUserID(), file);

                //add current users new skill set
                all.add(skillsString);

//                for(String line : all){
//                    System.out.println(line);
//                }
//                clear skills csv
                fc.clearFile(file);

//                rewrite skill set to skills csv
                fc.writeListToFile(all, file);

//                jobseeker.loadSkills();

        }
        catch(Exception e)
        {

        }


    }







}
