import javafx.beans.property.SimpleStringProperty;

public class Admin {

    private final SimpleStringProperty username;
    private final SimpleStringProperty password;

    public Admin(String uname, String pword) {
        
        this.username = new SimpleStringProperty(uname);
        this.password = new SimpleStringProperty(pword);

    }
    
    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }
    
}