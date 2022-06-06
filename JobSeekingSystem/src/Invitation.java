public class Invitation extends Message
{
    private String invitationText;
    private int jobID;

    public Invitation(int senderID, int receiverID, boolean hasRecieved, String invitationText, int jobID)
    {
        super(senderID, receiverID, hasRecieved);
        this.invitationText = invitationText;
        this.jobID = jobID;
    }
}