import java.util.ArrayList;

public class Search
{
    private static ArrayList<Job> jobList;
    private int matchScore;

    /* Some thoughts about this class. This needs to:
     * 1. Method for searching for Jobs that match search criteria
     * 2. Method for searching for JobSeekers that match search criteria
     */

    //Method for retrieving a list of all active jobs
    public void getJobList() {
        //TODO: Populate jobList with a list of all active (advertised) jobs in the system
    }

    // Method 1. Search for a list of matching jobs
    public ArrayList<Job> jobSearch(String jobDesc, String category,
        String location, boolean fullTime, boolean partTime, boolean casual, float salMin,
        float salMax, ArrayList<String> seekerSkills) throws Exception {

        //Setup a few variables
        ArrayList<Job> results = new ArrayList<Job>();

        //Let's make a few sample Jobs just for testing purposes
        Job jobOne = new Job(1, "Software Developer", 1, "Monash University",
                "Full-Time", true, "$100,000", new ArrayList<String>(),
                new ArrayList<Application>(), new ArrayList<String>(), "Queensland", "4101",
                "Some description about being a software developer goes here, blah blah blah",
                "Engineering - Software", "Another category", "9-5",
                true, false);
        jobList.add(jobOne);
        //Job jobTwo = new Job();
        //Job jobThree = new Job();

        //public Job(int jobID, String jobTitle, int recruiterID, String employer, String jobType,
        // Boolean isAdvertised, String salary, ArrayList<String> skills, ArrayList<Application> applications,
        // ArrayList<String> keywords, String locationState, String postCode, String jobDescription, String jobCategoryPrimary,
        // String jobCategorySecondary, String workingHours, boolean advertised, boolean archived)

        // First let's apply filter data to filter out unmatching jobs
        // For every job, check the filters and exclude that job if it
        // does not match the filters.
        for (Job tmp : jobList) {
            boolean valid = true;
            float newSal = Float.parseFloat(tmp.getSalary());

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
            if (!valid) {
                results.add(tmp);
            }
        }

        // Now the result list has been populated with Jobs which have passed
        // the search filters. Now to do some matching and scoring on search
        // criteria. TODO: score remaining jobs in jobList for relevancy,
        // TODO: and add them to the results list in order.
        // Remaining search criteria: Description, Skills, Category
        // Jobs outside the category will still return in results, but at lower relevancy
        // Jobs not exactly matching skill specification will still return, but at lower relevancy

        for (Job tmp : results) {

            // 1. Description
            // Break the descriptions into arrays of Strings to work with.
            int descMatch = 0;
            String[] searchDescArray = jobDesc.split("\\W+");
            String[] jobDescArray = tmp.getJobDescription().split("\\W+");
            // For each word in the searched Job Description, check if it matches a word
            // in the job Description. If yes, increment the number of matches.
            for (String word : searchDescArray) {
                    String lWord = word.toLowerCase();
                for (String check : jobDescArray) {
                    String lCheck = check.toLowerCase();
                    if (lWord.equals(lCheck)) {
                        //Direct match on the word
                        descMatch++;
                    }
                }
            }

            // 2. Skills
            // 3. Category

        }

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
