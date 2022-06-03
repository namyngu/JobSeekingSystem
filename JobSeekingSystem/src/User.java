import java.util.Arrays;

public abstract class User
{
    private int userID;
    private String name;
    private String userName;
    private char[] password;
    private boolean loggedIn;
    private boolean active;


    public User(String userName, char[] password)
    {
        this.userName = userName;
        this.password = password;
        loggedIn = false;
        active = true;
    }


    public User(int userID, String name, String userName, char [] password)
    {
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public char[] getPassword()
    {
        return password;
    }

    public void setPassword(char[] password)
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

    @Override
    public String toString()
    {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password=" + Arrays.toString(password) +
                ", loggedIn=" + loggedIn +
                ", active=" + active +
                '}';
    }
}
