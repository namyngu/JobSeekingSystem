public class Location {

    private int locationID;
    private String state;
    private int postcode;

   public Location()
   {

   }

   public Location(int locationID, String state)
   {
       this.locationID = locationID;
       this.state = state;
   }

   public Location(int locationID, String state, int postcode)
   {
       this.locationID = locationID;
       this.state = state;
       this.postcode = postcode;
   }

    public int getLocationID() {
        return locationID;
    }

    public String getState() {
        return state;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }
}
