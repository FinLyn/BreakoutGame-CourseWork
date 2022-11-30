package project.chenlin_17.tools;

import javafx.scene.image.Image;
import project.chenlin_17.GameApplication;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * This is the helper class of the worldBuilders.<br>
 * It closely connects with the scene option page.
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */
/**
 * Each constructor has its set value for brickSets,background music,background image and ball image.<br>
 * The enum name stands for the specific theme choice.
 */
public enum SceneOptions {
    /**
     * Default game setting for space theme
     */
    Space(new String[] {"SceneWorld/spaceEasy.txt","SceneWorld/spaceMedia.txt","SceneWorld/spaceDifficult.txt"},
            SoundEffect.SPACE,
            "Pictures/Space/spaceBackground.png",
            "Pictures/Space/spaceBallLeft.png"),
    /**
     * Default game setting for city theme
     */
    City(new String[]{"SceneWorld/cityEasy.txt","SceneWorld/cityMedia.txt","SceneWorld/cityDifficult.txt"},
            SoundEffect.CITY,
            "Pictures/City/cityBackground.png",
            "Pictures/City/cityBall.png"
            ),
    /**
     * Default game setting for soccer theme
     */
    Soccer(new String[]{"SceneWorld/soccerEasy.txt","SceneWorld/soccerMedia.txt","SceneWorld/soccerDifficult.txt"},
            SoundEffect.CITY,
            "Pictures/Soccer/soccerBackground.png",
            "Pictures/Soccer/soccerBall1.png"
            );
    /**
     * Sound for background music
     */
    private SoundEffect m_SoundEffect;
    /**
     * File path for background image
     */
    private String m_BackgroundPath;
    /**
     * File path for ball image
     */
    private String m_BallPath;
    /**
     * File path for brick image set
     */
    private ArrayList<String> m_WorldFilePath;

    /**
     * This constructor sets the default brick image, music, background image and ball image for the specific theme.
     * @param worldFilePath file path for brick image set
     * @param soundEffect file path for background music
     * @param backgroundPath file path for background image
     * @param ballPath file path for ball image
     */
    SceneOptions(String[] worldFilePath, SoundEffect soundEffect, String backgroundPath, String ballPath){
        m_WorldFilePath = new ArrayList<>(Arrays.asList(worldFilePath));
        m_SoundEffect = soundEffect;
        m_BackgroundPath = backgroundPath;
        m_BallPath = ballPath;
    }

    /**
     * This function helps the basic world building for wall.
     * @return specific brick image set file path of the specific theme topic
     */
    public ArrayList<String> GetM_WorldFilePath(){
        return m_WorldFilePath;
    }
    /**
     * This function helps the basic world building for background music.
     * @return specific background music associated with the specific theme topic
     */
    public SoundEffect GetM_SoundFilePath(){
        return  m_SoundEffect;
    }
    /**
     * This function helps the basic world building for background image.
     * @return specific background image associated with the specific theme topic
     */
    public String GetM_BackgroundPath(){
        return m_BackgroundPath;
    }

    /**
     * This function helps the basic world building for ball.
     * @return specific ball image set associated with the specific theme topic
     */
    public Image getBallImage(){
        Image ballImage= new Image(GameApplication.class.getResource(m_BallPath).toExternalForm());
        return ballImage;
    }

    }


