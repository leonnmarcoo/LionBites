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

public class CustomerSignupController implements Initializable {

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
    private Button signupButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void signupCustomer(ActionEvent event) throws IOException {
        
        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String contactnumber = contactnumberTextField.getText();
        String email = emailTextField.getText();
        String district = districtTextField.getText();
        String city = cityTextField.getText();

        if (firstname.isEmpty() || lastname.isEmpty() || contactnumber.isEmpty() || email.isEmpty() || district.isEmpty() || city.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
        }

        if (!firstname.matches("^[A-Za-z]+(?: [A-Za-z]+)*$") || !lastname.matches("^[A-Za-z]+(?: [A-Za-z]+)*$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("First name and Last name should contain only letters");
            alert.showAndWait();
            return;
        }

        if (!email.endsWith("@email.com")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Email must end with '@email.com'");
            alert.showAndWait();
            return;
        }

        if (!contactnumber.matches("\\d{11}")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Contact number must contain exactly 11 digits and should not contain letters");
            alert.showAndWait();
            return;
        }

        if (!district.matches("^[A-Za-z]+(?: [A-Za-z]+)*$") || !city.matches("^[A-Za-z]+(?: [A-Za-z]+)*$")) {
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
            alert.setContentText("Sign up successful!");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerLogin.fxml"));

            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sign up unsuccessful");
            alert.showAndWait();
        }

    }
    
}
