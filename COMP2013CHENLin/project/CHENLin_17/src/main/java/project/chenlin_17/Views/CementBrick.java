package project.chenlin_17.Views;



import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;


/**
 * This cement brick class is the concrete class extends from the brick class.<br>
 * It contains crack property.
 *  Note:  Its class name is cement brick, which makes its previous name "Brick2" readable.<br>
 * It contains changes from the awt to javafx.<br>
 * It contains changes of class variables according to Bob's code convention.
 *
 * @see Brick
 * @see Crack
 *
 * @author Lin CHEN- modified
 * @version Version2.0
 * <br> update: Jan 3rd
 */

 class CementBrick extends Brick {

     /**
      * Defined inner color
      */
    private static final Color DEF_INNER = Color.rgb(147, 147, 147);
     /**
      * Defined boarder color
      */
    private static final Color DEF_BORDER = Color.rgb(217, 199, 175);
     /**
      * Define brick's max strength
      */
    private static final int CEMENT_STRENGTH = 2;
     /**
      * Defined crack path
      */
    private Path m_Path;
     /**
      * Defined crack viewing
      */
    protected Crack m_Crack;
     /**
      * Defined viewing group
      */
    private Group m_Group;

    /**
     * Defined crack depth
     */
    public static final int DEF_CRACK_DEPTH = 1;
    /**
     * Defined steps that creates a crack that help make the crack look real
     */
    public static final int DEF_STEPS = 35;
     /**
      * This constructor builds the cement brick(with defined boarder color and inner color) and adds its view to the game board.
      * <pre> {@code
      *  Modified from the original source code:
      *  public Brick1(Point point, Dimension size){
      *      super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
      *      crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
      *      brickFace = super.brickFace;
      *  }
      * } </pre>
      * Modified:  Point -> Point2D <br>
      * Dimension -> Dimension2D
      * @param point brick point
      * @param size brick size
      * @param group brick's viewing group
      * @see Brick#Brick(Point2D, Dimension2D, Color, Color, int) 
      * @see Crack#Crack(int, int, Brick)
      *
      */
    public CementBrick(Point2D point, Dimension2D size, Group group){
        /*
         * Original source code:
         * crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
         */
        super(point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);

        m_Crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS,this);
        m_Group = group;
        m_Group.getChildren().add(super.getM_BrickFace());
    }

     /**
      * This constructor builds the image cement brick and adds its view to the game board.
      * @param point brick point
      * @param size brick size
      * @param image brick image
      * @param group brick viewing group
      * @see Brick#Brick(Point2D, Dimension2D, Image, int) 
      * @see Crack#Crack(int, int, Brick) 
      */
    public CementBrick(Point2D point, Dimension2D size, Image image, Group group){
        super(point,size,image,CEMENT_STRENGTH);

        m_Crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS,this);
        m_Group = group;
        m_Group.getChildren().add(super.getM_ImageBrickFace());
    }

    /**
     * This function returns the built brick face.

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
     * This function helps set the brick status after being hit by the ball.
     * If hits once, then the crack will show.<br>
     * If hits twice, the brick is set to be broken.
     * Note:  change remove brick after the impact judgement<br>
     * the crack connects to the brick is also removed.
     * <p>
     * Otherwise, if the cracked brick is not broken after impact, a new crack
     * will be created based on the impact point and the impact direction. Then
     * adds the crack into the crack array of the cracked brick, updates the
     * crack count and adds the view of the crack into the container.
     * <pre> {@code
     *  Modified from the original source code:
     *  public boolean setImpact(Point2D point, int dir) {
     *      if(super.isBroken())
     *          return false;
     *      super.impact();
     *      if(!super.isBroken()){
     *          crack.makeCrack(point,dir);
     *          updateBrick();
     *          return false;
     *      }
     *      return true;
     *  }
     * } </pre>
     *
     * @param point ball's point
     * @param dir ball's direction on the brick
     * @return the bricks' interaction with the ball
     * @see Brick#setImpact(Point2D, int)
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        super.impact();
        if(super.isBroken())
        {
            /*
            remove crack
             */
           m_Group.getChildren().remove(m_Path);
            /*
            remove image brick face if exists
             */
            if(super.getM_ImageBrickFace()!= null)
                m_Group.getChildren().remove(super.getM_ImageBrickFace());
            /*
            remove brick face
             */
            m_Group.getChildren().remove(super.getM_BrickFace());
            return true;
        }
        /*
        if not broken,add the crack
         */
        if(!super.isBroken()){
            m_Crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return false;
    }

     /**
      * This is the helper function that updates the brick's current status.
      */
    private void updateBrick(){
        if(!super.isBroken()){
            /**
             * Deleted: 
             * Original source code:
             * GeneralPath gp = crack.draw();
             * gp.append(super.brickFace,false);
             */

            Path gp = m_Crack.draw();
            m_Group.getChildren().remove(m_Path);
            m_Path = gp;
            m_Group.getChildren().add(m_Path);
        }
    }


     /**
      * This function helps repair the brick for replaying purpose by re adding the ball face or the image ball face if it used to have one.<br>
      * If the crack exists on the brick,it will also be removed.<br>
      * It resets the broken status to false and allowable strokes to 3.
      * <pre> {@code
      *  Modified from the original source code:
      *  public void repair(){
      *      super.repair();
      *      crack.reset();
      *      brickFace = super.brickFace;
      *  }
      * } </pre>
      *
      * @see Brick#repair()
      */
    public void repair() {
        /*
         * Original source code:
         * crack.reset();
         */
        if(super.isBroken()){
            /*
             set broken status to false and allowable strokes to 3
              */
            setM_Broken(false);
            setM_Strength();
            /*
             remove ball face
              */
            m_Group.getChildren().add(super.getM_BrickFace());
            if(super.getM_ImageBrickFace()!=null)
                m_Group.getChildren().add(super.getM_ImageBrickFace());
        }
       /*
       reset the crack status
        */
        if (m_Path != null) {
            m_Group.getChildren().remove(m_Path);
            m_Crack.reset();
        }
    }

}
