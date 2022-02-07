module adam0brien.pcbhelper {
    requires javafx.controls;
    requires javafx.fxml;


    opens adam0brien.pcbhelper to javafx.fxml;
    exports adam0brien.pcbhelper;
}