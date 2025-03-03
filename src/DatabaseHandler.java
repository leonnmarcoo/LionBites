import java.sql.*;
import your.Category.model.Category;
import your.Orders.model.Order;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatabaseHandler {
    
    private static DatabaseHandler handler = null;
        private static Statement stmt = null;
        private static PreparedStatement pstatement = null;

        public static DatabaseHandler getInstance() {
            if (handler == null) {
                handler = new DatabaseHandler();
            }
            return handler;
        }

        public static Connection getDBConnection()
        {
            Connection connection = null;
            String dburl = "jdbc:mysql://127.0.0.1:3306/librarydb";
            String userName = "root";
            String password = "password";

            try
            {
                connection = DriverManager.getConnection(dburl, userName, password);

            } catch (Exception e){
                e.printStackTrace();
            }

            return connection;
        }

        public ResultSet execQuery(String query) {
            ResultSet result;
            try {
                stmt = getDBConnection().createStatement();
                result = stmt.executeQuery(query);
            }
            catch (SQLException ex) {
                System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
                return null;
            }
            finally {
            }
            return result;
        }

        public static boolean validateadminLogin(String username, String password){

            getInstance();
            String query = "SELECT * FROM admin WHERE Username = '" + username + "' AND Password = '" + password + "'";
            
            System.out.println(query);
    
            ResultSet result = handler.execQuery(query);
            try {
                if (result.next()) {
                    return true;
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            return false;
        }

        public static ResultSet getAdmins() {

            ResultSet result = null;

            try {
                String query = "SELECT * FROM admin";
                result = handler.execQuery(query);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

    // CRUD Admin

    public static boolean addAdmin(Admin admin) {

        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO admin (Username, Password) VALUES (?, ?)");
            pstatement.setString(1, admin.getUsername());
            pstatement.setString(2, admin.getPassword());

            return pstatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean deleteAdmin(Admin admin) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM admin WHERE Username = ?");
            pstatement.setString(1, admin.getUsername());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean updateAdmin(Admin admin) {

        try {
            pstatement = getDBConnection().prepareStatement("UPDATE admin SET Username = ?, Password = ? WHERE Username = ?");
            pstatement.setString(1, admin.getUsername());
            pstatement.setString(2, admin.getPassword());
            pstatement.setString(3, admin.getUsername());

            int res = pstatement.executeUpdate();
            
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    // CRUD Customer

    public static ResultSet getCustomer() {
    
        ResultSet result = null;
        
        try {
            String query = "SELECT * FROM Customer";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static boolean addCustomer(Customer customer) {

        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO Customer (firstname, lastname, contactnumber, email, district, city) VALUES (?, ?, ?, ?, ?, ?)");
            pstatement.setString(1, customer.getFirstname());
            pstatement.setString(2, customer.getLastname());
            pstatement.setString(3, customer.getContactnumber());
            pstatement.setString(4, customer.getEmail());
            pstatement.setString(5, customer.getDistrict());
            pstatement.setString(6, customer.getCity());

            return pstatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean deleteCustomer(Customer customer) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM Customer WHERE customerID = ?");
            pstatement.setInt(1, customer.getCustomerID());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean updateCustomer(Customer customer) {

        try {
            pstatement = getDBConnection().prepareStatement("UPDATE Customer SET firstname = ?, lastname = ?, contactnumber = ?, email = ?, district = ?, city = ? WHERE customerID = ?");
            pstatement.setString(1, customer.getFirstname());
            pstatement.setString(2, customer.getLastname());
            pstatement.setString(3, customer.getContactnumber());
            pstatement.setString(4, customer.getEmail());
            pstatement.setString(5, customer.getDistrict());
            pstatement.setString(6, customer.getCity());
            pstatement.setInt(7, customer.getCustomerID());

            int res = pstatement.executeUpdate();
            
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    // CRUD Category
    
    public static ResultSet getCategory() {
    
        ResultSet result = null;
        
        try {
            String query = "SELECT * FROM Category";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static boolean addCategory(Category category) {

        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO Category (categoryname) VALUES (?)");
            pstatement.setString(1, category.getCategoryname());

            return pstatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean deleteCategory(Category category) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM Category WHERE categoryID = ?");
            pstatement.setInt(1, category.getCategoryID());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean updateCategory(Category category) {

        try {
            pstatement = getDBConnection().prepareStatement("UPDATE Category SET categoryname = ? WHERE categoryID = ?");
            pstatement.setString(1, category.getCategoryname());
            pstatement.setInt(2, category.getCategoryID());

            int res = pstatement.executeUpdate();
            
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    // CRUD Payment Method
    
    public static ResultSet getPaymentMethod() {
    
        ResultSet result = null;
        
        try {
            String query = "SELECT * FROM PaymentMethod";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static boolean addPaymentMethod(PaymentMethod paymentmethod) {

        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO PaymentMethod (paymentmethodname) VALUES (?)");
            pstatement.setString(1, paymentmethod.getPaymentmethodname());

            return pstatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean deletePaymentMethod(PaymentMethod paymentmethod) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM PaymentMethod WHERE paymentmethodID = ?");
            pstatement.setInt(1, paymentmethod.getPaymentmethodID());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean updatePaymentMethod(PaymentMethod paymentmethod) {

        try {
            pstatement = getDBConnection().prepareStatement("UPDATE PaymentMethod SET paymentmethodname = ? WHERE paymentmethodID = ?");
            pstatement.setString(1, paymentmethod.getPaymentmethodname());
            pstatement.setInt(2, paymentmethod.getPaymentmethodID());

            int res = pstatement.executeUpdate();
            
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    // CRUD Restaurant
    
    public static ResultSet getRestaurant() {
    
        ResultSet result = null;
        
        try {
            String query =  "SELECT restaurantID, rname, rdistrict, rcity, categoryname FROM Restaurant INNER JOIN Category ON Restaurant.categoryID = Category.categoryid";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public static boolean addRestaurant(Restaurant restaurant) {
        try {

            String queryCategory = "SELECT categoryID FROM Category WHERE categoryname = ?";
            PreparedStatement ps = getDBConnection().prepareStatement(queryCategory);
            ps.setString(1, restaurant.getCategoryname());
            ResultSet rs = ps.executeQuery();
            int categoryID;
            if(rs.next()){
                categoryID = rs.getInt("categoryID");
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Category name does not exist");
                alert.showAndWait();
                return false;
            }
            
            String insertQuery = "INSERT INTO Restaurant (rname, rdistrict, rcity, categoryID) VALUES (?, ?, ?, ?)";
            pstatement = getDBConnection().prepareStatement(insertQuery);
            pstatement.setString(1, restaurant.getRname());
            pstatement.setString(2, restaurant.getRdistrict());
            pstatement.setString(3, restaurant.getRcity());
            pstatement.setInt(4, categoryID);
            
            return pstatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteRestaurant(Restaurant restaurant) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM Restaurant WHERE restaurantID = ?");
            pstatement.setInt(1, restaurant.getRestaurantID());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean updateRestaurant(Restaurant restaurant) {
        try {

            String queryCategory = "SELECT categoryID FROM Category WHERE categoryname = ?";
            PreparedStatement ps = getDBConnection().prepareStatement(queryCategory);
            ps.setString(1, restaurant.getCategoryname());
            ResultSet rs = ps.executeQuery();
            int categoryID;
            if(rs.next()){
                categoryID = rs.getInt("categoryID");
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Category name does not exist");
                alert.showAndWait();
                return false;
            }
            
            String updateQuery = "UPDATE Restaurant SET rname = ?, rdistrict = ?, rcity = ?, categoryID = ? WHERE restaurantID = ?";
            pstatement = getDBConnection().prepareStatement(updateQuery);
            pstatement.setString(1, restaurant.getRname());
            pstatement.setString(2, restaurant.getRdistrict());
            pstatement.setString(3, restaurant.getRcity());
            pstatement.setInt(4, categoryID);
            pstatement.setInt(5, restaurant.getRestaurantID());
            
            int res = pstatement.executeUpdate();
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // CRUD Product
    
    public static ResultSet getProduct() {
    
        ResultSet result = null;
        
        try {
            String query =  "SELECT productID, pname, rname, pprice FROM Product INNER JOIN Restaurant ON Product.restaurantID = Restaurant.restaurantid";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public static boolean addProduct(Product product) {
        try {

            String queryRestaurant = "SELECT restaurantID FROM Restaurant WHERE rname = ?";
            PreparedStatement ps = getDBConnection().prepareStatement(queryRestaurant);
            ps.setString(1, product.getRname());
            ResultSet rs = ps.executeQuery();
            int restaurantID;
            if(rs.next()){
                restaurantID = rs.getInt("restaurantID");
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Restaurant does not exist");
                alert.showAndWait();
                return false;
            }
            
            String insertQuery = "INSERT INTO Product (pname, restaurantID, pprice) VALUES (?, ?, ?)";
            pstatement = getDBConnection().prepareStatement(insertQuery);
            pstatement.setString(1, product.getPname());
            pstatement.setInt(2, restaurantID);
            pstatement.setString(3, product.getPprice());
            
            return pstatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteProduct(Product product) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM Product WHERE productID = ?");
            pstatement.setInt(1, product.getProductID());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean updateProduct(Product product) {
        try {

            String queryRestaurant = "SELECT restaurantID FROM Restaurant WHERE rname = ?";
            PreparedStatement ps = getDBConnection().prepareStatement(queryRestaurant);
            ps.setString(1, product.getRname());
            ResultSet rs = ps.executeQuery();
            int restaurantID;
            if(rs.next()){
                restaurantID = rs.getInt("restaurantID");
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Restaurant does not exist");
                alert.showAndWait();
                return false;
            }
            
            String updateQuery = "UPDATE Product SET pname = ?, restaurantID = ?, pprice = ? WHERE productID = ?";
            pstatement = getDBConnection().prepareStatement(updateQuery);
            pstatement.setString(1, product.getPname());
            pstatement.setInt(2, restaurantID);
            pstatement.setString(3, product.getPprice());
            pstatement.setInt(4, product.getProductID());
            
            int res = pstatement.executeUpdate();
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Validate Customer Login

    public static boolean validatecustomerLogin(String email, String contactnumber){

        getInstance();
        String query = "SELECT * FROM Customer WHERE email = '" + email + "' AND contactnumber = '" + contactnumber + "'";
        
        System.out.println(query);

        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                Session.customerID = result.getInt("customerID");
                Session.customerEmail = result.getString("email");
                Session.customerContactNumber = result.getString("contactnumber");
                Session.customerDistrict = result.getString("district");
                Session.customerCity = result.getString("city");
                Session.customerFirstname = result.getString("firstname");
                Session.customerLastname = result.getString("lastname");
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // CRUD Orders   

    public static int addOrder(Order order) {
        int generatedOrderID = -1;
        try {
            String insertQuery = "INSERT INTO Orders (customerID, restaurantID, productID, quantity, amount, paymentmethodID, modes, orderDate, orderTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = getDBConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getCustomerID());
            ps.setInt(2, order.getRestaurantID());
            ps.setInt(3, order.getProductID());
            ps.setInt(4, order.getQuantity());
            ps.setDouble(5, order.getAmount());
            ps.setInt(6, order.getPaymentmethodID());
            ps.setString(7, order.getModes());
            ps.setDate(8, java.sql.Date.valueOf(order.getOrderDate()));
            ps.setTime(9, java.sql.Time.valueOf(order.getOrderTime()));
            
            if(ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    generatedOrderID = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedOrderID;
    }


    public static int getRestaurantID(String restaurantName) {
        int id = -1;
        try {
            String query = "SELECT restaurantID FROM Restaurant WHERE rname = ?";
            PreparedStatement ps = getDBConnection().prepareStatement(query);
            ps.setString(1, restaurantName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt("restaurantID");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    
    public static int getPaymentMethodID(String paymentMethodName) {
        int id = -1;
        try {
            String query = "SELECT paymentmethodID FROM PaymentMethod WHERE paymentmethodname = ?";
            PreparedStatement ps = getDBConnection().prepareStatement(query);
            ps.setString(1, paymentMethodName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt("paymentmethodID");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static boolean updateOrders(Order order) {
        try {
            int paymentMethodID = getPaymentMethodID(order.getPaymentmethod());
            
            int customerID = order.getCustomerID();
            
            String updateOrderQuery = "UPDATE Orders SET paymentmethodID = ?, modes = ? WHERE orderID = ?";
            pstatement = getDBConnection().prepareStatement(updateOrderQuery);
            pstatement.setInt(1, paymentMethodID);
            pstatement.setString(2, order.getMode());  // 'modes' stores the delivery mode
            pstatement.setInt(3, order.getOrderID());
            int resOrders = pstatement.executeUpdate();
            
            String updateCustomerQuery = "UPDATE Customer SET district = ?, city = ? WHERE customerID = ?";
            PreparedStatement custStmt = getDBConnection().prepareStatement(updateCustomerQuery);
            custStmt.setString(1, order.getDistrict());
            custStmt.setString(2, order.getCity());
            custStmt.setInt(3, customerID);
            int resCustomer = custStmt.executeUpdate();
            
            return (resOrders > 0 && resCustomer > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteOrders(Order order) {
        try {
            String deleteQuery = "DELETE FROM Orders WHERE orderID = ?";
            pstatement = getDBConnection().prepareStatement(deleteQuery);
            pstatement.setInt(1, order.getOrderID());
            
            int res = pstatement.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet getOrderDescription() {
        ResultSet result = null;
        try {
            String query = "SELECT o.orderID, o.customerID, c.firstname, c.lastname, " +
                           "p.pname AS orderDescription, o.quantity, o.amount, " +
                           "pm.paymentmethodname AS paymentmethod, c.district, c.city, " +
                           "o.modes AS mode " +
                           "FROM Orders o " +
                           "JOIN Customer c ON o.customerID = c.customerID " +
                           "JOIN PaymentMethod pm ON o.paymentmethodID = pm.paymentmethodID " +
                           "JOIN Product p ON o.productID = p.productID";
            result = DatabaseHandler.getInstance().execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ResultSet getCustomerOrderDescription(int customerID) {
        ResultSet result = null;
        try {
            String query = "SELECT o.orderID, o.customerID, c.firstname, c.lastname, " +
                           "p.pname AS product, o.quantity, o.amount, " +
                           "pm.paymentmethodname AS paymentmethod, c.district, c.city, " +
                           "o.modes AS mode " +
                           "FROM Orders o " +
                           "JOIN Customer c ON o.customerID = c.customerID " +
                           "JOIN PaymentMethod pm ON o.paymentmethodID = pm.paymentmethodID " +
                           "JOIN Product p ON o.productID = p.productID " +
                           "WHERE o.customerID = ?";
            PreparedStatement ps = getDBConnection().prepareStatement(query);
            ps.setInt(1, customerID);
            result = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Email and Contact Number checker

    public static boolean isUniqueEmail(String email) {
        try {
            pstatement = getDBConnection().prepareStatement("SELECT email FROM Customer WHERE email = ?");
            pstatement.setString(1, email);
            ResultSet rs = pstatement.executeQuery();
            // If rs.next() returns true, the email already exists
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean isUniqueContactNumber(String contactnumber) {
        try {
            pstatement = getDBConnection().prepareStatement("SELECT contactnumber FROM Customer WHERE contactnumber = ?");
            pstatement.setString(1, contactnumber);
            ResultSet rs = pstatement.executeQuery();
            // If rs.next() returns true, the contact number already exists
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }    
    
}