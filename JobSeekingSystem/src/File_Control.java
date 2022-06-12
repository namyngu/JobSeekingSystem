import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class File_Control {
    public File_Control()
    {
    }

    /* Things we want the class to do:
     * 1. Read the list of users and their passwords and types and keywords from the user file
     * 2. When a user signs up, write the new user to the user file
     *
     * One type of user file to read from: users.csv
     * Has: username, password, user type, keyword list (if seeker or recruiter)
     *
     */

    /* Method to read data in from a File. Returns a String with users separated by "\n"
     * User the .split method to separate these out into arrays or whatever
     * Then, use another .split to separate by "," to break each user into individual chunks
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

    //Method to write data to a file as a String
    public void writeFile(String fileName, String data) throws IOException
    {
        FileWriter file = new FileWriter(fileName, true);
        file.append(data);
        file.append("\n");
        file.close();
    }

    public void clearFile(String fileName) throws IOException
    {
        FileWriter file = new FileWriter(fileName);
//        file.append(data);
//        file.append("\n");
        file.close();
    }

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
            writeFile("jobList.csv", data);
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

    public ArrayList<String> fileSearchId(int id, String filename)
    {
        String searchId = Integer.toString(id);
        ArrayList list = new ArrayList();

        boolean found = false;
        try {
            Scanner scanner = new Scanner(new File(filename));

            while(scanner.hasNext() && !found)
            {
                String data = scanner.next();
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

        }
        catch (Exception e)
        {
            System.out.println("something went wrong");
        }
        return list;
    }

    //look at passed in file and write to an array all but item passed in
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

    protected static Job findJob(ArrayList<Job> jobList, int ID) throws Exception
    {
        Job myJob = null;
        for (Job tmpJob : jobList)
        {
            if (tmpJob.getJobID() == ID)
            {
                myJob = tmpJob;
                return myJob;
            }
        }
        throw new Exception("Error job doesn't exist!");

    }

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

