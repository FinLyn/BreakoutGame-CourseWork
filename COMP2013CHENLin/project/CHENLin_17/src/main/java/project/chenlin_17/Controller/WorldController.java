package project.chenlin_17.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import project.chenlin_17.Controller.GameOverScreenController;
import project.chenlin_17.Controller.PauseMenuController;
import project.chenlin_17.Controller.PowerUpController;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.GameApplication;
import project.chenlin_17.Models.GameStatusModel;
import project.chenlin_17.Views.GameStatusView;
import project.chenlin_17.tools.Record;
import project.chenlin_17.Views.World;

import java.io.IOException;

/**
 * This class is the controller of the game viewing and game playing status checker.
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */

public class WorldController {
    /**
     * The draw pane for the whole world buildings, includes the world components and game status.
     */
    private AnchorPane m_Root;
    /**
     * The pane for the player's current game status logger
     */
//    private GameStatus m_GameStatusChecker;
    private GameStatusView m_GameChecker;
    /**
     * The pane for the current game settings and its required components
     */
    private World m_World;
    /**
     * The control of the animation
     */
    private Timeline m_GameTimer;
    /**
     * Record whether the game has paused or not
     */
    private boolean m_ShowPauseMenu;
    /**
     * Frame for game animation
     */
    private KeyFrame m_Frame;
    /**
     * Pane for pause menu and power up menu
     */
    private static Pane m_PausePane;
    /**
     * Record for the current player and his score
     */
    private Record m_Record;
    /**
     * The player's highest score for each replaying round
     */
    private int m_HighestScore;

    /**
     * The constructor initializes the components of the game board and animates
     * the game board.
     * <pre> {@code
     *  Modified from the original source code:
     *  public GameBoard(JFrame owner){
     *      super();
     *
     *      strLen = 0;
     *      showPauseMenu = false;
     *
     *
     *
     *      menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);
     *
     *
     *      this.initialize();
     *      message = "Press SPACE to start";
     *      wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,
     *                                                      new Point(300,430));
     *
     *      debugConsole = new DebugConsole(owner,wall,this);
     *      //initialize the first level
     *      wall.nextLevel();
     *
     *      gameTimer = new Timer(10,e ->{
     *      wall.move();
     *      wall.findImpacts();
     *      message = String.format("Bricks: %d Balls %d",wall.getBrickCount(),
     *                                                  wall.getBallCount());
     *      if(wall.isBallLost()){
     *          if(wall.ballEnd()){
     *              wall.wallReset();
     *              message = "Game over";
     *          }
     *          wall.ballReset();
     *          gameTimer.stop();
     *      }
     *      else if(wall.isDone()){
     *          if(wall.hasLevel()){
     *              message = "Go to Next Level";
     *              gameTimer.stop();
     *              wall.ballReset();
     *              wall.wallReset();
     *              wall.nextLevel();
     *          }
     *          else{
     *              message = "ALL WALLS DESTROYED";
     *              gameTimer.stop();
     *          }
     *      }
     *
     *      repaint();
     *      });
     *
     *  }
     * } </pre>
     *
     * @see GameBoardManager
     * @see GameBoardFactory
     * @see GameStatusBar
     * @see Timeline
     * @see KeyFrame
     */

