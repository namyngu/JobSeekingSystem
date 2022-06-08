public abstract class Message
{
    private int senderID;
    private int receiverID;
    private boolean hasReceived;

    public Message()
    {
        this.senderID = 0;
        this.receiverID = 0;
        this.hasReceived = false;
    }

    public Message(int senderID, int receiverID)
    {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.hasReceived = false;
    }
}
