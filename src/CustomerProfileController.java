import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import javax.swing.table.TableColumn;
import javax.xml.transform.Templates;

public class CustomerProfileController implements Initializable {

    // ObservableList<Customer> customerList = FXCollections.observableArrayList();

    ObservableList<Order> ordersList = FXCollections.observableArrayList();

    @FXML
    private TableView<Order> myordersTable;

    @FXML
    private TableColumn<Order, Integer> orderidColumn;

    @FXML
    private TableColumn<Order, Integer> quantityColumn;

    @FXML
    private TableColumn<Order, String> productColumn;

    @FXML
    private TableColumn<Order, Double> amountColumn;

    @FXML
    private Button backButton;

    @FXML
    private Button editprofileButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label customeridLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phonenumberLabel;

    @FXML
    private Label districtLabel;

    @FXML
    private Label cityLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // @Override
    // public void initialize(URL url, ResourceBundle rb) {
    //     nameLabel.setText(Session.customerFirstname + " " + Session.customerLastname);
    //     customeridLabel.setText(String.valueOf("Customer ID: " + Session.customerID));
    //     emailLabel.setText(Session.customerEmail);
    //     phonenumberLabel.setText(Session.customerContactNumber);
    //     districtLabel.setText(Session.customerDistrict);
    //     cityLabel.setText(Session.customerCity);
    // }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        displayOrders();
        nameLabel.setText(Session.customerFirstname + " " + Session.customerLastname);
        customeridLabel.setText(String.valueOf("Customer ID: " + Session.customerID));
        emailLabel.setText(Session.customerEmail);
        phonenumberLabel.setText(Session.customerContactNumber);
        districtLabel.setText(Session.customerDistrict);
        cityLabel.setText(Session.customerCity);
    }

    private void initializeCol() {
        orderidColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("orderDescription"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void displayOrders() {

        ordersList.clear();

        ResultSet result = DatabaseHandler.getCustomerOrderDescription(Session.customerID);

        try {
            while (result.next()) {
                int orderID = result.getInt("orderID");
                int quantity = result.getInt("quantity");
                String product = result.getString("product");
                double amount = result.getDouble("amount");

                Order newOrder = new Order(orderID, Session.customerID, 
                               result.getString("firstname"), 
                               result.getString("lastname"), 
                               product, quantity, amount, 
                               result.getString("paymentmethod"), 
                               result.getString("district"), 
                               result.getString("city"), 
                               result.getString("mode"));

                ordersList.add(newOrder);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        myordersTable.setItems(ordersList);
    }

    @FXML
    private void editprofileButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerEditProfile.fxml"));
            root = loader.load();

            CustomerEditProfileController editProfileController = loader.getController();

            Customer currentCustomer = new Customer(
                Session.customerID, 
                Session.customerFirstname, 
                Session.customerLastname, 
                Session.customerContactNumber, 
                Session.customerEmail, 
                Session.customerDistrict, 
                Session.customerCity
            );

            editProfileController.setLoggedInCustomer(currentCustomer);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void backButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHome.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
