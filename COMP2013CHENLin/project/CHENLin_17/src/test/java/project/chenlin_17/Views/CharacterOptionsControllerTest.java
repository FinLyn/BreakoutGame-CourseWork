package project.chenlin_17.Views;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import project.chenlin_17.Controller.CharacterOptionsController;
import project.chenlin_17.GameApplication;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.tools.SceneFactory;
import project.chenlin_17.tools.SceneOptions;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@ExtendWith(ApplicationExtension.class)
class CharacterOptionsControllerTest extends ApplicationTest {
    Stage m_Stage;
    CharacterOptionsController m_Controller;
    SceneFactory m_SceneFactory;

    @Start
    public void start(Stage stage) throws IOException {
        DirectorApp.getInstance().setM_Stage(stage);
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 650);
        stage.setScene(scene);
        FXMLLoader fxmlLoader= new FXMLLoader(GameApplication.class.getResource("Fxml/CharacterOptions.fxml"));
        Parent root = fxmlLoader.load();
        m_Controller=fxmlLoader.getController();
        stage.getScene().setRoot(root);
        DirectorApp.getInstance().setM_UserName("CHENLIN");
        m_Stage = stage;
        stage.show();
    }


    private void initializer(String type){
        m_SceneFactory= new SceneFactory(type);
        m_Controller.initialize(m_SceneFactory.getBuilder(),m_SceneFactory.getCharacterOptions());
    }

    @Test
    @DisplayName("Test go back to scene Options")
    void testBack(){
        moveTo("#m_Back");
        clickOn(("#m_Back"));
        FxAssert.verifyThat("#m_SceneTitle",(Text text)->{
            return text.getText().equals("SELECT MODE");
        });
    }

    @Test
    @DisplayName("Test for General Initializer")
    void generalInitializerTest(){
        initializer("General");
        sleep(1000);
        testComboBox();
        testButton("#m_BallButton",false);
        testButton("#m_PaddleButton",false);
    }

    @Test
    @DisplayName("Test for Advanced Initializer")
    void advancedInitializerTest(){
        initializer("Space");
        sleep(1000);
        testComboBox();
        testButton("#m_BallButton",true);
        testButton("#m_PaddleButton",true);
    }

    private void testComboBox(){
        FxAssert.verifyThat("#m_ComboBox",(ComboBox comboBox)->{
            boolean res = comboBox.getValue().equals("Set1");
            res = res && !(comboBox.isDisabled());
            return res;
        });
    }

    private void testButton(String nodeName, boolean disable){
            RadioButton button = lookup(nodeName).query();
            Assertions.assertEquals(button.isSelected(),!disable);
            Assertions.assertEquals(button.isDisabled(),disable);
    }


    @Test
    @DisplayName("Test for changeable general initializer set")
    void completeGeneral() {
        initializer("General");
        Set<RadioButton> white= lookup("White").queryAllAs(RadioButton.class);
        List<RadioButton> whiteList= white.stream().toList();
        clickOn(whiteList.get(0));
        sleep(1000);
        clickOn(whiteList.get(2));

        moveTo("#m_Next");
        clickOn("#m_Next");
        World world = DirectorApp.getInstance().getM_World();
        Assertions.assertEquals(Color.WHITE, world.getM_Ball().getInnerColor());
        Assertions.assertEquals(Color.WHITE,world.getM_Player().getM_Inner());
    }

    @Test
    @DisplayName("Test for changeable advanced initializer set")
    void completeAdvanced() {
        initializer("Space");
        ComboBox comboBox = lookup("#m_ComboBox").query();
        moveTo(comboBox);
        clickOn(comboBox);
        ObservableList<String> values = comboBox.getItems();
        sleep(1000);
        clickOn(values.get(1));
        imageCorresponds(1);
    }

    @Test
    @DisplayName("Test for Default general initializer set")
    void completeDefaultGeneral() throws IOException {

        initializer("General");
        moveTo("#m_Next");
        clickOn("#m_Next");
        World world = DirectorApp.getInstance().getM_World();
        Assertions.assertEquals(Color.BLACK, world.getM_Ball().getInnerColor());
        Assertions.assertEquals(Color.BLACK,world.getM_Player().getM_Inner());

    }

    @Test
    @DisplayName("Test for Default advanced initializer set")
    void completeDefaultAdvanced() throws NoSuchFieldException {
        initializer("Space");
        ComboBox comboBox = lookup("#m_ComboBox").query();
        moveTo(comboBox);
        clickOn(comboBox);
        sleep(1000);
        imageCorresponds(0);
    }

    private void imageCorresponds(int options){
        moveTo("#m_Next");
        clickOn("#m_Next");
        World world = DirectorApp.getInstance().getM_World();
        ImagePaddleView exactPaddle = (ImagePaddleView) (world.getM_Player());
        String expectedPaddleUrl=m_SceneFactory.getCharacterOptions().get(options).getM_Player().getUrl();
        String actualPaddleUrl= exactPaddle.getM_ImagePaddleFace().getImage().getUrl();
        Assertions.assertEquals(expectedPaddleUrl,actualPaddleUrl,"Paddle image doesn't match");

        ImageBallView exactBall =(ImageBallView) (world.getM_Ball());
        String expectedBallUrl = SceneOptions.Space.getBallImage().getUrl();
        String actualBallUrl = exactBall.getM_ImageBall().getImage().getUrl();
        Assertions.assertEquals(expectedBallUrl,actualBallUrl,"Ball image doesn't match");
    }


}