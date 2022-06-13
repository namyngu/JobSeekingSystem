import java.util.ArrayList;
import java.util.TreeMap;

public class Search
{
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;
    private ArrayList<Jobseeker> jobseekerList;
    private int matchScore;
    private Object myParent;

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

    public Search(RecruiterControl parent, ArrayList<Job> jobs,
                  ArrayList<Location> locations, ArrayList<JobCategory> categories) {
        myParent = parent;
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        jobseekerList = new ArrayList<>();
    }

    // Method to filter out inappropriate search results based
    // on user input.
    public ArrayList<Job> filterResults(boolean fullTime, boolean partTime, boolean casual,
                                        int salMin, int salMax, String location) throws Exception {
        // Setup a few variables.
        ArrayList<Job> results = new ArrayList<>();

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
                    break;
                }

                // Filter 2. Job must match Job Type search specification
                switch (tmp.getJobType()) {
                    case "FullTime":
                        if (!fullTime) {
                            // Job does not match criteria
                            valid = false;
                            break;
                        }
                        break;
                    case "PartTime":
                        if (!partTime) {
                            // Job does not match criteria
                            valid = false;
                            break;
                        }
                        break;
                    case "Casual":
                        if (!casual) {
                            // Job does not match criteria
                            valid = false;
                            break;
                        }
                        break;
                    default:
                        break;
                }

                // 3. Job must match salary range
                if (salary >= salMin && salary <= salMax) {
                    //Job is within salary range specified
                } else {
                    // Job is outside desired salary range
                    valid = false;
                    break;
                }

                // 4. Job must include some part of location
                // If the Job postcode or Job State name appears in the searched
                // location text entry then I have considered this filter to be satisfied.

                int thisJobPostCode = locationList.get(tmp.getLocationID()-1).getPostcode();
                String thisJobState = locationList.get(tmp.getLocationID()-1).getState().toLowerCase();
                String thisJobCity = locationList.get(tmp.getLocationID()-1).getCity().toLowerCase();

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

                int searchPostCode = 0;

                try {
                    searchPostCode = Integer.parseInt(postCodeCheck.toString());
                }
                catch (Exception e) {
                    // Don't assign any value to searchPostCode
                }

                // b. Has the user entered a State or City to be searched?
                // Check the location entered and see if it contains any
                // relevant data.
                int locationMatch = 0;
                String[] locationsSearched = location.split("\\W+");
                for (String searchString : locationsSearched) {
                    String lSearchString = searchString.toLowerCase();
                    if (lSearchString.equals(thisJobState) || lSearchString.equals(thisJobCity)) {
                        // This location search term matches this Job's location
                        // and this Job is therefore relevant.
                        locationMatch++;
                    }
                }

                // c. Pull all the locations check parameters together
                if (locationMatch == 0 && searchPostCode == 0 && searchPostCode != thisJobPostCode) {
                    // This job's location does not match any of the location search data
                    valid = false;
                    break;
                }

                // Last step: If the Job has passed all the filter criteria, then
                // add it to the list of search results.
                if (valid) {
                    results.add(tmp);
                    break;
                }
            }
        }
        // Return filtered jobs.
        return results;
    }

    // Method 1. Search for a list of matching jobs
    public ArrayList<Job> jobSearch(String jobDesc, String categoryPrimary,
        String categorySecondary, String location, boolean fullTime, boolean partTime,
        boolean casual, int salMin, int salMax, ArrayList<String> seekerSkills) throws Exception {

        // Setup a few variables.
        ArrayList<Job> results = new ArrayList<>();
        TreeMap<Integer, Job> scoredResults = new TreeMap<>();

        // Change search algorithm weightings here, if needed.
        int titleWeight = 35;
        int keywordWeight = 20;
        int skillWeight = 20;
        int primaryCatWeight = 15;
        int secondaryCatWeight = 10;

        // First let's apply filter data to filter out unmatching jobs
        results = filterResults(fullTime, partTime, casual, salMin, salMax, location);

        /* Now the result list has been populated with Jobs which have passed
         * the search filters. Now to do some matching and scoring on search
         * criteria.
         * Remaining search criteria: Title, Keywords, Skills, Category1 and Category2.
         * Jobs outside the category will still return in results, but at lower relevancy
         * Jobs not exactly matching skill specification will still return, but at lower relevancy*/

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

            // 2. Keywords
            // Check each search term to see if it matches a keyword.
            int keywordMatch = 0;

            for (String keyword : tmp.getKeywords()) {
                String lKeyword = keyword.toLowerCase();
                for (String check : searchDescArray) {
                    String lCheck = check.toLowerCase();
                    if (lKeyword.equals(lCheck)) {
                        // Direct match on this word.
                        keywordMatch++;
                    }
                }
            }

            // Weight the keyword match parameter.
            int keywordResult = (keywordMatch / tmp.getKeywords().size()) * keywordWeight;

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
            if (categoryPrimary.equals(jobCategoryList.get(tmp.getJobID()-1).getJobPrimaryCategory())) {
                // Match on Primary Category.
                primaryCatMatch = 1;
            }
            if (categorySecondary.equals(jobCategoryList.get(tmp.getJobID()-1).getJobSubCategory())) {
                secondaryCatMatch = 1;
            }


            // Weight the category matches.
            int primaryCatResult = primaryCatMatch * primaryCatWeight;
            int secondaryCatResult = secondaryCatMatch * secondaryCatWeight;

            // 4. Score this Job for the search
            int totalResult = titleResult + keywordResult + skillResult + primaryCatResult + secondaryCatResult;

            // 5. Jobs that score particularly low have made it through the
            // filters, but are not very relevant and should not be included
            // in search results.
            if (totalResult < 10) {
                break;
            }

            // 6. Add this job and its score into a TreeMap for sorting
            scoredResults.put(totalResult,tmp);
            System.out.println(scoredResults);
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




        return results;
    }

    protected int calculateMatch(int jobID, int userID)
    {
        return 0;
    }
}
