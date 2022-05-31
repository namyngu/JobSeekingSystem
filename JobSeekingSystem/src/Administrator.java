public class Administrator extends User
{

    private boolean isPrimaryAdmin;

    //default constructor needed
    public Administrator(int userID, String name, String userName, String password, boolean loggedIn, boolean active)
    {
        super(userID, name, userName, password, loggedIn, active);
    }
}
