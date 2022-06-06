public class Start
{
    private JSS program;
    private LoginGUI startGUI;

    public Start()
    {
        this.program = new JSS();
        this.startGUI = new LoginGUI(this.program);

    }

    public static void main(String[] args)
    {
        JobSeekerUpdateGUI jsu = new JobSeekerUpdateGUI();
        Start obj = new Start();

    }
}