    /**
     * This constructor sets the basic world settings of the world passed by the player and the initializes the game status.
     * @param world world settings chosen by the player
     */
    public WorldController(World world) {
        /*
        initialize the highest score to be 0
         */
        m_HighestScore = 0;

        m_World = world;

        /*
        new game stauts checker
         */
        GameStatusModel gameModel = new GameStatusModel(world);
        m_GameChecker = new GameStatusView(gameModel);
        /*
        no pause menu shown at first
         */
        m_ShowPauseMenu = false;
        /*
         new game animation
         */
        m_Frame = new KeyFrame(Duration.ONE.multiply(10), e -> {
            try {
                step();
            }
            catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        m_GameTimer = new Timeline();
        m_GameTimer.setCycleCount(Timeline.INDEFINITE);
        m_GameTimer.getKeyFrames().add(m_Frame);
    }

    /**
     * This function simulates game playing status and continuously update the current game setting and the game status checker.<br>
     * It detects all the exceptions in the game playing process.<br>
     * It acts when the game ends and do the corresponding action.<br>
     * The highest score would be the user's score for each losing round.
     * @throws IOException if loading fails
     * @throws InterruptedException if loading fails
     */
    private void step() throws IOException, InterruptedException {
        //get ball and paddle movement instructions
        m_World.move();
        //find their impact on the wall
        m_World.findImpacts();
        //update the status checking board
        m_GameChecker.update();
        GameStatusModel model = m_GameChecker.getM_Model();
        int status =model.getM_Status();
        /*
        if the player loses this round
         */
        if(status == GameStatusModel.LOSE){
            updateHighestScore(model.getM_Score());
            m_World.ballPositionReset();
        }
        /*
         * if the player loses three rounds
         * reset the whole game scene back to original status
         */
        else if(status == GameStatusModel.DIE){
            updateHighestScore(model.getM_Score());
            m_Record = new Record (DirectorApp.getInstance().getM_UserName(), m_HighestScore);
            showStatusScreen();

        }
        /*
        if the player win the round
         */
        else if(status == GameStatusModel.WIN){
            if(m_World.hasLevel())
            {
                m_GameTimer.stop();
                m_World.wallReset();
                m_World.nextLevel();
            }
            /*
            it means the player finishes all the round
             */
            else{
                updateHighestScore(model.getM_Score());
                m_Record = new Record(DirectorApp.getInstance().getM_UserName(), model.getM_Score());
                showStatusScreen();
            }
        }
    }

    /**
     * This function helps update the player's best score if he loses this round and needs to play again.
     * @param m_score score received
     */
    private void updateHighestScore(int m_score) {
        if(m_HighestScore<m_score){
            m_HighestScore = m_score;
        }
        m_GameTimer.stop();
    }

    /**
     * This function shows the final record screen when the game ends.<br>
     * There are two cases that the game will end, the first case is that the player loses three rounds, the second case is that the player wins.
     * @throws IOException if loading fails
     * @throws InterruptedException if loading fails
     */
    private void showStatusScreen() throws IOException, InterruptedException {
        m_World.resetInitial();
        m_GameTimer.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("Fxml/GameOverScreen.fxml"));
        fxmlLoader.load();
        GameOverScreenController controller = fxmlLoader.getController();
        controller.setGameStatus(m_GameChecker.getM_Model().getM_Status(), m_Record);
        Pane GameOverScreen = controller.getSceneRoot();
        m_Root.getScene().setRoot(GameOverScreen);

    }

    /**
     * This function aims at initializing the game board and make all the game settings visible on the pane
     * @param m_Stage current game stage
     */
    public void initialize(Stage m_Stage){
        m_Stage.setResizable(false);
        m_Stage.setHeight(m_World.getHeight());
        m_Stage.setWidth(m_World.getWidth());
        m_Stage.centerOnScreen();
        /*
         new pane is introduced to be the container
         */
        m_Root= new AnchorPane();
        /*
        set the initialization scene by getting the wall
         */
        AnchorPane.setTopAnchor(m_World.getM_Group(),60.00);
        m_Root.getChildren().add(m_World.getM_Group());
        m_Root.getChildren().add(m_GameChecker.getM_Group());

        /*
        set background music
         */
        DirectorApp.getInstance().setBackgroundMusic(m_World.getM_BackgroundSound());
        /*
        set sensable key input
         */
        setKeyInput(m_Stage.getScene());
        setWindowFocus(m_Stage.getScene().getWindow());
        /*
        does not start game at first
         */
        m_GameTimer.stop();
    }

    /**
     * This function helps gets the players' action from the keyboard and make corresponding actions.<br>
     * It associates with pressing ESC. SPACE. comnination of F1,Alt and SHIFT.
     * @param scene current game scene
     */
    public void setKeyInput(Scene scene){
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode= event.getCode();
            if(!m_ShowPauseMenu){
                if(keyCode.equals(KeyCode.ESCAPE)){
                    m_ShowPauseMenu = !m_ShowPauseMenu;
                    m_GameTimer.stop();
                    try {
                        showPauseMenu("Fxml/PauseMenu.fxml");
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(keyCode.equals(KeyCode.F1)) {
                    if (event.isAltDown() &&event.isShiftDown()) {
                        m_ShowPauseMenu = !m_ShowPauseMenu;
                        m_GameTimer.stop();
                        try {
                            showPauseMenu("Fxml/PowerUp.fxml");
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if(keyCode.equals(KeyCode.LEFT)){
                m_World.movePlayerLeft();
            }if(keyCode.equals(KeyCode.RIGHT)) {
                m_World.movePlayerRight();
            }if(keyCode.equals(KeyCode.SPACE)){
                if(!m_ShowPauseMenu) {
                    if (m_GameTimer.getStatus() == Animation.Status.RUNNING)
                        m_GameTimer.stop();
                    else
                        m_GameTimer.play();
                }
            }
        });
        scene.setOnKeyReleased(event -> {
            m_World.playStop();
        });
    }

    /**
     * This function helps show the pause menu or the power up menu by adding it to the scene root.
     * @param file the file name for pausemenu, either the file name for debug panel or the pause menu
     * @throws IOException if load menu fails
     */
    private void showPauseMenu(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource(file));
        fxmlLoader.load();
        m_PausePane =  fxmlLoader.getRoot();

        if(fxmlLoader.getController() instanceof PowerUpController)
            ((PowerUpController)fxmlLoader.getController()).initialize(this);
        else if(fxmlLoader.getController() instanceof PauseMenuController)
            ((PauseMenuController)fxmlLoader.getController()).initialize(this);
        m_PausePane.setPrefHeight(m_Root.getHeight());
        m_PausePane.setPrefWidth(m_Root.getWidth());
        m_Root.getChildren().add(m_PausePane);
    }



    /**
     * This function pauses the game when the window focus lost.
     * @param window current window
     */
    public void setWindowFocus(Window window)
    {
        window.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean onHidden, Boolean onShown) {
                if(onHidden && m_GameTimer.getStatus() == Animation.Status.RUNNING) {
                    m_GameTimer.stop();
                }
            }
        });

    }

    /**
     * This function allows the user to close the pause menu or the power up menu.
     */
    public void backToScene(){
        m_ShowPauseMenu= false;
        m_Root.getChildren().remove(m_PausePane);
    }

    /**
     * This function allows others to get the current game settings and the checker.<br>
     * It is used for the index page's directing.
     * @return game setting and the checker
     */
    public Pane getM_Root(){return m_Root;}

    /**
     * This function reset the current gameStatus to be a new one since it may need updates.<br>
     * It is used by the some functions in the pause menu and power up menu pane.
     * @param gameStatus expected or newly created game status
     * */
    public void resetM_GameChecker(GameStatusModel gameStatus){
        m_Root.getChildren().remove(m_GameChecker.getM_Group());
        m_GameChecker = new GameStatusView(gameStatus) ;
        m_Root.getChildren().add(m_GameChecker.getM_Group());
    }

    /**
     * This function get the current game world setting.
     * @return current game world setting
     */
    public World getM_World() {
        return m_World;
    }

    /**
     * This function get the current game playing status.
     * @return current game playing status
     */
    public GameStatusView getM_GameChecker(){return m_GameChecker;}


}
