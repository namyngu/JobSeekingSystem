import java.util.Scanner;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

public class File_Control {

    /* Things we want the class to do:
     * 1. Read the list of users and their passwords and types and keywords from the user file
     * 2. When a user signs up, write the new user to the user file
     *
     * One type of user file to read from: users.csv
     * Has: username, password, user type, keyword list (if seeker or recruiter)
     *
     */

    /* Method to read data in from a File. Returns a String with users separated by ";"
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
            returnString += scan.nextLine();
            returnString += ";";
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

}
