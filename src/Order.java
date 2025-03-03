import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final SimpleIntegerProperty orderID;
    private final SimpleIntegerProperty customerID;
    private final SimpleIntegerProperty restaurantID;
    private final SimpleIntegerProperty productID;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty amount;
    private final SimpleIntegerProperty paymentmethodID;
    private final SimpleStringProperty modes; 
    private final SimpleStringProperty orderDate;
    private final SimpleStringProperty orderTime;
    
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty lastname;
    private final SimpleStringProperty orderDescription; 
    private final SimpleStringProperty district;
    private final SimpleStringProperty city;
    private final SimpleStringProperty paymentMethod; 
    private final SimpleStringProperty deliveryMode;  

    public Order(int orderID, int customerID, int restaurantID, int productID, int quantity,
                 double amount, int paymentmethodID, String modes, String orderDate, String orderTime) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.restaurantID = new SimpleIntegerProperty(restaurantID);
        this.productID = new SimpleIntegerProperty(productID);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.amount = new SimpleDoubleProperty(amount);
        this.paymentmethodID = new SimpleIntegerProperty(paymentmethodID);
        this.modes = new SimpleStringProperty(modes);
        this.orderDate = new SimpleStringProperty(orderDate);
        this.orderTime = new SimpleStringProperty(orderTime);
        
        this.firstname = new SimpleStringProperty("");
        this.lastname = new SimpleStringProperty("");
        this.orderDescription = new SimpleStringProperty("");
        this.district = new SimpleStringProperty("");
        this.city = new SimpleStringProperty("");
        this.paymentMethod = new SimpleStringProperty("");
        this.deliveryMode = new SimpleStringProperty(modes);
    }
    
    public Order(int orderID, int customerID, String firstname, String lastname, String orderDescription,
                 int quantity, double amount, String paymentMethod, String district, String city, String deliveryMode) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.restaurantID = new SimpleIntegerProperty(0);
        this.productID = new SimpleIntegerProperty(0);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.amount = new SimpleDoubleProperty(amount);
        this.paymentmethodID = new SimpleIntegerProperty(0);
        this.modes = new SimpleStringProperty(deliveryMode);
        this.orderDate = new SimpleStringProperty("");
        this.orderTime = new SimpleStringProperty("");
        
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.orderDescription = new SimpleStringProperty(orderDescription);
        this.district = new SimpleStringProperty(district);
        this.city = new SimpleStringProperty(city);
        this.paymentMethod = new SimpleStringProperty(paymentMethod);
        this.deliveryMode = new SimpleStringProperty(deliveryMode);
    }
    
    public Order(int orderID, int quantity, String paymentMethod, String district, String city, String deliveryMode) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.customerID = new SimpleIntegerProperty(0);
        this.restaurantID = new SimpleIntegerProperty(0);
        this.productID = new SimpleIntegerProperty(0);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.amount = new SimpleDoubleProperty(0.0);
        this.paymentmethodID = new SimpleIntegerProperty(0);
        this.modes = new SimpleStringProperty(deliveryMode);
        this.orderDate = new SimpleStringProperty("");
        this.orderTime = new SimpleStringProperty("");
        
        this.firstname = new SimpleStringProperty("");
        this.lastname = new SimpleStringProperty("");
        this.orderDescription = new SimpleStringProperty("");
        this.district = new SimpleStringProperty(district);
        this.city = new SimpleStringProperty(city);
        this.paymentMethod = new SimpleStringProperty(paymentMethod);
        this.deliveryMode = new SimpleStringProperty(deliveryMode);
    }

    public Order(int orderID, int customerID, int quantity, String paymentMethod, String district, String city, String deliveryMode) {
        this(orderID, customerID, "", "", "", quantity, 0.0, paymentMethod, district, city, deliveryMode);
    }     

    public Order(int orderID, int quantity, int product, double amount) {
        this(orderID, 0, 0, product, quantity, amount, 0, "", "", "");
    }
    
    public int getOrderID() { 
        return orderID.get(); 
    }

    public int getCustomerID() { 
        return customerID.get(); 
    }

    public int getRestaurantID() { 
        return restaurantID.get(); 
    }

    public int getProductID() { 
        return productID.get(); 
    }

    public int getQuantity() { 
        return quantity.get(); 
    }

    public double getAmount() { 
        return amount.get(); 
    }

    public int getPaymentmethodID() { 
        return paymentmethodID.get(); 
    }

    public String getModes() { 
        return modes.get(); 
    }

    public String getOrderDate() { 
        return orderDate.get(); 
    }

    public String getOrderTime() { 
        return orderTime.get(); 
    }

    public String getFirstname() { 
        return firstname.get(); 
    }

    public String getLastname() { 
        return lastname.get(); 
    }

    public String getOrderDescription() { 
        return orderDescription.get(); 
    }

    public String getDistrict() { 
        return district.get(); 
    }

    public String getCity() { 
        return city.get(); 
    }

    public String getPaymentmethod() { 
        return paymentMethod.get(); 
    }

    public String getMode() { 
        return deliveryMode.get(); 
    }
}