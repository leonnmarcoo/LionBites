import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Templates;

public class AdminHomeController implements Initializable {

    ObservableList<Admin> adminList = FXCollections.observableArrayList();

    @FXML
    private TableView<Admin> adminTable;

    @FXML
    private Label homeLabel;

    @FXML
    private TableColumn<Admin, String> usernameColumn;

    @FXML
    private TableColumn<Admin, String> passwordColumn;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button createButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button customerButton;

    @FXML
    private Hyperlink linkHyperlink;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        displayAdmin();
    }

    public void displayName(String username) {
        homeLabel.setText(username + "!");
    }

    private void initializeCol() {

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

    }

    private void displayAdmin() {

        adminList.clear();

        ResultSet result = DatabaseHandler.getAdmins();


        try {
            while (result.next()) {
                String username = result.getString("Username");
                String password = result.getString("Password");
                Admin newAdmin = new Admin(username, password);
                adminList.add(newAdmin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adminTable.setItems(adminList);

    }

    @FXML
    public void linkHyperlink(ActionEvent event) throws URISyntaxException, IOException{
        Desktop.getDesktop().browse(new URI("https://food.grab.com/ph/en/"));
    }

    @FXML
    private void createAdmin(ActionEvent event) throws IOException {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        username = username.trim();
        password = password.trim(); 

        if (username.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Empty username");
            alert.showAndWait();
        }

        if (password.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Empty password");
            alert.showAndWait();
        }

        Admin admin = new Admin(username, password);

        if (DatabaseHandler.addAdmin(admin)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Admin created successfully");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot create new admin");
            alert.showAndWait();
        }

        displayAdmin();
        
    }

    @FXML
    private void deleteAdmin (ActionEvent event) {

        Admin admin = adminTable.getSelectionModel().getSelectedItem();

        String username = admin.getUsername();

        if (DatabaseHandler.deleteAdmin(admin)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Admin deleted successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot delete admin");
            alert.showAndWait();
        }

        displayAdmin();
    }

    @FXML
    private void updateAdmin (ActionEvent event) {
        
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        username = username.trim();
        password = password.trim(); 

        if (username.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Empty username");
            alert.showAndWait();
        }

        if (password.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Empty password");
            alert.showAndWait();
        }

        Admin admin = new Admin(username, password);

        if (DatabaseHandler.updateAdmin(admin)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Admin updated successfully");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot update admin");
            alert.showAndWait();
        }

        displayAdmin();
        
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