import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Category {

    private final SimpleIntegerProperty categoryID;
    private final SimpleStringProperty categoryname;

    public Category(int categoryID, String categoryname) {

        this.categoryID = new SimpleIntegerProperty(categoryID);
        this.categoryname = new SimpleStringProperty(categoryname);

    }

    public int getCategoryID() {
        return categoryID.get();
    }

    public String getCategoryname() {
        return categoryname.get();
    }
    
}
