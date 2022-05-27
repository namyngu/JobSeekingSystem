public class Application extends Message
{

    private String coverLetter;
    private String skills;

    public Application(String coverLetter, String skills)
    {
        this.coverLetter = coverLetter;
        this.skills = skills;
    }
}
