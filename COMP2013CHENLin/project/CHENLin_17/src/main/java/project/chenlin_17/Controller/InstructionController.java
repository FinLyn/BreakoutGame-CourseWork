package project.chenlin_17.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.tools.SoundEffect;

/**
 * This class is the controller of the instruction page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 28th
 */
public class InstructionController {
    /**
     * Image container for close icon
     */
    @FXML
    private ImageView m_Back;
    /**
     * Sound for button clicking
     */
    private SoundEffect m_ButtonClicked;
    /**
     * Adjustable value for setting close image's view property
     */
    private final int BACK_LEN = 40;
    /**
     * Adjustable value for setting close image's position
     */
    private final int BACK_POS =4;
    /**
     * This function sets the basic setting required for this scene. <br>
     * It contains the default value for the button clicking music.
     */
    public void initialize(){
        m_ButtonClicked = SoundEffect.BUTTONCLICK;
    }


    /**
     * This function allows the player to come m_Back to the index screen.
     */
    public void OnClickedBack() {
        m_ButtonClicked.click();
        DirectorApp.getInstance().showStartScreen();
    }


}
