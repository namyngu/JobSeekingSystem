/**
 * This class represents Locations in the world where Users can live
 * and where Jobs can be advertised in.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */
public class Location {
    private int locationID;
    private String state;
    private int postcode;
    private String city;

    /**
     * This is the Default constructor for the class.
     */
   public Location()
   {
        locationID = -1;
        state = "Unknown State";
        postcode = 1111;
        city = "Unknown City";
   }

    /**
     * This is a Non-default constructor for the class.
     * @param locationID an Integer representing the ID number of this Location.
     * @param state      a String containing the State of this Location.
     */
   public Location(int locationID, String state)
   {
       this.locationID = locationID;
       this.state = state;
   }

    /**
     * This is a Non-default constructor for the class.
     * @param locationID an Integer representing the ID number of this Location.
     * @param state      a String containing the State of this Location.
     * @param postcode   an Integer representing the Postcode of this Location.
     * @param city       a String containing the City of this Location.
     */
   public Location(int locationID, String state, int postcode, String city)
   {
       this.locationID = locationID;
       this.state = state;
       this.postcode = postcode;
       this.city = city;
   }

    /**
     * This is the display method for this class.
     */
   public void display() {
       System.out.println("Location ID: " + locationID);
       System.out.println("State: " + state);
       System.out.println("City: " + city);
       System.out.println("Postcode: " + postcode);
   }

    /**
     * This is the Accessor method for the city field.
     * @return  a String representing the City of this Location.
     */
    public String getCity() {return city;}

    /**
     * This is the Accessor method for the locationID field.
     * @return  an Integer representing this Location's ID number.
     */
    public int getLocationID() {
        return locationID;
    }

    /**
     * This is the Accessor method for the postcode field.
     * @return  an Integer representing this Location's postcode.
     */
    public int getPostcode() {
        return postcode;
    }

    /**
     * This is the Accessor method for the state field.
     * @return  a String representing the State of this Location.
     */
    public String getState() {
        return state;
    }

    /**
     * This is the Mutator method for the city field.
     * @param city  a String representing the new City for this Location.
     */
    public void setCity(String city) {this.city = city;}

    /**
     * This is the Mutator method for the locationID field.
     * @param locationID an Integer representing the new ID number for this Location.
     */
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    /**
     * This is the Mutator method for the postcode field.
     * @param postcode an Integer representing the new postcode for this Location.
     */
    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    /**
     * This is the Mutator method for the state field.
     * @param state a String representing the new state for this Location.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * This method concatenates information about this Location and returns it
     * as a String.
     * @return  a String containing the field information about this Location
     *          concatenated together.
     */
    public String toString(){
       return getCity() + " " + getState() + " " + Integer.toString(getPostcode());
    }
}
