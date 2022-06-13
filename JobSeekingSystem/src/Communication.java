import java.util.ArrayList;

public interface Communication
{

    public default boolean sendMessage(JSS program, int messageID, int senderID, int receiverID, String header, String body)
    {
        boolean sent = false;
        try
        {
            program.storeMessage(messageID, senderID, receiverID, header, body);
            sent = true;
        }
        catch (Exception e)
        {
            sent = false;
            e.printStackTrace();
        }
           return sent;
    }

    public default int getSenderByID(JSS program, int messageID)
    {
        Message message = program.retrieveMessage(messageID);
        int senderID = message.getSenderID();
        return senderID;
    }


}
