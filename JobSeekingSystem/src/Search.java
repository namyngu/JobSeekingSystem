import java.util.ArrayList;
import java.util.TreeMap;

public class Search
{
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;
    private ArrayList<Jobseeker> jobseekerList;
    private int matchScore;
    private JobseekerControl myParent;

    // Default constructor.
    public Search() {
        myParent = null;
        jobList = new ArrayList<>();
        locationList = new ArrayList<>();
        jobCategoryList = new ArrayList<>();
        jobseekerList = new ArrayList<>();
    }

    // Non-default constructor.
    public Search(JobseekerControl parent, ArrayList<Job> jobs,
                  ArrayList<Location> locations, ArrayList<JobCategory> categories) {
        myParent = parent;
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        jobseekerList = new ArrayList<>();
    }

    // Method 1. Search for a list of matching jobs
    public ArrayList<Job> jobSearch(String jobDesc, String categoryPrimary,
        String categorySecondary, String location, boolean fullTime, boolean partTime,
        boolean casual, float salMin, float salMax, ArrayList<String> seekerSkills) throws Exception {

        // Setup a few variables.
        ArrayList<Job> results = new ArrayList<>();
        TreeMap<Integer, Job> scoredResults = new TreeMap<>();

        // TODO: For testing purposes, setup the seekerSkills.
        // TODO: Not needed for actual method when real skills
        // TODO: will be provided.

        seekerSkills.add("skill1");
        seekerSkills.add("skill2");
        seekerSkills.add("skill3");

        // Change search algorithm weightings here, if needed.
        // TODO: Description analysis should be changed to keywords
        int titleWeight = 35;
        int descWeight = 20;
        int skillWeight = 20;
        int primaryCatWeight = 15;
        int secondaryCatWeight = 10;

        // First let's apply filter data to filter out unmatching jobs
        // TODO: Cut out and put into separate filterJobs method

        // For every job, check the filters and exclude that job if it
        // does not match the filters.
        for (Job tmp : jobList) {
            // Setup a boolean to track if the job should be filtered out.
            boolean valid = true;

            // As soon as the job is filtered out, we will end checking.
            // If we reach the end of the check and the job is still valid,
            // we can add it to the list of Jobs to be considered for Search results.
            while (valid) {
                // Retrieve the job Salary.
                int salary = tmp.getSalary();

                // Precheck: Job Type specification must be supplied for
                // search to execute properly.
                if (!fullTime && !partTime && !casual) {
                    // Search has not specified any job type, search cannot be executed
                    throw new Exception("You must specify a Job Type!");
                }

                // Filter 1. Job must be currently advertised.
                if (!tmp.getJobStatus().equals("Advertised")) {
                    valid = false;
                }

                // Filter 2. Job must match Job Type search specification
                switch (tmp.getJobType()) {
                    case "Full-Time":
                        if (!fullTime) {
                            // Job does not match criteria
                            valid = false;
                        }
                        break;
                    case "Part-Time":
                        if (!partTime) {
                            // Job does not match criteria
                            valid = false;
                        }
                        break;
                    case "Casual":
                        if (!casual) {
                            // Job does not match criteria
                            valid = false;
                        }
                        break;
                    default:
                        break;
                }

                // 3. Job must match salary range
                if (Float.compare(salary, salMin) >= 0 && Float.compare(salary, salMax) <= 0) {
                    //Job is within salary range specified
                } else {
                    // Job is outside desired salary range
                    valid = false;
                }

                // 4. Job must include some part of location
                // If the Job postcode or Job State name appears in the searched
                // location text entry then I have considered this filter to be satisfied.

                int thisJobPostCode = locationList.get(tmp.getLocationID()-1).getPostcode();
                String thisJobState = locationList.get(tmp.getLocationID()-1).getState();
                String thisJobCity = locationList.get(tmp.getLocationID()-1).getCity();

                // a. Has the user entered a postcode to be searched?
                // Check the location entered and see if it contains
                // a valid postcode.
                char[] chars = location.toCharArray();
                StringBuilder postCodeCheck = new StringBuilder();
                for (char c : chars) {
                    if (Character.isDigit(c)) {
                        postCodeCheck.append(c);
                    }
                }
                int searchPostCode = Integer.parseInt(postCodeCheck.toString());



                tmp.getLocationID()


                if (location.contains(tmp.getPostCode()) ||
                        location.contains(tmp.getLocationState().toLowerCase())) {
                    // Match
                } else {
                    // No match
                    valid = false;
                }


                // Last step: If the Job has passed all the filter criteria, then
                // add it to the list of search results.
                if (valid) {
                    results.add(tmp);
                }
            }
        }

        // Now the result list has been populated with Jobs which have passed
        // the search filters. Now to do some matching and scoring on search
        // criteria. TODO: score remaining jobs in jobList for relevancy,
        // TODO: and add them to the results list in order.
        // Remaining search criteria: Title, Description, Skills, Category1 and Category2.
        // Jobs outside the category will still return in results, but at lower relevancy
        // Jobs not exactly matching skill specification will still return, but at lower relevancy

        for (Job tmp : results) {

            // 1. Title
            // This should have a high weighting - if the title matches more
            // or less the search description, then this job should return in
            // the results fairly high.
            int titleMatch = 0;
            String[] searchDescArray = jobDesc.split("\\W+");
            String[] jobTitle = tmp.getJobTitle().split("\\W");
            // Check each word in the search description against each word
            // in the job title.
            for (String word : jobTitle) {
                String lWord = word.toLowerCase();
                for (String check : searchDescArray) {
                    String lCheck = check.toLowerCase();
                    if (lWord.equals(lCheck)) {
                        // Direct match on this word.
                        titleMatch++;
                    }
                }
            }

            // Weight the title match parameter.
            int titleResult = (titleMatch / jobTitle.length) * titleWeight;

            // 2. Description
            // Break the descriptions into arrays of Strings to work with.
            int descMatch = 0;
            int descResult = 0;
            ArrayList<String> jobDescAL = tmp.getJobDescription();
            for (int i = 0; i < jobDescAL.size(); i++) {
                String[] jobDescArray = jobDescAL.get(i).split("\\W+");
                for (String word : searchDescArray) {
                    String lWord = word.toLowerCase();
                    for (String check : jobDescArray) {
                        String lCheck = check.toLowerCase();
                        if (lWord.equals(lCheck)) {
                            descMatch++;
                        }
                    }
                }
                descResult += (descMatch / jobDescArray.length) * descWeight;
            }
            /*
            String[] jobDescArray = tmp.getJobDescription().split("\\W+");
            // For each word in the searched Job Description, check if it matches a word
            // in the job Description. If yes, increment the number of matches.
            for (String word : searchDescArray) {
                String lWord = word.toLowerCase();
                for (String check : jobDescArray) {
                    String lCheck = check.toLowerCase();
                    if (lWord.equals(lCheck)) {
                        // Direct match on the word
                        descMatch++;
                    }
                }
            }
            */

            // Weight the description match.
            //int descResult = (descMatch / jobDescArray.length) * descWeight;

            // 3. Skills
            // For each of the skills listed against the Job, check to see if they
            // match any of the skills listed against the Job Seeker.
            int skillMatch = 0;
            for (String skill : seekerSkills) {
                String lSkill = skill.toLowerCase();
                for (String check : tmp.getSkills()) {
                    String lJobSkill = check.toLowerCase();
                    if (lSkill.equals(lJobSkill)) {
                        //Direct match on the skill
                        skillMatch++;
                    }
                }
            }

            // Weight the skills match.
            int skillResult = 0;
            if (seekerSkills.size() != 0) {
                // Jobseeker has some skills, and skill match can be calculated.
                skillResult = (skillMatch / seekerSkills.size()) * skillWeight;
            }

            // 4. Category
            // Check to see if we match on the job category - Primary and Secondary
            int primaryCatMatch = 0;
            int secondaryCatMatch = 0;
            if (categoryPrimary.equals(tmp.getJobCategoryPrimary())) {
                // Match on Primary Category.
                primaryCatMatch = 1;
            }
            if (categorySecondary.equals(tmp.getJobCategorySecondary())) {
                secondaryCatMatch = 1;
            }

            // Weight the category matches.
            int primaryCatResult = primaryCatMatch * primaryCatWeight;
            int secondaryCatResult = secondaryCatMatch * secondaryCatWeight;

            // 4. Score this Job for the search
            int totalResult = titleResult + descResult + skillResult + primaryCatResult + secondaryCatResult;

            // 5. Add this job and its score into a TreeMap for sorting
            scoredResults.put(totalResult,tmp);
        }

        // Sort the TreeMap and put the sorted list back into results.
        // Note the .values() method should return the list already
        // sorted by the score.
        results.clear();
        results = new ArrayList<>(scoredResults.values());

        return results;
    }

