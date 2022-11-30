package project.chenlin_17.Controller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import project.chenlin_17.GameApplication;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.tools.SceneFactory;
import project.chenlin_17.Views.WorldBuilder;
import project.chenlin_17.tools.CharacterOptions;
import project.chenlin_17.tools.SoundEffect;

import java.io.IOException;
import java.util.ArrayList;
/**
 * This class is the controller of the scene option page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 28th
 */
public class SceneOptionsController {
    /**
     * The draw pane for the scene option
     */
    @FXML
    private AnchorPane m_Background;
    /**
     * Container for node alignment
     */
    @FXML
    private VBox m_Vbox;

    /**
     * Container for imageView and corresponding label set
     */
    @FXML
    private HBox m_ImageBox;
    /**
     * Container for button selection
     */
    @FXML
    private HBox m_DifficultyChoice,m_ChangeScene,m_TextChoice;

    /**
     * Label for giving user instructions about what they are filling in for
     */
    @FXML
    private Label m_SceneOptions,m_DifficultyChoiceText;
    /**
     * Choice for theme
     */
    @FXML
    private ComboBox m_ComboBox;
    /**
     * Radio button set for difficulty choice
     */
    @FXML
    private ToggleGroup m_Difficulty;

    /**
     * Container for player's selections of the theme and the difficult level
     */
    @FXML
    private VBox m_ChooseVbox;
    /**
     * Sound for button clicking
     */
    private SoundEffect m_ButtonClicked;

    /**
     * This function is to initialize the controller. <br>
     * When first loading the page, make sure that the all the property have been bounded with the size,so that the scene is resizable.
     */
    public void initialize(){
        m_ButtonClicked = SoundEffect.BUTTONCLICK;

        m_Vbox.spacingProperty().bind(m_Background.heightProperty().multiply(0.08));
        m_ImageBox.prefWidthProperty().bind(m_Background.widthProperty().multiply(0.8));
        m_ImageBox.prefHeightProperty().bind(m_Background.heightProperty().multiply(0.25));
        m_ImageBox.spacingProperty().bind(m_Background.widthProperty().multiply(0.05));
        m_ChooseVbox.spacingProperty().bind(m_Background.heightProperty().multiply(0.1));


        adjustWidthSpacing(m_ChangeScene,m_Vbox,0.3);
        adjustWidthSpacing(m_TextChoice,m_ChooseVbox,0.06);
        adjustWidthSpacing(m_DifficultyChoice,m_ChooseVbox,0.06);
        setChooseProperty(m_SceneOptions);
        setChooseProperty(m_DifficultyChoiceText);
    }

    /**
     * This function adjust the Label node to the m_ChooseVbox and set its proposition for height and width.
     * @param label stands for correspondingly changed label node.
     */
    private void setChooseProperty(Label label) {
        label.prefWidthProperty().bind(m_ChooseVbox.widthProperty().multiply(0.3));
        label.prefHeightProperty().bind(label.widthProperty().multiply(0.15));
    }

    /**
     * This function adjusts the nodes' spacing property in the referred node by binding with its width property.
     * @param intend stands for the correspondingly changed node
     * @param refer stands for the changing node
     * @param value stands for the proposition of intend node' property in to refer node
     */
    private void adjustWidthSpacing(HBox intend, Node refer, double value) {
        if(refer instanceof AnchorPane)
            intend.spacingProperty().bind(((AnchorPane)refer).widthProperty().multiply(value));
        else
            intend.spacingProperty().bind(((VBox)refer).widthProperty().multiply(value));
    }


    /**
     * This function allows the player to go back to the index page.
     * @param mouseEvent activated when mouse click event is detected
     */

    public void PreviousSetting(MouseEvent mouseEvent) {
        m_ButtonClicked.click();
        DirectorApp.getInstance().showStartScreen();
    }

    /**
     * This function passes the player's choice for the theme into next page, which allows the player to set his paddle.<br>
     * It redirects the player to the character option page.
     * @param mouseEvent activated when mouse click event is detected
     * @throws IOException if it can't load the new scene
     */
    public void NextSetting(MouseEvent mouseEvent) throws IOException {
        m_ButtonClicked.click();
        /*
        get the player's choice for theme and difficult level
         */
        String choiceForEnvironmentSettings = (String) m_ComboBox.getValue();
        String choiceForDiff = m_Difficulty.getSelectedToggle().toString();
        /*
        build the new scene
         */
        SceneFactory sceneFactory = new SceneFactory(choiceForEnvironmentSettings);
        WorldBuilder worldBuilder = sceneFactory.getBuilder();
        worldBuilder.buildLevel(choiceForDiff);
        ArrayList<CharacterOptions> characterOptions = sceneFactory.getCharacterOptions();
        /*
        pass it to the next page
         */
        loadScene(worldBuilder,characterOptions);
    }

    /**
     * This helper function redirects the player to the character option page.
     * @param builder general or advanced builder based on the player's theme selection
     * @param options initial paddle setting for the player to choose in the next page
     * @throws IOException if it can't load the new scene
     */
    private void loadScene(WorldBuilder builder, ArrayList<CharacterOptions> options) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(GameApplication.class.getResource("Fxml/CharacterOptions.fxml"));
        fxmlLoader.load();
        CharacterOptionsController characterOptionsController =fxmlLoader.getController();
        characterOptionsController.initialize(builder,options);
        AnchorPane characterChoose = fxmlLoader.getRoot();
        m_Background.getScene().setRoot(characterChoose);
    }


}
