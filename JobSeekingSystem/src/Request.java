public class Request extends Message
{
    private String categoryName;
    private String justification;

    public Request()
    {
        this.categoryName = "Blank";
        this.justification = "Blank";
    }

    public Request(String categoryName, String justification)
    {
        this.categoryName = categoryName;
        this.justification = justification;
    }
}
