import java.awt.desktop.AppForegroundListener;
import java.util.ArrayList;

public class Jobseeker extends User {
    private ArrayList<Application> applications;
    private ArrayList<Invitation> invitations; // I don't think we need this anymore, going to put list of messages in User class
    private ArrayList<String> skills;
    private int totalApplied;
    private int totalOffers;
    private int totalInvites;

    private String email;

    private String phone;

    private Location location;

    public Jobseeker(int userID, String firstName, String lastName, String userName, String password) {

        super(userID, firstName, lastName, userName, password, "Jobseeker");

        this.applications = new ArrayList<Application>();
        this.invitations = new ArrayList<Invitation>();
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
        loadContact();
        System.out.println(getFullName());
    }

    public Jobseeker(int userID, String firstName, String lastName, String userName, String password, boolean active, boolean forHomeGui) {

        super(userID, firstName, lastName, userName, password, "Jobseeker", active);

        this.applications = new ArrayList<Application>();
        this.invitations = new ArrayList<Invitation>();
        this.skills = new ArrayList<String>();
        this.totalApplied = 0;
        this.totalOffers = 0;
        this.totalInvites = 0;
        loadSkills();
        loadContact();
        System.out.println(getFullName());
    }

    //populate jobseeker skillslist from csv
    public void loadSkills() {

        File_Control fc = new File_Control();

        skills = fc.fileSearchId(super.getUserID(), "jobseeker-skills.csv");

        System.out.println("skills loaded\n");

        System.out.println(skills);
    }

    public void loadContact() {

        File_Control fc = new File_Control();

         ArrayList<String> temp = fc.fileSearchId(super.getUserID(), "user-contact.csv");


        if(temp.size() == 3)
        {
            System.out.println("load contact details");
            int locationId = Integer.parseInt(temp.get(0));
            email = temp.get(1);
            phone = temp.get(2);
            //get contact info
            loadLocation(locationId);
        }
        else {
            System.out.println("No contact details, filling with temp");
            email = "temporary email";
            phone = "temporary phone";
            loadLocation(813);
        }


    }


    public void loadLocation(int locationId)
    {
        File_Control fc = new File_Control();
        ArrayList<String> temp = fc.fileSearchId(locationId, "Location.csv");

        //state postcode suburb
        location = new Location(locationId, temp.get(0), Integer.parseInt(temp.get(1)), temp.get(2));
        System.out.println(location.toString());
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

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    // Method to set list of Skills (needed at the moment for search testing)
    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
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
