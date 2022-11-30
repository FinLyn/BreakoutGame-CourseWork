package project.chenlin_17.Controller;


import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.Models.GameStatusModel;
import project.chenlin_17.Views.World;
import project.chenlin_17.tools.SoundEffect;
/**
 * This class is the controller of the pause menu page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 28th
 */
public class PauseMenuController {
    /**
     * Default pane for the pause menu page
     */
    @FXML
    private AnchorPane m_PausePane;

    /**
     * Store current game setting status
     */
    private WorldController m_WorldController;
    /**
     * Sound for button clicking
     */
    private SoundEffect m_ButtonClicked;

    /**
     * This function sets the basic setting for this page using the received game settings. <br>
     * It contains the default value for the button clicking music.
     * @param worldController current game setting and information
     */
    public void initialize(WorldController worldController){


        m_WorldController = worldController;
        m_ButtonClicked=SoundEffect.BUTTONCLICK;
    }

    /**
     * This function allows the player to come back to the current game setting with game restarted.
     */
    public void RestartButtonOnClicked() {
        m_ButtonClicked.click();
        World world = m_WorldController.getM_World();
        world.resetInitial();
        GameStatusModel model = new GameStatusModel(world);
        m_WorldController.resetM_GameChecker(model);
//        m_WorldController.resetM_GameStatusChecker(new GameStatus(world,(int) m_WorldController.getM_Root().getWidth()));
        m_WorldController.backToScene();
    }

    /**
     * This function allows the player to come back to the current game setting.
     */
    public void ContinueButtonOnClicked() {
        m_ButtonClicked.click();
        m_WorldController.backToScene();
    }

    /**
     * This function allows the player to go to the index page.
     */
    public void MenuClicked() {
        m_ButtonClicked.click();
        DirectorApp.getInstance().showStartScreen();
    }


}

