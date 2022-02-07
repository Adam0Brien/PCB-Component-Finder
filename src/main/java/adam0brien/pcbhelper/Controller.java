package adam0brien.pcbhelper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Controller {
    @FXML
    ImageView view;
    public ImageView imageChosen;


    Stage stage;


    public void open(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image file");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            file.toURI();

            Image image = new Image(file.toURI().toString(), view.getFitWidth(), view.getFitHeight(), false, true);
            view.setImage(image);
        }

    }



    public void setGreyScale (ActionEvent event){
        greyScale(view);

    }


    public void greyScale (ImageView imageView) throws IndexOutOfBoundsException {


        try {
            //get image width and height
            int width = (int) imageView.getImage().getWidth();
            int height = (int) imageView.getImage().getHeight();
            byte[] buffer = new byte[width * height * 4];
            WritableImage ii = new WritableImage(width, height);

            imageView.getImage().getPixelReader().getPixels(0,
                    0,
                    width,
                    height,
                    PixelFormat.getByteBgraInstance(),
                    buffer,
                    0,
                    width * 4);


            //convert to grayscale
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int p = imageView.getImage().getPixelReader().getArgb(x, y);

                    int a = (p >> 24) & 255;
                    int r = (p >> 16) & 255;
                    int g = (p >> 8) & 255;
                    int b = p & 255;


                    //calculate average
                    int avg = (r + g + b) / 3;

                    //replace RGB value with avg
                    p = (a << 24) | (r << 16) | (g << 16) | b;   //make green pixels disappear


                    ii.getPixelWriter().setArgb(x, y, p);

                }
            }
            
            imageView.setImage(ii);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }



    public void findResistors(){





    }








}
