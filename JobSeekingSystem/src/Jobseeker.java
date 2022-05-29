import java.awt.desktop.AppForegroundListener;
import java.util.ArrayList;

public class Jobseeker extends User
{
    private ArrayList<Application> applications;
    private ArrayList<Invitation> invitations;
    private ArrayList<String> skills;

    public Jobseeker(int userID, String name, String userName, String password, boolean loggedIn, boolean active)
    {
        super(userID, name, userName, password, loggedIn, active);
        this.applications = new ArrayList<Application>();
        this.invitations = new ArrayList<Invitation>();
        this.skills = new ArrayList<String>();
    }
}
