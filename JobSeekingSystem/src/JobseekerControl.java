import java.util.ArrayList;

public class JobseekerControl implements Communication
{

    private Search mainSearch;
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;


    private Jobseeker jobseeker;
    private JSS program;
    public JobseekerControl() {
    }
    public JobseekerControl(User user, ArrayList<Job> jobs, ArrayList<Location> locations,
                            ArrayList<JobCategory> categories) {

        jobseeker = new Jobseeker(user.getUserID(), user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword(),user.isActive(), true);
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        mainSearch = new Search(this, jobList, locationList, jobCategoryList);
        JobSeekerHomeGUI jobSeekerHomeGUI = new JobSeekerHomeGUI(this, jobCategoryList, locationList, jobseeker);
    }

    public JobseekerControl(JSS program, User user, ArrayList<Job> jobs, ArrayList<Location> locations,
                            ArrayList<JobCategory> categories) {

        jobseeker = new Jobseeker(user.getUserID(), user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword(),user.isActive(), true);
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        mainSearch = new Search(this, jobList, locationList, jobCategoryList);
        JobSeekerHomeGUI jobSeekerHomeGUI = new JobSeekerHomeGUI(this, jobCategoryList, locationList, jobseeker);
        this.program = program;
    }

    public ArrayList<Job> jobSearch(String jobDesc, String categoryPrimary,
                          String categorySecondary, String location, boolean fullTime, boolean partTime,
                          boolean casual, int salMin, int salMax, ArrayList<String> seekerSkills)
    {
        System.out.println("Searching...");
        ArrayList<Job> searchResults = new ArrayList<>();
        try {
            searchResults = mainSearch.jobSearch(jobDesc, categoryPrimary, categorySecondary, location, fullTime, partTime,
            casual, salMin, salMax, seekerSkills);
        }
        catch (Exception e) {
            PromptGUI notification = new PromptGUI(e.getMessage());
        }
        return searchResults;
    }

    public ArrayList<Job> recommendedSearch() {
        // TODO: Get the jobSeeker's location somehow.
        // TODO: Mocked in for now.
        Location seekerLocation = locationList.get(0);
        ArrayList<Job> searchResults = new ArrayList<>();
        try {
            searchResults = mainSearch.recommendedJobs(seekerLocation, jobseeker.getSkills());
        }
        catch (Exception e) {
            PromptGUI notification = new PromptGUI(e.getMessage());
        }
        return searchResults;
}

    public float matchScore(ArrayList<String> keywords, ArrayList skills)
    {
        float matchPercentage = 0;
        return matchPercentage;
    };

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
            System.out.println("Updated skill information has been saved to jobseeker-skill.csv");
        }
        catch(Exception e)
        {

        }
    }

    public void saveContactInfo()
    {

        String file = "user-contact.csv";
        try {

            String newLocationString = Integer.toString(jobseeker.getUserID()) + "," + String.valueOf(getLocation().getLocationID()) + "," + getEmail() + "," + getPhone();
//
            File_Control fc = new File_Control();

            //get all contact details except for current jobseeker
            ArrayList<String> all = fc.removeById(jobseeker.getUserID(), file);

            //add updated contact info to full contact array
            all.add(newLocationString);

            //clear contact csv
            fc.clearFile(file);

            //rewrite contact info
            fc.writeListToFile(all, file);
            System.out.println("Updated contact information has been saved to user-contact.csv");
        }
        catch(Exception e)
        {

        }
    }

    public Location getLocation()
    {
        return jobseeker.getLocation();
    }

    public String getEmail()
    {
        return jobseeker.getEmail();
    }

    public String getPhone()
    {
        return jobseeker.getPhone();
    }

    public void setLocation(Location location)
    {
        jobseeker.setLocation(location);
    }

    public void setEmail(String email)
    {
        jobseeker.setEmail(email);
    }

    public void setPhone(String phone)
    {
        jobseeker.setPhone(phone);
    }

//method that creates new application
    //returns true if sent
    //sends via send message method in interface
    public boolean apply(int jobID, String text)
    {
        boolean sent = false;
        JSS program = this.relayProgram();
        Job applyFor = jobList.get(jobID);
        int recruiterID = applyFor.getRecruiterID();
        try
        {
            int messageID = program.issueMessageID();
            Application application = new Application(messageID,this.jobseeker.getUserID(), recruiterID, "Application", text);
            application.setJobRef(applyFor.getJobID());

            sent = this.sendMessage(program,application);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sent;
    }

    @Override
    public User relayUser()
    {
        return this.jobseeker;
    }

    @Override
    public JSS relayProgram()
    {
        return this.program;
    }
}
