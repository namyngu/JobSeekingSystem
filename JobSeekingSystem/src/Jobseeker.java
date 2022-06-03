import java.awt.desktop.AppForegroundListener;
import java.util.ArrayList;

public class Jobseeker extends User
{
    private ArrayList<Application> applications;
    private ArrayList<Invitation> invitations;
    private ArrayList<String> skills;
    private int totalApplied;
    private int totalOffers;
    private int totalInvites;

    public Jobseeker(int userID, String name, String userName, char [] password)
    {

        super(userID, name, userName, password);

        this.applications = new ArrayList<Application>();
        this.invitations = new ArrayList<Invitation>();
        this.skills = new ArrayList<String>();
        this.totalApplied = 0;
        this.totalOffers =0;
        this.totalInvites = 0;
    }
}
