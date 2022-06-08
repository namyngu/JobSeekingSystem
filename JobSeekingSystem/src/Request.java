public class Request extends Message
{
    private String categoryName;
    private String justification;

    public Request()
    {
        this.categoryName = "Blank";
        this.justification = "Blank";
    }

    public Request(int senderID, int receiverID, String categoryName, String justification)
    {
        super(senderID, receiverID);
        this.categoryName = categoryName;
        this.justification = justification;
    }
}
