package project.chenlin_17;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import project.chenlin_17.Controller.WorldController;
import project.chenlin_17.Models.*;
import project.chenlin_17.Views.GeneralBuilder;
import project.chenlin_17.Views.World;
import project.chenlin_17.Views.WorldBuilder;
import project.chenlin_17.tools.SoundEffect;

import java.io.IOException;
import java.util.Optional;
/**
 * This class is the controller of the scene option page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */


public class DirectorApp {
    /**
     * Stage default width value
     */
    private static final int DEF_WIDTH = 600;
    /**
     * Stage default height value
     */
    private static final int DEF_HEIGHT = 650;
    /**
     * Defined value for the stage title
     */
    private final String TITLE ="Breakout Game";
    /**
     * Defined value for the alert title
     */
    private final String ALERT_TITLE ="Exit Game";
    /**
     * Defined value for the alert content
     */
    private final String ALERT_CONTENT ="Do you want to exit the game?";
    /**
     * Defined value for the ball and paddle's view
     */
    private final Color DEF_COLOR = Color.LIGHTBLUE;
    /**
     * Defined value for adjusting the height
     */
    private final double HEIGHT_RATE = 0.8;
    /**
     * Defined value for adjusting the width
     */
    private final double WIDTH_RATE = 0.5;


    /**
     * Stage for scene showing
     */
    private Stage m_Stage;
    /**
     * Stage for created world
     */
    private World m_World;
    /**
     * Store the current player's name
     */
    private String m_UserName;
    /**
     * The current game setting and corresponding score recording
     */
    private WorldController m_WorldController;
    /**
     * Sound for background music
     */
    private SoundEffect m_BackgroundMusic;


    /**
     * Single application created for the player
     */
    private static final DirectorApp m_Instance = new DirectorApp();

    /**
     * This constructor is set to private due to the single game scene purpose.
     */
    private DirectorApp() {}

    /**
     * This function returns the single game scene
     * @return the application instance created for game playing
     */
    public static DirectorApp getInstance() {
        return m_Instance;
    }
    /**
     * This function initialize the stage for game playing and the exit of the game.<br>
     * It contains the creation of the stage with defined width and height.<br>
     * It contains the confirmation behaviour for the close stage.
     * @param stage current stage for displaying
     */
    public void initialize(Stage stage)  {
        /*
        set game root and default stage settings
         */
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, DEF_WIDTH,DEF_HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setWidth(DEF_WIDTH);
        stage.setHeight(DEF_HEIGHT);
        /*
         fired when the player clicks the close button on the stage board
         */
        stage.setOnCloseRequest(windowEvent -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(ALERT_TITLE);
            alert.setHeaderText(null);
            alert.setContentText(ALERT_CONTENT);

            Optional<ButtonType> click = alert.showAndWait();
            if (click.isPresent() &&click.get() == ButtonType.OK) gameExit();
            else windowEvent.consume();
        });
        /*
        initialize the stage
         */
        m_Stage = stage;
        /*
        led to the login page
         */
        showLogInPage();
        stage.show();
    }

    /**
     * This function leads the player to the login page
     */
    public void showLogInPage(){
       LogIn.load(m_Stage,DEF_WIDTH,DEF_HEIGHT);
    }

    /**
     * This function sets the initial game state.<br>
     * If the player does not choose the scene option, the default world builder would build one for the player.<br>
     * If the player had chosen the scene option, then his option would be set.
     */
    public void gameStart() {
        /*
        if the player does not choose the scene option
         */
        if (m_World == null) {
            buildInitialScene();
        }
        /*
        set new game setting with the player's choice for game
         */
        m_WorldController = new WorldController(m_World);
        m_WorldController.initialize(m_Stage);
        m_Stage.getScene().setRoot(m_WorldController.getM_Root());
    }

    /**
     * This function allows the change of the background music for the unique game setting
     * @param backgroundMusic defined background music
     */
    public void setBackgroundMusic(SoundEffect backgroundMusic){
        if (m_BackgroundMusic!= null)
             m_BackgroundMusic.stop();
        m_BackgroundMusic = backgroundMusic;
        /*
        change background music
         */
        m_BackgroundMusic.startplay();
    }


    /**
    This function allows the player to close the stage.<br>
    It can either be done by clicking the icon above the stage page or the exit button.
     */
    public void gameExit() {
        m_Stage.close();
    }

    /**
     * This function allows the player to go to the index page.
     */
    public void showStartScreen() {
        m_Stage.setResizable(true);
        Index.load(m_Stage);
    }

    /**
     * This function leads the player to the scene choose page.
     */
    public void sceneChoose()  {
        setScene("Fxml/SceneOptions.fxml");
    }

    /**
     * This function leads the player to the instruction information page.
     */
    public void instructionInfo() {
        setScene("Fxml/Instruction.fxml");
    }
    /**
     * This function leads the player to the rank page.
     */
    public void showRank()  {setScene("Fxml/HighestRank.fxml");}

    /**
     * This function allows the scene options to set the player's choice for the game playing.
     * @param world world built by the player through scene choice page
     */
    public void setWorld(World world) {
        m_World = world;
    }

    /**
     * This function builds the default world if the player doesn't choose any game settings.
     */
    private void buildInitialScene() {
        WorldBuilder initializer = new GeneralBuilder();
        double height= m_Stage.getHeight();
        double width = m_Stage.getWidth();
        initializer.buildBackground( height,width);
        initializer.buildSound();

        BallModel ballModel = new BallModel(new Point2D(width* WIDTH_RATE, height*HEIGHT_RATE ),10,10);
        PaddleModel paddleModel= new PaddleModel(new Point2D(width*WIDTH_RATE, height* HEIGHT_RATE),150,10);
        initializer.buildPaddle(paddleModel, DEF_COLOR,null, new Rectangle(0, 0, width, height*HEIGHT_RATE));
        initializer.buildBall(ballModel,DEF_COLOR);
        initializer.buildWall(new Rectangle(0, 0, width, height*HEIGHT_RATE),  0);
        initializer.buildGroup();

        m_World = initializer.getWorld();
    }


    /**
     * This helper function is aimed at set different root for the scene.<br>
     * It is needed for jumping between the scene classes.
     * @param fileName fileName for loaded fxml file
     */

    private void setScene(String fileName){

        try {
            Parent root = FXMLLoader.load(GameApplication.class.getResource(fileName));
            m_Stage.getScene().setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This function allows the scene options to set the player's choice for the game playing.
     * @param userName username passed by the player through the login page
     */
    public void setM_UserName(String userName){
        m_UserName = userName;
    }

    /**
     * This function returns the current player's nickname.
     * @return the current player's name
     */
    public String getM_UserName(){
        if(m_UserName == null)
            return "null";
        return m_UserName;
    }

    /**
     * This function allows the stage to be set, it is used for testing.
     * @param stage stands for the passed in stage by the player.
     */
    public void setM_Stage(Stage stage){
        m_Stage = stage;
    }

    /**
     * This function allows the unique background music for the game to be gotten.
     * @return current background music
     */
    public SoundEffect getM_BackgroundMusic(){
        return m_BackgroundMusic;
    }
    /**
     * This function allows the unique game setting to be gotten.
     * @return current game setting
     */
    public World getM_World(){
        return m_World;
    }

    /**
     * This function allows the unique background music for the gameSetting and game playing controller.
     * @return current game view setting and playing controller
     */
    public WorldController getM_WorldController(){
        return m_WorldController;
    }
}