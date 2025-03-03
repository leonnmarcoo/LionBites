import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PaymentMethod {

    private final SimpleIntegerProperty paymentmethodID;
    private final SimpleStringProperty paymentmethodname;

    public PaymentMethod(int paymentmethodID, String paymentmethodname) {

        this.paymentmethodID = new SimpleIntegerProperty(paymentmethodID);
        this.paymentmethodname = new SimpleStringProperty(paymentmethodname);

    }

    public int getPaymentmethodID() {
        return paymentmethodID.get();
    }

    public String getPaymentmethodname() {
        return paymentmethodname.get();
    }
    
}
