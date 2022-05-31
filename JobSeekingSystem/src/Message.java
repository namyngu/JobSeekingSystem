public abstract class Message
{
    private int senderID;
    private int recieverID;
    private boolean hasRecieved;

    public Message()
    {
        this.senderID = 0;
        this.recieverID = 0;
        this.hasRecieved = false;
    }

    public Message(int senderID, int recieverID, boolean hasRecieved)
    {
        this.senderID = senderID;
        this.recieverID = recieverID;
        this.hasRecieved = false;
    }
}
