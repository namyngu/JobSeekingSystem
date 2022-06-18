/**
 * This class represents a Job Application, which is an extension of the Message Object.
 * @author  Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import java.time.LocalDate;

public class Application extends Message
{
    private int jobID;
    private String status;

    /**
     * This is the Default constructor for the class.
     */
    public Application()
    {
        this.jobID = -1;
        this.status = "no status";
    }

    /**
     * This is a Non-default constructor for the class.
     * @param senderID  an Integer containing the ID number of the sender.
     * @param receiverID an Integer containing the ID number of the recipient.
     * @param header    a String containing the subject line of the Application.
     * @param body      a String containing the body of the Application.
     * @param sentDate  a LocalDate containing the date on which the Application was sent.
     */
    public Application(int senderID, int receiverID, String header, String body, LocalDate sentDate)
    {
        super(senderID, receiverID, header, body, sentDate);
        status = "pending";
    }

    /**
     * This is a Non-default constructor for the class.
     * @param nextMessageID an Integer containing the ID number of the Application.
     * @param hasReceived        a String containing the status of the Application.
     * @param sender        an Integer containing the ID number of the sender.
     * @param messageTo     an Integer containing the ID number of the recipient.
     * @param header        a String containing the subject line of the Application.
     * @param body          a String containing the body of the Application.
     * @param sentDate      a LocalDate containing the date on which the Application was sent.
     */
    public Application(int nextMessageID, boolean hasReceived, int sender, int messageTo, String header, String body, LocalDate sentDate)
    {
        super(nextMessageID, hasReceived, sender, messageTo, header, body, sentDate);
        this.status = "pending";
    }

    /**
     * This is a Non-default constructor for the class.
     * @param messageID     an Integer containing the ID number of the Application.
     * @param senderID      an Integer containing the ID number of the sender.
     * @param receiverID    an Integer containing the ID number of the recipient.
     * @param header        a String containing the subject line of the Application.
     * @param text          a String containing the body of the Application.
     * @param sentDate      a LocalDate containing the date on which the Application was sent.
     */
    public Application(int messageID, int senderID, int receiverID, String header, String text, LocalDate sentDate)
    {
        super(messageID, senderID, receiverID, header, text, sentDate);
        status = "Pending";
    }

    /**
     * This is a Non-default constructor for the class.
     * @param messageID
     * @param hasReceived
     * @param senderID
     * @param receiverID
     * @param header
     * @param text
     * @param jobID
     * @param sentDate
     */
    public Application(int messageID, boolean hasReceived, int senderID, int receiverID, String header, String text, int jobID, LocalDate sentDate)
    {
        super(messageID, hasReceived, senderID, receiverID, header, text, sentDate);
        this.jobID = jobID;
        this.status = "Pending";
    }

    /**
     * This is a Non-default constructor for the class.
     * @param messageID
     * @param hasReceived
     * @param senderID
     * @param receiverID
     * @param header
     * @param text
     * @param jobID
     * @param sentDate
     * @param status
     */
    public Application(int messageID, boolean hasReceived, int senderID, int receiverID, String header, String text, int jobID, LocalDate sentDate, String status)
    {
        super(messageID, hasReceived, senderID, receiverID, header, text, sentDate);
        this.jobID = jobID;
        this.status = status;
    }

    /**
     * This is the display method for this class.
     */
    public void display()
    {
        System.out.println("Job reference is " + jobID);
    }
    

    //Setters and Getters below
    /**
     * This method returns new characters to be used as the Application ID number.
     * @return an Array of Characters.
     */
    public int getApplicationID()
    {
        return (this.getMessageID());
    }

    /**
     * This is the Accessor method for the jobRef field.
     * @return  an Integer containing the ID number of the Job this Application
     *          relates to.
     */
    public int getJobRef()
    {
        return jobID;
    }

    /**
     * This is the Accessor method for the status field.
     * @return  a String containing the status of this Application.
     */
    public String getStatus() {
        return status;
    }

    /**
     * This is the Mutator method for the jobRef field.
     * @param jobID an Integer containing the Job ID this Application should relate to.
     */
    public void setJobRef(int jobID)
    {
        this.jobID = jobID;
    }

    /**
     * This is the Mutator method for the status field.
     * @param status a String containing the status of this Application.
     */
    public void setStatus(String status) {
        this.status = status;
        try
        {
            saveStatus(status);
        }
        catch (Exception e)
        {
            System.out.println("Failed to save application status to memory!");
        }
    }

    /**
     * Method to write Application status to csv.
     * @throws Exception
     */
    public void saveStatus(String status) throws Exception
    {
        File_Control io = new File_Control();
        String appContent = "";
        //Reading from application csv.
        try
        {
            appContent = io.readFile("Application.csv");
            String [] application = appContent.split("\n");
            //Skip first line
            for (int i = 0; i < application.length; i++)
            {
                //skip first line
                if (i != 0)
                {
                    //messageID,jobID,Status
                    String[] appDetails = application[i].split(",");
                    if (Integer.parseInt(appDetails[0]) == this.getMessageID())
                    {
                        appDetails[2] = status;
                        application[i] = appDetails[0] + "," + appDetails[1] + "," + appDetails[2];
                    }
                }
            }

            //write back to the csv
            String appContent2 = "";
            //skip first line
            for (int i = 0; i < application.length; i++)
            {
                appContent2 += application[i] + "\n";
            }
            try
            {
                io.overwriteFile("Application.csv", appContent2);
            }
            catch (Exception e)
            {
                System.out.println("Failed to write file");
            }
        }
        catch (Exception e)
        {
            System.out.println("Failed to read file");
        }
    }
}