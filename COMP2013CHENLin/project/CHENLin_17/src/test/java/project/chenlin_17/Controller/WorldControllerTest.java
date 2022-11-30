package project.chenlin_17.Controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import project.chenlin_17.GameApplication;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.Views.GameStatusView;
import project.chenlin_17.Views.World;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorldControllerTest extends ApplicationTest {
    World m_World;
//    GameStatus m_GameStatus;
    GameStatusView m_GameChecker;
    Stage m_Stage;
    Parent m_Root;

    @Start
    public void start(Stage stage) throws IOException {

        DirectorApp.getInstance().setM_Stage(stage);
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 650);
        stage.setScene(scene);
        Parent root = FXMLLoader.load(GameApplication.class.getResource("Fxml/Index.fxml"));
        stage.getScene().setRoot(root);
        m_Stage =stage;
        stage.show();
    }

    private void initialize(){
        Button button = lookup("START GAME").queryButton();
        moveTo(button);
        clickOn(button);
        m_World = DirectorApp.getInstance().getM_WorldController().getM_World();
//        m_GameStatus= DirectorApp.getInstance().getM_WorldController().getM_GameStatusChecker();
        m_GameChecker = DirectorApp.getInstance().getM_WorldController().getM_GameChecker();
    }

    @Test
    @DisplayName("Test initialized game board")
    @Order(1)
    public void testInitializedGameBoard(){
        initialize();
        sleep(1000);
        Assertions.assertEquals(m_World.getBallCount(),3,"There should be three balls");
        Assertions.assertEquals(m_World.getM_Score(),0, "The score should be initialized to zero");
        Assertions.assertEquals(m_World.getBrickCount(),31,"There should be 31 bricks for the initialized brick group");
        Assertions.assertEquals(m_World.getPosition(m_World.getM_Ball()),m_World.getPosition(m_World.getM_Player()),"The point should be set to be the same");
    }

    @Test
    @DisplayName("Test ball movement")
    @Order(3)
    public void testBallMove(){
        initialize();
        sleep(1000);
        Point2D originalBallPosition = m_World.getPosition(m_World.getM_Ball());;
        press(KeyCode.SPACE);
        Point2D afterBallPosition = m_World.getPosition(m_World.getM_Ball());;
        Assertions.assertNotEquals(originalBallPosition,afterBallPosition,"Ball's position is expected to be changed");
    }

    @Test
    @DisplayName("Test paddle movement")
    @Order(4)
    public void testPaddleMove(){
        initialize();
        sleep(1000);
        double originalPaddle =m_World.getPosition(m_World.getM_Player()).getX();
        press(KeyCode.SPACE);
        release(KeyCode.SPACE);
        press(KeyCode.LEFT);
        release(KeyCode.LEFT);
        double afterLeft = m_World.getPosition(m_World.getM_Player()).getX();;
        Assertions.assertTrue(afterLeft <=originalPaddle,"Paddle have moved left");

        press(KeyCode.RIGHT);
        release(KeyCode.RIGHT);
        double afterRight =m_World.getPosition(m_World.getM_Player()).getX();
        Assertions.assertTrue(afterRight>=afterLeft,"Paddle have moved right");

    }

    @Test
    @DisplayName("Test show pause menu")
    @Order(5)
    public void testPauseMenu(){
        initialize();
        sleep(1000);
        press(KeyCode.ESCAPE);
        release(KeyCode.ESCAPE);
        FxAssert.verifyThat("#m_TextTitle",(Text text)->{
            return text.getText().equals("Paused");
        });
    }

    @Test
    @DisplayName("Test debug panel")
    @Order(6)
    public void testDebugPanel(){
        initialize();
        sleep(100);
        press(KeyCode.SHIFT,KeyCode.ALT,KeyCode.F1);
        release(KeyCode.F1);
        release(KeyCode.SHIFT);
        release(KeyCode.ALT);
        FxAssert.verifyThat("#m_TextTitle",(Text text)->{
            return text.getText().equals("Buff");
        });
    }

    @Test
    @DisplayName("Test next level")
    @Order(2)
    public void testNextLevel(){
        initialize();
        Point2D ballPos = m_World.getPosition(m_World.getM_Ball());;
        ArrayList<Group> wallGroups = m_World.getM_WallGroups();
        boolean containLevel1= m_World.getM_Group().getChildren().contains(wallGroups.get(0));
        boolean containLevel2 = m_World.getM_Group().getChildren().contains(wallGroups.get(1));
        Assertions.assertTrue(containLevel1,"The initial level0 has been set");
        Assertions.assertFalse(containLevel2,"The level2 has not been included");

        press(KeyCode.SPACE);
        release(KeyCode.SPACE);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                m_World.nextLevel();
                Point2D newBallPos= m_World.getPosition(m_World.getM_Ball());;
                Assertions.assertEquals(ballPos,newBallPos,"The ball position has been reset");
                boolean containLevel1= m_World.getM_Group().getChildren().contains(wallGroups.get(0));
                boolean containLevel2 = m_World.getM_Group().getChildren().contains(wallGroups.get(1));
                Assertions.assertFalse(containLevel1,"The initial level0 has been removed");
                Assertions.assertTrue(containLevel2,"The level2 has been included");
                sleep(1000);
            }
        });

    }

    @Test
    @DisplayName("Test game lose")
    @Order(7)
    public void testGameLose() throws NoSuchFieldException, IllegalAccessException {
        initialize();

        press(KeyCode.SPACE);
        release(KeyCode.SPACE);

        Field ballCount= World.class.getDeclaredField("m_BallCount");
        ballCount.setAccessible(true);
        ballCount.setInt(m_World,0);

        sleep(1000);

        FxAssert.verifyThat("#m_ScoreField",(Label text)->{
            return text.getText().equals("Yours");
        });

        FxAssert.verifyThat("#m_Heading",(ImageView imageView)->{
            String imageUrl= imageView.getImage().getUrl();
            return imageUrl.contains("Lose");
        });


    }

}