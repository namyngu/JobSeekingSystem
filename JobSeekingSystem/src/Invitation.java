import java.time.LocalDate;

/**
 * This class represents an Invitation to attend an interview, which is a subtype
 * of a Message Object.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */
public class Invitation extends Message
{
    private int jobID;

    /**
     * This is the Default constructor for the class.
     */
    public Invitation(){
    }
    /**
     * This is the Default constructor for the class.
     * @param jobID is the job to be applied for
     *
     */
    public Invitation(int messageID, int senderID, int receiverID, String header, String text, LocalDate sentDate, int jobID)
    {
        super(messageID, senderID, receiverID, header, text, sentDate);
        this.jobID = jobID;
    }

    /**
     * This is the Accessor method for the jobID field.
     * @return an Integer representing the ID number of the corresponding Job.
     */
    public int getJobID(){
        return jobID;
    }

    /**
     * This is the Mutator method for the jobID field.
     * @param newID an Integer containing the ID number of the corresponding Job.
     */
    public void setJobID(int newID){
        jobID = newID;
    }
}