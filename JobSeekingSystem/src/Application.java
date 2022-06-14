public class Application extends Message
{
    private int applicationID;
    private String coverLetter;
    private String status;

    public Application()
    {

    }

    public Application(int applicationID, String coverLetter)
    {
        this.applicationID = applicationID;
        this.coverLetter = coverLetter;
        this.status = "Pending";
    }

    public void display()
    {
        System.out.println("Application no: " + applicationID);
        System.out.println("Message: " + coverLetter);
    }
//
    public int getApplicationID()
    {
        return applicationID;
    }

    public String getCoverLetter()
    {
        return coverLetter;
    }

    public void setApplicationID(int applicationID)
    {
        this.applicationID = applicationID;
    }

    public void setCoverLetter(String coverLetter)
    {
        this.coverLetter = coverLetter;
    }
}