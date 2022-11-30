package project.chenlin_17.Views;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import project.chenlin_17.GameApplication;
import project.chenlin_17.Models.BallModel;

import java.io.IOException;
import java.lang.reflect.Field;

@ExtendWith(ApplicationExtension.class)
class ImageBallTest extends ApplicationTest {
    BallView m_ImageBall;
    Group m_Group;
    AnchorPane m_Root;
    @Start
    public void start(Stage stage) throws IOException {
        m_Root = new AnchorPane();
        Scene scene = new Scene(m_Root, 600, 650);
        stage.setScene(scene);
        stage.show();
    }
    private void initialize(){
        m_Group= new Group();
        BallModel model = new BallModel(new Point2D(20,20),10,10);
        m_ImageBall = new ImageBallView( model,new Image(GameApplication.class.getResource("Pictures/City/cityBall.png").toExternalForm()),m_Group);
    }

    @Test
    @DisplayName("Test reset position")
    void testPosition() {
        initialize();
        try {
            Field ballCenter = BallModel.class.getDeclaredField("m_Center");
            ballCenter.setAccessible(true);
            ballCenter.set(m_ImageBall.getM_BallModel(),new Point2D(30,30));
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        Assertions.assertEquals(m_ImageBall.getPosition(), new Point2D(30,30));
    }

    @Test
    @DisplayName("Test ballImage")
    void testImage(){
        initialize();
        String imageUrl = ((ImageBallView)m_ImageBall).getM_ImageBall().getImage().getUrl();
        Assertions.assertTrue(imageUrl.contains("city"));
    }



}