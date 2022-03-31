package adam0brien.pcbhelper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Driver extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("pcbHelper.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 925, 614);
        stage.setTitle("PCB Helper");
        stage.setScene(scene);
        stage.getIcons().add(new Image("https://www.clipartmax.com/png/middle/133-1336612_vector-clip-art-of-rsa-electronics-capacitor-symbol-simbolo-de-un-resistor.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }



    // push to github
}