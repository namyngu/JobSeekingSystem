import java.util.ArrayList;

public class AdminControl implements Communication
{
    private Administrator admin;
    JSS program;

    public AdminControl(Administrator user, JSS program)
    {
        this.admin = user;
        this.program = program;
//        System.out.println("creating admin control okay....");

    }

    public void switchLock(int userIndex)
    {
        try{
            program.alterActive(userIndex);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PromptGUI error = new PromptGUI("Failed to lock", e.toString());
        }

    }

    public void blockedMessage(int senderID, int receiverID)
    {
        boolean sent = false;
        try
        {
            String header = "ACCOUNT LOCK WARNING";
            String text = "Your account will be blocked by Administrator " + this.admin.getUserName();
            text += ". Please contact them immediately to discuss";
            int messageID = this.program.issueMessageID();
            Message notification = new Message(messageID, senderID, receiverID,header,text);
            program.storeMessage(messageID,false, senderID,receiverID,header,text);
            sent = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sent = false;
        }
    }



    public ArrayList<Message> relayMessages()
    {


        ArrayList<Message> toRelay = this.admin.getMessages();



        return toRelay;

    }

    public int adminID()
    {
        return this.admin.getUserID();
    }

    public void createMessage(int sender, int destination, String header, String body)
    {
        int messageID = this.program.issueMessageID();
        this.sendMessage(this.program,messageID,sender,destination,header,body);
    }


    @Override
    public User relayUser()
    {
        return this.admin;
    }

    @Override
    public JSS relayProgram()
    {
        return program;
    }
}
