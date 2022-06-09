import java.util.ArrayList;
import java.util.TreeMap;

public class Search
{
    private ArrayList<Job> jobList;
    private int matchScore;
    private JobseekerControl myParent;

    /* Some thoughts about this class. This needs to:
     * 1. Method for searching for Jobs that match search criteria
     * 2. Method for searching for JobSeekers that match search criteria
     */

    //Method for retrieving a list of all active jobs
    public void getJobList() {
        //TODO: Populate jobList with a list of all active (advertised) jobs in the system.
        //TODO: To do this, set jobList = import list of all Jobs from somewhere.
    }

    // Default constructor.
    public Search() {
        jobList = new ArrayList<>();
    }

    // Non-default constructor.
    public Search(JobseekerControl parent) {
        myParent = parent;
        jobList = new ArrayList<>();
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
        int titleWeight = 35;
        int descWeight = 20;
        int skillWeight = 20;
        int primaryCatWeight = 15;
        int secondaryCatWeight = 10;

        //Let's make a few sample Jobs just for testing purposes
        Job jobOne = new Job(1, "Software Developer", 1, "Monash University",
                "Full-Time", true, "$100,000", new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), "Queensland", "4101",
                new ArrayList<>(), //"Some description about being a software developer goes here, blah blah blah",
                "Engineering - Software", "Another category", "9-5",
                true, false);
        jobList.add(jobOne);
        //Job jobTwo = new Job();
        //Job jobThree = new Job();

        // First let's apply filter data to filter out unmatching jobs
        // For every job, check the filters and exclude that job if it
        // does not match the filters.
        for (Job tmp : jobList) {
            boolean valid = true;
            char[] salary = tmp.getSalary().toCharArray();
            StringBuilder salBuilder = new StringBuilder();
            for (char single : salary) {
                if (Character.isDigit(single)) {
                    // Character is a digit and is therefore ok to add to StringBuilder.
                    salBuilder.append(single);
                }
            }

            String convertedSal = salBuilder.toString();
            int newSal = Integer.parseInt(convertedSal);

            // Job must be currently advertised (just in case we accidentally
            // pulled some in that are not.)
            if (!tmp.getAdvertised()) {
                valid = false;
            }

            // Job Type specification must be supplied
            if (!fullTime && !partTime && !casual) {
                // Search has not specified any job type, search cannot be executed
                throw new Exception ("You must specify a Job Type!");
            }

            // 1. Job must match Job Type search specification
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

            // 2. Job must match salary range
            if (Float.compare(newSal, salMin) >=0  && Float.compare(newSal, salMax) <=0) {
                //Job is within salary range specified
            }
            else {
                // Job is outside desired salary range
                valid = false;
            }

            // 3. Job must include some part of location
            // If the Job postcode or Job State name appears in the searched
            // location text entry then I have considered this filter to be satisfied.
            if (location.contains(tmp.getPostCode()) ||
                location.contains(tmp.getLocationState().toLowerCase())) {
                // Match
            }
            else {
                // No match
                valid = false;
            }

            // Last step: If the Job has passed all the filter criteria, then
            // add it to the list of search results.
            if (valid) {
                results.add(tmp);
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
    public ArrayList<Jobseeker> seekerSearch() {
        //TODO
        ArrayList<Jobseeker> results = new ArrayList<Jobseeker>();
        return results;
    }

    protected int calculateMatch(int jobID, int userID)
    {
        return 0;
    }
}
