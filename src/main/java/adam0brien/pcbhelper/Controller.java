package adam0brien.pcbhelper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    ImageView view;
    @FXML
    ImageView processedView;
    Stage stage;
    @FXML
    Label fileName;
    @FXML
    Label fileSize;
    @FXML
    Label fileName1;
    @FXML
    Label fileSize1;

    public void open(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image file");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            file.toURI();

            Image image = new Image(file.toURI().toString(), view.getFitWidth(), view.getFitHeight(), false, true);

            imagePicked = image;



            view.setImage(image);
            processedView.setImage(image);

            fileName.setText(file.getName());

            fileSize.setText(file.length() + "KB");

            fileName1.setText(file.getName());

            fileSize1.setText(file.length() + "KB");
        }

    }

    /**
     * Black and white image conversion
     *
     * @param event
     */
    public void setBaW(ActionEvent event) {
        BaW(processedView);

    }

    public void BaW(ImageView imageView) throws IndexOutOfBoundsException {

        try {
            //get image width and height
            int width = (int) imageView.getImage().getWidth();
            int height = (int) imageView.getImage().getHeight();
            WritableImage ii = new WritableImage(width, height);

            //convert to grayscale
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int p = imageView.getImage().getPixelReader().getArgb(x, y);

                    int a = (p >> 24) & 255;
                    int r = (p >> 16) & 255;
                    int g = (p >> 8) & 255;
                    int b =  p & 255;

                    //calculate average
                    int avg = (r + g + b) / 3;

                    //replace RGB value with avg
                    p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                    //img.setRGB(x, y, p);
                    ii.getPixelWriter().setArgb(x, y, p);
                }
            }
            imageView.setImage(ii);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }


    /**
     * Sliders for Image Processing
     */


    @FXML
    Slider hueSlider;

    @FXML
    Slider brightnessSlider;

    @FXML
    Slider saturationSlider;


    ColorAdjust colorAdjust = new ColorAdjust();

    public void hueChanger(double value) {
        try {

            colorAdjust.setHue(value);

            processedView.setEffect(colorAdjust);


        } catch (Exception e) {
            System.out.println("Error:" + e);
            System.out.println(hueSlider.getValue());
        }

    }

    public void hueSlider() {
        hueChanger(hueSlider.getValue());
    }


    public void brightnessChanger(double value) {

        colorAdjust.setBrightness(value);

        processedView.setEffect(colorAdjust);
    }

    public void brightnessSlider() {
        brightnessChanger(brightnessSlider.getValue());
    }


    public void saturationChanger(double value) {

        colorAdjust.setSaturation(value);
        processedView.setEffect(colorAdjust);


    }

    public void saturationSlider() {
        saturationChanger(saturationSlider.getValue());

    }

    public void reset(ActionEvent actionEvent) {

        Image original = view.getImage();
        processedView.setImage(original);
        colorAdjust.setHue(0);
        colorAdjust.setSaturation(0);
        colorAdjust.setBrightness(0);

        hueSlider.setValue(0);
        saturationSlider.setValue(0);
        brightnessSlider.setValue(0);
    }


    /**
     * Component Finder
     * <p>
     * - This will find components of a specific color and change them to a more obvious color
     * - The Background will then me removed be left with just the coloured components based on shape/size/original color
     */

    private Color selectedColor;



    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }
    public Color getSelectedColor() {
        return selectedColor;
    }
    Image imagePicked;
    WritableImage baW;


    public void changeComponentColor(ActionEvent event) {
        processedView.setImage(imagePicked);
        brightnessSlider.getValue();
        saturationSlider.getValue();

        double finalHue = getSelectedColor().getHue();                                          //hue
        double finalSat = getSelectedColor().getSaturation();          //saturation
        double finalBri = getSelectedColor().getBrightness();       //brightness


        PixelReader pixelReader = processedView.getImage().getPixelReader();
        WritableImage baW = new WritableImage((int) processedView.getImage().getWidth(), (int) processedView.getImage().getHeight());
        PixelWriter writer = baW.getPixelWriter();

        Color black = new Color(0, 0, 0, 1);
        Color white = new Color(1, 1, 1, 1);

        //convert
        for (int i = 0; i < imagePicked.getWidth(); i++) {
            for (int j = 0; j < imagePicked.getHeight(); j++) {

                Color oldColor = pixelReader.getColor(i, j);


                //for green PCB's
                if (oldColor.getGreen() <= oldColor.getRed() + oldColor.getBlue()) {
                    writer.setColor(i, j, black);
                }
//                //for blue PCB's
//                else if(oldColor.getBlue() <= oldColor.getGreen() + oldColor.getRed()) {
//
//                    writer.setColor(i,j,black);
//                }
//                //for red PBC's
//                else if(oldColor.getRed() <= oldColor.getBlue()+ oldColor.getGreen()){
//                    writer.setColor(i,j,black);
//
//                }
            }


                    //find average saturation hue and brightness for integrated circuits resistors LEDs and capacitors

                    //use those values in a series of if statements to change the color of each component so they are easily recognisable


                }

                processedView.setImage(baW);
                setBawImage(baW);


    }


    private void setBawImage(WritableImage baW) {
        this.baW = baW;
    }



    public void selectColour(ActionEvent event) throws IOException {
        //gets image colour

        processedView.setOnMousePressed(e -> {
            Color selectedColor = processedView.getImage().getPixelReader().getColor((int) e.getX(), (int) e.getY());
            System.out.println("\nHue: " + selectedColor.getHue());
            System.out.println("Saturation: " + selectedColor.getSaturation());
            System.out.println("Brightness: " + selectedColor.getBrightness());
            System.out.println("Red Value: " + selectedColor.getRed() * 255);
            System.out.println("Green Value: " + selectedColor.getGreen() * 255);
            System.out.println("Blue Value: " + selectedColor.getBlue() * 255);

            setSelectedColor(selectedColor);
        });
    }
}
