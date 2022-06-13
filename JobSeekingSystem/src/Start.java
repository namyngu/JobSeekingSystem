public class Start
{
    private JSS program;
    private LoginGUI startGUI;

    // Default Constructor.
    public Start()
    {
        this.program = new JSS();
        this.startGUI = new LoginGUI(this.program);
    }

    // Non-default Constructor.
    public Start(JSS program, LoginGUI login)
    {
        this.program = program;
        this.startGUI = login;
    }

    // Display method.
    public void display()
    {
        System.out.println("Main JSS Program: " + program);
        System.out.println("Login GUI: " + startGUI);
    }

    // Accessor methods.
    public JSS getProgram()
    {
        return program;
    }

    public LoginGUI getStartGUI()
    {
        return startGUI;
    }

    // Method to begin main program execution.
    public static void main(String[] args)
    {
        Start obj = new Start();
    }

    // Mutator methods.
    public void setProgram(JSS program)
    {
        this.program = program;
    }

    public void setStartGUI(LoginGUI login)
    {
        startGUI = login;
    }
}
