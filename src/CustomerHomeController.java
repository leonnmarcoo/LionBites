import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Templates;

public class CustomerHomeController implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private Button receiptButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<String> restaurantComboBox;

    @FXML
    private ComboBox<Product> productComboBox;

    @FXML
    private TextField quantityTextField;

    @FXML
    private ComboBox<String> paymentmethodComboBox;

    @FXML
    private ComboBox<String> modeComboBox;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCategories();
        loadPaymentMethods();
        loadModes();

        categoryComboBox.setOnAction(e -> {
            String selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
            loadRestaurants(selectedCategory);
        });

        restaurantComboBox.setOnAction(e -> {
            String selectedRestaurant = restaurantComboBox.getSelectionModel().getSelectedItem();
            loadProducts(selectedRestaurant);
        });
    }

    private void loadCategories() {

        ResultSet rs = DatabaseHandler.getCategory();
        
        try {
            while (rs.next()) {
                String categoryName = rs.getString("categoryname");
                categoryComboBox.getItems().add(categoryName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadRestaurants(String categoryName) {

        restaurantComboBox.getItems().clear();

        String query = "SELECT rname FROM Restaurant INNER JOIN Category ON Restaurant.categoryID = Category.categoryID "
                      + "WHERE categoryname = '" + categoryName + "'";
        ResultSet rs = DatabaseHandler.getInstance().execQuery(query);

        try {
            while (rs.next()) {
                String restaurantName = rs.getString("rname");
                restaurantComboBox.getItems().add(restaurantName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadProducts(String restaurantName) {

        productComboBox.getItems().clear();
        
        String query = "SELECT productID, pname, pprice FROM Product INNER JOIN Restaurant ON Product.restaurantID = Restaurant.restaurantID "
                      + "WHERE rname = '" + restaurantName + "'";
        ResultSet rs = DatabaseHandler.getInstance().execQuery(query);

        try {
            while (rs.next()) {
                int productID = rs.getInt("productID");
                String pname = rs.getString("pname");
                int pprice = rs.getInt("pprice");
                Product product = new Product(productID, pname, pprice);
                productComboBox.getItems().add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void loadPaymentMethods() {
        
        ResultSet rs = DatabaseHandler.getPaymentMethod();

        try {
            while (rs.next()) {
                String paymentMethodName = rs.getString("paymentmethodname");
                paymentmethodComboBox.getItems().add(paymentMethodName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadModes() {

        modeComboBox.getItems().clear();
        modeComboBox.getItems().addAll("Delivery", "Pickup");    
    }

    @FXML
    private void receiptButtonHandler(ActionEvent event) {

        Product selectedProduct = null;

        if (categoryComboBox.getSelectionModel().isEmpty() ||
            restaurantComboBox.getSelectionModel().isEmpty() ||
            productComboBox.getSelectionModel().isEmpty() ||
            quantityTextField.getText().isEmpty() ||
            paymentmethodComboBox.getSelectionModel().isEmpty() ||
            modeComboBox.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please select in all the fields");
            alert.showAndWait();
            return;
        } else {
            Session.selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
            Session.selectedRestaurant = restaurantComboBox.getSelectionModel().getSelectedItem();
            selectedProduct = productComboBox.getSelectionModel().getSelectedItem();
            Session.selectedProductName = selectedProduct.toString();
            Session.selectedProductPrice = Integer.parseInt(selectedProduct.getPprice());
            try {
                Session.selectedQuantity = Integer.parseInt(quantityTextField.getText());
                if (Session.selectedQuantity <= 0) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Quantity must be greater than 0.");
                    alert.showAndWait();
                    return;
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Quantity must be a number.");
                alert.showAndWait();
                return;
            }
            Session.selectedPaymentMethod = paymentmethodComboBox.getSelectionModel().getSelectedItem();
            Session.selectedMode = modeComboBox.getSelectionModel().getSelectedItem();
        }

        int customerID = Session.customerID; 
        int restaurantID = DatabaseHandler.getRestaurantID(Session.selectedRestaurant);
        int productID = selectedProduct.getProductID();
        int quantity = Session.selectedQuantity;
        
        int subtotal = Session.selectedProductPrice * Session.selectedQuantity;
        int deliveryFee = 0;
        if (Session.selectedMode.equalsIgnoreCase("Delivery")) {
            deliveryFee = 39;
        }
        int totalAmount = subtotal + deliveryFee;
        
        int paymentmethodID = DatabaseHandler.getPaymentMethodID(Session.selectedPaymentMethod);
        String mode = Session.selectedMode;
        
        LocalDate orderDate = LocalDate.now();
        LocalTime orderTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        
        Order order = new Order(0, customerID, restaurantID, productID, quantity, totalAmount, paymentmethodID, mode, orderDate.toString(), orderTime.toString());

        int orderID = DatabaseHandler.addOrder(order);
        if (orderID != -1) {
            Session.orderID = orderID;
            System.out.println("Order added successfully with OrderID: " + orderID);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Failed to add order to database.");
            alert.showAndWait();
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerOrderSummary.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void logoutButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogoutConfirmation.fxml"));
            Parent root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.setTitle("Confirm Logout");
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void profileButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerProfile.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}