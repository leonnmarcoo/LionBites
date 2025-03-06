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

public class AdminRestaurantController implements Initializable {

    ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();

    @FXML
    private TableView<Restaurant> restaurantTable;

    @FXML
    private TableColumn<Restaurant, Integer> restaurantidColumn;

    @FXML
    private TableColumn<Restaurant, String> nameColumn;

    @FXML
    private TableColumn<Restaurant, String> districtColumn;

    @FXML
    private TableColumn<Restaurant, String> cityColumn;

    @FXML
    private TableColumn<Restaurant, String> categorynameColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField districtTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField categorynameTextField;

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
        displayRestaurant();
    }

    private void initializeCol() {

        restaurantidColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("rname"));
        districtColumn.setCellValueFactory(new PropertyValueFactory<>("rdistrict"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("rcity"));
        categorynameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryname"));

    }

    private void displayRestaurant() {

        restaurantList.clear();

        ResultSet result = DatabaseHandler.getRestaurant();

        try {
            while (result.next()) {
                int restaurantID = result.getInt("restaurantID");
                String rname = result.getString("rname");
                String rdistrict = result.getString("rdistrict");
                String rcity = result.getString("rcity");
                String categoryname = result.getString("categoryname");

                Restaurant newRestaurant = new Restaurant (restaurantID, rname, rdistrict, rcity, categoryname);

                restaurantList.add(newRestaurant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        restaurantTable.setItems(restaurantList);

    }
    
    @FXML
    private void createRestaurant(ActionEvent event) throws IOException {

        String rname = nameTextField.getText().trim();
        String rdistrict = districtTextField.getText().trim();
        String rcity = cityTextField.getText().trim();
        String categoryname = categorynameTextField.getText().trim();

        if (rname.isEmpty() || rdistrict.isEmpty() || rcity.isEmpty() || categoryname.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        Restaurant restaurant = new Restaurant(0, rname, rdistrict, rcity, categoryname);

        if (DatabaseHandler.addRestaurant(restaurant)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Restaurant added successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot create new restaurant");
            alert.showAndWait();
        }

        displayRestaurant();
    }

    @FXML
    private void deleteRestaurant(ActionEvent event) {

        Restaurant restaurant = restaurantTable.getSelectionModel().getSelectedItem();

        int restaurantID = restaurant.getRestaurantID();

        if (DatabaseHandler.deleteRestaurant(restaurant)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Restaurant deleted successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot delete restaurant");
            alert.showAndWait();
        }

        displayRestaurant();
    }

    @FXML
    private void updateRestaurant(ActionEvent event) {

        String rname = nameTextField.getText().trim();
        String rdistrict = districtTextField.getText().trim();
        String rcity = cityTextField.getText().trim();
        String categoryname = categorynameTextField.getText().trim();

        if (rname.isEmpty() || rdistrict.isEmpty() || rcity.isEmpty() || categoryname.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        Restaurant selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();

        if (selectedRestaurant == null) {
            return;
        }

        Restaurant restaurant = new Restaurant(selectedRestaurant.getRestaurantID(), rname, rdistrict, rcity, categoryname);

        if (DatabaseHandler.updateRestaurant(restaurant)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Restaurant updated successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot update restaurant");
            alert.showAndWait();
        }

        displayRestaurant();
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
