import java.util.ArrayList;

public interface Communication
{

    default boolean sendMessage(JSS program, int messageID, int senderID, int receiverID, String header, String body)
    {
        boolean sent = false;
        try
        {
            program.storeMessage(messageID, false, senderID, receiverID, header, body);
            sent = true;
        }
        catch (Exception e)
        {
            sent = false;
            e.printStackTrace();
        }
           return sent;
    }

   default int getSenderByID(JSS program, int messageID)
    {
        Message message = program.retrieveMessage(messageID);
        int senderID = message.getSenderID();
        return senderID;
    }
    default Message messageToOpen(int messageID)
    {


        User user = this.relayUser();

        ArrayList<Message> messages = user.getMessages();


        int messageIndex = 0;
        int counter = 0;
        for (Message each:
             messages)
        {
            int checkID = each.getMessageID();

            if (messageID == checkID)
            {

                messageIndex = counter;
            }
            counter +=1;
        }



        Message toOpen = messages.get(messageIndex);

        toOpen.setHasReceived(true);
        JSS program = this.relayProgram();

        //mark the message as sent
        program.markAsSent(toOpen);



        return toOpen;
    }

    default boolean checkHasReceived (int messageID)
    {
        boolean sent = false;

        User user = relayUser();
        ArrayList<Message> messages = user.getMessages();
      Message toCheck = messages.get(messageID);
      if (toCheck.isHasReceived() == true)
      {
          sent = true;
      }

        return sent;
    }

    //requires implementing class to provide User for use in interface methods
    User relayUser();
    JSS relayProgram();

}
