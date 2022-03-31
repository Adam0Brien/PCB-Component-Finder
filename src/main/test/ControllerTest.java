import adam0brien.pcbhelper.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ControllerTest {

    private Controller controller;
    ImageView view;
    Image image;
    @Before
    public void setUp() throws Exception {

        controller = new Controller();
        //controller.view = new ImageView();


        image = new Image("resources/pcb1.jpg", controller.view.getFitWidth(), controller.view.getFitHeight(), false, true);
        controller.view.setImage(image);


    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }


//    @org.junit.jupiter.api.Test
//    void open() {
//
//        controller.view.setImage(image);
//        assertEquals(image,controller.view.getImage());
//
//    }

}
