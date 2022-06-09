public abstract class Message
{
    private int senderID;
    private int receiverID;
    private boolean hasReceived = false;

    public Message()
    {
        this.senderID = 0;
        this.receiverID = 0;
    }

    public Message(int senderID, int receiverID)
    {
        this.senderID = senderID;
        this.receiverID = receiverID;
    }
}
