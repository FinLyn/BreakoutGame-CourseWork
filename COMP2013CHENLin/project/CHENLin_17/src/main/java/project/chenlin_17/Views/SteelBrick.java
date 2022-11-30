package project.chenlin_17.Views;


import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.geometry.Point2D;
import java.util.Random;
import javafx.scene.paint.Color;
import project.chenlin_17.Views.Brick;
/**
 * This steel brick class is the concrete class extends from the brick class.<br>
 * Steel brick type would break according to a generated random value.
 * Note:  Its class name is steel brick, which makes its previous name "Brick3" readable.<br>
 * It contains changes from the awt to javafx.<br>
 * It contains changes of class variables according to Bob's code convention.
 * @see Brick
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */
 class SteelBrick extends Brick {

     /**
      * Defined inner color
      */
     private static final Color DEF_INNER = Color.rgb(203, 203, 201);
     /**
      * Defined boarder color
      */
    private static final Color DEF_BORDER = Color.BLACK;
     /**
      * Defined brick's max strength
      */
    private static final int STEEL_STRENGTH = 1;
     /**
      * Defined brick property
      */
    private static final double STEEL_PROBABILITY = 0.4;
     /**
      * Defined value for this random int
      */
    private Random m_Rnd;
     /**
      * Defined viewing group
      */
    private Group m_Group;

    /**
     * This constructor builds the steel brick(with defined boarder color and inner color) and adds its view to the game board.
     * Modified:  Point -> Point2D <br>
     * Dimension -> Dimension2D
     * @param point brick point
     * @param size brick size
     * @param group viewing group for bricks
     *
     * @see Brick#Brick(Point2D, Dimension2D, Color, Color, int)
     */
    public SteelBrick(Point2D point, Dimension2D size, Group group){
        super(point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        m_Rnd = new Random();
        m_Group = group;
        m_Group.getChildren().add(super.getM_BrickFace());
    }

     /**
      * This constructor builds the image steel brick and adds its view to the game board.
      * @param point brick point
      * @param size brick size
      * @param image brick image
      * @param group brick viewing group
      * @see Brick#Brick(Point2D, Dimension2D, Image, int)
      */
    public SteelBrick(Point2D point, Dimension2D size, Image image, Group group){
        super(point,size,image,STEEL_STRENGTH);
        m_Group=group;
        m_Group.getChildren().add(super.getM_ImageBrickFace());
    }

    /**
     * This function returns the built brick face.
     *
     * Modified:  Point->Point2D
     * Dimension -> Dimension2D
     * @param pos brick position
     * @param size brick size
     * @return built brick face with position and size
     * @see Brick#makeBrickFace(Point2D, Dimension2D)
     */
    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        /*
         * Original source code:
         * return new Rectangle(pos,size);
         */
        Rectangle brickFace = new Rectangle(pos.getX(),pos.getY(),size.getWidth(),size.getHeight());
        return brickFace;
    }


     /**
      * This function helps set the brick status after being hit by the ball
      * Note:  change remove brick after the impact judgement
      * @param point ball's point
      * @param dir ball's direction on the brick
      * @return the bricks' interaction with the ball
      * @see Brick#setImpact(Point2D, int)
      */

    @Override
    public boolean setImpact(Point2D point,int dir){
        /*
         * remove brick after the impact judgement
         */
        super.impact();
        if(super.isBroken())
        {
            if(super.getM_ImageBrickFace()!=null)
               m_Group.getChildren().remove(super.getM_ImageBrickFace());
           m_Group.getChildren().remove(super.getM_BrickFace());

            return true;
        }
        return super.isBroken();
    }

     /**
      * This function sets the steel bricks' broken condition.<br>
      * It only breaks randomly using the possibility.
      * @see Brick#impact()
      */
    @Override
    public void impact(){
        if(m_Rnd.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }

     /**
      * This function helps repair the brick for replaying purpose by re adding the ball face or the image ball face if it used to have one.<br>
      * It resets the broken status to false and allowable strokes to 3.
      * @see Brick#repair()
      */
     @Override
     public void repair() {
         if(super.isBroken()){
             /*
             set broken status to false and allowable strokes to 3
              */
             setM_Broken(false);
             setM_Strength();
             /*
             remove brick face
              */
             m_Group.getChildren().add(super.getM_BrickFace());
             if(super.getM_ImageBrickFace()!=null)
                 m_Group.getChildren().add(super.getM_ImageBrickFace());
         }
     }

}
