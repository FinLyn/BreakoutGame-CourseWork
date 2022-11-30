package project.chenlin_17.Models;


import project.chenlin_17.Views.World;

/**
 * This is the class defines the basic attributes of the game playing.<br>
 * It detects the change of the game board and set the gaming basics that
 * includes the life, remaining brick and the level.<br>
 * Since it is the observer of the world, it is not allowed to be set by the user, once it has been created.
 * @author Lin CHEN- modified
 * @version Version2.0
 * <br> update: Jan 3rd
 */

public class GameStatusModel {
    /**
     * Defined lose number
     */
    public static int LOSE=1;
    /**
     * Defined win number
     */
    public static int WIN=2;
    /**
     * Defined start number
     */
    public static int START=0;
    /**
     * Defined die number
     */
    public static int DIE=4;
    /**
     * Current game setting
     */
    private World m_World;
    /**
     * Game checking value, four numbers: LOSE WIN START DIE
     */
    private int m_Status;



    /**
     * This constructor sets the basic setting for the game process check boarder.
     * @param world current game setting
     */

    public GameStatusModel(World world){
        m_Status = world.getBallCount();
        m_World = world;
    }

    /**
     * This function updates the game status while playing.<br>
     * It contains three status, die win and lose.
     */
    public void update(){
        /*
        use up all 3 lives
         */
        if(m_World.getBallCount()==0)
            m_Status=DIE;
        /*
        all bricks broken
         */
        else if(m_World.getBrickCount()==0)
            m_Status=WIN;
        /*
        lose one round
         */
        else if(m_World.isBallLost())
        {
            m_Status=LOSE;
        }
        else
            m_Status=START;
    }

    /**
     * This function gets the player's current playing state.
     * @return current game status
     */
    public int getM_Status(){
        return m_Status;
    }

    /**
     * This function gets the player's current score.
     * @return player score
     */
    public int getM_Score(){return m_World.getM_Score();}
    /**
     * This function gets the remaining brick number in the game.
     * @return remaining brick number
     */
    public int getRemains(){return m_World.getBrickCount();}
    /**
     * This function gets the world's width.
     * @return world's width
     */
    public double getWidth(){return m_World.getWidth();}
    /**
     * This function gets the player's current game level.
     * @return game level
     */
    public int getLevel(){return m_World.getM_Level();}



}
