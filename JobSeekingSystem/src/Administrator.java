public class Administrator extends User
{

    private boolean isPrimaryAdmin;

    public Administrator() {
        super();
        isPrimaryAdmin = false;
    }

    public Administrator(int userID, String firstName, String lastName, String userName, String password, boolean active)
    {
        super(userID, firstName, lastName, userName, password, "Admin", active);
        this.isPrimaryAdmin = isPrimaryAdmin;
    }
}
