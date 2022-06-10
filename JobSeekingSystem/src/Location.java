public class Location {

    private int locationID;
    private String state;
    private int postcode;
    private String city;

   public Location()
   {

   }

   public Location(int locationID, String state)
   {
       this.locationID = locationID;
       this.state = state;
   }

   public Location(int locationID, String state, int postcode, String city)
   {
       this.locationID = locationID;
       this.state = state;
       this.postcode = postcode;
       this.city = city;
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
    public String getCity() {return city;}

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }
    public void setCity(String city) {this.city = city;}
}
