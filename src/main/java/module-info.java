module com.magazin.magazin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
            
                            
    opens com.magazin.magazin to javafx.fxml;
    exports com.magazin.magazin;
}