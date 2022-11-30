package project.chenlin_17.Controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import project.chenlin_17.Models.GameStatusModel;
import project.chenlin_17.Views.World;
import project.chenlin_17.tools.SoundEffect;
/**
 * This class is the controller of the power up page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 28th
 */
public class PowerUpController {
    /**
     * Slider for ball's x speed value
     */
    @FXML
    Slider m_XSpeed;
    /**
     * Slider for ball's y speed value
     */
    @FXML
    Slider m_YSpeed;
    /**
     * Button for reset game setting
     */
    @FXML
    Button m_Reset;
    /**
     * Button for skip level
     */
    @FXML
    Button m_Skip;

    /**
     * Label for showing warning message
     */
    @FXML
    Label m_Warning;

    /**
     * Checker for whether the button has been selected
     */
    boolean m_resetStatus, m_skipStatus;
    /**
     * Sound for button clicking
     */
    SoundEffect m_ButtonClicked;
    /**
     * Store current game setting status
     */
    WorldController m_WorldController;
    /**
     * Defined button style name for button clicking
     */
    private final String BUTTONCLICK ="speedBox2";
    /**
     * Defined button style name for button canceling
     */
    private final String BUTTONCANCLE ="speedBox1";

    /**
     * This function sets the basic setting before the world controller has been received.<br>
     * It contains the default value for the button clicking music.<br>
     * It contains the default value for the button chosen status - not chosen.
     */
    public void initialize(){
        m_resetStatus = false;
        m_skipStatus = false;
        m_ButtonClicked = SoundEffect.BUTTONCLICK;
    }

    /**
     * This function sets the basic setting for this page using the received game settings. <br>
     * It contains the initial setting for ball's m_Xspeed  and m_Yspeed. It also allows the user to change the xpeed and m_Yspeed using the slider.
     * @param worldController current game setting and information
     */
    public void initialize(WorldController worldController){

        m_WorldController = worldController;
        int xValue = m_WorldController.getM_World().getM_BallSpeedX();
        int yValue = m_WorldController.getM_World().getM_BallSpeedY();
        m_XSpeed.setValue(xValue);
        m_YSpeed.setValue(yValue);
        m_XSpeed.valueProperty().addListener((observableValue, number, t1) ->
                m_WorldController.getM_World().setBallSpeedX(t1.intValue()));
        m_YSpeed.valueProperty().addListener((observableValue, number, t1) ->
                m_WorldController.getM_World().setBallSpeedY(t1.intValue()));
    }

    /**
     * This function allows the player to choose whether to reset the ball or not.<br>
     * It contains the changing for button's style.
     * @param mouseEvent activated when the mouse click have been detected
     */
    public void ResetBall(MouseEvent mouseEvent) {
        m_ButtonClicked.click();
        m_resetStatus =!m_resetStatus;
        if(m_resetStatus)
            changeStyle(m_Reset,BUTTONCANCLE,BUTTONCLICK);
        else
            changeStyle(m_Reset,BUTTONCLICK,BUTTONCANCLE);
    }

    /**
     * This function allows the player to choose whether to skip the level or not.<br>
     * It contains the changing for button's style.<br>
     * When there are no next level, the warning message is shown and the button is disabled.
     * @param mouseEvent activated when the mouse click have been detected
     */
    public void SkipLevel(MouseEvent mouseEvent) {
        m_ButtonClicked.click();
        /*
         * If there is no next level, disable the button and show the warning message.
         */
        if(!m_WorldController.getM_World().hasLevel()) {
            m_Skip.setDisable(true);
            m_Warning.setVisible(true);
        }
        else{
            m_skipStatus = !m_skipStatus;
            if(m_skipStatus)
                changeStyle(m_Skip,BUTTONCANCLE,BUTTONCLICK);
            else
                changeStyle(m_Skip,BUTTONCLICK,BUTTONCANCLE);
        }

    }

    /**
     * This function allows the player to close the power up menu and get his option set.<br>
     * @param mouseEvent activated when the mouse click have been detected
     */
    public void Exit(MouseEvent mouseEvent){

        if(m_resetStatus){
            /*
             * reset game setting
             */
            m_WorldController.getM_World().resetBallCount();
            resetStatus(m_WorldController.getM_World());

        }

        if(m_skipStatus)
        {
            World world = m_WorldController.getM_World();
            /*
             * reset game setting if there is next level
             */
            if(world.hasLevel()){
                world.skipLevel();
                resetStatus(world);
            }

        }
        m_ButtonClicked.click();
        /*
         * close current menu
         */
        m_WorldController.backToScene();
    }

    /**
     * This function changes the buttons style using the passed in style class.
     * @param button button that needs to be changed
     * @param oldStyle previous style class
     * @param newStyle expected style class
     */
    private void changeStyle(Button button,String oldStyle,String newStyle){
        button.getStyleableParent().getStyleClass().remove(oldStyle);
        button.getStyleableParent().getStyleClass().add(newStyle);
    }

    /**
     * This function resets game status checker boarder.
     * @param world the updated game setting
     */
    private void resetStatus(World world){
        GameStatusModel model = new GameStatusModel(world);
        m_WorldController.resetM_GameChecker(model);
    }

}
