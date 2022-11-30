package project.chenlin_17.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import project.chenlin_17.DirectorApp;
import project.chenlin_17.tools.SoundEffect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
/**
 * This class is the controller of the highest rank page
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 28th
 */
public class HighestRankController {
    /**
     * Container for coming m_Back to the start page
     */
    @FXML
    private ImageView m_Back;
    /**
    Container for showing rank
     */
    @FXML
    private GridPane m_GridPane;
    /**
    Score view for the player's highest score
     */
    @FXML
    private Text m_UserScore;

    /**
    Store results gotten from the rank file
     */
    private HashMap<String, String> m_HashMap;
    /**
    Sound for button clicking
     */
    private SoundEffect m_SoundEffect;
    /**
     * Defined searching for top 5 players
     */
    private final int RANK = 5;
    /**
     * Adjustable value for setting close image's view property
     */
    private final int BACK_LEN = 40;
    /**
     * Adjustable value for setting close image's position
     */
    private final int BACK_POS =4;

    /**
     * This function allows the player to come m_Back to the index screen.
     */
    public void OnClickedBack() {
        m_SoundEffect.click();
        DirectorApp.getInstance().showStartScreen();
    }

    /**
     * This function makes the view of close image looks bigger when the mouse is hovering on it.
     */
    public void OnHoveredBack() {
        m_Back.setFitWidth(BACK_LEN);
        m_Back.setFitHeight(BACK_LEN);
        m_Back.setX(m_Back.getX()-BACK_POS);
        m_Back.setY(m_Back.getY()-BACK_POS);
    }

    /**
     * This function makes the view of close image looks normal when the mouse is removed.
     */
    public void OffHoveredBack() {
        m_Back.setFitWidth(BACK_LEN);
        m_Back.setFitHeight(BACK_LEN);
        m_Back.setX(m_Back.getX()+BACK_POS);
        m_Back.setY(m_Back.getY()+BACK_POS);
    }

    /**
     * This function sets the initial value used for the rank page.<br>
     * It contains the initialization of the button clicking sound. <br>
     * It contains the initialization of the rankings.<br>
     * It also contains the getting of the player's highest recorded score.
     */
    public void initialize() {
        ObservableList<Node> nodeList = m_GridPane.getChildren();
        HBox[] labels =initializeHBox(nodeList);
        /*
        play when the button is clicked
         */
        m_SoundEffect = SoundEffect.BUTTONCLICK;
        /*
        initialize hashMap for storing the file message
         */
        m_HashMap = new HashMap<>();

        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("src/main/resources/project/chenlin_17/ScoreRecords/record.txt"));
            for (int i = 0; i < RANK; i++) {
                line = bufferedReader.readLine();

                if (line == null) {
                    break;
                }
                String[] record = line.split(" ");
                /*
                set rank number
                 */
                Label rank = (Label) labels[i].getChildren().get(0);
                rank.setText(String.valueOf(i+1));
                /*
                set player name and corresponding score
                 */
                setRank(labels[i],record[0],record[1]);

            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        set the highest score for the current user
         */
        getUserScore(DirectorApp.getInstance().getM_UserName());
    }

    /**
     * This is a helper function that binds the button's width showing property with the page width.
     * @param nodeList The HBox list contained showing items
     * @return the initialized hbox list for ranking items
     */
    private HBox[] initializeHBox(ObservableList<Node> nodeList) {
        HBox[] hbox = new HBox[nodeList.size()];
        for(int i = 1;i<nodeList.size();i++){
            hbox[i-1] = (HBox) nodeList.get(i);
        }
        return hbox;
    }

    /**
     * This function is used to set the player's name and corresponding score in the rank board.
     * @param labelContainer stands for the container of showing the player's record
     * @param name sets the player's name
     * @param score sets the player's score
     */
    private void setRank(HBox labelContainer, String name, String score) {
        Label nameLabel =(Label) labelContainer.getChildren().get(1);
        Label scoreLabel =(Label) labelContainer.getChildren().get(2);
        nameLabel.setText(name);
        scoreLabel.setText(score);
        setHashMap(name,score);
    }

    /**
     * This function contains the highest score for each player.
     * The first found mean
     * @param name stands for each searched players' name
     * @param score stands for each score read from the record file
     */
    private void setHashMap(String name, String score) {
        /*
        If is not in the list, add it to the list
         */
        if(!m_HashMap.containsKey(name))
            m_HashMap.put(name,score);
        else{
            /*
                if in the list but the score value is larger than the stored one,update the map
            */
            if(Integer.parseInt(m_HashMap.get(name))<Integer.parseInt(score))
                m_HashMap.replace(name, score);
        }
    }

    /**
     * This function sets the current player's highest score on the board.<br>
     * If the player doesn't have any stored record, 0 will be shown.
     * @param userName player's name
     */
    private void getUserScore(String userName){
        m_UserScore.setText(m_HashMap.getOrDefault(userName, "0"));
    }

}
