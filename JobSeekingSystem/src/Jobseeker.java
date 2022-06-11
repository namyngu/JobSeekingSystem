import java.awt.desktop.AppForegroundListener;
import java.util.ArrayList;

public class Jobseeker extends User {
    private ArrayList<Application> applications;
    private ArrayList<Invitation> invitations; // I don't think we need this anymore, going to put list of messages in User class
    private ArrayList<String> skills;
    private int totalApplied;
    private int totalOffers;
    private int totalInvites;

    public Jobseeker(int userID, String firstName, String lastName, String userName, String password) {

        super(userID, firstName, lastName, userName, password, "Jobseeker");

        this.applications = new ArrayList<Application>();
        this.invitations = new ArrayList<Invitation>();
        loadSkills();
        this.totalApplied = 0;
        this.totalOffers = 0;
        this.totalInvites = 0;
    }

    public Jobseeker(int userID, String firstName, String lastName, String userName, String password, boolean active) {

        super(userID, firstName, lastName, userName, password, "Jobseeker", active);

        this.applications = new ArrayList<Application>();
        this.invitations = new ArrayList<Invitation>();
        this.skills = new ArrayList<String>();
        this.totalApplied = 0;
        this.totalOffers = 0;
        this.totalInvites = 0;
        loadSkills();
        System.out.println(getFullName());
    }

    //populate jobseeker skillslist from csv
    public void loadSkills() {

        File_Control fc = new File_Control();

        skills = fc.fileSearchId(super.getUserID(), "jobseeker-skills.csv");

        System.out.println("skills loaded\n");

        System.out.println(skills);
    }

    public String getFullName()
    {
        String fullname = getFirstName() + " " + getLastName();

        return fullname;
    }




    // Method to hand skill Array off to Search class
    public ArrayList<String> getSkills() {
        return skills;
    }

    // Method to set list of Skills (needed at the moment for search testing)
    public void setSkills(ArrayList<String> newSkills) {
        skills = newSkills;
    }

    // Method to print this Jobseeker out as a String
    public String toString() {

        return "Jobseeker{" +
                "applications=" + applications + "\'" +
                ", invitations=" + invitations + "\'" +
                ", skills=" + skills + "\'" +
                ", totalApplied=" + totalApplied + "\'" +
                ", totalOffers=" + totalOffers + "\'" +
                ", totalInvites=" + totalInvites + "\'" +
                "}";
    }
}