    // Method 2. Search for a list of matching JobSeekers
    public ArrayList<Jobseeker> seekerSearch(String location, ArrayList<String> requiredSkills) throws Exception {
        ArrayList<Jobseeker> results = new ArrayList<Jobseeker>();
        //TODO: Get an ArrayList of all jobSeekers from somewhere. Mocked in for now.
        jobseekerList = new ArrayList<Jobseeker>();
        jobseekerList.add(new Jobseeker(1,"Tom","Barker","tBarker","password",true));
        jobseekerList.add(new Jobseeker(2,"Jakeob","Clarke-Kennedy","jClarke","mypass22",true));
        jobseekerList.add(new Jobseeker(3,"Sam","Smith","ss123","secureStrong",false));

        ArrayList<String> skills = new ArrayList<String>();
        ArrayList<String> skills2 = new ArrayList<String>();
        skills.add("Incident Command");
        skills.add("Incineration");
        skills.add("Project Control");
        jobseekerList.get(0).setSkills(skills);
        skills2.add("Incident Command");
        skills2.add("Project Direction");
        skills2.add("Project Engineering");
        jobseekerList.get(1).setSkills(skills2);

        // Quick debug:
        for (Jobseeker seeker : jobseekerList) {
            System.out.println(seeker);
            System.out.println(seeker.getSkills());
        }

        return results;
    }

    protected int calculateMatch(int jobID, int userID)
    {
        return 0;
    }
}
