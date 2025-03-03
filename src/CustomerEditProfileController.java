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

public class CustomerEditProfileController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField contactnumberTextField;

    @FXML
    private TextField districtTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private Button updateButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private Customer loggedInCustomer;

    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
        firstnameTextField.setText(customer.getFirstname());
        lastnameTextField.setText(customer.getLastname());
        contactnumberTextField.setText(customer.getContactnumber());
        emailTextField.setText(customer.getEmail());
        districtTextField.setText(customer.getDistrict());
        cityTextField.setText(customer.getCity());
    }


    @FXML
    private void updateCustomer(ActionEvent event) {

        if (loggedInCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No customer loaded. Please try again.");
            alert.showAndWait();
            return;
        }

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
            alert.setContentText("First name and Last name should contain only letters.");
            alert.showAndWait();
            return;
        }

        if (!contactnumber.matches("\\d{11}")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Contact number must contain exactly 11 digits and should not contain letters.");
            alert.showAndWait();
            return;
        }

        if (!email.endsWith("@email.com")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Email must end with '@email.com'.");
            alert.showAndWait();
            return;
        }

        if (!district.matches("[A-Za-z]+") || !city.matches("[A-Za-z]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("District and City should contain only letters.");
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

        Customer updatedCustomer = new Customer(
            loggedInCustomer.getCustomerID(),
            firstname, lastname, contactnumber, email, district, city
        );

        if (DatabaseHandler.updateCustomer(updatedCustomer)) {

            Session.customerFirstname = firstname;
            Session.customerLastname = lastname;
            Session.customerContactNumber = contactnumber;
            Session.customerEmail = email;
            Session.customerDistrict = district;
            Session.customerCity = city;

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Customer updated successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot update customer");
            alert.showAndWait();
        }
    }

    @FXML
    private void backButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerProfile.fxml"));
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