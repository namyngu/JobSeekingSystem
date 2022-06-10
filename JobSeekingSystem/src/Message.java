public class Message
{
    private int senderID;
    private int receiverID;
    private boolean hasReceived;
    private String header;
    private String body;

    public Message()
    {
        this.senderID = -1;
        this.receiverID = -1;
        this.hasReceived = false;
        this.header = "";
        this.body = "";

    }

    public Message(int senderID, int receiverID, String header, String body)
    {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.hasReceived = false;
        this.header = header;
        this.body = body;
    }

    public int getSenderID()
    {
        return senderID;
    }

    public void setSenderID(int senderID)
    {
        this.senderID = senderID;
    }

    public int getReceiverID()
    {
        return receiverID;
    }

    public void setReceiverID(int receiverID)
    {
        this.receiverID = receiverID;
    }

    public boolean isHasReceived()
    {
        return hasReceived;
    }

    public void setHasReceived(boolean hasReceived)
    {
        this.hasReceived = hasReceived;
    }

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }
}
