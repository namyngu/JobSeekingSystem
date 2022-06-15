import java.time.LocalDate;
import java.util.ArrayList;

public interface Communication
{

    default boolean sendMessage(JSS program, Message message)
    {

        boolean sent = false;
        int messageID = message.getMessageID();
        int senderID = message.getSenderID();
        int receiverID = message.getReceiverID();
        String header = message.getHeader();
        String body = message.getBody();
        LocalDate date = message.getSentDate();

        try
        {

        if (message instanceof Application)
        {

            int jobRef = ((Application) message).getJobRef();
            program.storeMessage(messageID, false, senderID, receiverID, header, body, jobRef,date);
        }

        else if (message instanceof Invitation)

        {

        }
            else

            program.storeMessage(messageID, false, senderID, receiverID, header, body, -1,date);
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
