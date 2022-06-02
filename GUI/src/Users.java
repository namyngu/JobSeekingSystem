public class Users
{
    private String username;
    private char[] password;
    private int accessLvl;

    public Users()
    {
        username = "admin";
        password = new char[]{'a', 'd', 'm', 'i', 'n', '1', '2', '3'};
        accessLvl = 10;
    }

    public Users(String username, char[] password)
    {
        this.username = username;
        this.password = password;
        this.accessLvl = 1;
    }

    public String getUser()
    {
        return username;
    }

    //probably make this getter protected in your actual code
    public char[] getPassword()
    {
        return password;
    }

    public void setUser(String username)
    {
        this.username = username;
    }

    public void setPassword(char[] password)
    {
        this.password = password;
    }

}
