import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
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

public class AdminPaymentMethodController implements Initializable {

    ObservableList<PaymentMethod> paymentmethodList = FXCollections.observableArrayList();

    @FXML
    private TableView<PaymentMethod> paymentmethodTable;

    @FXML
    private TableColumn<PaymentMethod, Integer> paymentmethodidColumn;

    @FXML
    private TableColumn<PaymentMethod, String> paymentmethodnameColumn;

    @FXML
    private TextField paymentmethodnameTextField;

    @FXML 
    private Button createButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private Button customerButton;

    @FXML
    private Button restaurantButton;

    @FXML
    private Button productButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button categoryButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        displayPaymentMethod();
    }

    private void initializeCol() {

        paymentmethodidColumn.setCellValueFactory(new PropertyValueFactory<>("paymentmethodID"));
        paymentmethodnameColumn.setCellValueFactory(new PropertyValueFactory<>("paymentmethodname"));
    }

    private void displayPaymentMethod() {

        paymentmethodList.clear();

        ResultSet result = DatabaseHandler.getPaymentMethod();

        try {
            while (result.next()) {
                int paymentmethodID = result.getInt("paymentmethodID");
                String paymentmethodname = result.getString("paymentmethodname");

                PaymentMethod newPaymentMethod = new PaymentMethod(paymentmethodID, paymentmethodname);

                paymentmethodList.add(newPaymentMethod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        paymentmethodTable.setItems(paymentmethodList);
    }

    @FXML
    private void createPaymentMethod(ActionEvent event) throws IOException {

        String paymentmethodname = paymentmethodnameTextField.getText().trim();

        if (paymentmethodname.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        PaymentMethod paymentmethod = new PaymentMethod(0, paymentmethodname);

        if (DatabaseHandler.addPaymentMethod(paymentmethod)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Payment Method created successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot create new payment method");
            alert.showAndWait();
        }

        displayPaymentMethod();
    }

    @FXML
    private void deletePaymentMethod(ActionEvent event) {

        PaymentMethod paymentmethod = paymentmethodTable.getSelectionModel().getSelectedItem();

        int paymentmethodID = paymentmethod.getPaymentmethodID();

        if (DatabaseHandler.deletePaymentMethod(paymentmethod)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Payment Method deleted successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot delete payment method");
            alert.showAndWait();
        }

        displayPaymentMethod();
    }
    
    @FXML
    private void updatePaymentMethod(ActionEvent event) {

        String paymentmethodname = paymentmethodnameTextField.getText().trim();

        paymentmethodname = paymentmethodname.trim();

        if (paymentmethodname.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        PaymentMethod selectedPaymentMethod = paymentmethodTable.getSelectionModel().getSelectedItem();

        if (selectedPaymentMethod == null) {
            return;
        }

        PaymentMethod paymentmethod = new PaymentMethod (selectedPaymentMethod.getPaymentmethodID(), paymentmethodname);

        if (DatabaseHandler.updatePaymentMethod(paymentmethod)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Payment Method updated successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot update payment method");
            alert.showAndWait();
        }

        displayPaymentMethod();

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
    private void customerButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCustomer.fxml"));

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
}
