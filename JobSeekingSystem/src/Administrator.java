public class Administrator extends User
{

    private boolean isPrimaryAdmin;

    public Administrator(int userID, String firstName, String lastName, String userName, char [] password)
    {
        super(userID, firstName, lastName, userName, password, "Admin");
        this.isPrimaryAdmin = isPrimaryAdmin;
    }
}
