public class AdminAlert extends Message
{
    private String alertText;
    private int jobRef;
    private int userRef;

    public AdminAlert()
    {
        this.alertText = "";
        this.jobRef = -1;
        this.userRef = -1;
    }
    //constructor for Admin messages RE: Jobs
    public AdminAlert(int senderID, int receiverID, String alertText, int jobRef)
    {
        super(senderID, receiverID);
        this.alertText = alertText;
        this.jobRef = jobRef;
    }
    //constructor for Admin messages RE: Jobseekers
    public AdminAlert(int senderID, int receiverID, int userRef)
    {
        super(senderID, receiverID);
        this.userRef = userRef;
    }
    //constructor for Admin message RE:Jobseekers and Jobs eg applications
    public AdminAlert(int senderID, int receiverID, String alertText, int jobRef, int userRef)
    {
        super(senderID, receiverID);
        this.alertText = alertText;
        this.jobRef = jobRef;
        this.userRef = userRef;
    }

    public AdminAlert(int senderID, int receiverID)
    {
        super(senderID,receiverID);
    }

    public AdminAlert(int senderID, int receiverID, String alertText)
    {
        super(senderID, receiverID);
        this.alertText = alertText;
    }

    public String getAlertText()
    {
        return alertText;
    }

    public void setAlertText(String alertText)
    {
        this.alertText = alertText;
    }

    public int getJobRef()
    {
        return jobRef;
    }

    public void setJobRef(int jobRef)
    {
        this.jobRef = jobRef;
    }

    public int getUserRef()
    {
        return userRef;
    }

    public void setUserRef(int userRef)
    {
        this.userRef = userRef;
    }
}
