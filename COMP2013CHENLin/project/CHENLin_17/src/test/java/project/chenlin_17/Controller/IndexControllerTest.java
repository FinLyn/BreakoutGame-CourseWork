package project.chenlin_17.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
import project.chenlin_17.tools.SoundEffect;

import java.io.IOException;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ApplicationExtension.class)
class IndexControllerTest extends ApplicationTest {
    Stage m_Stage;

    @Start
    public void start(Stage stage) throws IOException {
        DirectorApp.getInstance().setM_Stage(stage);
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 650);
        stage.setScene(scene);
        Parent root = FXMLLoader.load(GameApplication.class.getResource("Fxml/Index.fxml"));
        stage.getScene().setRoot(root);
        DirectorApp.getInstance().setM_UserName("CHENLIN");
        m_Stage = stage;
        stage.show();
    }



    @Test
    @DisplayName("Test start game board")
    @Order(7)
    void StartGameTest() {
        simulateClick("START GAME");
        sleep(1000);
        Assertions.assertNotNull(m_Stage.getScene().getRoot());

    }

    private void simulateClick(String name) {
        Button button = lookup(name).queryButton();
        moveTo(button);
        clickOn(button);
    }

    @Test
    @DisplayName("Test show instruction")
    @Order(1)
    void ShowInstructionsTest() {
        simulateClick("HOW TO PLAY");
        sleep(1000);
        FxAssert.verifyThat("#m_MainPaneText",(Label label)->{
            return label.getText().equals("INFO");
        });
    }

    @Test
    @DisplayName("Test show option")
    @Order(2)
    void ShowOptionsTest() {
       simulateClick("OPTIONS");
        sleep(1000);
        FxAssert.verifyThat("#m_SceneTitle",(Text text)->{
            return text.getText().equals("SELECT MODE");
        });
    }

    @Test
    @DisplayName("Test exit game")
    @Order(3)
    void ExitGameTest() {
       simulateClick("EXIT");
    }

    @Test
    @DisplayName("Test show slider")
    @Order(4)
    void OnClickedVolumeTest() {
        moveTo("#m_Music");
        clickOn("#m_Music");
        sleep(1000);
        FxAssert.verifyThat("#m_BGMVolumeSlider",(Slider slider)->{
            return slider.isVisible();
        });
    }


    @Test
    @DisplayName("Test volume changer")
    @Order(5)
    void OnChangeVolumeTest() {
        moveTo("#m_Music");
        clickOn("#m_Music");
        sleep(1000);
        Slider slider= lookup("#m_BGMVolumeSlider").query();
        int[] testValues= {10,0,40,100};
        for (int testValue : testValues) {
            slider.setValue(testValue);
            SoundEffect soundEffect = DirectorApp.getInstance().getM_BackgroundMusic();
            Assertions.assertEquals(testValue, (int) (soundEffect.getM_Volume() * 100));
        }
    }

    @Test
    @DisplayName("Test show rank page")
    @Order(6)
    void ShowRankTest() {
        simulateClick("RANK");
        sleep(1000);
        FxAssert.verifyThat("#m_UserScore",(Text text)->{
            boolean res;
            res =text.getText().equals("30");
            return res;
        });
    }
}