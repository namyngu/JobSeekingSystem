/**
 * This class represents the Search control class, which will execute the main search
 * algorithm functions of the system.
 * @author  Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class Search
{
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;
    private ArrayList<Jobseeker> jobseekerList;
    private Object myParent;

    /**
     * This is the Default constructor for this class.
     */
    public Search() {
        myParent = null;
        jobList = new ArrayList<>();
        locationList = new ArrayList<>();
        jobCategoryList = new ArrayList<>();
        jobseekerList = new ArrayList<>();
    }

    /**
     * This is a Non-default constructor for this class.
     * @param parent    a JobseekerControl Object, representing the Object which
     *                  spawned this Search Object.
     * @param jobs      an ArrayList of Jobs containing all Jobs in the system.
     * @param locations an ArrayList of Locations containing all the Locations
     *                  in the system.
     * @param categories an ArrayList of JobCategories containing all the Job
     *                   Categories in the system.
     */
    public Search(JobseekerControl parent, ArrayList<Job> jobs,
                  ArrayList<Location> locations, ArrayList<JobCategory> categories) {
        myParent = parent;
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        jobseekerList = new ArrayList<>();
    }

    /**
     * This is a Non-default constructor for this class.
     * @param parent    a RecruiterControl Object, representing the Object which
     *                  spawned this Search Object.
     * @param jobs      an ArrayList of Jobs containing all the Jobs in the system.
     * @param locations an ArrayList of Locations containing all the Locations
     *                  in the system.
     * @param categories an ArrayList of JobCategories containing all the Job
     *                   Categories in the system.
     */
    public Search(RecruiterControl parent, ArrayList<Job> jobs,
                  ArrayList<Location> locations, ArrayList<JobCategory> categories) {
        myParent = parent;
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        jobseekerList = new ArrayList<>();
    }

    /**
     * This is a Non-default constructor for this class.
     * @param parent    a RecruiterControl Object, representing the Object which
     *                  spawned this Search Object.
     * @param jobs      an ArrayList of Jobs containing all the Jobs in the system.
     * @param locations an ArrayList of Locations containing all the Locations
     *                  in the system.
     * @param categories an ArrayList of JobCategories containing all the Job
     *                   Categories in the system.
     * @param seekers   an ArrayList of Jobseekers, containing all the Users which
     *                  are set as userType "Jobseeker" in the system.
     */
    public Search(RecruiterControl parent, ArrayList<Job> jobs,
                  ArrayList<Location> locations, ArrayList<JobCategory> categories,
                  ArrayList<Jobseeker> seekers) {
        myParent = parent;
        jobList = jobs;
        locationList = locations;
        jobCategoryList = categories;
        jobseekerList = seekers;
    }

    /**
     * This is the display method for this class.
     */
    public void display() {
        System.out.println("Parent Object: " + myParent);
        System.out.println("Job List: " + jobList);
        System.out.println("Location List: " + locationList);
        System.out.println("Category List: " + jobCategoryList);
        System.out.println("Job Seeker List: " + jobseekerList);
    }

    /**
     * This method examines a list of Jobs, filters out ones that do not
     * meet specified requirements, and returns the rest.
     * @param fullTime  a Boolean representing if Jobs of Type "FullTime" should
     *                  be included in the results or not.
     * @param partTime  a Boolean representing if Jobs of Type "PartTime" should
     *                  be included in the results or not.
     * @param casual    a Boolean representing if Jobs of Type "Casual" should
     *                  be included in the results or not.
     * @param salMin    an Integer representing the minimum salary amount. Jobs
     *                  with a salary less than this should be filtered out.
     * @param salMax    an Integer representing the maximum salary amount. Jobs
     *                  with a salary more than this should be filtered out.
     * @param location  a String representing the location which has been searched for.
     * @return          an ArrayList of Jobs which fall into the filter parameters
     *                  provided.
     */
    public ArrayList<Job> filterResults(boolean fullTime, boolean partTime, boolean casual,
                                        int salMin, int salMax, String location) {
        // Setup a few variables.
        ArrayList<Job> results = new ArrayList<>();

        for (Job tmp : jobList) {
            // Set up a boolean to track if the job should be filtered out.
            boolean valid = true;

            /* As soon as the job is filtered out, we will end checking.
             * If we reach the end of the check and the job is still valid,
             * we can add it to the list of Jobs to be considered for Search results.
             */
            while (valid) {
                // Retrieve the job Salary.
                int salary = tmp.getSalary();

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
                if (salary < salMin || salary > salMax) {
                    // Job is outside desired salary range
                    valid = false;
                    break;
                }

                /* 4. Job must include some part of location
                 * If the Job postcode or Job State name appears in the searched
                 * location text entry then I have considered this filter to be satisfied.
                 */

                int thisJobPostCode = 0;
                String thisJobState = "";
                String thisJobCity = "";

                for (Location place : locationList) {
                    if (place.getLocationID() == tmp.getLocationID()) {
                        thisJobPostCode = place.getPostcode();
                        thisJobState = place.getState().toLowerCase();
                        thisJobCity = place.getCity().toLowerCase();
                    }
                }

                /* a. Has the user entered a postcode to be searched?
                 * Check the location entered and see if it contains
                 * a valid postcode.
                 */
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

                /* b. Has the user entered a State or City to be searched?
                 * Check the location entered and see if it contains any
                 * relevant data.
                 */
                int locationMatch = 0;
                String[] locationsSearched = location.split("\\W+");
                for (String searchString : locationsSearched) {
                    String lSearchString = searchString.toLowerCase();
                    if (lSearchString.equals(thisJobState) || lSearchString.equals(thisJobCity)) {
                        /* This location search term matches this Job's location
                         * and this Job is therefore relevant.
                         */
                        locationMatch++;
                    }
                }

                // c. Pull all the locations check parameters together
                if (locationMatch == 0 && (searchPostCode == 0 || searchPostCode != thisJobPostCode)) {
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

    /**
     * This is the Accessor method for the jobCategoryList field.
     * @return an ArrayList of Job Categories.
     */
    public ArrayList<JobCategory> getJobCategoryList() {
        return jobCategoryList;
    }

    /**
     * This is the Accessor method for the jobList field.
     * @return an ArrayList of Jobs in the system.
     */
    public ArrayList<Job> getJobList() {
        return jobList;
    }

    /**
     * This is the Accessor method for the jobseekerList field.
     * @return an ArrayList of Jobseekers in the system.
     */
    public ArrayList<Jobseeker> getJobseekerList() {
        return jobseekerList;
    }

    /**
     * This is the Accessor method for the locationList field.
     * @return an ArrayList of Locations in the system.
     */
    public ArrayList<Location> getLocationList() {
        return locationList;
    }

    /**
     * This is the Accessor method for the myParent field.
     * @return the Object which spawned this Search class.
     */
    public Object getMyParent() {
        return myParent;
    }

    /**
     * This method searches for Jobs in the system which match provided
     * parameters and returns them in a TreeMap.
     * @param jobDesc           a String containing the search text entered by
     *                          the User.
     * @param categoryPrimary   a String containing the Primary Category selected
     *                          to search in by the User.
     * @param categorySecondary a String containing the Secondary Category selected
     *                          to search in by the User.
     * @param location          a String containing the location text entered by
     *                          the User.
     * @param fullTime          a Boolean containing the checkbox selection for "Fulltime"
     *                          made by the User.
     * @param partTime          a Boolean containing the checkbox selection for "Parttime"
     *                          made by the User.
     * @param casual            a Boolean containing the checkbox selection for "Casual"
     *                          made by the User.
     * @param salMin            an Integer containing the minimum salary selected by the User
     *                          to search for.
     * @param salMax            an Integer containing the maximum salary selected bt the User
     *                          to search for.
     * @param seekerSkills      an ArrayList of Strings representing the skills of the
     *                          Jobseeker who executed the search.
     * @return                  a TreeMap of ArrayList<Job>, sorted by relevance descending.
     * @throws Exception        Exceptions are thrown when parameters which are required to
     *                          successfully execute the search have not been provided.
     */
    public TreeMap<Integer, ArrayList<Job>> jobSearch(String jobDesc, String categoryPrimary,
        String categorySecondary, String location, boolean fullTime, boolean partTime,
        boolean casual, int salMin, int salMax, ArrayList<String> seekerSkills) throws Exception {

        // Throw notification messages if needed data is missing.
        if (jobDesc.isEmpty()) {
            throw new Exception("Please enter a Job Title to search for!");
        }
        if (location.isEmpty()) {
            throw new Exception("Please enter a location to search in!");
        }
        if (!fullTime && !partTime && !casual) {
            throw new Exception("Please select a Job Type to search for!");
        }
        if (categoryPrimary.equals("Category")) {
            throw new Exception("Please select a Primary Category to search in!");
        }


        // Setup a few variables.
        TreeMap<Integer, ArrayList<Job>> scoredResults = new TreeMap<>(Collections.reverseOrder());

        // Change search algorithm weightings here, if needed.
        int titleWeight = 35;
        int keywordWeight = 20;
        int skillWeight = 20;
        int primaryCatWeight = 15;
        int secondaryCatWeight = 10;

        // First let's apply filter data to filter out unmatching jobs
        ArrayList<Job> results = filterResults(fullTime, partTime, casual, salMin, salMax, location);

        /* Now the result list has been populated with Jobs which have passed
         * the search filters. Now to do some matching and scoring on search
         * criteria.
         * Remaining search criteria: Title, Keywords, Skills, Category1 and Category2.
         * Jobs outside the category will still return in results, but at lower relevancy
         * Jobs not exactly matching skill specification will still return, but at lower relevancy
         */

        for (Job tmp : results) {

            /* 1. Title
             * This should have a high weighting - if the title matches more
             * or less the search description, then this job should return in
             * the results fairly high.
             */
            int titleMatch = 0;
            String[] searchDescArray = jobDesc.split("\\W+");
            String[] jobTitle = tmp.getJobTitle().split("\\W");
            /* Check each word in the search description against each word
             * in the job title.
             */
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

            /* 2. Keywords
             * Check each search term to see if it matches a keyword.
             */
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

            /* 3. Skills
             * For each of the skills listed against the Job, check to see if they
             * match any of the skills listed against the Job Seeker.
             */
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

            /* 4. Category
             * Check to see if we match on the job category - Primary and Secondary.
             */
            int primaryCatMatch = 0;
            int secondaryCatMatch = 0;
            for (JobCategory checkCat : jobCategoryList) {
                if (categoryPrimary.equals(checkCat.getJobPrimaryCategory())) {
                    // Match on Primary Category.
                    primaryCatMatch = 1;
                }
                if (categorySecondary.equals(checkCat.getJobSubCategory())) {
                    // Match on Secondary Category, or "All" has been selected.
                    secondaryCatMatch = 1;
                }
                if (primaryCatMatch != 0 && secondaryCatMatch != 0) {
                    break;
                }
            }

            // Weight the category matches.
            int primaryCatResult = primaryCatMatch * primaryCatWeight;
            /* The secondary category should *only* match if it is a valid
             * subset of the primary category.*/
            int secondaryCatResult = 0;
            if (primaryCatResult > 0) {
                secondaryCatResult = secondaryCatMatch * secondaryCatWeight;
            }

            // 4. Score this Job for the search
            int totalResult = titleResult + keywordResult + skillResult + primaryCatResult + secondaryCatResult;

            /* 5. Jobs that score particularly low have made it through the
             * filters, but are not very relevant and should not be included
             * in search results.
             */
            if (totalResult < 10) {
                break;
            }

            // 6. Add this job and its score into a TreeMap for sorting
            if (scoredResults.get(totalResult) == null) {
                ArrayList<Job> newList = new ArrayList<>();
                newList.add(tmp);
                scoredResults.put(totalResult,newList);
            } else {
                scoredResults.get(totalResult).add(tmp);
            }
            System.out.println(scoredResults);
        }

        // Return the TreeMap (which is already sorted descending) as results.
        return scoredResults;

        /* Sort the TreeMap and put the sorted list back into results.
         * Note the TreeMap should always be already sorted for  in
         * descending order for us as specified in our intialization, so
         * simply iterating through gets us the Job list
         * sorted by the score descending.

        results.clear();
        results = new ArrayList<>();
        for (Integer key : scoredResults.keySet()) {
            for (int i = 0; i < scoredResults.get(key).size(); i++) {
                results.add(scoredResults.get(key).get(i));
            }
        }
        return results;*/
    }

    /**
     * This method searches for Jobseekers in the system that match provided parameters.
     * @param searchLocation a String containing the text input the Recruiter
     *                       has searched in.
     * @param seekerSkills   an ArrayList of Strings containing the skills that
     *                       the Recruiter has searched for.
     * @return               an ArrayList of Jobseekers which match the search
     *                       parameters.
     */
    public ArrayList<Jobseeker> seekerSearch(String searchLocation, ArrayList<String> seekerSkills) {

        // Setup a few variables.
        ArrayList<Jobseeker> results = new ArrayList<>();
        TreeMap<Integer, ArrayList<Jobseeker>> scoredResults = new TreeMap<>(Collections.reverseOrder());

        /* For each Jobseeker in our list of Jobseekers, check to see
         * if they are in the right location. Also check to see if they
         * have any skills that match our requirements. If so, add them
         * to an initial list of results.
         */
        for (Jobseeker seeker : jobseekerList) {
            Location seekerLocation = seeker.getLocation();
            boolean valid = true;

            int searchPostCode = 0;
            String searchState = "";
            String searchCity = "";

            for (Location place : locationList) {
                if (place.getLocationID() == seekerLocation.getLocationID()) {
                    searchPostCode = place.getPostcode();
                    searchState = place.getState().toLowerCase();
                    searchCity = place.getCity().toLowerCase();
                    break;
                }
            }

            /* a. Has the user entered a postcode to be searched?
             * Check the location entered and see if it contains
             * a valid postcode.
             */
            char[] chars = searchLocation.toCharArray();
            StringBuilder postCodeCheck = new StringBuilder();
            for (char c : chars) {
                if (Character.isDigit(c)) {
                    postCodeCheck.append(c);
                }
            }

            int checkPostCode = 0;
            try {
                checkPostCode = Integer.parseInt(postCodeCheck.toString());
            }
            catch (Exception e) {
                // Don't assign any value to checkPostCode
            }

            /* b. Has the user entered a State or City to be searched?
             * Check the location entered and see if it contains any
             * relevant data.
             */
            int locationMatch = 0;
            String[] locationsSearched = searchLocation.split("\\W+");
            for (String searchString : locationsSearched) {
                String lSearchString = searchString.toLowerCase();
                if (lSearchString.equals(searchState) || searchCity.contains(lSearchString)) {
                    /* This location search term matches this seeker's location
                     * and this seeker is therefore relevant.
                     */
                    locationMatch++;
                }
            }

            // c. Pull all the locations check parameters together
            if (locationMatch == 0 && (checkPostCode == 0 || checkPostCode != searchPostCode)) {
                // This seeker's location does not match any of the location search data
                valid = false;
                continue;
            }

            if (valid) {
                // This seeker matches searched parameters.
                results.add(seeker);
            }
        }

        /* Now score the seekers in the results list and put them back
         * into the results list in scored order.
         */
        int skillMatch = 0;
        for (Jobseeker seeker : results) {
            for (String skill: seeker.getSkills()) {
                String lSkill = skill.toLowerCase();
                for (String searchSkill : seekerSkills) {
                    String lSearchSkill = searchSkill.toLowerCase();
                    if (lSkill.equals(lSearchSkill)) {
                        // Direct match on the skill
                        skillMatch++;
                    }
                }
            }

            // Weight the skillMatch.
            int skillResult = 0;
            if (seeker.getSkills().size() > 0) {
                // This seeker has skills and we can calculate skillResult
                skillResult = skillMatch / seekerSkills.size() * 100;
            }

            // Add the scored entry into scored results.
            if (skillResult > 10) {
                if (scoredResults.get(skillResult) == null) {
                    ArrayList<Jobseeker> newList = new ArrayList<>();
                    newList.add(seeker);
                    scoredResults.put(skillResult, newList);
                } else {
                    scoredResults.get(skillResult).add(seeker);
                }
            }
        }

        /* Sort the TreeMap and put the sorted list back into results.
         * Note the TreeMap should always be already sorted for  in
         * descending order for us as specified in our intialization, so
         * simply iterating through gets us the Job list
         * sorted by the score descending.
         */
        results.clear();
        results = new ArrayList<>();
        for (Integer key : scoredResults.keySet()) {
            for (int i = 0; i < scoredResults.get(key).size(); i++) {
                results.add(scoredResults.get(key).get(i));
            }
        }
        return results;
    }

    /**
     * This is the Mutator method for the jobCategoryList field.
     * @param jobCategories an ArrayList of JobCategories containing all the
     *                      Job Categories in the system.
     */
    public void setJobCategoryList(ArrayList<JobCategory> jobCategories) {
        jobCategoryList = jobCategories;
    }

    /**
     * This is the Mutator method for the jobList field.
     * @param jobs an ArrayList of Jobs containing all the Jobs in the system.
     */
    public void setJobList(ArrayList<Job> jobs) {
        jobList = jobs;
    }

    /**
     * This is the Mutator method for the jobseekerList field.
     * @param seekers an ArrayList of Jobseekers containing all the Users in
     *                the system of Type Jobseeker.
     */
    public void setJobseekerList(ArrayList<Jobseeker> seekers) {
        jobseekerList = seekers;
    }

    /**
     * This is the Mutator method for the locationList field.
     * @param locations an ArrayList of Locations containing all the Locations
     *                  in the system.
     */
    public void setLocationList(ArrayList<Location> locations) {
        locationList = locations;
    }

    /**
     * This is the Mutator method for the myParent field.
     * @param parent the Object which spawned this Search Object and should be
     *               set as the parent Object.
     */
    public void setMyParent(Object parent) {
        myParent = parent;
    }

    /**
     * This method searches for Jobs which should be recommended to the User.
     * @param seekerLocation    the Location which this User is in.
     * @param seekerSkills      an ArrayList of Strings which represent the skills
     *                          this User has entered on their profile.
     * @return                  an ArrayList of Jobs, sorted by relevance, which
     *                          can be recommended to the User.
     */
    public ArrayList<Job> recommendedJobs(Location seekerLocation, ArrayList<String> seekerSkills) {

        // Setup a few variables.
        ArrayList<Job> results = new ArrayList<>();
        TreeMap<Integer, ArrayList<Job>> scoredResults = new TreeMap<>(Collections.reverseOrder());

        /* For each job in our list our jobs, filter out the ones that do not
         * match the seeker's location. Also filter out jobs that do not match
         * any of the jobseeker's skills. Put the remaining jobs into a list
         * to be scored.
         */

        for (Job tmp: jobList) {

            String thisJobState = "";
            String thisJobCity = "";

            for (Location place : locationList) {
                if (place.getLocationID() == tmp.getLocationID()) {
                    thisJobState = place.getState().toLowerCase();
                    thisJobCity = place.getCity().toLowerCase();
                    break;
                }
            }

            int skillMatch = 0;
            for (String skill: seekerSkills) {
                String lSkill = skill.toLowerCase();
                for (String check : tmp.getSkills()) {
                    String lJobSkill = check.toLowerCase();
                    if (lJobSkill.equals(lSkill)) {
                        // Direct match on the skill
                        skillMatch++;
                    }
                }
            }

            if ((thisJobState.equals(seekerLocation.getState().toLowerCase())
                    || thisJobCity.equals(seekerLocation.getCity().toLowerCase()))
                    && skillMatch > 0) {
                /* This job matches the seeker's location and at least one of the
                 * seeker's skills.
                 */
                results.add(tmp);
            }

        }

        /* For each job in our filtered list, score each job according to how
         * well it compares to the seeker's skills. Also score on location -
         * jobs offered in the seeker's Postcode or City should rank higher
         * than jobs offered in the seeker's State.
         */

        // Setup variables.
        int skillMatch = 0;
        int locationMatch = 0;

        for (Job tmp: results) {

            // Score on skills.
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

            // Score on location.
            String thisJobState = "";
            String thisJobCity = "";
            int thisJobPostcode = 0;

            for (Location place : locationList) {
                if (place.getLocationID() == tmp.getLocationID()) {
                    thisJobState = place.getState().toLowerCase();
                    thisJobCity = place.getCity().toLowerCase();
                    thisJobPostcode = place.getPostcode();
                    break;
                }
            }

            if (thisJobPostcode == seekerLocation.getPostcode()) {
                // Job is very nearby.
                locationMatch = 50;
            } else if (thisJobCity.equals(seekerLocation.getCity())) {
                // Job is nearby.
                locationMatch = 30;
            } else {
                // Job is in the same State.
                locationMatch = 10;
            }

            // Weight this job's matches as a percentage.
            int skillResult = (skillMatch / seekerSkills.size() * 50) + locationMatch;

            // Add this job and its scored to the TreeMap.
            if (scoredResults.get(skillResult) == null) {
                ArrayList<Job> newList = new ArrayList<>();
                newList.add(tmp);
                scoredResults.put(skillResult,newList);
            } else {
                scoredResults.get(skillResult).add(tmp);
            }
        }

        /* Sort the TreeMap and put the sorted list back into results.
         * Note the TreeMap should always be already sorted for  in
         * descending order for us as specified in our intialization, so
         * simply iterating through gets us the Job list
         * sorted by the score descending.
         */
        results.clear();
        results = new ArrayList<>();
        for (Integer key : scoredResults.keySet()) {
            for (int i = 0; i < scoredResults.get(key).size(); i++) {
                results.add(scoredResults.get(key).get(i));
            }
        }
        return results;
    }
}