import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Templates;

public class CustomerOrderSummaryController implements Initializable {

    @FXML
    private Label addressLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label customeridLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label deliveryfeeLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Label modeLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label orderidLabel;

    @FXML
    private Label paymentmethodLabel;

    @FXML
    private Label productLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label restaurantLabel;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label totalLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         nameLabel.setText(Session.customerFirstname + " " + Session.customerLastname);
         customeridLabel.setText(String.valueOf(Session.customerID));
         
         categoryLabel.setText(Session.selectedCategory);
         restaurantLabel.setText(Session.selectedRestaurant);
         productLabel.setText(Session.selectedProductName);
         quantityLabel.setText(String.valueOf(Session.selectedQuantity));
         paymentmethodLabel.setText(Session.selectedPaymentMethod);
         modeLabel.setText(Session.selectedMode);
         
         int subtotal = Session.selectedProductPrice * Session.selectedQuantity;
         subtotalLabel.setText("Php " + subtotal);

         int deliveryFee = 0;
         if (Session.selectedMode.equalsIgnoreCase("Delivery")) {
            deliveryFee = 39;
         }
         deliveryfeeLabel.setText("Php " + deliveryFee);
         
         int total = subtotal + deliveryFee;
         totalLabel.setText("Php " + total + ".00");
         
         LocalDate currentDate = LocalDate.now();
         LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
         dateLabel.setText(currentDate.toString());
         timeLabel.setText(currentTime.toString());
         
         addressLabel.setText(Session.customerDistrict + ", " + Session.customerCity);
         orderidLabel.setText(String.valueOf(Session.orderID));
         amountLabel.setText("Php " + total + ".00");
    }
    
    @FXML
    private void homeButtonHandler(ActionEvent event) {
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