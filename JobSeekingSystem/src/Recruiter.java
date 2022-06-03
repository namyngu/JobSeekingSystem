import java.util.ArrayList;

public class Recruiter extends User
{
    private ArrayList<Job> jobs;
    private ArrayList<Invitation> invitations;
    private int totalAdverts;
    private int totalApplications;

    public Recruiter(String userName, char[] password)
    {
        super(userName, password);
    }
}
