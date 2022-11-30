package project.chenlin_17.Views;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import project.chenlin_17.GameApplication;
import project.chenlin_17.Models.BallModel;
import project.chenlin_17.Models.GameStatusModel;

import java.util.ArrayList;

/**
 * This is the class for getting the information of the current game status.
 * <p>
 * The layout of the check board binds with the game status model, every
 * time there is an update on the game status model, it will be reflected on the stage using
 * this class.
 * </p>
 * @see GameStatusModel
 * @author Lin CHEN
 * @version Version2.0
 * <br> update: Jan 3rd
 */
public class GameStatusView {

    /**
     * Container for life status showing
     */
    private HBox m_Line;
    /**
     * Container for group
     */
    private HBox m_Group;
    /**
     * Container for life status
     */
    private ArrayList<ImageView> m_Life;
    /**
     * Text for labeling
     */
    private Text m_BrickCount,m_Score,m_Level;
    /**
     * Text for game status
     */
    private Text m_BrickText,m_ScoreText,m_LifeText,m_LevelText;
    /**
     * Defined font value for showing text and score
     */
    private final String DEF_FONT ="Verdana";
    /**
     * Defined game status model
     */
    private GameStatusModel m_Model;

    /**
     * This function initialize the basic setting of the viewing components.
     * @param model game status model contains the status of the playing
     */
    public GameStatusView(GameStatusModel model){
        m_Model = model;
        m_Group= new HBox();
        m_Life = new ArrayList<>();
        m_Line = new HBox();
        m_BrickText = buildText("Remaining Brick");
        m_ScoreText = buildText("Score");
        m_LifeText = buildText("Life");
        m_LevelText = buildText("Level");
        initialize();
    }

    /**
     * This function updates the game status while playing.<br>
     * It contains three status, die win and lose.
     */
    public void update(){
       m_Model.update();
       updateText();
       if(m_Model.getM_Status() == GameStatusModel.LOSE)
           m_Line.getChildren().remove(m_Life.remove(m_Life.toArray().length-1));
    }

    /**
     * This function sets the initial viewing for game status checker board.
     */
    private void initialize(){
        /*
        set max height for the board
         */
        m_Group.maxHeight(60);
        m_Group.setPrefHeight(60);
        m_Group.setPrefWidth(m_Model.getWidth());
        m_BrickCount =buildValue(m_Model.getRemains());
        m_Score = buildValue(m_Model.getM_Score());
        m_Level = buildValue(m_Model.getLevel());
        Image life = new Image (GameApplication.class.getResource("Pictures/Background/life.png").toExternalForm());
        for(int i= 0;i<m_Model.getM_Status();i++){
            m_Life.add(new ImageView(life));
        }
        m_Line.getChildren().addAll(m_Life);
        m_Group.getChildren().addAll(m_LevelText,m_Level,m_BrickText,m_BrickCount,m_ScoreText
                ,m_Score,m_LifeText,m_Line);
        m_Group.setAlignment(Pos.CENTER);
        m_Group.setSpacing(28);

    }

    /**
     * This is a helper function of building the text format
     * @param value text content
     * @return content with specified text format
     */
    private Text buildValue(int value) {
        Text text=  new Text(String.valueOf(value));
        text.setFont(Font.font(DEF_FONT, FontWeight.BOLD, 10));
        return text;
    }

    /**
     * This is a helper function to set text's viewing.
     * @param text expected showing text value
     * @return built text with expected format
     */
    private Text buildText(String text){
        Text textForm = new Text(text);
        textForm.setFont(Font.font(DEF_FONT, FontWeight.BOLD, 12));
        return textForm;
    }


    /**
     * This function gets the game status checkerboard viewing.
     * @return checkerboard viewing
     */
    public HBox getM_Group(){
        return m_Group;
    }

    /**
     * This function gets the game status that stores actual value for gaming.
     * @return game status
     */
    public GameStatusModel getM_Model(){
        return m_Model;
    }

    /**
     * This is a helper function for updating the score, level and remaining brick count.
     */
    private void updateText(){
      m_Level.setText(String.valueOf(m_Model.getLevel()));
      m_Score.setText(String.valueOf(m_Model.getM_Score()));
      m_BrickCount.setText(String.valueOf(m_Model.getRemains()));

    }

}
