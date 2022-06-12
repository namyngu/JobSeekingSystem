public interface Communication
{

    public default boolean sendMessage(JSS program, int senderID, int receiverID, String header, String body)
    {
        boolean sent = false;
        try
        {
            program.storeMessage(senderID, receiverID, header, body);
            sent = true;
        }
        catch (Exception e)
        {
            sent = false;
            e.printStackTrace();
        }
           return sent;
    }
}
