public abstract class User
{
    private int userID;
    private String name;
    private String userName;
    private String password;
    private boolean loggedIn;
    private boolean active;


    public User(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }

    public User(int userID, String name, String userName, String password, boolean loggedIn, boolean active)
    {
        this.userID = userID;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.loggedIn = loggedIn;
        this.active = active;
    }
}
