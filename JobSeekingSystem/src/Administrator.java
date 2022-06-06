public class Administrator extends User
{

    private boolean isPrimaryAdmin;

    public Administrator(int userID, String firstName, String lastName, String userName, String password)
    {
        super(userID, firstName, lastName, userName, password, "Admin");
        this.isPrimaryAdmin = isPrimaryAdmin;
    }
}
