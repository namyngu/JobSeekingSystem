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
}
