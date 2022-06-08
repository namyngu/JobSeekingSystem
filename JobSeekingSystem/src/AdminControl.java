public class AdminControl
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
            String text = "Your account will be blocked by Administrator " + this.admin.getUserName();
            text += "\nplease contact them immediately to discuss";
            Message notification = new AdminAlert(senderID, receiverID, text);
            sent = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sent = false;
        }
    }

    public int adminID()
    {
        return this.admin.getUserID();
    }
}
