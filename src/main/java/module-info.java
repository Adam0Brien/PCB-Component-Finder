module adam0brien.pcbhelper {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.hotspot.agent;


    opens adam0brien.pcbhelper to javafx.fxml;
    exports adam0brien.pcbhelper;
}