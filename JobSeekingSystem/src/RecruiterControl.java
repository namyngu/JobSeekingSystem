import java.util.ArrayList;

public class RecruiterControl {

    private User recruiter;
    private Search mainSearch;
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;
    private ArrayList<User> userList;

    public RecruiterControl() {
        mainSearch = new Search();
    }

    public RecruiterControl(User recruiter) {
        RecruiterHomeGUI recruiterHomeGUI = new RecruiterHomeGUI(this, locationList);
        this.recruiter = recruiter;
        mainSearch = new Search();
    }

    public RecruiterControl(User recruiter, ArrayList<Job> jobs, ArrayList<Location> locations,
                            ArrayList<JobCategory> categories, ArrayList<User> userList) {
        this.recruiter = recruiter;
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        mainSearch = new Search(this, jobList, locationList, jobCategoryList);
        this.userList = userList;

        RecruiterHomeGUI recruiterHomeGUI = new RecruiterHomeGUI(this, locationList);
    }

    public ArrayList<Jobseeker> seekerSearch(String location, ArrayList<String> requiredSkills)
    {
        System.out.println("Searching...");
        ArrayList<Jobseeker> searchList = new ArrayList<>();
        try
        {
            searchList = mainSearch.seekerSearch(location, requiredSkills);

            // Debug search results:
            System.out.println("Results: \n");
            for (Jobseeker seeker : searchList) {
                System.out.println(seeker.toString());
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return searchList;
    }

    public User getRecruiter() {
        return recruiter;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public ArrayList<Job> getJobList() {
        return jobList;
    }

    public void setJobList(ArrayList<Job> jobList) {
        this.jobList = jobList;
    }

    public ArrayList<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<Location> locationList) {
        this.locationList = locationList;
    }

    public ArrayList<JobCategory> getJobCategoryList() {
        return jobCategoryList;
    }

    public void setJobCategoryList(ArrayList<JobCategory> jobCategoryList) {
        this.jobCategoryList = jobCategoryList;
    }


}
