import java.util.ArrayList;

public class Recruiter extends User
{
    private ArrayList<Job> jobs;
    private ArrayList<Invitation> invitations;

    public Recruiter(int userID, String name, String userName, String password, boolean loggedIn, boolean active, ArrayList<Job> jobs, ArrayList<Invitation> invitations)
    {
        super(userID, name, userName, password, loggedIn, active);
        this.jobs = new ArrayList<Job>();
        this.invitations = new ArrayList<Invitation>();
    }
}
