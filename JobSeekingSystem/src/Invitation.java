public class Invitation extends Message
{
    private String invitationText;
    private int jobID;

    public Invitation(int senderID, int recieverID, boolean hasRecieved, String invitationText, int jobID)
    {
        super(senderID, recieverID, hasRecieved);
        this.invitationText = invitationText;
        this.jobID = jobID;
    }
}