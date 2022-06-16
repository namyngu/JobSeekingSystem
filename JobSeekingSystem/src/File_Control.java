/**
 * This class is a generic class to be used for interacting with database files.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class File_Control {

    /**
     * This is the Default constructor for the class.
     */
    public File_Control()
    {
    }

    /**
     * This method opens and clears a specified file.
     * @param fileName     a String describing the filename in the database
     *                     file directory.
     * @throws IOException Exceptions are thrown if the file specified cannot
     *                     be located in the file directory.
     */
    public void clearFile(String fileName) throws IOException
    {
        FileWriter file = new FileWriter(fileName);
        // file.append(data);
        // file.append("\n");
        file.close();
    }

    /**
     * This method searches through a given file and returns all matching values.
     * @param id       an Integer containing the ID number to be searched for.
     * @param filename a String containing the filename to be opened.
     * @return an ArrayList of Strings containing all the matching data
     *         found within the file.
     */
    public ArrayList<String> fileSearchId(int id, String filename)
    {
        String searchId = Integer.toString(id);
        ArrayList list = new ArrayList();

        boolean found = false;
        try {
            Scanner scanner = new Scanner(new File(filename));

            while(scanner.hasNextLine() && !found)
            {
                String data = scanner.nextLine();
                String[] arr = data.split(",");
                //if id is found
                if(arr[0].equals(searchId))
                {
                    //convert array to arraylist
                    list = new ArrayList<>(Arrays.asList(arr));
                    //remove id
                    list.remove(0);
                    found = true;
                }
            }
            scanner.close();

        }
        catch (Exception e)
        {
            System.out.println("something went wrong");
        }
        return list;
    }
    
    /**
     * This method reads in a file of data and returns it.
     * @param fileName      a String describing the filename in the database
     *                      file directory.
     * @return              a String containing all of the file data.
     * @throws IOException  Exceptions are thrown if the file specified cannot
     *                      be located in the file directory.
     */
    public String readFile(String fileName) throws IOException
    {
        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        String returnString = "";

        while (scan.hasNextLine())
        {
            returnString += scan.nextLine() + "\n";
        }

        file.close();
        return returnString;
    }

    /**
     * This method opens a specified file and returns all the items in the file
     * that do not match a given ID parameter.
     * @param id        an integer containing the ID number of the item to be
     *                  removed.
     * @param filename  a String containing the filename to be opened.
     * @return an ArrayList of Strings of all data from the file that did not match
     *         the ID parameter.
     */
    public ArrayList<String> removeById(int id, String filename)
    {
        String searchId = Integer.toString(id);
        ArrayList list = new ArrayList();

        try {
            Scanner scanner = new Scanner(new File(filename));
            while(scanner.hasNextLine())
            {
                String data = scanner.nextLine();
                String[] values = data.split(",");
                if(!values[0].equals(searchId))
                {
                    list.add(data);
                }
            }
        }
        catch (Exception e)
        {

        }
        return list;
    }

    /**
     * This method saves a new Job into the Job data files used by the system.
     * @param jobID         an Integer containing the Job ID number.
     * @param jobTitle      a String containing the Job title.
     * @param employer      a String containing the Job employer.
     * @param recruiterID   an Integer containing the ID number of the Recruiter
     *                      who created the Job.
     * @param jobType       a String containing the type of the Job.
     * @param jobStatus     a String containing the status of the Job.
     * @param salary        an Integer containing the salary of the Job.
     * @param locationID    an Integer containing the ID number of the Location
     *                      that this Job takes place in.
     * @param jobDescription a String containing the Job description.
     * @param skills        an ArrayList of Strings describing the skills required
     *                      to successfully complete this Job.
     * @param category      a JobCategory assigned to this Job.
     */
    public void saveJob(int jobID, String jobTitle, String employer, int recruiterID, String jobType,
                        String jobStatus, int salary, int locationID, String jobDescription, ArrayList<String> skills, JobCategory category)
    {
        //replaces all new line in the jobDescription with a "|"
        String jobDescription2 = jobDescription.replaceAll("\n", "|");

        String data = jobID + "," + jobTitle + "," + employer + "," + recruiterID + "," + jobType + "," + jobStatus + "," + salary + "," + locationID + "," + "\"" + jobDescription2 + "\"";
        System.out.println("data is: " + data);
        String data2 = "";

        for (String tmpSkill : skills)
        {
            data2 += jobID + "," + tmpSkill;
            try
            {
                writeFile("JobSkills.csv", data2);
            }
            catch (Exception e)
            {
                System.out.println("Error failed to save skills.");
            }
            data2 = "";
        }

        try
        {
            writeFile("JobList.csv", data);
        }
        catch (Exception e)
        {
            System.out.println("Error failed to save Job.");
        }

        String categoryData = jobID + "," + category.getJobPrimaryCategory() + "," + category.getJobSubCategory();
        try
        {
            writeFile("JobCategory.csv",categoryData);
        }
        catch (Exception e)
        {
            System.out.println("Error failed to save category.");
        }
    }

    /**
     * This method updates an existing Job in the Job data files in the database.
     * @param jobID         an Integer containing the ID of the Job to be updated.
     * @param jobTitle      a String containing the new Job title.
     * @param employer      a String containing the new Job employer.
     * @param recruiterID   an Integer containing the ID number of the Recruiter
     *                      who created this Job.
     * @param jobType       a String containing the new type of this Job.
     * @param jobStatus     a String containing the new status of this Job.
     * @param salary        an Integer containing the new Job salary.
     * @param locationID    an Integer containing the ID number of the new
     *                      Location for this Job.
     * @param jobDescription a String containing the new Job description.
     * @param skills        an ArrayList of Strings which represent the new
     *                      skills required to complete this Job.
     * @param category      a JobCategory to be assigned to this Job.
     * @throws IOException  Exceptions are thrown if the job database files cannot
     *                      be found.
     */
    public void updateJob(int jobID, String jobTitle, String employer, int recruiterID, String jobType,
                          String jobStatus, int salary, int locationID, String jobDescription, ArrayList<String> skills, JobCategory category) throws IOException {

        //replaces all new line in the jobDescription with a "|"
        String jobDescription2 = jobDescription.replaceAll("\n", "|");

        String data = jobID + "," + jobTitle + "," + employer + "," + recruiterID + "," + jobType + "," + jobStatus + "," + salary + "," + locationID + "," + "\"" + jobDescription2 + "\"";
        System.out.println("data is: " + data);
        String data2 = "";

        ArrayList<String> updatedSkills = removeById(jobID, "JobSkills.csv");
        clearFile("JobSkills.csv");
        writeFile("JobSkills.csv", "jobID, skills");
        writeListToFile(updatedSkills, "JobSkills.csv");

        for (String tmpSkill : skills)
        {
            data2 += jobID + "," + tmpSkill;
            try
            {
                writeFile("JobSkills.csv", data2);
            }
            catch (Exception e)
            {
                System.out.println("Error failed to save skills.");
            }
            data2 = "";
        }

        ArrayList<String> updatedJobs = removeById(jobID, "JobList.csv");
        clearFile("JobList.csv");
        writeFile("JobList.csv", "jobID,jobTitle,employer,recruiterID,jobType,status,salary,locationID,jobDescription");
        writeListToFile(updatedJobs, "JobList.csv");
        try
        {
            writeFile("JobList.csv", data);
        }
        catch (Exception e)
        {
            System.out.println("Error failed to save Job.");
        }

        ArrayList<String> updatedCategory = removeById(jobID, "JobCategory.csv");
        clearFile("JobCategory.csv");
        writeFile("JobCategory.csv", "jobID,jobPrimaryCategory,jobSubCategory");
        writeListToFile(updatedCategory, "JobCategory.csv");

        String categoryData = jobID + "," + category.getJobPrimaryCategory() + "," + category.getJobSubCategory();
        try
        {
            writeFile("JobCategory.csv",categoryData);
        }
        catch (Exception e)
        {
            System.out.println("Error failed to save category.");
        }
    }

    /**
     * This method writes data into a file.
     * @param fileName     a String describing the filename in the database
     *                     file directory.
     * @param data         a String containing all the data to be saved
     *                     into the file.
     * @throws IOException Exceptions are thrown if the file specified cannot
     *                     be located in the file directory.
     */
    public void writeFile(String fileName, String data) throws IOException
    {
        FileWriter file = new FileWriter(fileName, true);
        file.append(data);
        file.append("\n");
        file.close();
    }

    /**
     * This method writes data in ArrayList format to a file.
     * @param list     an ArrayList of Strings to be written to the file.
     * @param filename a String containing the filename in the database
     *                 file directory.
     */
    public void writeListToFile(ArrayList<String> list, String filename)
    {
        try {
            FileWriter file = new FileWriter(filename);
            PrintWriter write = new PrintWriter(file);
            for(String line: list){
                write.println(line);
            }
            write.close();
        }
        catch(Exception e) {
            System.out.println("Something went wrong");
        }
    }
}

