public class Invitation extends Message
{
    private String invitationText;
    private String jobTitle;

    public Invitation(String invitationText, String jobTitle)
    {
        this.invitationText = invitationText;
        this.jobTitle = jobTitle;
    }
}
