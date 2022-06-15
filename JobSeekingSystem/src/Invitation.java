/**
 * This class represents an Invitation to attend an interview, which is a subtype
 * of a Message Object.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */
public class Invitation extends Message
{
    private String invitationText;
    private int jobID;

    /**
     * This is the Default constructor for the class.
     */
    public Invitation(){
    }

    /**
     * This is the Accessor method for the invitationText field.
     * @return a String containing the body of the Invitation.
     */
    public String getInvitationText(){
        return invitationText;
    }

    /**
     * This is the Accessor method for the jobID field.
     * @return an Integer representing the ID number of the corresponding Job.
     */
    public int getJobID(){
        return jobID;
    }

    /**
     * This is the Mutator method for the invitationText field.
     * @param newText a String representing the body of the Invitation.
     */
    public void setInvitationText(String newText){
        invitationText = newText;
    }

    /**
     * This is the Mutator method for the jobID field.
     * @param newID an Integer containing the ID number of the corresponding Job.
     */
    public void setJobID(int newID){
        jobID = newID;
    }
}