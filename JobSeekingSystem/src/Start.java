/**
 * This class represents the main Object which will start the system.
 * @author  Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

public class Start
{
    private JSS program;
    private LoginGUI startGUI;

    /**
     * This is the Default constructor for this class.
     */
    public Start()
    {
        this.program = new JSS();
        this.startGUI = new LoginGUI(this.program);
    }

    /**
     * This is the Non-default constructor for this class.
     * @param program   A JSS Object representing the main Control class for the
     *                  system.
     * @param login     a LoginGUI Object which will present to the User and
     *                  allow them to login.
     */
    public Start(JSS program, LoginGUI login)
    {
        this.program = program;
        this.startGUI = login;
    }

    /**
     * This is the display method for this class.
     */
    public void display()
    {
        System.out.println("Main JSS Program: " + program);
        System.out.println("Login GUI: " + startGUI);
    }

    /**
     * This is the Accessor method for the program field.
     * @return  a JSS Object representing the main control class for the system.
     */
    public JSS getProgram()
    {
        return program;
    }

    /**
     * This is the Accessor method for the startGUI field.
     * @return  a LoginGUI Object representing the main login screen for the User.
     */
    public LoginGUI getStartGUI()
    {
        return startGUI;
    }

    /**
     * This is the main method of the entire system, which begins program execution.
     * @param args  A String Array of arguments which modifies main program execution.
     */
    public static void main(String[] args)
    {
        Start obj = new Start();
    }

    /**
     * This is the Mutator method for the program field.
     * @param program a JSS Object representing the main control class for the system.
     */
    public void setProgram(JSS program)
    {
        this.program = program;
    }

    /**
     * This is the Mutator method for the startGUI field.
     * @param login a LoginGUI Object representing the main login screen for the User.
     */
    public void setStartGUI(LoginGUI login)
    {
        startGUI = login;
    }
}
