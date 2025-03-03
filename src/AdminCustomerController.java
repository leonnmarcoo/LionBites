import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Templates;

public class AdminCustomerController implements Initializable {

    ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> customeridColumn;

    @FXML
    private TableColumn<Customer, String> firstnameColumn;

    @FXML
    private TableColumn<Customer, String> lastnameColumn;

    @FXML
    private TableColumn<Customer, String> contactnumberColumn;

    @FXML
    private TableColumn<Customer, String> emailColumn;

    @FXML
    private TableColumn<Customer, String> districtColumn;

    @FXML
    private TableColumn<Customer, String> cityColumn;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField contactnumberTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField districtTextField;

    @FXML
    private TextField cityTextField;

    @FXML 
    private Button createButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private Button restaurantButton;

    @FXML
    private Button productButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button categoryButton;

    @FXML
    private Button paymentmethodButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        displayCustomer();
    }

    private void initializeCol() {

        customeridColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        contactnumberColumn.setCellValueFactory(new PropertyValueFactory<>("contactnumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        districtColumn.setCellValueFactory(new PropertyValueFactory<>("district"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

    }

    private void displayCustomer() {

        customerList.clear();

        ResultSet result = DatabaseHandler.getCustomer();

        try {
            while (result.next()) {
                int customerID = result.getInt("customerID");
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                String contactnumber = result.getString("contactnumber");
                String email = result.getString("email");
                String district = result.getString("district");
                String city = result.getString("city");

                Customer newCustomer = new Customer(customerID, firstname, lastname, contactnumber, email, district, city);

                customerList.add(newCustomer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        customerTable.setItems(customerList);
    }

    @FXML
    private void createCustomer(ActionEvent event) throws IOException {
        
        String firstname = firstnameTextField.getText().trim();
        String lastname = lastnameTextField.getText().trim();
        String contactnumber = contactnumberTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String district = districtTextField.getText().trim();
        String city = cityTextField.getText().trim();

        if (firstname.isEmpty() || lastname.isEmpty() || contactnumber.isEmpty() || email.isEmpty() || district.isEmpty() || city.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        if (!firstname.matches("[A-Za-z]+") || !lastname.matches("[A-Za-z]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("First name and Last name should contain only letters");
            alert.showAndWait();
            return;
        }

        if (!contactnumber.matches("\\d{11}")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Contact number must contain exactly 11 digits and should not contain letters");
            alert.showAndWait();
            return;
        }

        if (!email.endsWith("@email.com")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Email must end with '@email.com'");
            alert.showAndWait();
            return;
        }

        if (!district.matches("[A-Za-z]+") || !city.matches("[A-Za-z]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("District and City should contain only letters");
            alert.showAndWait();
            return;
        }

        if (!DatabaseHandler.isUniqueEmail(email)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Email is already in use!");
            alert.showAndWait();
            return;
        }
        
        if (!DatabaseHandler.isUniqueContactNumber(contactnumber)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Contact number is already in use!");
            alert.showAndWait();
            return;
        }

        Customer customer = new Customer(0, firstname, lastname, contactnumber, email, district, city);

        if (DatabaseHandler.addCustomer(customer)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Customer created successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot create new customer");
            alert.showAndWait();
        }

        displayCustomer();
    }
    
    @FXML
    private void deleteCustomer(ActionEvent event) {

        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        int customerID = customer.getCustomerID();

        if (DatabaseHandler.deleteCustomer(customer)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Customer deleted successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot delete customer");
            alert.showAndWait();
        }

        displayCustomer();
    }

    @FXML
    private void updateCustomer(ActionEvent event) {

        String firstname = firstnameTextField.getText().trim();
        String lastname = lastnameTextField.getText().trim();
        String contactnumber = contactnumberTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String district = districtTextField.getText().trim();
        String city = cityTextField.getText().trim();

        if (firstname.isEmpty() || lastname.isEmpty() || contactnumber.isEmpty() || email.isEmpty() || district.isEmpty() || city.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        if (!firstname.matches("[A-Za-z]+") || !lastname.matches("[A-Za-z]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("First name and Last name should contain only letters");
            alert.showAndWait();
            return;
        }

        if (!contactnumber.matches("\\d{11}")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Contact number must contain exactly 11 digits and should not contain letters");
            alert.showAndWait();
            return;
        }

        if (!email.endsWith("@email.com")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Email must end with '@email.com'");
            alert.showAndWait();
            return;
        }

        if (!district.matches("[A-Za-z]+") || !city.matches("[A-Za-z]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("District and City should contain only letters");
            alert.showAndWait();
            return;
        }

        if (!DatabaseHandler.isUniqueEmail(email)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Email is already in use!");
            alert.showAndWait();
            return;
        }
        
        if (!DatabaseHandler.isUniqueContactNumber(contactnumber)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Contact number is already in use!");
            alert.showAndWait();
            return;
        }

        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            return;
        }

        Customer customer = new Customer (selectedCustomer.getCustomerID(), firstname, lastname, contactnumber, email, district, city);

        if (DatabaseHandler.updateCustomer(customer)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Customer updated successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot update customer");
            alert.showAndWait();
        }

        displayCustomer();
    }

    @FXML
    private void backButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));

            root = loader.load();

            AdminHomeController controller = loader.getController();
            controller.displayName(AdminSession.getUsername());

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void restaurantButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRestaurant.fxml"));

            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void productButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminProduct.fxml"));

            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void orderButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminOrder.fxml"));

            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void categoryButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCategory.fxml"));

            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void paymentmethodButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPaymentMethod.fxml"));

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
