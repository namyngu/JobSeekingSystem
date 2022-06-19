/**
 * This class represents an Administrator user, which is an extension of the User class.
 * @author  Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

public class Administrator extends User
{
    private boolean isPrimaryAdmin;

    /**
     * This is the Default constructor for the class.
     */
    public Administrator() {
        super();
        isPrimaryAdmin = false;
    }

    /**
     * This is the Non-default constructor for the class.
     * @param userID    an Integer containing the User ID number for the Administrator.
     * @param firstName a String containing the Administrator's first name.
     * @param lastName  a String containing the Administrator's last name.
     * @param userName  a String containing the Administrator's username.
     * @param password  a String containing the Administrator's password.
     * @param active    a Boolean describing if this Administrator is active
     *                  in the system or not.
     */
    public Administrator(int userID, String firstName, String lastName, String userName, String password, boolean active)
    {
        super(userID, firstName, lastName, userName, password, "Admin", active);
        this.isPrimaryAdmin = false;
    }

    /**
     * This is the display method for the class.
     */
    public void display(){
        System.out.println("Admin ID: " + getUserID());
        System.out.println("First Name: " + getFirstName());
        System.out.println("Last Name: " + getLastName());
        System.out.println("Username: " + getUserName());
        System.out.println("Password: " + getPassword());
        System.out.println("Active? " + isActive());
        System.out.println("Primary Admin? " + isPrimaryAdmin());
    }

    /**
     * This is the Accessor method for the isPrimaryAdmin field.
     * @return  a Boolean describing if this Administrator is the primary
     *          Administrator of the system or not.
     */
    public Boolean isPrimaryAdmin() {
        return isPrimaryAdmin;
    }

    /**
     * This is the Mutator method for the isPrimaryAdmin field.
     * @param primary a Boolean describing if this Administrator should be
     *                the main system admin or not.
     */
    public void setPrimaryAdmin(Boolean primary) {
        isPrimaryAdmin = primary;
    }

}
