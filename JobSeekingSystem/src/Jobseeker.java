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

    public Jobseeker(int userID, String firstName, String lastName, String userName, String password)
    {

        super(userID, firstName, lastName, userName, password, "Jobseeker");

        this.applications = new ArrayList<Application>();
        this.invitations = new ArrayList<Invitation>();
        this.skills = new ArrayList<String>();
        this.totalApplied = 0;
        this.totalOffers =0;
        this.totalInvites = 0;
    }

    public Jobseeker(int userID, String firstName, String lastName, String userName, String password, boolean active)
    {

        super(userID, firstName, lastName, userName, password, "Jobseeker", active);

        this.applications = new ArrayList<Application>();
        this.invitations = new ArrayList<Invitation>();
        this.skills = new ArrayList<String>();
        this.totalApplied = 0;
        this.totalOffers =0;
        this.totalInvites = 0;
    }

    // Method to hand skill Array off to Search class
    public ArrayList<String> getSkills() {
        return skills;
    }

}
