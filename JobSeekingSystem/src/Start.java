public class Start
{
    private JSS program;
    private LoginGUI startGUI;

    public Start()
    {
        this.program = new JSS();
        this.startGUI = new LoginGUI(this.program);
        JobSeekerHomeGUI js = new JobSeekerHomeGUI();
    }

    public static void main(String[] args)
    {

        Start obj = new Start();

    }
}
