package project.chenlin_17.Controller;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.tools.SoundEffect;

/**
 * This class is the controller of the index start page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 28th
 */

public class IndexController {
    /**
     * The draw pane for the index page
     */
    @FXML
    private AnchorPane m_BackgroundView;
    /**
     * Image container for the background view
     */
    @FXML
    private ImageView m_ImageView;
    /**
     * Image container for the breakout game title
     */
    @FXML
    private ImageView m_Title;
    /**
     * Image container for the music display icon
     */
    @FXML
    private ImageView m_Music;
    /**
     * Container for button alignment
     */
    @FXML
    private VBox m_ButtonBox;

    /**
     * Slider for changing background music's volume
     */
    @FXML
    private Slider m_BGMVolumeSlider;

    /**
    Sound for background music
     */
    private SoundEffect m_BackgroundMusic;
    /**
    * Sound for button clicking
     */
    private SoundEffect m_ButtonClicked;
    /**
     * Defined value for the initial game playing volume
     */
    private final int DEF_VOLUME=50;
    /**
     * Defined value for adjusting the viewing of the bar
     */
    private final double  SHOW_BAR = 180;
    /**
     * Defined value for hiding the viewing of the bar
     */
    private final double HIDE_BAR = 20;

    /**
     * This is the initialize function for the Index pages' basic setting.<br>
     * It contains the default value for background music and the button clicking music.<br>
     * It contains the view adjustment for the scene components because they are designed to be resizable.<br>
     * It contains the initialization of the music slider's volume.
     */

    public void initialize(){
        /*
        set default value for the music settings
         */
        m_BackgroundMusic = SoundEffect.BACKGROUND;
        m_ButtonClicked= SoundEffect.BUTTONCLICK;
        DirectorApp.getInstance().setBackgroundMusic(m_BackgroundMusic);
        /*
        adjust resizable view components
         */
        viewerAdjust();
        /*
        initialize the slider volume and let the user be able to set the value
         */
        m_BGMVolumeSlider.setValue(DEF_VOLUME);
        m_BGMVolumeSlider.valueProperty().addListener((observableValue, number, t1) -> {
            m_BackgroundMusic.setVolume(Float.parseFloat(t1.toString())/100);
            m_BackgroundMusic.changeBGMVolume();
            m_BackgroundMusic.startplay();
        });

    }

    /**
     * This function adjusts the view component to the background by binding property.<br>
     * It allows the scene to be resizable.
     */
    private void viewerAdjust() {
        m_ImageView.fitWidthProperty().bind(m_BackgroundView.widthProperty());
        m_ImageView.fitHeightProperty().bind(m_BackgroundView.heightProperty());
        m_Title.fitWidthProperty().bind(m_BackgroundView.widthProperty());
        m_ButtonBox.maxWidthProperty().bind(m_BackgroundView.widthProperty().multiply(0.5));
        prefButtonAdjust();
    }

    /**
     * This function is a helper function to adjust the button's size and make the class more maintainable.
     */
    private void prefButtonAdjust() {
        ObservableList<Node> nodes = m_ButtonBox.getChildren();
        for (Node node : nodes) {
            HBox hbox = (HBox) node;
            Button button = (Button) hbox.getChildren().get(1);
            button.prefWidthProperty().bind(m_ButtonBox.widthProperty().multiply(0.8));
        }
    }


    /**
     * This function allows the player to the game start page.
     * @param mouseEvent activated when the mouse is clicked
     */
    public void StartGame(MouseEvent mouseEvent) {
        m_ButtonClicked.click();
        m_BackgroundView.getChildren().clear();
        DirectorApp.getInstance().gameStart();
    }

    /**
     * This function allows the player to see the instruction page.
     * @param mouseEvent activated when the mouse is clicked
     */
    public void ShowInstructions(MouseEvent mouseEvent)  {

        m_ButtonClicked.click();
        DirectorApp.getInstance().instructionInfo();
    }

    /**
     * This function allows the player to choose the theme and game settings.
     * @param mouseEvent activated when the mouse is clicked
     */
    public void ShowOptions(MouseEvent mouseEvent) {

        m_ButtonClicked.click();
            DirectorApp.getInstance().sceneChoose();
    }

    /**
     * This function allows the player to exit the game.
     * @param mouseEvent activated when the mouse is clicked
     */
    public void ExitGame(MouseEvent mouseEvent) {
        m_ButtonClicked.click();
        DirectorApp.getInstance().gameExit();
    }

    /**
     * This function sets the slider to be visible and allows the user to set the volume using the slider.
     * @param mouseEvent activated when the mouse is clicked
     */
    public void OnClickedVolume(MouseEvent mouseEvent) {
        m_BGMVolumeSlider.setVisible(true);
        AnchorPane.setRightAnchor(m_Music,SHOW_BAR);
    }

    /**
     * This function resets the music icon to a new position and gives place for the slider.
     * @param mouseEvent activated when the mouse is hovered
     */
    public void OnHoverVolume(MouseEvent mouseEvent) {
            m_BGMVolumeSlider.setOnMouseExited(mouseEvent1 -> {
            AnchorPane.setRightAnchor(m_Music,HIDE_BAR);
            m_BGMVolumeSlider.setVisible(false);
        });
    }

    /**
     * This function allows the slider to be seen when the player is changing the volume.
     * @param mouseEvent activated when the mouse is clicked
     */
    public void OnChangeVolume(MouseEvent mouseEvent) {
        AnchorPane.setRightAnchor(m_Music,SHOW_BAR);
        m_BGMVolumeSlider.setVisible(true);
    }

    /**
     * This function allows the player to go to the rank page.
     * @param mouseEvent activated when the mouse is clicked
     */
    public void ShowRank(MouseEvent mouseEvent) {
        m_ButtonClicked.click();
        DirectorApp.getInstance().showRank();
    }
}
