package project.chenlin_17.Views;



import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import project.chenlin_17.Views.Brick;
/**
 * This clay brick class is the concrete class extends from the brick class.<br>
 * Note:  Its class name is clay brick, which makes its previous name "Brick2" readable.<br>
 * It contains changes from the awt to javafx.<br>
 * It contains changes of class variables according to Bob's code convention.
 * @see Brick
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 */

class ClayBrick extends Brick {

    /**
     * Defined clay brick inner color
     */
    private static final Color DEF_INNER = Color.rgb(178, 34, 34).darker();
    /**
     * Defined clay brick boarder color
     */
    private static final Color DEF_BORDER = Color.GRAY;
    /**
     * Defined clay brick's max hit stroke
     */
    private static final int CLAY_STRENGTH = 1;
    /**
     * Defined viewing group
     */
    private Group m_Group;

    /**
     * This constructor builds the clay brick(with defined boarder color and inner color) and adds its view to the game board.
     * Modified:  Point -> Point2D
     * Note:  add group parameter to show in the scene
     * @param point brick's center point
     * @param size brick's size
     * @param group brick's view belonging group
     *
     */
    public ClayBrick(Point2D point, Dimension2D size, Group group){
        super(point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
        m_Group= group;
        m_Group.getChildren().add(super.getM_BrickFace());
    }

    /**
     * This constructor builds the image brick adn adds its view to the game board.
     * @param point brick's center point
     * @param size brick's size
     * @param image brick's image
     * @param group brick's view belonging group
     */
    public ClayBrick(Point2D point, Dimension2D size, Image image, Group group){
        super(point,size,image,CLAY_STRENGTH);
        m_Group= group;
        m_Group.getChildren().add(super.getM_ImageBrickFace());
    }
    /**
     * This function helps make the geometric brick face.
     * Modified:  Point -> Point2D <br>
     * Dimension -> Dimension2D
     * @param pos brick's center point
     * @param size brick's size
     * @return built geometric brick face with defined center and size
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
     * This function helps set the brick status after being hit by the ball.
     * Note:  change remove brick after the impact judgement
     * @param point ball's point
     * @param dir ball's direction on the brick
     * @return the bricks' interaction with the ball
     */
    @Override
    public boolean setImpact(Point2D point,int dir){
        super.impact();
        if(super.isBroken())
        {
            if(super.getM_ImageBrickFace()!=null)
               m_Group.getChildren().remove(super.getM_ImageBrickFace());
            m_Group.getChildren().remove(super.getM_BrickFace());

            return true;
        }
        return false;
    }

    /**
     * This function helps repair the brick for replaying purpose by re adding the ball face or the image ball face if it used to have one.<br>
     * It resets the broken status to false and allowable strokes to 3.
     */
    @Override
    public void repair(){
        if(super.isBroken()){
            /*
             set broken status to false and allowable strokes to 3
              */
            setM_Broken(false);
            setM_Strength();
            m_Group.getChildren().add(super.getM_BrickFace());
            /*
             remove brick face
              */
            if(super.getM_ImageBrickFace()!=null)
                m_Group.getChildren().add(super.getM_ImageBrickFace());
        }
    }

}
