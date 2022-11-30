package project.chenlin_17.Views;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import project.chenlin_17.tools.SoundEffect;

import java.util.ArrayList;
import java.util.Random;
/**
 * This class contains the model for all the game board settings. <br>
 * Its class name is world, it separates the scene part from its previous class "GameBoard".
 * Note:  Each world have levels set and the current level set<br>
 * It has defined ball setting, paddle setting unchanged during the playing process, also the background setting,life setting.
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */
public class World{
    /**
     * User chosen level set
     */
    private int m_InitialLevel;
    /**
     * Group for current world, including ball wall and paddle
     */
    private Group m_Group;

    /**
     * Size of the wall
     */
    private Rectangle m_Area;
    /**
     * Game life count
     */
    private int m_BallCount;
    /**
     * Game continuing status
     */
    private boolean m_BallLost;
    /**
     * Ball start point
     */
    private Point2D m_StartPoint;
    /**
     * Current level value
     */
    private int m_Level;
    /**
     * List for bricks of current wall setting
     */
    private ArrayList<Brick> m_Bricks;
    /**
     * List for wall settings of all the level
     */
    private ArrayList<ArrayList<Brick>> m_BrickGroups;
    /**
     * Remaining bricks
     */
    private int m_BrickCount;
    /**
     * Background image
     */
    private ImageView m_Background;
    /**
     * Current score value for the game
     */
    private int m_Score;
    /**
     * Current brick interacts with the ball
     */
    private Brick m_Brick;
    /**
     * Current world set for groups
     */
    private ArrayList<Group> m_WallGroups;
    /**
     * Sound for background music
     */
    private SoundEffect m_BackgroundSound;
    /**
     * Sound for brick when breaking
     */
    private SoundEffect m_Break;
    /**
     * Current ball set
     */
    private BallView m_Ball;
    /**
     * Current paddle set
     */
    private PaddleView m_PlayerView;
    /**
     * Sound play when the level goes up
     */
    private SoundEffect m_LevelUp;
    /**
     * Sound play when the ball hits the wall
     */
    private SoundEffect m_WallImpact;
    /**
     * Adjust value for height viewing
     */
    private final  double HEIGHT_RATE = 0.8;
    /**
     * Adjust value for width viewing
     */
    private  final  double WIDTH_RATE = 2;


    /**
     * This constructor sets the basic setting for the ball,wall and score.
     * It also initializes the sound for the breaking brick.
     */
    World(){

        m_Level = 0;
        m_BallCount = 3;
        m_BallLost = false;

        m_Score=0;
        m_Break = SoundEffect.BROKEN;
        m_LevelUp =SoundEffect.LEVELUP;
        m_WallImpact = SoundEffect.WALLIMPACT;

    }

    /**
     * This function moves the ball and player.
     */
    public void move(){
        m_Ball.move();
        m_PlayerView.move();
    }

