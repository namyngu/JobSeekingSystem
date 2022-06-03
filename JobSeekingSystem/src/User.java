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
}
