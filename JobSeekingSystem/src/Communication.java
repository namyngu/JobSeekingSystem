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
//        System.out.println("com31 userID " + user.getUserID());
        ArrayList<Message> messages = user.getMessages();
        //TODO this needs to get the index relevant to the user!

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
        //running through to find the message index

//        for (int i=0; i<messages.size(); i+=1)
//        {
//            if (messageID == messages.get(i).getMessageID())
//            {
//                messageIndex = i;
//                System.out.println("com40 message ID is" + messages.get(i).getMessageID());
//                System.out.println("counter is " +i);
//            }
//        }


        Message toOpen = messages.get(messageIndex);

        toOpen.setHasReceived(true);
        JSS program = this.relayProgram();

        //mark the message as sent
        program.markAsSent(toOpen);



        return toOpen;
    }

    //requires implementing class to provide User for use in interface methods
    User relayUser();
    JSS relayProgram();

}
