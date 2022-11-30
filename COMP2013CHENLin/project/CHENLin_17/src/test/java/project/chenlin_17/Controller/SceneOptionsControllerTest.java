package project.chenlin_17.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import project.chenlin_17.GameApplication;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.tools.CharacterOptions;

import java.io.IOException;
import java.util.function.Predicate;

@ExtendWith(ApplicationExtension.class)
class SceneOptionsControllerTest extends ApplicationTest {
    Stage m_Stage;
    @Start
    public void start(Stage stage)throws IOException {
        DirectorApp.getInstance().setM_Stage(stage);
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 650);
        stage.setScene(scene);
        Parent root = FXMLLoader.load(GameApplication.class.getResource("Fxml/SceneOptions.fxml"));
        stage.getScene().setRoot(root);
        m_Stage = stage;
        stage.show();
    }
    @Test
    @DisplayName("Test initialize settings")
    void initialize(){
        ComboBox modeChoice = lookup("#m_ComboBox").queryComboBox();
        RadioButton easy = lookup("#m_Easy").query();
        Assertions.assertEquals(modeChoice.getValue().toString(),"General","Default scene choice level shoule be general");
        Assertions.assertTrue(easy.isSelected(), "Default difficulty level shoule be easy");
    }

    @Test
    @DisplayName("Back to the index page")
    void PreviousSetting() {
        moveTo("#m_Back");
        clickOn("#m_Back");
        sleep(1000);
        FxAssert.verifyThat("#m_BackgroundView", (Predicate<AnchorPane>) Node::isVisible);
    }

    @Test
    @DisplayName("Choose General Mode")
    void GeneralMode() {
        selectOrder(0);
    }
    @Test
    @DisplayName("Choose City Mode")
    void CityMode() {
        selectOrder(1);
    }

    @Test
    @DisplayName("Choose Space Mode")
    void SpaceMode() {
        selectOrder(2);
    }

    @Test
    @DisplayName("Choose Soccer Mode")
    void SoccerMode() {
        selectOrder(3);
    }


    private void selectOrder(int number){
        ComboBox modeChoice = lookup("#m_ComboBox").queryComboBox();
        ObservableList<String> choice = modeChoice.getItems();

        moveTo("#m_ComboBox");
        clickOn("#m_ComboBox");
        sleep(1000);
        clickOn(choice.get(number));
        checkSelection(modeChoice.getValue().toString());
    }

    private void checkSelection(String options) {
        moveTo("#m_Next");
        clickOn("#m_Next");
        sleep(1000);
        FxAssert.verifyThat("#m_CharacterBackground",(AnchorPane pane)->{
            return pane.isVisible();
        });

        verifyButton(options);
        verifyImage(options);
    }

    private void verifyImage(String options) {
            HBox personBox = lookup("#m_PersonBox").query();
            ImageView imageView = (ImageView) personBox.getChildren().get(0);
            String exact = imageView.getImage().getUrl();
            String expect = switch (options) {
                case "General" -> CharacterOptions.GeneralSet1.getM_Person().getUrl();
                case "Space" -> CharacterOptions.SpaceSet1.getM_Person().getUrl();
                case "City" -> CharacterOptions.CitySet1.getM_Person().getUrl();
                default -> CharacterOptions.SoccerSet1.getM_Person().getUrl();
            };
        Assertions.assertEquals(exact,expect);

    }

    private void verifyButton(String options){
        FxAssert.verifyThat("#m_BallButton",(RadioButton button)->{
            if(options.equals("General")) return button.isSelected();
            else return button.isDisabled();
        });
    }

}