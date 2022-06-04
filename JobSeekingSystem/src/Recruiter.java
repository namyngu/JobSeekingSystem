import java.util.ArrayList;

public class Recruiter extends User
{
    private ArrayList<Job> jobs;
    private ArrayList<Invitation> invitations;
    private int totalAdverts;
    private int totalApplications;

    public Recruiter(int userID, String name, String userName, char[] password)
    {
        super(userID, name, userName, password);
        this.jobs = new ArrayList<Job>();
        this.invitations = new ArrayList<Invitation>();
        this.totalAdverts = 0;
        this.totalApplications = 0;
    }
}
