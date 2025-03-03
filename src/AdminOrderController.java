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
import javax.xml.transform.Templates;

public class AdminOrderController implements Initializable {

    ObservableList<Order> ordersList = FXCollections.observableArrayList();

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Integer> orderidColumn;

    @FXML
    private TableColumn<Order, Integer> customeridColumn;

    @FXML
    private TableColumn<Order, String> firstnameColumn;

    @FXML
    private TableColumn<Order, String> lastnameColumn;

    @FXML
    private TableColumn<Order, String> orderColumn;

    @FXML
    private TableColumn<Order, Integer> quantityColumn;

    @FXML
    private TableColumn<Order, Double> amountColumn;

    @FXML
    private TableColumn<Order, String> paymentmethodColumn;

    @FXML
    private TableColumn<Order, String> districtColumn;

    @FXML
    private TableColumn<Order, String> cityColumn;

    @FXML
    private TableColumn<Order, String> modeColumn;

    @FXML
    private TextField paymentmethodTextField;

    @FXML
    private TextField deliverypickupTextField;

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
    private Button categoryButton;

    @FXML
    private Button paymentmethodButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        displayOrders();
    }

    private void initializeCol() {

        orderidColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        customeridColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        orderColumn.setCellValueFactory(new PropertyValueFactory<>("orderDescription"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        paymentmethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentmethod"));
        districtColumn.setCellValueFactory(new PropertyValueFactory<>("district"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        modeColumn.setCellValueFactory(new PropertyValueFactory<>("mode"));
    }

    private void displayOrders() {

        ordersList.clear();

        ResultSet result = DatabaseHandler.getOrderDescription();

        try {
            while (result.next()) {
                int orderID = result.getInt("orderID");
                int customerID = result.getInt("customerID");
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                String order = result.getString("orderDescription");
                int quantity = result.getInt("quantity");
                double amount = result.getDouble("amount");
                String paymentmethod = result.getString("paymentmethod");
                String district = result.getString("district");
                String city = result.getString("city");
                String mode = result.getString("mode");

                Order newOrder = new Order (orderID, customerID, firstname, lastname, order, quantity, amount, paymentmethod, district, city, mode);

                ordersList.add(newOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ordersTable.setItems(ordersList);

    }

    @FXML 
    private void deleteOrders(ActionEvent event) {

        Order order = ordersTable.getSelectionModel().getSelectedItem();

        int orderID = order.getOrderID();

        if (DatabaseHandler.deleteOrders(order)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Order deleted successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot delete order");
            alert.showAndWait();
        }

        displayOrders();
    }

    @FXML
    private void updateOrders(ActionEvent event) {

        if (paymentmethodTextField.getText().trim().isEmpty() || deliverypickupTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }
    
        String paymentmethod = paymentmethodTextField.getText().trim();
        String deliverypickup = deliverypickupTextField.getText().trim();

        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            return;
        }

        String district = selectedOrder.getDistrict();
        String city = selectedOrder.getCity();
        int quantity = selectedOrder.getQuantity();

        Order order = new Order(selectedOrder.getOrderID(), 
                        selectedOrder.getCustomerID(), 
                        quantity, 
                        paymentmethod, 
                        district, 
                        city, 
                        deliverypickup);

        if (DatabaseHandler.updateOrders(order)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Order updated successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot update order");
            alert.showAndWait();
        }

        displayOrders();
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
