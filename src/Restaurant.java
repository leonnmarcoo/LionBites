import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Restaurant {

    private final SimpleIntegerProperty restaurantID;
    private final SimpleStringProperty rname;
    private final SimpleStringProperty rdistrict;
    private final SimpleStringProperty rcity;
    private final SimpleStringProperty categoryname;

    public Restaurant(int restaurantID, String rname, String rdistrict, String rcity, String categoryname) {

        this.restaurantID = new SimpleIntegerProperty(restaurantID);
        this.rname = new SimpleStringProperty(rname);
        this.rdistrict = new SimpleStringProperty(rdistrict);
        this.rcity = new SimpleStringProperty(rcity);
        this.categoryname = new SimpleStringProperty(categoryname);
        
    }

    public int getRestaurantID() {
        return restaurantID.get();
    }

    public String getRname() {
        return rname.get();
    }

    public String getRdistrict() {
        return rdistrict.get();
    }

    public String getRcity() {
        return rcity.get();
    }

    public String getCategoryname() {
        return categoryname.get();
    }
    
}