    /**
     * This function simulates the ball and paddle interaction.
     */
    public void findImpacts(){
        if(m_PlayerView.impact(m_Ball)){
            m_Ball.reverseY();
        }
        else if(impactWall()){
            /*
            For efficiency reverse is done into method impactWall because for every brick program checks for horizontal and vertical impacts
             */
            m_Break.click();
            m_BrickCount--;
            m_Score+=m_Brick.getM_FullStrength()*(m_Level+1);
        }
        else if(impactBorder()) {
            m_WallImpact.click();
            m_Ball.reverseX();
        }
        else if(m_Ball.getPosition().getY() < m_Area.getY()){
            m_WallImpact.click();
            m_Ball.reverseY();
        }
        else if(m_Ball.getPosition().getY() > m_Area.getY() + m_Area.getHeight()){
            m_BallCount--;
            m_BallLost = true;
        }
    }
  /**
   * This function reflects the ball's impact on the wall.
   * @return whether the ball had reached the wall
   */
    public boolean impactWall(){
        for(Brick b : m_Bricks){
            m_Brick = b;
            switch(b.findImpact(m_Ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    m_Ball.reverseY();
                    /*
                     * Original source code:
                     * return b.setImpact(m_Ball.m_Down, Brick.Crack.UP);
                     */
                    return b.setImpact(m_Ball.getM_Down(), Crack.UP);
                case Brick.DOWN_IMPACT:
                    m_Ball.reverseY();
                    /*
                     * return b.setImpact(m_Ball.m_Down, Brick.Crack.DOWN);
                     */
                    return b.setImpact(m_Ball.getM_Up(),Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    m_Ball.reverseX();
                    /*
                     * Original source code:
                     * return b.setImpact(m_Ball.m_Down, Brick.Crack.RIGHT);
                     */
                    return b.setImpact(m_Ball.getM_Right(),Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    m_Ball.reverseX();
                    /*
                     * Original source code:
                     * return b.setImpact(m_Ball.m_Down, Brick.Crack.LEFT);
                     */
                    return b.setImpact(m_Ball.getM_Left(),Crack.LEFT);

            }
        }

        return false;
    }

    /**
     * This function reflects boarder's impact on the ball.
     * @return whether the ball has been reflected
     */
    public boolean impactBorder(){
        Point2D p = m_Ball.getPosition();
        return ((p.getX() < m_Area.getX()) ||(p.getX() > (m_Area.getX() + m_Area.getWidth())));
    }

    /**
     * This function gets the gamer's remaining life.
     * @return gamer's remaining life
     */
    public int getBallCount(){
        return m_BallCount;
    }

    /**
     * This function gets whether the player has lost this round.
     * @return the player's game status for this round
     */
    public boolean isBallLost(){
        return m_BallLost;
    }

    /**
     * This function gets the ball's x speed.
     * @return ball's x speed
     */
    public int getM_BallSpeedX(){return m_Ball.getSpeedX();}
    /**
     * This function gets the ball's y speed.
     * @return ball's y speed
     */
    public int getM_BallSpeedY(){return m_Ball.getSpeedY();}


    /**
     * This function gets the current wall status.
     * @return current wall status
     */
    public ArrayList<Brick> getBricks() {
        return m_Bricks;
    }

    /**
     * This function sets the background music.
     * @param soundEffect received background music
     */
    public void setM_BackgroundSound(SoundEffect soundEffect){m_BackgroundSound =soundEffect;}

    /**
     * This function sets the ball.
     * @param ballView received game ball
     */
    public void setM_Ball(BallView  ballView){m_Ball = ballView;}
    /**
     * This function sets the paddle
     * @param paddle received game paddle
     */


    /**
     * This function gets the current world group.
     * @return current world group
     */
    public Group getM_Group() {
        return m_Group;
    }

    /**
     * This function sets the current world group.
     * @param group current group defined by the player
     */
    public void setM_Group(Group group) {
        m_Group = group;
    }

    /**
     * This function gets the bricks for the wall.
     * @param bricks current game wall
     */
    public void setM_GameWall(ArrayList<Brick> bricks) {
        m_Bricks = bricks;
        m_BrickCount = m_Bricks.toArray().length;
    }

    /**
     * This function sets the current game level.
     * @param level player chosen game level
     */
    public void setM_Level(int level){
        m_InitialLevel = level;
        m_Level = level;
    }

    /**
     * This function sets wall area size
     * @param drawArea wall area size
     */
    public void setM_DrawArea(Rectangle drawArea) {
        m_Area = drawArea;
    }

    /**
     * This function sets the start point of the ball.
     * @param startPoint ball's start point
     */
    public void setM_BallPoint(Point2D startPoint){
        m_StartPoint = startPoint;
    }

    /**
     * This function sets the background image of the game.
     * @param imageview background image
     */
    public void setM_Background(ImageView imageview) {m_Background = imageview;}

    /**
     * This function gets the remaining brick count.
     * @return remaining brick count
     */
    public int getBrickCount() {
        return m_BrickCount;
    }

    /**
     * This function gets whether the world goes to the last level.
     * @return judgement for whether gets the last level
     */
    public boolean hasLevel(){
        return m_Level + 1 <m_BrickGroups.toArray().length;
    }

    /**
     * This function gets the score of the current game status.
     * @return score of the player
     */
    public int getM_Score(){return m_Score;}

    /**
     * This function resets the ball and player to the initial state.
     */
    public void ballPositionReset(){
        m_PlayerView.moveTo(m_StartPoint);
        m_Ball.moveTo(m_StartPoint);
        m_Ball.resetSpeed();
        m_BallLost = false;
    }

    /**
     * This function recovers the current wall set.
     */
    public void wallReset(){
        for(Brick b : m_Bricks)
            b.repair();
        m_BrickCount = m_Bricks.toArray().length;
    }

    /**
     * This function resets the current game setting.
     */
    public void gameReset(){
        ballPositionReset();
        wallReset();

        m_BallCount = 3;
        m_BallLost = false;
        m_Score=0;
    }

    /**
     * This function allows the player to get to the next level if his life doesn't end.
     */
    public void nextLevel(){
        m_LevelUp.click();
        ballPositionReset();
        wallReset();
        m_Group.getChildren().remove(m_WallGroups.get(m_Level));
        m_Level++;
        m_Group.getChildren().add(m_WallGroups.get(m_Level));
        setM_GameWall(m_BrickGroups.get(m_Level));

    }

    /**
     * This function allows the player to go to the next level of the world.
     */
    public void skipLevel() {
        m_LevelUp.click();
        wallReset();
        m_Group.getChildren().remove(m_WallGroups.get(m_Level));
        m_Level++;
        m_Group.getChildren().add(m_WallGroups.get(m_Level));
        setM_GameWall(m_BrickGroups.get(m_Level));
    }

    /**
     * This function sets the wall.
     * @param wallGroups current wall for game playing
     */
    public void setM_WallGroups(ArrayList<Group> wallGroups) {
        m_WallGroups = wallGroups;
    }

    /**
     * This function sets the wall setting of all the levels
     * @param brickWallGroup current game wall settings for all levels
     */
    public void setM_GameWalls(ArrayList<ArrayList<Brick>> brickWallGroup) {
        m_BrickGroups = brickWallGroup;
    }

    /**
     * This function allows the paddle to move to right.
     */
    public void movePlayerRight(){
        m_PlayerView.moveRight();
    }

    /**
     * This function allows the paddle to move to left.
     */
    public void movePlayerLeft() {
        m_PlayerView.moveLeft();
    }

    /**
     * This function allows the paddle to stop.
     */
    public void playStop(){
        m_PlayerView.stop();
    }

    /**
     * This function resets the player's life
     */
    public void resetBallCount() {
        m_BallCount =3;
    }
    /**
     * This function gets the ball.
     * @return ball
     */
    public BallView getM_Ball(){return m_Ball;}

    /**
     * This function gets the paddle.
     * @return paddle
     */
    public PaddleView getM_Player() {
        return m_PlayerView;
    }

    /**
     * This function gets the player's initial choice for the world setting.
     * @return player chosen world setting
     */
    public int getInitialLevel(){
        return m_InitialLevel;
    }

    /**
     * This function gets the current game setting.
     * @return current game setting
     */
    public ArrayList<Group> getM_WallGroups(){return m_WallGroups;}
    /**
     * This function gets the current game background music.
     * @return current background music
     */
    public SoundEffect getM_BackgroundSound(){return m_BackgroundSound;}

    /**
     * This function gets the game object's current position.<br>
     * The factory pattern is used here.
     * @param object game setting objects
     * @return game setting object's position value
     */
    public Point2D getPosition(Object object){
        if(object instanceof BallView)
            return ((BallView) object).getPosition();
        if(object instanceof PaddleView)
            return(((PaddleView) object).getPosition());
        return null;
    }

    /**
     * This function allows to set the ball's x speed.
     * @param speed expected x speed
     */
    public void setBallSpeedX(int speed){
        m_Ball.setXSpeed(speed);
    }

    /**
     * This function allows to set ball's y speed.
     * @param speed expected y speed
     */
    public void setBallSpeedY(int speed){m_Ball.setYSpeed(speed);}

    /**
     * This function gets the ball's x speed.
     * @return ball's x speed
     */
    public int getBallSpeedX(){
        return m_Ball.getSpeedX();
    }

    /**
     * This function gets the ball's y speed.
     * @return ball's y speed
     */
    public int getBallSpeedY(){
        return m_Ball.getSpeedY();
    }

    /**
     * This function gets the current game level.
     * @return current game level
     */
    public int getM_Level() {
        return m_Level+1;
    }

    /**
     * This function sets the paddle into the world setting.
     * @param paddleView expected paddle
     */
    public void setM_PlayerView(PaddleView paddleView) {
        m_PlayerView = paddleView;
    }

    /**
     * This function gets the background view.
     * @return the background view of the game board
     */
    public ImageView getM_Background(){return m_Background;}

    /**
     * This function gets the width of the current stage.
     * @return current stage's width
     */
    public double getWidth(){return m_StartPoint.getX() * WIDTH_RATE;}
    /**
     * This function gets the height of the current stage.
     * @return current stage's height
     */
    public double getHeight(){return m_StartPoint.getY()/HEIGHT_RATE;}

    /**
     * This function sets the world to the one that the user choose.<br>
     * It also makes all things start from stretch, such as the ball count and ball position.
     */

    public void resetInitial(){
        gameReset();
        m_Group.getChildren().remove(m_WallGroups.get(m_Level));
        m_Group.getChildren().add(m_WallGroups.get(m_InitialLevel));
        setM_Level(m_InitialLevel);
        setM_GameWall(m_BrickGroups.get(m_Level));
    }


}
