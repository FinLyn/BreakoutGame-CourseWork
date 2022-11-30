package project.chenlin_17.Controller;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import project.chenlin_17.Models.BallModel;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.Models.PaddleModel;
import project.chenlin_17.Views.WorldBuilder;
import project.chenlin_17.tools.CharacterOptions;
import project.chenlin_17.tools.SoundEffect;

import java.util.ArrayList;

/**
 * This class is the controller of the character option page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 28th
 */
public class CharacterOptionsController {
    /**
     * Component's expected height view showing rate related to the scene
     */
    private final double HEIGHT_RATE = 0.8;
    /**
     * Component's expected width view showing rate related to the scene
     */
    private final double WIDTH_RATE = 0.5;
    /**
     * The draw pane for the characterOption page
     */
    @FXML
    private AnchorPane m_CharacterBackground;
    /**
     * Container for back and next button
     */
    @FXML
    private HBox m_ChangeScene;

    /**
     * Container for option choose
     */
    @FXML
    private HBox m_PersonBox,m_BallChoose,m_SetChoose,m_PaddleChoose;
    /**
     * Container for the node alignment
     */
    @FXML
    private VBox m_Vbox;
    /**
     * Container for m_BallChoose and m_PaddleChoose in order to make node alignment
     */
    @FXML
    private VBox m_ChooseVbox;
    /**
     * Button for default color value of the ball and paddle
     */
    @FXML
    private RadioButton m_BallButton,m_PaddleButton;
    /**
     * Label for giving user instructions about what they are filling in for
     */
    @FXML
    private Label m_ChooseBallText,m_ChoosePaddleText,m_ChooseSetText;
    /**
     * Choose for wall set
     */
    @FXML
    private ComboBox m_ComboBox;
    /**
     * Radio button set for choosing color
     */
    @FXML
    private ToggleGroup m_ChooseBall,m_ChoosePaddle;

    /**
     * worldBuilder returned from the theme chosen page
     */
    private WorldBuilder m_WorldBuilder;
    /**
     * present character options for viewing
     */
    private ArrayList<CharacterOptions> m_CharacterOptions;
    /**
     * Choice for the paddle
     */
    private CharacterOptions m_Player;
    /**
     * Sound for button clicking
     */
    private SoundEffect m_ButtonClicked;

    /**
     * This function is to initialize the controller. <br>
     * When first loading the page, make sure that the all the property have been bounded with the size,so that the scene is resizable.
     */
    public void initialize(){
        /*
         * add sound effect for button clicking
         */
        m_ButtonClicked = SoundEffect.BUTTONCLICK;
        /*
         * bind the item's width, height and spacing property with the pane's property change
         */
        m_PersonBox.prefWidthProperty().bind(m_CharacterBackground.widthProperty().multiply(0.8));
        m_PersonBox.prefHeightProperty().bind(m_CharacterBackground.heightProperty().multiply(0.25));
        m_PersonBox.spacingProperty().bind(m_PersonBox.widthProperty().multiply(0.05));

        /*
         * adjust vbox's spacing property
         */
        adjustHeightSpacing(m_ChooseVbox,0.10);
        adjustHeightSpacing(m_Vbox,0.08);

        /*
         * adjust hbox's width property
         */
        adjustWidthSpacing(m_ChangeScene,m_CharacterBackground,0.3);
        adjustWidthSpacing(m_PaddleChoose,m_ChooseVbox,0.06);
        adjustWidthSpacing(m_SetChoose,m_ChooseVbox,0.06);
        adjustWidthSpacing(m_BallChoose,m_ChooseVbox,0.06);
        /*
         * adjust labels' shown property
         */
        setChooseProperty(m_ChooseSetText);
        setChooseProperty(m_ChooseBallText);
        setChooseProperty(m_ChoosePaddleText);

    }

    /**
     * This function adjust the node's spacing property in the whole pane by binding with its height property.
     * @param intend stands for the corresponding changed node.
     * @param value stands for the proposition of intend node' property in the whole pane.
     */
    private void adjustHeightSpacing(VBox intend, double value) {
        intend.spacingProperty().bind(m_CharacterBackground.heightProperty().multiply(value));
    }

    /**
     * This function adjusts the nodes' spacing property in the referred node by binding with its width property.
     * @param intend stands for the correspondingly changed node
     * @param refer stands for the changing node
     * @param value stands for the proposition of intend node' property in the referred node
     */
    private void adjustWidthSpacing(HBox intend, Node refer, double value) {
       if(refer instanceof AnchorPane)
           intend.spacingProperty().bind(((AnchorPane)refer).widthProperty().multiply(value));
       else
           intend.spacingProperty().bind(((VBox)refer).widthProperty().multiply(value));
    }

    /**
     * This function adjust the Label node to the chooseVbox and set its proposition for height and width.
     * @param label stands for correspondingly changed label node.
     */
    private void setChooseProperty(Label label) {
        label.prefWidthProperty().bind(m_ChooseVbox.widthProperty().multiply(0.3));
        label.prefHeightProperty().bind(label.widthProperty().multiply(0.15));
    }

