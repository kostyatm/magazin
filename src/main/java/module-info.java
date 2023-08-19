module com.magazin.magazin {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.magazin.magazin to javafx.fxml;
    exports com.magazin.magazin;
}