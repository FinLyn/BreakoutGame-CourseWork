package project.chenlin_17.Views;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import project.chenlin_17.Models.BallModel;
import project.chenlin_17.Models.PaddleModel;

import java.util.ArrayList;
import java.util.Random;
/**
 * This class is the builder for the game setting.<br>
 * It separates the scene building part from the original GameBoard class.
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */
public abstract class WorldBuilder {
    /**
    World contains all the game settings
     */
    protected World m_World;
    /**
    Random number for ball speed
     */
    protected Random m_Rnd;
    /**
     * Brick set for all levels
     */
    protected ArrayList<ArrayList<Brick>> m_BrickWallGroup;
    /**
     * Visible wall groups for all levels
     */
    protected ArrayList<Group> m_WallGroups;
    /**
     * Wall group for current level
     */
    protected Group m_Group;
    /**
     * Current level
     */
    protected int m_Level;

    /**
     * This constructor initializes the variables in the builder
     */
    WorldBuilder(){
        m_Group = new Group();
        m_World = new World();
        m_Rnd = new Random();
        m_WallGroups = new ArrayList<>();
        m_BrickWallGroup = new ArrayList<>();
        m_Level =0;
    }

    /**
     * This function is used for building background music.<br>
     * It needs to be implemented by its children, because different subclass has its own function of building the background music.
     */
    public abstract void buildSound();

    /**
     * This function is used for converting the palyer readable difficult level to int type.<br>
     * The converted level could be used for getting the specific level from the wall group in order to make the wall view.
     * @param difficultLevel string stands for difficult level
     */
    public void buildLevel(String difficultLevel){
        int level;
        if(difficultLevel.contains("Easy"))
            level =0;
        else if(difficultLevel.contains("Media"))
            level=1;
        else if (difficultLevel.contains("Difficult"))
            level=2;
        else
            level =4;
        m_Level =level;
        m_World.setM_Level(level);
    };
    /**
     * This function is used for building wall.<br>
     * It needs to be implemented by its children, because different subclass has its own function of building the wall.
     * @param rectangle expected wall drawing area
     * @param options player's choice for theme, not used in this builder type
     */
    protected abstract void buildWallGroups(Rectangle rectangle, int options);

    /**
     * This function is used for building background image.<br>
     * It needs to be implemented by its children, because different subclass has its own function of building the background image.
     * @param height expected background height
     * @param width expected background width
     */
    public abstract void buildBackground(double height, double width);

    /**
     * This function is used for setting the wall according to the player chosen difficult level.
     * @param rectangle the area used for showing bricks
     * @param options player's choice for theme, not used in this builder type
     */
    public void buildWall(Rectangle rectangle, int options){
        buildWallGroups(rectangle,options);
        m_World.setM_GameWall(m_BrickWallGroup.get(m_Level));
        m_World.setM_GameWalls(m_BrickWallGroup);
        m_Group.getChildren().add(m_WallGroups.get(m_Level));
    };

    /**
     * This function sets the current wall group according to player chosen difficult level
     */
    public void buildGroup(){
        m_World.setM_WallGroups(m_WallGroups);
        m_World.setM_Group(m_Group);
    };

    /**
     * This function returns the built world
     * @return world that have been built by the world builder
     */
    public World getWorld(){
        return m_World;
    };
    /**
     * This function is used for building ball.<br>
     * It needs to be implemented by its children, because different subclass has its own function of building the ball.
     * @param model expected ball model
     * @param color expected ball color
     */
    public abstract void buildBall(BallModel model, Color color);
    /**
     * This function is used for building paddle.<br>
     * It needs to be implemented by its children, because different subclass has its own function of building the paddle.
     * @param model expected paddle model
     * @param color expected paddle color
     * @param drawArea expected drawing area for the paddle
     * @param image expected image associated with the paddle
     */

    public abstract void buildPaddle(PaddleModel model, Color color, Image image, Rectangle drawArea);
    /**
     * This function is getting the builder type.<br>
     * It needs to be implemented by its children, because different subclass stands for different world builder type.
     * @return current world builder type
     */
    public abstract String getType();
}
