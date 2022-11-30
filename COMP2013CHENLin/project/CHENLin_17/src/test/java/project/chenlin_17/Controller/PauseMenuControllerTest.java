package project.chenlin_17.Controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import project.chenlin_17.GameApplication;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.Views.World;

import java.io.IOException;
import java.lang.reflect.Field;

@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class PauseMenuControllerTest extends ApplicationTest {
    World m_World;

    @Start
    public void start(Stage stage) throws IOException {
        DirectorApp.getInstance().setM_Stage(stage);
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 650);

        stage.setScene(scene);
        Parent root = FXMLLoader.load(GameApplication.class.getResource("Fxml/Index.fxml"));
        stage.getScene().setRoot(root);
        stage.show();
    }
    private void initialize(){
        Button button = lookup("START GAME").queryButton();
        moveTo(button);
        clickOn(button);
        sleep(1000);
        m_World = DirectorApp.getInstance().getM_WorldController().getM_World();
    }
    @Test
    @DisplayName("Test pause status")
    @Order(1)
    void testPauseStatus(){
        initialize();
        press(KeyCode.SPACE);
        press(KeyCode.ESCAPE);
        Point2D ballPoint = m_World.getPosition(m_World.getM_Ball());
        Point2D ballPointAfter=m_World.getPosition(m_World.getM_Ball());
        Assertions.assertEquals(ballPoint,ballPointAfter,"Ball is expected not to move");
    }

    @Test
    @DisplayName("Test pause status for a still ball")
    @Order(2)
    void testPauseStillStatus(){
        initialize();
        press(KeyCode.SPACE);
        press(KeyCode.SPACE);
        press(KeyCode.ESCAPE);
        Point2D ballPoint = m_World.getPosition(m_World.getM_Ball());
        Point2D ballPointAfter=m_World.getPosition(m_World.getM_Ball());
        Assertions.assertEquals(ballPoint,ballPointAfter,"Ball is expected not to move");
    }


    @Test
    @DisplayName("Test continue button clicked")
    @Order(3)
    void continueButtonOnClicked() {
        initialize();
        press(KeyCode.ESCAPE);
        Button continueButton = lookup("Continue").queryButton();
        clickOn(continueButton);
        sleep(1000);
        testRemoved();

    }

    @Test
    @DisplayName("Test back to menu button clicked")
    @Order(4)
    void menuClicked() {
        initialize();
        press(KeyCode.ESCAPE);
        Button backButton = lookup("Start Menu").queryButton();
        clickOn(backButton);

        FxAssert.verifyThat("#m_BackgroundView",(AnchorPane pane)->{
            return  pane.isVisible();
        });

    }

    @Test
    @DisplayName("Test replay button clicked")
    @Order(5)
    void replayClicked(){

        initialize();
        Field ballCount = null;
        try {
            ballCount = World.class.getDeclaredField("m_BallCount");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        ballCount.setAccessible(true);
        try {
            ballCount.setInt(m_World,1);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        press(KeyCode.ESCAPE);
        Button replayButton = lookup("Replay").queryButton();
        clickOn(replayButton);
        sleep(1000);
        Assertions.assertEquals(3,m_World.getBallCount(),"The ball count is expected to be 3");

        testRemoved();
    }

    private void testRemoved(){
        try{
            lookup("#m_ShowBox").query();
        }catch (Exception e){
            Assertions.assertNotNull(e,"The pause pane is expected to be removed");
            return;
        }
        Assertions.fail();
    }
}