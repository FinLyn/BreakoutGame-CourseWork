package project.chenlin_17.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import project.chenlin_17.GameApplication;
import project.chenlin_17.DirectorApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogInControllerTest extends ApplicationTest {
    Stage m_Stage;

    @Start
    public void start(Stage stage) throws IOException {
            DirectorApp.getInstance().setM_Stage(stage);
            AnchorPane anchorPane = new AnchorPane();
            Scene scene = new Scene(anchorPane, 600, 650);
            stage.setScene(scene);
            Parent root = FXMLLoader.load(GameApplication.class.getResource("Fxml/LogIn.fxml"));
            stage.getScene().setRoot(root);
            m_Stage = stage;
            stage.show();
    }

    @Test
    @Order(1)
    @DisplayName("Test empty user name and password")
    void BothNull(FxRobot robot) throws NoSuchFieldException {
        robot.doubleClickOn("#m_SignIn");
        testDialog(robot,"Action Required","Please set your user name and password first");
    }

    @Test
    @Order(2)
    @DisplayName("Test empty password")
    void UserNameNull(FxRobot robot) throws NoSuchFieldException{

        clickOn("#m_UserName").write("CHENLIN");
        sleep(100);
        robot.doubleClickOn("#m_SignIn");
        testDialog(robot,"Action Required","Password can't be null");
    }

    @Test
    @Order(3)
    @DisplayName("Test empty user name")
    void UserPasswordNull(FxRobot robot) throws NoSuchFieldException{

        clickOn("#m_Password").write("1234");
        sleep(100);
        robot.doubleClickOn("#m_SignIn");
        testDialog(robot,"Action Required","User name can't be null");
    }

    @Test
    @Order(4)
    @DisplayName("Test valid user name and invalid password")
    void InvalidPassword(FxRobot robot) throws NoSuchFieldException{
        clickOn("#m_UserName").write("CHENLIN");
        clickOn("#m_Password").write("1");
        sleep(100);
        robot.doubleClickOn("#m_SignIn");
        testDialog(robot,"Action Required","Incorrect Password");
    }

    @Test
    @Order(5)
    @DisplayName("Test valid user name and valid password")
    void ValidLogIn(FxRobot robot) throws NoSuchFieldException {
        clickOn("#m_UserName").write("CHENLIN");
        clickOn("#m_Password").write("1234");
        sleep(100);
        robot.doubleClickOn("#m_SignIn");
        sleep(1000);
        FxAssert.verifyThat("#m_BackgroundView",(AnchorPane pane) -> {
            return pane.isVisible();
        });

        Assertions.assertEquals("CHENLIN",DirectorApp.getInstance().getM_UserName());
    }

    private void testDialog(FxRobot robot, String headText, String contentText){
        sleep(100);
        Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(headText, dialogPane.getHeaderText());
        assertEquals(contentText, dialogPane.getContentText());
    }

    private Stage getTopModalStage(FxRobot robot) {
        // Get a list of windows but ordered from top[0] to bottom[n] ones.
        // It is needed to get the first found modal window.
        final List<Window> allWindows = new ArrayList<>(robot.robotContext().getWindowFinder().listWindows());
        Collections.reverse(allWindows);

        return (Stage) allWindows
                .stream()
                .filter(window -> window instanceof Stage)
                .filter(window -> ((Stage) window).getModality() == Modality.APPLICATION_MODAL)
                .findFirst()
                .orElse(null);
    }
}