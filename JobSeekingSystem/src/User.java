/**
 * This class represents a user in the System, and contains all the necessary
 * data for that User.
 * @author  Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import java.util.ArrayList;

public abstract class User
{
    private int userID;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean loggedIn;
    private boolean active;
    private String userType;
    private ArrayList<Message> messages;


    /**
     * This is the Default constructor for the class.
     */
    public User ()
    {
        userID = -1;
        firstName = "Default";
        lastName = "Default";
        userName = "unknownUser";
        password = "password";
        loggedIn = false;
        active = true;
        userType = "Guest";
        messages = new ArrayList<>();
    }

    /**
     * This is a Non-default constructor for the class.
     * @param userName a String representing the User's desired username.
     * @param password a String representing the User's desired password.
     */
    public User(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
        loggedIn = false;
        active = true;
        userType = "Guest";
        messages = new ArrayList<>();
    }

    /**
     * This is a Non-default constructor for the class.
     * @param userID an Integer representing the User's ID number.
     * @param firstName a String containing the User's first name.
     * @param lastName a String containing the User's last name.
     * @param userName a String representing the User's desired username.
     * @param password a String representing the User's desired password.
     * @param userType a String representing what type this User will be:
     *                 "Admin", "Jobseeker" or "Recruiter".
     */
    public User(int userID, String firstName, String lastName, String userName, String password, String userType)
    {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        loggedIn = false;
        active = true;
        this.userType = userType;
        messages = new ArrayList<>();
    }

    /**
     * This is a Non-default constructor for the class.
     * @param userID an Integer representing the User's ID number.
     * @param firstName a String containing the User's first name.
     * @param lastName a String containing the User's last name.
     * @param userName a String representing the User's desired username.
     * @param password a String representing the User's desired password.
     * @param userType a String representing what type this User will be:
     *                 "Admin", "Jobseeker" or "Recruiter".
     * @param active    a Boolean representing is this User is active in the
     *                  system or not.
     */
    public User(int userID, String firstName, String lastName, String userName, String password, String userType, boolean active)
    {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        loggedIn = false;
        this.active = active;
        this.userType = userType;
        messages = new ArrayList<>();
    }

    /**
     * This method adds a new Message to this User.
     * @param message a Message which will be added to this User.
     * @return  a Boolean, true if the Message has been successfully added,
     *          false if the Message was not able to be successfully added.
     */
    public boolean addMessage(Message message)
    {
        boolean delivered = false;
        try
        {
            this.messages.add(message);
            delivered = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return delivered;
    }

    /**
     * This method prints and displays details about the User to the terminal.
     */
    public void display()
    {
        System.out.println("UserID: " + userID);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Username: " + userName);
        System.out.println("UserType: " + userType);
    }

    /**
     * This is the Accessor method for the Active field.
     * @return  a Boolean representing if the User is active in the system
     *          or not. True: User is active, False: User is inactive.
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * This is the Accessor method for the loggedIn field.
     * @return  a Boolean representing if the User is currently logged in
     *          or not. True: User is logged in, False: User is not logged in.
     */
    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    /**
     * This is the Accessor method for the firstName field.
     * @return  a String representing the User's first name.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * This is the Accessor method for the lastName field.
     * @return  a String representing the User's last name.
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * This is the Accessor method for the Messages field.
     * @return  an ArrayList of Message objects, representing
     *          all of the User's Messages.
     */
    public ArrayList<Message> getMessages()
    {
        return messages;
    }

    /**
     * This is the Accessor method for the password field.
     * @return  a String representing the User's password.
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * This is the Accessor method for the userID field.
     * @return  an Integer representing the User's ID number.
     */
    public int getUserID()
    {
        return userID;
    }

    /**
     * This is the Accessor method for the userName field.
     * @return  a String representing the User's chosen username.
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * This is the Accessor method for the userType.
     * @return  a String representing the User's type, "Admin",
     *          "Jobseeker" or "Recruiter".
     */
    public String getUserType()
    {
        return userType;
    }

    /**
     * This method converts this User's list of Messages into a String.
     * Mainly used for debugging (printing the String to the terminal),
     * or thrown into a PromptGUI for error notification.
     * @return  a String representing all of the User's Messages
     *          concatenated together using a StringBuilder.
     */
    public String messagesToString()
    {
        StringBuilder mail = new StringBuilder();
        for (Message list: this.messages)
        {
           String header = list.getHeader().toUpperCase();
           String body = list.getBody().toLowerCase();
           mail.append(header);
           mail.append(":");
           mail.append(body);
        }
        return mail.toString();
    }

    /**
     * This is the Mutator method for the active field.
     * @param active A Boolean describing whether this User should be
     *               set as active in the system or not.
     */
    public void setActive(boolean active)
    {
        this.active = active;
    }

    /**
     * This is the Mutator method for the firstName field.
     * @param firstName a String containing the User's first name.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * This is the Mutator method for the lastName field.
     * @param lastName a String containing the User's last name.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * This is the Mutator method for the loggedIn field.
     * @param loggedIn a Boolean describing whether or not this User should
     *                 be set as currently logged in or not.
     */
    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }

    /**
     * This is the Mutator method for the messages field.
     * @param messages an ArrayList containing all of the Messages for this User.
     */
    public void setMessages(ArrayList<Message> messages)
    {
        this.messages = messages;
    }

    /**
     * This is the Mutator method for the password field.
     * @param password a String containing this User's password.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * This is the Mutator method for the userID field.
     * @param userID an Integer containing the User's ID number.
     */
    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    /**
     * This is the Mutator method for the userName field.
     * @param userName a String containing this User's username.
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * This is the Mutator method for the userType field. Note that this method is
     * included for completeness, however it would be possible to use this method to
     * override a User's userType to "Admin" and potentially compromise the system.
     * @param userType a String containing the User's type. Should be set to
     *                 "Admin", "Jobseeker" or "Recruiter" as needed.
     */
    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    /**
     * This method concatenates important User data and returns it as a String.
     * Can be used for debugging to print this information to the terminal, or
     * to populate a PromptGUI error notification for the system User.
     * @return  a String containing all of the User's relevant data, concatenated
     *          together.
     */
    @Override
    public String toString()
    {
        return "User{" +
                "userID=" + userID +
                ", name='" + firstName + " " + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password=" + password +
                ", loggedIn=" + loggedIn +
                ", active=" + active +
                '}';
    }

    // TODO: Remove if not needed.
    //Method to save user to users.csv
    //    public void saveUser(String fileName)
    //    {
    //        try
    //        {
    //            String userData = userID + "," + firstName + "," + lastName + "," + userName + "," + password + "," + userType;
    //            File_Control io = new File_Control();
    //            io.writeFile("users.csv", userData);
    //        }
    //        catch (Exception e)
    //        {
    //            System.out.println("Error failed to save user into csv.");
    //        }
    //
    //    }
}
