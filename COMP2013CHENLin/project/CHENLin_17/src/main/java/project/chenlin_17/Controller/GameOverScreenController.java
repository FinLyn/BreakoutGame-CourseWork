package project.chenlin_17.Controller;

import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import project.chenlin_17.GameApplication;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.Models.GameStatusModel;
import project.chenlin_17.tools.Record;
import project.chenlin_17.tools.SoundEffect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class is the controller of the game over page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 28th
 */
public class GameOverScreenController {
    /**
     * The draw pane for game over page
     */
    @FXML
    private Pane m_Background;
    /**
     * Container for scene components
     */
    @FXML
    private VBox m_ShowBoard;
    /**
     * Container for showing page title
     */
    @FXML
    private VBox m_LabelContainer;
    /**
     * Record item groups
     */
    @FXML
    private HBox m_MyScore;
    /**
     * The pane for the top five game winner
     */
    @FXML
    private GridPane m_RankBoard;
    /**
     * Container for arrow groups that point to the players' score
     */
    @FXML
    private HBox m_MyTip;
    /**
     * Container for buttons that allow change page
     */
    @FXML
    private HBox m_HboxButton;
    /**
     * Image container for arrow that points to the player's score
     */
    @FXML
    private ImageView m_Arrow;

    /**
     * Image container for gameStatus
     */
    @FXML
    private ImageView m_Heading;
    /**
     * List for record item groups in order to write reusable code
     */
    private HBox[] m_Label;
    /**
     * Stores records that read from the file
     */
    private ArrayList<String> m_Records;
    /**
     * The current player's record
     */
    private Record m_MyRecord;
    /**
     * Transition for myScore's showing animation
     */
    private TranslateTransition m_Transition;
    /**
     * Sound for button clicking
     */
    private SoundEffect m_ButtonClicked;
    /**
     * Top 5 best players
     */
    private static final int RANK =5;

