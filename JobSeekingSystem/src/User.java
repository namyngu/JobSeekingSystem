import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

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


    public User(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
        loggedIn = false;
        active = true;
        userType = "Guest";
        messages = new ArrayList<Message>();
    }


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
        messages = new ArrayList<Message>();
    }

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
        messages = new ArrayList<Message>();
    }

    public void display()
    {
        System.out.println("UserID: " + userID);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Username: " + userName);
        System.out.println("UserType: " + userType);
    }


    //TODO look at putting this into control class?

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
    /*
    Method below is just to check messages are getting though, probably delete later, Gerard
     */
    public String messagesToString()
    {
        String mail = "";
        for (Message list: this.messages
             )
        {

           String header = list.getHeader().toUpperCase();
           String body = list.getBody().toLowerCase();
           mail += header + ": " + body;

        }
        return mail;
    }


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


    //Setters & Getters

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public String getUserType()
    {
        return userType;
    }


    //possible security concern - can set a user to Admin.
    public String setUserType(String userType)
    {
        return (this.userType = userType);
    }


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
}
