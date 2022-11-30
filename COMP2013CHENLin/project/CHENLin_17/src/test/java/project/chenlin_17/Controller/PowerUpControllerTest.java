package project.chenlin_17.Controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import project.chenlin_17.GameApplication;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.Views.World;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PowerUpControllerTest extends ApplicationTest {
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
    @DisplayName("Test Reset Ball count")
    @Order(2)
    void ResetBall() {
        initialize();
        press(KeyCode.SPACE);
        changeBallCount();
        press(KeyCode.SHIFT,KeyCode.ALT,KeyCode.F1);
        changeButton("#m_Reset");
        verifyPosition();
        Assertions.assertEquals(3,m_World.getBallCount());

    }

    @Test
    @DisplayName("Test skip level")
    @Order(3)
    void SkipLevel() {
        initialize();
        press(KeyCode.SHIFT,KeyCode.ALT,KeyCode.F1);
        changeButton("#m_Skip");
        verifyPosition();
        verifyLevel(0,1);
    }

    @Test
    @DisplayName("Test exit")
    @Order(1)
    void Exit() {
        initialize();
        press(KeyCode.SHIFT,KeyCode.ALT,KeyCode.F1);
        ImageView exitButton = lookup("#m_Exit").query();
        clickOn(exitButton);
        try{
            lookup("#m_Exit").query();
        }catch (Exception e){
            Assertions.assertNotNull(e,"The power up menu is expected to be removed");
            return;
        }
        Assertions.fail();

    }

    @Test
    @DisplayName("Test setBall X speed")
    @Order(4)
    void TestSetXspeed(){
        initialize();
        press(KeyCode.SHIFT,KeyCode.ALT,KeyCode.F1);
        changeSlider("#m_XSpeed",3);
        verifyPosition();
        verifySlider("#m_XSpeed",3);

    }

    private void changeSlider(String queryName, int value) {
        Slider slider = lookup(queryName).query();
        slider.setValue(value);
    }

    @Test
    @DisplayName("Test setBall Y speed")
    @Order(5)
    void TestSetYspeed(){
        initialize();
        press(KeyCode.SHIFT,KeyCode.ALT,KeyCode.F1);
        changeSlider("#m_YSpeed",3);
        verifyPosition();
        verifySlider("#m_YSpeed",3);
    }
    @Test
    @DisplayName("Test all settings")
    @Order(6)
    void TestAllSettings(){
        initialize();
        changeBallCount();
        press(KeyCode.SHIFT,KeyCode.ALT,KeyCode.F1);
        changeSlider("#m_YSpeed",3);
        changeSlider("#m_XSpeed",2);
        changeButton("#m_Reset");
        changeButton("#m_Skip");
        verifyPosition();
        verifySlider("#m_YSpeed",3);
        verifySlider("#m_XSpeed",2);
        verifyLevel(1,2);
        Assertions.assertEquals(3,m_World.getBallCount());


    }

    private void changeBallCount(){
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
    }
    private void changeButton(String queryName){
        Button button = lookup(queryName).queryButton();
        clickOn(button);
    }
    private void verifyPosition(){
        Point2D ballPosition =m_World.getPosition(m_World.getM_Ball());
        ImageView exitButton = lookup("#m_Exit").query();
        clickOn(exitButton);
        Point2D ballPositionAfter = m_World.getPosition(m_World.getM_Ball());
        Assertions.assertEquals(ballPosition,ballPositionAfter);
    }

    private void verifySlider(String queryName, int value){
        if(queryName.equals("#m_XSpeed"))
            Assertions.assertEquals(value,m_World.getBallSpeedX());
        if(queryName.equals("#m_YSpeed"))
            Assertions.assertEquals(value,m_World.getBallSpeedY());
    }

    private void verifyLevel(int origin,int next){
        ArrayList<Group> wallGroups = m_World.getM_WallGroups();
        boolean containLevel1= m_World.getM_Group().getChildren().contains(wallGroups.get(origin));
        boolean containLevel2 = m_World.getM_Group().getChildren().contains(wallGroups.get(next));
        Assertions.assertFalse(containLevel1,"The initial level has been removed");
        Assertions.assertTrue(containLevel2,"The next has been included");
    }
    @AfterAll
    public void reset(){

    }
}