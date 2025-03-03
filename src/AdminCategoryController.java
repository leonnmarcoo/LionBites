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

public class AdminCategoryController implements Initializable {

    ObservableList<Category> categoryList = FXCollections.observableArrayList();

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, Integer> categoryidColumn;

    @FXML
    private TableColumn<Category, String> categorynameColumn;

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
    private Button restaurantButton;

    @FXML
    private Button productButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button paymentmethodButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialieCol();
        displayCategory();
    }

    private void initialieCol() {

        categoryidColumn.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        categorynameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryname"));

    }

    private void displayCategory() {

        categoryList.clear();

        ResultSet result = DatabaseHandler.getCategory();

        try {
            while (result.next()) {
                int categoryID = result.getInt("categoryID");
                String categoryname = result.getString("categoryname");

                Category newCategory = new Category(categoryID, categoryname);

                categoryList.add(newCategory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        categoryTable.setItems(categoryList);
    }

    @FXML
    private void createCategory(ActionEvent event) throws IOException {

        String categoryname = categorynameTextField.getText().trim();

        if (categoryname.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        Category category = new Category(0, categoryname);

        if (DatabaseHandler.addCategory(category)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Category created successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot create new category");
            alert.showAndWait();
        }

        displayCategory();
    }

    @FXML
    private void deleteCategory(ActionEvent event) {

        Category category = categoryTable.getSelectionModel().getSelectedItem();

        int categoryID = category.getCategoryID();

        if (DatabaseHandler.deleteCategory(category)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Category deleted successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot delete category");
            alert.showAndWait();
        }

        displayCategory();
    }

    @FXML
    private void updateCategory(ActionEvent event) {

        String categoryname = categorynameTextField.getText().trim();

        categoryname = categoryname.trim();

        if (categoryname.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            return;
        }

        Category category = new Category (selectedCategory.getCategoryID(), categoryname);

        if (DatabaseHandler.updateCategory(category)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Category updated successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot update category");
            alert.showAndWait();
        }

        displayCategory();
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
