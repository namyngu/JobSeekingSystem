public class Application extends Message
{


    private int jobRef;

    public Application()
    {

    }

    public Application(int senderID, int receiverID, String header, String body)
    {
        super(senderID, receiverID, header, body);
    }

    public Application(int nextMessageID, String status, int sender, int messageTo, String header, String body)
    {
        super(nextMessageID, status, sender, messageTo, header, body);
    }

    public Application(int messageID, int senderID, int receiverID, String header, String text)
    {
        super(messageID, senderID, receiverID, header, text);
    }

    public void display()
    {
        System.out.println("Job refrence is " + jobRef);
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
}