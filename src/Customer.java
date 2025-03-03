import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Customer {

    private final SimpleIntegerProperty customerID;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty lastname;
    private final SimpleStringProperty contactnumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty district;
    private final SimpleStringProperty city;

    public Customer(int customerID, String firstname, String lastname, String contactnumber, String email, String district, String city) {
        
        this.customerID = new SimpleIntegerProperty(customerID);
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.contactnumber = new SimpleStringProperty(contactnumber);
        this.email = new SimpleStringProperty(email);
        this.district = new SimpleStringProperty(district);
        this.city = new SimpleStringProperty(city);

    }

    public int getCustomerID() {
        return customerID.get();
    }

    public String getFirstname() {
        return firstname.get();
    }

    public String getLastname() {
        return lastname.get();
    }

    public String getContactnumber() {
        return contactnumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getDistrict() {
        return district.get();
    }

    public String getCity() {
        return city.get();
    }
    
}