    /**
     * This function is intended to initialize the showing nodes for the character choose class. <br>
     * Because the content in this page will change according to users' previous options for the theme setting.
     * @param worldBuilder accepts the world builder's type from the previous setting
     * @param characterOptions accepts the showing scene for the character, which allows the user to make further choice in this page
     */
    public void initialize(WorldBuilder worldBuilder,ArrayList<CharacterOptions> characterOptions){
        /*
         * stores those variables using class variables
         */
        m_CharacterOptions = characterOptions;
        m_WorldBuilder = worldBuilder;

        /*
         * type stands for the worldBuilders' type
         */
        String type = worldBuilder.getType();
        /*
         * if it is a generalWorldBuilder, the ball color should be set to the default value:black
         * if it is a generalWorldBuilder, the paddle color should be set to the default value:black
         */
        if(type.equals("General")) {
            m_BallButton.setSelected(true);
            m_PaddleButton.setSelected(true);
        }else {
            /*
             * if it is an advancedWorldBuilder, the user is unable to choose the ball color
             * if it is an advancedWorldBuilder, the user is unable to choose the paddle color
             */
            m_PaddleChoose.setDisable(true);
            m_BallChoose.setDisable(true);
        }

        /*
         * set person view in the character option page according to the user's theme choice
         */

        for(int i=0;i<4;i++){
            ImageView imageView=(ImageView) m_PersonBox.getChildren().get(i);
            imageView.setImage(characterOptions.get(i).getM_Person());
        }

        /*
         * set the default choose for the character and player set
         */
        m_Player = characterOptions.get(0);

    }

    /**
     * This function is intended for building the world according to the user's choice. <br>
     * This completes the unfinished scene left by the previous page. <br>
     * It adds the paddle, the ball, the wall set and the background music.
     */
    public void Complete() {
        /*
         * add music for clicking done button
         */
        m_ButtonClicked.click();
        /*
         * get user's final choice for the paddle/ball/wall set
         */
        int setChoice = m_ComboBox.getSelectionModel().getSelectedIndex();
        m_Player=m_CharacterOptions.get(setChoice);
        /*
         * call the world builder to build the final scene
         */
        double height = m_CharacterBackground.getScene().getWindow().getHeight();
        double width =  m_CharacterBackground.getScene().getWindow().getWidth();
        m_WorldBuilder.buildBackground(height,width);
        m_WorldBuilder.buildSound();
        buildBall(width ,height);
        buildPaddle(width, height);
        buildWall(width,height,setChoice);
        m_WorldBuilder.buildGroup();
        /*
         * go back to the start screen
         */
        DirectorApp.getInstance().setWorld(m_WorldBuilder.getWorld());
        DirectorApp.getInstance().showStartScreen();
    }

    /**
     * This function allows worldBuilder to build the wall group.
     * @param width expected wall container length
     * @param height expected wall container height
     * @param options users' choice for the set choosing
     */
    private void buildWall(double width, double height, int options) {
        m_WorldBuilder.buildWall(new Rectangle(0,0, width,height*HEIGHT_RATE),options);
    }

    /**
     * This function allows the worldBuilder to build the paddle.<br>
     * If it is the general world builder, then the paddle is built according to the color the user had chosen.<br>
     * If it is the advanced world builder, then the paddle is expected to be black.
     * @param width expected paddle container length
     * @param height expected paddle container height
     */
    private void buildPaddle(double width,double height) {
        PaddleModel paddleModel = new PaddleModel(new Point2D(width*WIDTH_RATE, height*HEIGHT_RATE),150,10);
        if(m_Player.getM_Player()!=null)
            m_WorldBuilder.buildPaddle(paddleModel,Color.BLACK,m_Player.getM_Player(),new Rectangle(0,0,width,height*HEIGHT_RATE));
        else{
            Color color = getColor(m_ChoosePaddle);
            m_WorldBuilder.buildPaddle(paddleModel,color,null,new Rectangle(0,0,width,height*HEIGHT_RATE));
        }

    }

    /**
     * This function allows the worldBuilder to build the ball. <br>
     * Only the general world builder will utilize the color here. <br>
     * Because in the advanced world builder, it uses the ImageBall and the ball face have been removed and the color actually doesn't matter.
     * @param width expected ball container length
     * @param height expected ball container height
     */
    private void buildBall(double width,double height) {
        Color color= getColor(m_ChooseBall);
        BallModel model = new BallModel(new Point2D(width*WIDTH_RATE,height*HEIGHT_RATE),10,10);
        m_WorldBuilder.buildBall(model,color);
    }

    /**
     * @param toggleGroup get user's final choice for the color of the ball and paddle in the string format
     * @return the color value that will be used for building the ball and paddle in Color format
     */
    private Color getColor(ToggleGroup toggleGroup)
    {
        if(toggleGroup.getSelectedToggle()==null) return null;
        String toggleBallColor = toggleGroup.getSelectedToggle().toString();
        if(toggleBallColor.contains("Black"))
          return Color.BLACK;
        if(toggleBallColor.contains("White"))
           return Color.WHITE;
        if(toggleBallColor.contains("Blue"))
           return Color.ALICEBLUE;
        if(toggleBallColor.contains("Grey"))
           return Color.LIGHTGREY;
        else return null;
    }

    /**
     * This function allows user to reset the theme choice.
     * @param mouseEvent do when mouse are clicked
     */
    public void ToSceneChoice(MouseEvent mouseEvent) {
        m_ButtonClicked.click();
        DirectorApp.getInstance().sceneChoose();
    }
}
