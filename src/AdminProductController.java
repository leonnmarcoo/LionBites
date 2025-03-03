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

public class AdminProductController implements Initializable {

    ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productidColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> restaurantColumn;

    @FXML
    private TableColumn<Product, Integer> priceColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField restaurantTextField;

    @FXML
    private TextField priceTextField;

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
        displayProduct();
    }

    private void initializeCol() {

        productidColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("pname"));
        restaurantColumn.setCellValueFactory(new PropertyValueFactory<>("rname"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pprice"));

    }

    private void displayProduct() {

        productList.clear();

        ResultSet result = DatabaseHandler.getProduct();

        try {
            while (result.next()) {
                int productID = result.getInt("productID");
                String pname = result.getString("pname");
                String rname = result.getString("rname");
                String pprice = result.getString("pprice");

                Product newProduct = new Product(productID, pname, rname, pprice);

                productList.add(newProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        productTable.setItems(productList);
    }

    @FXML
    private void createProduct(ActionEvent event) throws IOException {

        String pname = nameTextField.getText().trim();
        String rname = restaurantTextField.getText().trim();
        String pprice = priceTextField.getText().trim();

        if (pname.isEmpty() || rname.isEmpty() || pprice.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        if (!pprice.matches("\\d+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Price must only contain integer numbers.");
            alert.showAndWait();
            return;
        }

        Product product = new Product(0, pname, rname, pprice);

        if (DatabaseHandler.addProduct(product)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Product added successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot create new product");
            alert.showAndWait();
        }

        displayProduct();
    }

    @FXML
    private void deleteProduct(ActionEvent event) {

        Product product = productTable.getSelectionModel().getSelectedItem();

        int productID = product.getProductID();

        if (DatabaseHandler.deleteProduct(product)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Product deleted successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot delete product");
            alert.showAndWait();
        }

        displayProduct();
    }

    @FXML
    private void updateProduct(ActionEvent event) {

        String pname = nameTextField.getText().trim();
        String rname = restaurantTextField.getText().trim();
        String pprice = priceTextField.getText().trim();

        pname = pname.trim();
        rname = rname.trim();
        pprice = pprice.trim();

        if (pname.isEmpty() || rname.isEmpty() || pprice.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        if (!pprice.matches("\\d+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Price must only contain integer numbers.");
            alert.showAndWait();
            return;
        }

        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            return;
        }

        Product product = new Product(selectedProduct.getProductID(), pname, rname, pprice);

        if (DatabaseHandler.updateProduct(product)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Product updated successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot update product");
            alert.showAndWait();
        }

        displayProduct();
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
