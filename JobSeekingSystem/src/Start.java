public class Start
{
    private JSS program;
    private LoginGUI startGUI;

    public Start()
    {
        this.program = new JSS(this);
        this.startGUI = new LoginGUI(this.program);
    }

    public static void main(String[] args)
    {
        Start obj = new Start();
    }
}
