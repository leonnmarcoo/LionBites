import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Product {

    private final SimpleIntegerProperty productID;
    private final SimpleStringProperty pname;
    private final SimpleStringProperty rname;
    private final SimpleStringProperty pprice;

    public Product(int productID, String pname, String rname, String pprice) {

        this.productID = new SimpleIntegerProperty(productID);
        this.pname = new SimpleStringProperty(pname);
        this.rname = new SimpleStringProperty(rname);
        this.pprice = new SimpleStringProperty(pprice);
    }

    public Product(int productID, String pname, int pprice) {
        this.productID = new SimpleIntegerProperty(productID);
        this.pname = new SimpleStringProperty(pname);
        this.rname = new SimpleStringProperty("");
        this.pprice = new SimpleStringProperty(String.valueOf(pprice));
    }
    

    public int getProductID() {
        return productID.get();
    }

    public String getPname() {
        return pname.get();
    }

    public String getRname() {
        return rname.get();
    }

    public String getPprice() {
        return pprice.get();
    }

    @Override
    public String toString() {
        return getPname() + " - Php " + getPprice();
    }

    
}
