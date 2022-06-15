import java.time.LocalDate;

public class Application extends Message
{
    private int jobRef;
    private String status;

    public Application()
    {

    }

    public Application(int senderID, int receiverID, String header, String body, LocalDate sentDate)
    {
        super(senderID, receiverID, header, body, sentDate);
        status = "pending";
    }

    public Application(int nextMessageID, String status, int sender, int messageTo, String header, String body, LocalDate sentDate)
    {
        super(nextMessageID, status, sender, messageTo, header, body, sentDate);
        status = "pending";
    }

    public Application(int messageID, int senderID, int receiverID, String header, String text, LocalDate sentDate)
    {
        super(messageID, senderID, receiverID, header, text, sentDate);
        status = "pending";
    }

    public void display()
    {
        System.out.println("Job reference is " + jobRef);
    }
//

    public int getJobRef()
    {
        return jobRef;
    }

    public void setJobRef(int jobRef)
    {
        this.jobRef = jobRef;
    }

    public char[] getApplicationID()
    {
        return new char[2];
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}