    /**
     * This function is intended to initialize the game over board.<br>
     * It contains the node adjustment to the whole pane. <br>
     * It contains the reading and setting of the Top 5 player records from the file.<br>
     * It contains the initialization of the button click music.
     */
    public void initialize() {

        /*
         add sound for button clicking
         */
        m_ButtonClicked = SoundEffect.BUTTONCLICK;
        /*
         adjust the scene components to make all of them resizable
         */
        screenJustify();

        m_Records = new ArrayList<>();
        BufferedReader file;
        ObservableList<Node> nodeList = m_RankBoard.getChildren();
        m_Label = new HBox[nodeList.size()+1];
        for(int i = 1;i< nodeList.size();i++){
            m_Label[i-1] = (HBox) nodeList.get(i);
        }
        m_Label[nodeList.size()]= m_MyScore;
        String line;
        try {
            file = new BufferedReader(new FileReader("src/main/resources/project/chenlin_17/ScoreRecords/record.txt"));
            for (int i = 0; i < RANK; i++) {
                line = file.readLine();
                if (line==null) {
                    break;
                }
                m_Records.add(line);
            }
            file.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    /**
     * This function allows the arrow to directly points to the player's score by making the calculation.<br>
     * If the player gets a very good mark and be in the Top 5's list, the arrow will point to the player's position in the newly updated rank board.<br>
     * If the player's current score is not in the Top 5's list, then the arrow would point to the place under the rank board.
     * @param rank stands for the player's rank
     */
    private void setLocation(int rank){
        double startHeight;
        /*
         the start of the rank table
         */
        double tableStartHeight = m_LabelContainer.getHeight()+20;
        /*
         calculate the rank's corresponding position
         */
        double rankBoardSize = m_RankBoard.getHeight();
        double tableLineHeight = rankBoardSize/(RANK+1);

        if(rank<(RANK+1))
            startHeight =  tableStartHeight + tableLineHeight*rank;
        else
            startHeight =tableStartHeight + rankBoardSize + 25;
        /*
        set the arrow to the player's score
         */
        AnchorPane.setTopAnchor(m_MyTip, startHeight);
    }

    /**
     * This function is aimed at making twinkling animation for the record.
     * @param node stands for the twinkling one
     */
    private void setTransition(Node node){
            m_Transition = new TranslateTransition();
            m_Transition.setByX(20);
            /*
            set twinkle time to be 500 minutes
             */
            m_Transition.setDuration(Duration.millis(500));
            m_Transition.setCycleCount(TranslateTransition.INDEFINITE);
            m_Transition.setAutoReverse(true);
            m_Transition.setNode(node);
            m_Transition.play();
    }

    /**
     * This function is used for accepting the user's game status passed from the finished game playing scene
     * and is used for initializing the current game board.
     * @param i stands for the player's game status, whether die or win
     * @param record stands for getting the player's record after the game have been finished
     */
    public void setGameStatus(int i,Record record){
        m_MyRecord = record;
        /*
          set the showing title according the game status
         */
        if(i== GameStatusModel.DIE){
            m_Heading.setImage(loader("Pictures/Background/gameLose.png"));
        }
        if(i == GameStatusModel.WIN){
            m_Heading.setImage(loader("Pictures/Background/gameWin.png"));
        }
        /*
         set the player's rank for current round
         */
        scoreCalculatoin(record);
        /*
        let the arrow pointing animation play
         */
        setTransition(m_MyTip);
    }

    /**
     * This function make the calculation for the user's rank after the finished round.<br>
     * If the user plays well, his current record will be inserted in the Top 5 rank list. <br>
     * If the user's score is not high enough to be in the rank board,
     * his score would be shown below the rank board and rank board will not be updated.
     * @param myRecord receives the users
     */
    private void scoreCalculatoin(Record myRecord){
        int myRank = RANK+1;
        /*
        compare the user's score with the stored one
         */
        for(int i =0;i<RANK;i++){
            /*
            if the rank board is empty or doesn't contain 5 records
             */
            if(m_Records.get(i) == null){
                myRank =i;
                showRank(m_Records.get(i),m_Label[i]);
                break;
            }
            else {
                String record = m_Records.get(i);
                String[] recordsSet= record.split(" ");
                int score = Integer.parseInt(recordsSet[1]);
                /*
                if the player's current score is high enough to be inserted
                it would be inserted in the right place
                 */
                if (score <= myRecord.getScore() && myRank > RANK) {
                        myRank = i;
                        m_Records.add(i,myRecord.toFormattedString());
                    }
                /*
                update the scene for showing the user's current score
                 */
                showRank(m_Records.get(i),m_Label[i]);
                }
            }
            /*
            if the player's score is not high enough, show it under the rank board
             */
            if(myRank == (RANK+1))
                showRank(myRecord.toFormattedString(),m_MyScore);
            /*
            set the arrow animation position according to the current score
             */
            int finalMyRank = myRank+1;
         /*
         * Frame for animation
          */
        KeyFrame m_KeyFrame = new KeyFrame(Duration.ONE.multiply(10), e -> setLocation(finalMyRank));
            Timeline m_GameTimer = new Timeline();
            m_GameTimer.setCycleCount(Timeline.INDEFINITE);
            m_GameTimer.getKeyFrames().add(m_KeyFrame);
            m_GameTimer.play();

    }

    /**
     * This function shows the text view of the player's recording by getting its rank.
     * @param record contains the user's name score and played time
     * @param hbox container for showing the score
     */
    private void showRank(String record, HBox hbox) {
        Label name = (Label)hbox.getChildren().get(0);
        Label score= (Label) hbox.getChildren().get(1);
        Label time =(Label) hbox.getChildren().get(2);

        String[] element= record.split(" ");
        name.setText(element[0]);
        score.setText(element[1]);
        time.setText(element[2]);

        hbox.setVisible(true);

    }

    /**
     * This function is intended to set the layout for scene components and achieves the goal of resizable.
     */
    private void screenJustify() {
        m_MyTip.prefHeightProperty().bind(m_Background.heightProperty());
        m_LabelContainer.prefHeightProperty().bind(m_Background.heightProperty().multiply(0.1));
        m_RankBoard.prefHeightProperty().bind(m_Background.heightProperty().multiply(0.6));
        m_HboxButton.spacingProperty().bind(m_Background.widthProperty().multiply(0.3));
    }

    /**
     * This function allows other class to get the pane and components for this page.
     * It's useful when the WorldController
     * knows the game has been ended and needs to show this page.
     * @return the draw pane for this page
     */
    public Pane getSceneRoot(){
        return m_Background;
    }

    /**
     * This function allows the player to save the record when they are satisfied.
     * If he is not satisfied the record will not be saved in the permanent file.
     * @param mouseEvent catch the mouse click event done by the user
     */
    public void SaveThisRecord(MouseEvent mouseEvent) {
        m_Transition.pause();
        m_Transition = null;
        /*
         add sound for button clicking
         */
        m_ButtonClicked.click();
        try{
            m_MyRecord.updateRecord();
        }catch (Exception e){
            /*
            print out error message
             */
            System.out.println("Update score fails");
        }
        try{
            DirectorApp.getInstance().showStartScreen();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This function allows the player to go back to the main menu when he finishes playing and see his scores.
     * @param mouseEvent catch the mouse click event done by the user
     */
    public void BackToMenu(MouseEvent mouseEvent){
        /*
         add sound when button is clicked
         */
        m_ButtonClicked.click();
        /*
         try to go back to the index page
         */
        try{
            DirectorApp.getInstance().showStartScreen();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This is a helper function to load the files
     * @param fileName load file's name
     * @return Image built with specified file name
     */
    private Image loader(String fileName){
        if(GameApplication.class.getResource(fileName)== null)
            throw new NullPointerException("File null");
        else return new Image(GameApplication.class.getResource(fileName).toExternalForm());

    }
}
