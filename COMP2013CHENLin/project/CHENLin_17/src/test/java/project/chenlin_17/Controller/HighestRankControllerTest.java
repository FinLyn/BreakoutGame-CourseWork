package project.chenlin_17.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import project.chenlin_17.GameApplication;
import project.chenlin_17.DirectorApp;

import java.io.IOException;
import java.util.function.Predicate;

@ExtendWith(ApplicationExtension.class)
class HighestRankControllerTest extends ApplicationTest {
    @Start
    public void Start(Stage stage) throws IOException {
        DirectorApp.getInstance().setM_Stage(stage);
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 650, 600);
        stage.setScene(scene);
        DirectorApp.getInstance().setM_UserName("CHENLIN");
        Parent root = FXMLLoader.load(GameApplication.class.getResource("Fxml/HighestRank.fxml"));
        stage.getScene().setRoot(root);
        stage.show();
    }


    @Test
    @DisplayName("Test for scoring")
    public void Initialze(){
        GridPane gridPane = lookup("#m_GridPane").query();
        HBox firstWinner = (HBox) gridPane.getChildren().get(1);
        Label firstLabel = (Label) firstWinner.getChildren().get(2) ;
        String firstScore = firstLabel.getText();
        HBox secondWinner = (HBox) gridPane.getChildren().get(2);
        Label secondLabel = (Label) secondWinner.getChildren().get(2) ;
        String secondScore = secondLabel.getText();
        Assertions.assertTrue(Integer.parseInt(firstScore)>=Integer.parseInt(secondScore));
    }

    @Test
    @DisplayName("Test for clicking close")
    void OnClickedBack() {
     moveTo("#m_Back");
     clickOn("#m_Back");
        FxAssert.verifyThat("#m_BackgroundView", (Predicate<AnchorPane>) Node::isVisible);
    }

}