public class Administrator extends User
{

    private boolean isPrimaryAdmin;

    public Administrator(String userName, char[] password, boolean isPrimaryAdmin)
    {
        super(userName, password);
        this.isPrimaryAdmin = isPrimaryAdmin;
    }
}
