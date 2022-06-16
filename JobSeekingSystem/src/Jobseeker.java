/**
 * This class represents a Jobseeker, which is an extension of the User class.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

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

    /**
     * This is the Default constructor for the class.
     */
    public Jobseeker() {
        super();
        applications = new ArrayList<>();
        invitations = new ArrayList<>();
        skills = new ArrayList<>();
        totalApplied = -1;
        totalOffers = -1;
        totalInvites = -1;
        email = "default@email.com";
        phone = "0000 000 000";
        location = new Location();

    }

    /**
     * This is a Non-default constructor for the class.
     * @param userID    an Integer containing the Jobseeker's User ID number.
     * @param firstName a String containing the Jobseeker's first name.
     * @param lastName  a String containing the Jobseeker's last name.
     * @param userName  a String containing the Jobseeker's username.
     * @param password  a String containing the Jobseeker's password.
     */
    public Jobseeker(int userID, String firstName, String lastName, String userName, String password) {

        super(userID, firstName, lastName, userName, password, "Jobseeker");

        this.applications = new ArrayList<>();
        this.invitations = new ArrayList<>();
        this.totalApplied = 0;
        this.totalOffers = 0;
        this.totalInvites = 0;
    }

    /**
     * This is a Non-default constructor for the class.
     * @param userID    an Integer containing the Jobseeker's User ID number.
     * @param firstName a String containing the Jobseeker's first name.
     * @param lastName  a String containing the Jobseeker's last name.
     * @param userName  a String containing the Jobseeker's username.
     * @param password  a String containing the Jobseeker's password.
     * @param active    a Boolean representing if the Jobseeker is currently
     *                  active in the system or not.
     */
    public Jobseeker(int userID, String firstName, String lastName, String userName, String password, boolean active) {

        super(userID, firstName, lastName, userName, password, "Jobseeker", active);

        this.applications = new ArrayList<>();
        this.invitations = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.totalApplied = 0;
        this.totalOffers = 0;
        this.totalInvites = 0;
        loadSkills();
        loadContact();
        System.out.println(getFullName());
    }

    /**
     * This is a Non-default constructor for the class.
     * @param userID     an Integer containing the Jobseeker's User ID number.*
     * @param firstName  a String containing the Jobseeker's first name.
     * @param lastName   a String containing the Jobseeker's last name.
     * @param userName   a String containing the Jobseeker's username.
     * @param password   a String containing the Jobseeker's password.
     * @param active     a Boolean representing if the Jobseeker is currently active
     *                   in the system or not.
     * @param forHomeGui a Boolean describing if the Jobseeker should be directed
     *                   to the Home GUI or not.
     */
    public Jobseeker(int userID, String firstName, String lastName, String userName, String password, boolean active, boolean forHomeGui) {

        super(userID, firstName, lastName, userName, password, "Jobseeker", active);

        this.applications = new ArrayList<>();
        this.invitations = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.totalApplied = 0;
        this.totalOffers = 0;
        this.totalInvites = 0;
        loadSkills();
        loadContact();
        System.out.println(getFullName());
    }

    public void display(){
        System.out.println("Total Applied: " + totalApplied);
        System.out.println("Total Offers: " + totalOffers);
        System.out.println("Total Invites: " + totalInvites);
        System.out.println("Email:" + email);
        System.out.println("Phone: " + phone);
        System.out.println("Location: " + location.toString());
        System.out.println("Skills: {");
        for (String skill : skills) {
            System.out.println(skill + "\n");
        }
        System.out.println("}");
        System.out.println("Applications: {");
        for (Application app : applications) {
            System.out.println(app.toString() + "\n");
        }
        System.out.println("}");
        System.out.println("Invitations: {");
        for (Invitation invite : invitations) {
            System.out.println(invite.toString() + "\n");
        }
        System.out.println("}");
    }

    /**
     * This is the Accessor method for the applications field.
     * @return  an ArrayList<Application> which contains a list of all the Applications
     *          this Jobseeker has made.
     */
    public ArrayList<Application> getApplications(){
        return applications;
    }

    /**
     * This is the Accessor method for the email field.
     * @return a String containing the Jobseeker's email address.
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * This method concatenates the Jobseeker's names and returns a String containing
     * their full name.
     * @return a String containing the Jobseeker's full name.
     */
    public String getFullName()
    {
        String fullname = getFirstName() + " " + getLastName();
        return fullname;
    }

    /**
     * This is the Accessor method for the invitations field.
     * @return an ArrayList of Invitations containing all of the Invitations
     *         this Jobseeker's has received.
     */
    public ArrayList<Invitation> getInvitations(){
        return invitations;
    }

    /**
     * This is the Accessor method for the location field.
     * @return  a Location Object which represents the Location this Jobseeker
     *          has listed as their Location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * This is the Accessor method for the phone field.
     * @return a String containing the Jobseeker's phone number.
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * This is the Accessor for the skills field.
     * @return  an ArrayList of Strings which represent a list of the skills this
     *          Jobseeker has added to their profile.
     */
    public ArrayList<String> getSkills() {
        return skills;
    }

    /**
     * This is the Accessor method for the totalApplied field.
     * @return  an Integer representing the total number of Applications
     *          this Jobseeker has made.
     */
    public int getTotalApplied(){
        return totalApplied;
    }

    /**
     * This is the Accessor method for the totalInvites field.
     * @return  an Integer representing the total number of Invitations
     *          this Jobseeker has received.
     */
    public int getTotalInvites(){
        return totalInvites;
    }

    /**
     * This is the Accessor method for the totalOffers field.
     * @return  an Integer representing the total number of job Offers this
     *          Jobseeker has received.
     */
    public int getTotalOffers(){
        return totalOffers;
    }

    /**
     * This method loads the contact information for this Jobseeker in from the
     * database.
     */
    public void loadContact() {

        File_Control fc = new File_Control();

        ArrayList<String> temp = fc.fileSearchId(super.getUserID(), "user-contact.csv");


        if (temp.size() == 3) {
            System.out.println("load contact details");
            int locationId = Integer.parseInt(temp.get(0));
            email = temp.get(1);
            phone = temp.get(2);
            //get contact info
            loadLocation(locationId);
        } else {
            System.out.println("No contact details, filling with temp");
            email = "temporary email";
            phone = "temporary phone";
            loadLocation(813);
        }
    }

    /**
     * This method loads the Location information for this Jobseeker in from
     * the database.
     * @param locationId an Integer representing the ID number of the Location
     *                   to be loaded in.
     */
    public void loadLocation(int locationId)
    {
        File_Control fc = new File_Control();
        ArrayList<String> temp = fc.fileSearchId(locationId, "Location.csv");

        //state postcode suburb
        location = new Location(locationId, temp.get(0), Integer.parseInt(temp.get(1)), temp.get(2));
        System.out.println(location.toString());
    }

    /**
     * This method loads the skill information for this Jobseeker in from the
     * database.
     */
    public void loadSkills() {

        File_Control fc = new File_Control();

        skills = fc.fileSearchId(super.getUserID(), "jobseeker-skills.csv");

        System.out.println("skills loaded\n");

        System.out.println(skills);
    }

    /**
     * This is the Mutator method for the applications field.
     * @param apps an ArrayList of Applications containing the Applications
     *             this Jobseeker has made.
     */
    public void setApplications(ArrayList<Application> apps) {
        applications = apps;
    }

    /**
     * This is the Mutator method for the email field.
     * @param email a String containing the Jobseeker's email address.
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * This is the Mutator method for the invitations field.
     * @param invites an ArrayList of Invitations containing the Invitations
     *                this Jobseeker has received.
     */
    public void setInvitations(ArrayList<Invitation> invites) {
        invitations = invites;
    }

    /**
     * This is the Mutator method for the location field.
     * @param location a Location this Jobseeker has set as their Location.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    /**
     * This is the Mutator method for the phone field.
     * @param phone a String containing the Jobseeker's phone number.
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * This is the Mutator method for the skills field.
     * @param skills an ArrayList of Strings containing all the skills this
     *               Jobseeker has added to their profile.
     */
    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    /**
     * This is the Mutator method for the applied field.
     * @param applied an Integer representing the number of total Applications
     *                this Jobseeker has made.
     */
    public void setTotalApplied(int applied) {
        totalApplied = applied;
    }

    /**
     * This is the Mutator method for the invites field.
     * @param invites an Integer representing the number of total Invitations
     *                this Jobseeker has received.
     */
    public void setTotalInvites(int invites) {
        totalInvites = invites;
    }

    /**
     * This is the Mutator method for the offers field.
     * @param offers an Integer representing the number of total Offers this
     *               Jobseeker has received.
     */
    public void setTotalOffers(int offers) {
        totalOffers = offers;
    }

    /**
     * This is the toString method for this class.
     * @return a String containing all the necessary information for this Jobseeker.
     */
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
