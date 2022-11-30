package project.chenlin_17.tools;

import javafx.scene.image.Image;
import project.chenlin_17.GameApplication;

import java.util.ArrayList;
/**
 * This is the helper class of building corresponding brick's image views.
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */

/**
 * Only defined constructor of the brick building is allowed.<br>
 * Each constructor has its set value for brick settings.
 */
public enum BrickFactory {
    /**
     * Defined clay set for space theme
     */
    Space("Pictures/Bricks/spaceClay.png","Pictures/Bricks/spaceCement.png","Pictures/Bricks/spaceSteel.png"),
    /**
     * Defined clay set for city theme
     */
    City("Pictures/Bricks/cityClay.png","Pictures/Bricks/cityCement.png","Pictures/Bricks/citySteel.png"),
    /**
     * Defined clay set for soccer theme
     */
    Soccer("Pictures/Bricks/socClay.png","Pictures/Bricks/socCement.png","Pictures/Bricks/socSteel.png");

    /**
     * Current imageView list
     */
    private ArrayList<Image> m_Image;

    /**
     * Constructor for image brick settings according to the theme set.
     * @param clay clay image's file path
     * @param cement cement image's file path
     * @param steel steel image's file path
     */
    BrickFactory(String clay, String cement,String steel){
        m_Image = new ArrayList<>();
        m_Image.add(new Image(GameApplication.class.getResource(clay).toExternalForm()));
        m_Image.add(new Image(GameApplication.class.getResource(cement).toExternalForm()));
        m_Image.add(new Image(GameApplication.class.getResource(steel).toExternalForm()));
        m_Image.add(new Image(GameApplication.class.getResource("Pictures/Bricks/additionalBrick.png").toExternalForm()));
    }

    /**
     * This function returns theme brick image sets for the chosen theme.
     * @return theme brick image sets
     */
    public ArrayList<Image> getM_Image(){
        return m_Image;
    }

    /**
     * This function returns the specific clay brick image for the chosen theme.
     * @return theme brick clay image
     */
    public Image getClay(){
        return m_Image.get(0);
    }
    /**
     * This function returns the specific cement brick image for the chosen theme.
     * @return theme brick cement image
     */
    public Image getCement(){
        return m_Image.get(1);
    }
    /**
     * This function returns the specific steel brick image for the chosen theme.
     * @return theme brick steel image
     */
    public Image getSteel(){
        return m_Image.get(2);
    }
    /**
     * This function returns the specific additional brick image for the chosen theme.<br>
     * The additional image is used for making more beautiful game scene.
     * @return theme brick additional image
     */
    public Image getAdditional(){return m_Image.get(3); }
}
