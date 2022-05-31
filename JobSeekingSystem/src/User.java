public abstract class User
{
    private int userID;
    private String name;
    private String userName;
    private String password;
    private boolean loggedIn;
    private boolean active;


    //we need to think about how userID is issued
    //defult constructor needed because of code conventions
    //but we can't have it defaulting to the same number
    //static variable that increments on calling constructor?

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
