package adam0brien.pcbhelper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class Controller {



    HashSet<Integer> rootValues = new HashSet<>();
    @FXML
    public ImageView view;
    @FXML
    ImageView processedView;
    @FXML
    ImageView finalView;
    Stage stage;
    @FXML
    Label fileName;
    @FXML
    Label fileSize;
    @FXML
    Label fileName1;
    @FXML
    Label fileSize1;

    /**
     *
     * Getters / setters
     *
     */

    public Slider getHueSlider() {
        return hueSlider;
    }

    public void setHueSlider(Slider hueSlider) {
        this.hueSlider = hueSlider;
    }

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }

    public Slider getBrightnessSlider() {
        return brightnessSlider;
    }

    public void setBrightnessSlider(Slider brightnessSlider) {
        this.brightnessSlider = brightnessSlider;
    }

    public Slider getSaturationSlider() {
        return saturationSlider;
    }

    public void setSaturationSlider(Slider saturationSlider) {
        this.saturationSlider = saturationSlider;
    }


    public WritableImage getBaW() {
        return baW;
    }

    public void setBaW(WritableImage baW) {
        this.baW = baW;
    }

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
            finalView.setImage(image);


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


    private void setBawImage(WritableImage baW) {
        this.baW = baW;
    }

    public void selectColour(ActionEvent event) throws IOException {
        //gets image colour

        processedView.setImage(imagePicked);
        processedView.setOnMousePressed(e -> {
            Color selectedColor = processedView.getImage().getPixelReader().getColor((int) e.getX(), (int) e.getY());
            System.out.println("\nHue: " + selectedColor.getHue());
            System.out.println("Saturation: " + selectedColor.getSaturation());
            System.out.println("Brightness: " + selectedColor.getBrightness());
            System.out.println("Opacity" + selectedColor.getOpacity());
            System.out.println("Red Value: " + selectedColor.getRed() * 255);
            System.out.println("Green Value: " + selectedColor.getGreen() * 255);
            System.out.println("Blue Value: " + selectedColor.getBlue() * 255);


            setSelectedColor(selectedColor);
            drawSmallRectangles((int)e.getX()-5,(int)e.getY()-5,(int)e.getX()+5,(int)e.getY()+5);
        });
    }


    public void updateNewColor(ActionEvent event) {
        processedView.setImage(imagePicked);
        brightnessSlider.getValue();
        saturationSlider.getValue();
        //multiply slider value with selected colour b/h/s/c
        double selectedHue = getSelectedColor().getHue();                                          //hue
        double selectedSat = getSelectedColor().getSaturation();          //saturation
        double selectedBrightness = getSelectedColor().getBrightness();       //brightness

        PixelReader pixelReader = processedView.getImage().getPixelReader();
        Color black = new Color(0, 0, 0, 1);
        Color white = new Color(1, 1, 1, 1);
        WritableImage baW = new WritableImage((int) processedView.getImage().getWidth(), (int) processedView.getImage().getHeight());
        PixelWriter writer = baW.getPixelWriter();
        for (int i = 0; i < imagePicked.getWidth(); i++) {
            for (int j = 0; j < imagePicked.getHeight(); j++) {
                Color oldColor = pixelReader.getColor(i, j);
                // if hue/saturation/brightness out of range -> turn black
                // for black specific ICs (due to the fact black's hue is around between 255 degrees and 40 degrees
                if (selectedHue > 180 || selectedHue < 40) {
                    if (((oldColor.getHue() >= selectedHue) && (selectedHue < 40) && (oldColor.getHue() < 180)) || (oldColor.getHue() <= selectedHue - 20) && (selectedHue > 180) && (oldColor.getHue() > 40)) {
                        writer.setColor(i, j, black);
                    } else {
                        if (oldColor.getSaturation() <= selectedSat - .09 || oldColor.getSaturation() >= selectedSat + .09) {
                            writer.setColor(i, j, black);
                        } else {
                            if (oldColor.getBrightness() <= selectedBrightness - 0.5 || oldColor.getBrightness() >= selectedBrightness + 0.5) {
                                writer.setColor(i, j, black);
                            } else {
                                writer.setColor(i, j, white);

                            }
                        }
                    }
                }else if (((oldColor.getHue() >= selectedHue) && (selectedHue < 18) && (oldColor.getHue() < 35)) || (oldColor.getHue() <= selectedHue - 20) && (selectedHue > 35) && (oldColor.getHue() > 18)) {
                    writer.setColor(i, j, black);
                } else {
                    if (oldColor.getSaturation() <= selectedSat - .55 || oldColor.getSaturation() >= selectedSat + .55) {
                        writer.setColor(i, j, black);
                    } else {
                        if (oldColor.getBrightness() <= selectedBrightness - 0.80 || oldColor.getBrightness() >= selectedBrightness + 0.80) {
                            writer.setColor(i, j, black);
                        } else {
                            writer.setColor(i, j, white);
                        }
                    }
                }


            }

        }
        processedView.setImage(baW);
        setBawImage(baW);

    }


    int[] imageArray;

    /**
     *
     * Uses union to get each pixel to join with the upper left-most pixel
     *
     */

    public void process(ActionEvent event) {
        //create array the size of the width multiplied by the height
        imageArray = new int[(int) imagePicked.getHeight() * (int) imagePicked.getWidth()];  //height*width of picture
        Color white = new Color(1, 1, 1, 1);
        //go through pixel by pixel, if black { -1 },if white { row*width+column }
        PixelReader pixelReader = processedView.getImage().getPixelReader();
        for (int i = 0; i < processedView.getImage().getHeight(); i++) {
            for (int j = 0; j < processedView.getImage().getWidth(); j++) {
                Color getColor = pixelReader.getColor(j, i);
                //if white set position of array to coords
                if (getColor.equals(white)) {
                    //set white pixel position to the array.
                    imageArray[(i * (int) processedView.getImage().getWidth()) + j] = (i * (int) processedView.getImage().getWidth()) + j;
                } else
                    imageArray[(i * (int) processedView.getImage().getWidth()) + j] = -1; //sets black pixels to -1 in the array. could use -2,3,4....-100 it doesn't matter
            }
        }
        int right;
        int down;
        for (int data = 0; data < imageArray.length; data++) {

            if ((data + 1) < imageArray.length) {
                right = data + 1;
            } else{
                right = imageArray.length-1; //goes to the very end +1 then does -1 once you run out of pixels
            }

            //checks if down is possible
            if ((data + (int) processedView.getImage().getWidth()) < imageArray.length) {
                down = data + (int) processedView.getImage().getWidth();
            } else{
                down = imageArray.length-1;
            }


            //unions all data that is not -1
            if (imageArray[data] > -1) {
                if(down < imageArray.length && imageArray[down] > -1) { //
                    union(imageArray, data, down);
                }
                if(right < imageArray.length && imageArray[right] > -1 ) {
                    union(imageArray, data, right);
                }
            }
        }



        getRectPositions(imageArray);






    }


    int noiseReduction = 0;

    @FXML
    Slider noiseSlider;

    public Slider getNoiseSlider() {
        return noiseSlider;
    }

    public void setNoiseSlider(Slider noiseSlider) {
        this.noiseSlider = noiseSlider;
    }

    public void noiseChanger(double value) {

            noiseReduction = (int) noiseSlider.getValue();

    }


    public void noiseSlider() {
        noiseChanger(noiseSlider.getValue());
    }


    public void getRectPositions(int[] imageArray) {
        ((AnchorPane)finalView.getParent()).getChildren().removeIf(r->r instanceof Rectangle);
        ((AnchorPane)finalView.getParent()).getChildren().removeIf(t->t instanceof Text);


        //uses recursive find to get all the first instances of an element NOT being -1
        // in the image and adds them to the hashset roots
        for(int i = 0; i < imageArray.length; i++){
            if(imageArray[i] != -1){
                rootValues.add(find(imageArray,i));
            }
        }
        //hashset rootValues is now going to be referenced by rootValue
        for(int rootValue : rootValues) {
            // calculate component bounds for every component.
            int minX = (int) processedView.getImage().getWidth();
            int minY = (int) processedView.getImage().getHeight();
            int maxX = 0;
            int maxY = 0;

            for (int i = 0; i < imageArray.length; i++) {
                if (imageArray[i] != -1) {
                    int root = find(imageArray, i);

                    if (rootValue == root) {
                        int rootX = i % (int) processedView.getImage().getWidth();
                        int rootY = i / (int) processedView.getImage().getWidth();

                        if (rootX < minX) minX = rootX;
                        if (rootY < minY) minY = rootY;
                        if (rootX > maxX) maxX = rootX;
                        if (rootY > maxY) maxY = rootY;
                    }
                }
            }

            /**
             * Noise Reduction
             */
            // cleans up any loose pixels that may have accidentally formed a disjoint set by coincidence
            if ((((maxX - minX)*(maxY - minY)) > noiseReduction)) drawRectangles(minX, minY, maxX, maxY);

        }

    }




    // unions the disjoint sets into bigger ones
    public void union(int[] imageArray, int a, int b) {
        imageArray[find(imageArray,b)]=find(imageArray,a); //The root of b is made reference a
    }

    // finds the root of each of the pixels
    public int find(int[] imageArray, int data) {
        if(imageArray[data]==data) {
            return data;
        }
        else{
            return find(imageArray, imageArray[data]);
        }
    }
    //Recursive version of find  ^^^^^^^





    // adds rectangles around components



    LinkedList rectangles = new LinkedList();
    LinkedList rectangleSize = new LinkedList();
    public void drawRectangles(int minX, int minY, int maxX, int maxY) {
        Rectangle rect = new Rectangle(minX, minY, maxX-minX, maxY-minY);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(3);
        rect.setLayoutX(processedView.getLayoutX());
        rect.setLayoutY(processedView.getLayoutY());
        ((AnchorPane)finalView.getParent()).getChildren().add(rect);


        double rectangle = rect.getHeight() * rect.getWidth();


        //System.out.println("Size : " + rectangleSize);

        rectangles.add(rectangle);
        //Collections.sort(rectangles);
        for (int i = 0;i < rectangles.size(); i++) {

            Tooltip.install(rect, new Tooltip("Component Number: " + (i + 1) + "\nCluster Size : " + rectangles.get(i)));
        }

    }


    // adds rectangles around components  //used for pointer rectangle
    public void drawSmallRectangles(int minX, int minY, int maxX, int maxY) {
        Rectangle rect = new Rectangle(minX, minY, maxX-minX, maxY-minY);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(2);
        rect.setLayoutX(processedView.getLayoutX());
        rect.setLayoutY(processedView.getLayoutY());
        ((AnchorPane)processedView.getParent()).getChildren().add(rect);
    }

    public void printImageArray(ActionEvent event){
        System.out.println(Arrays.toString(imageArray));
    }

    public void estimateComponents() {

        System.out.println(Utilities.diffValues(imageArray) -1);  //https://stackoverflow.com/questions/32444193/count-different-values-in-array-in-java

    }



    public void sizeOfEachDisjoint(ActionEvent event){
        System.out.println(rootValues.size());
    }

}


