package project.chenlin_17.Views;





import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
/**
 * This is the abstract base class for all brick classes which defines the
 * common attributes and behaviors of all brick classes.
 * <p>
 * The layout of the view of the brick contains model of the brick and
 * adds the view of the brick into container, the brick could be displayed
 * onto the stage.
 *
 * Note:  It contains changes from the awt to javafx.<br>
 * It contains changes of class variables according to Bob's code convention.
 * Deleted:  crack variable, since some type of the brick doesn't have crack
 * @author Lin CHEN - modified
 * @version Version1.0
 * <br> update: December 29th
 */

abstract class Brick  {

    /**
     * Defined brick's behaviour when the ball upper hits the brick
     */
    static final int UP_IMPACT = 100;
    /**
     * Defined brick's behaviour when the ball down hits the brick
     */
    static final int DOWN_IMPACT = 200;
    /**
     * Defined brick's behaviour when the ball left hits the brick
     */
    static final int LEFT_IMPACT = 300;
    /**
     * Defined brick's behaviour when the ball right hits the brick
     */
    static final int RIGHT_IMPACT = 400;


    /**
     * Geometric brick face
     */
    private Shape m_BrickFace;
    /**
     * Brick boarder color
     */
    private Color m_Border;
    /**
     * Brick inner color
     */
    private Color m_Inner;

    /**
     * Brick's break strokes
     */
    private int m_FullStrength;
    /**
     * Brick's current hit times
     */
    private int m_Strength;
    /**
     * Whether the brick has broken
     */
    private boolean m_Broken;
    /**
     * Brick's size with width and height
     */
    private Dimension2D m_Size;
    /**
     * Brick's related image face
     */
    private ImageView m_ImageBrickFace;

    /**
     This constructor helps build basic settings for the brick.
     * It is a helper constructor for color and image brick settings.
     * <pre> {@code
     *  Modified from the original source code:
     *  public Brick(String name, Point pos,Dimension size,Color border,
     *  Color inner,int strength){
     *      rnd = new Random();
     *      broken = false;
     *      this.name = name;
     *      brickFace = makeBrickFace(pos,size);
     *      this.border = border;
     *      this.inner = inner;
     *      this.fullStrength = this.strength = strength;
     *  }
     * } </pre>
     *
     *  @param pos brick's position
     *  @param size brick's size
     * @param strength brick's strength

     */
    private Brick(Point2D pos, Dimension2D size, int strength){
        m_Broken = false;
        m_BrickFace = makeBrickFace(pos,size);
        m_Size = size;
        m_FullStrength = m_Strength = strength;
    }
   /**
    * This constructor helps build general brick view with colors.
     * Modified:   Point->Point2D <br>
     * Dimension -> Dimension2D
     * @param pos brick's position
     * @param size brick's size
     * @param strength brick's strength
     * @param border brick's defined border color
     * @param inner brick's defined inner color
     */

    public Brick(Point2D pos, Dimension2D size, Color border, Color inner, int strength){
       this(pos, size, strength);
       setM_Inner(inner);
       setM_Border(border);
    }
    /**
     * This constructor helps build general brick view with image.
     * <pre> {@code
     *  Modified from the original source code:
     *  public Brick(String name, Point pos,Dimension size,Color border,
     *  Color inner,int strength){
     *      rnd = new Random();
     *      broken = false;
     *      this.name = name;
     *      brickFace = makeBrickFace(pos,size);
     *      this.border = border;
     *      this.inner = inner;
     *      this.fullStrength = this.strength = strength;
     *  }
     * } </pre>
     * Modified:   Point->Point2D <br>
     * Dimension -> Dimension2D
     * @param pos brick's position
     * @param size brick's size
     * @param strength brick's strength
     * @param image brick's associated image
     */
    public Brick( Point2D pos, Dimension2D size, Image image, int strength){

        this(pos, size, strength);
        m_BrickFace.setOpacity(0);
        m_BrickFace.setLayoutX(pos.getX());
        m_BrickFace.setLayoutY(pos.getY());
        makeImageBrickFace(image);

    }

    /**
     * This function helps add the image to the ball face.<br>
     * @param image ball face image
     */
    private void makeImageBrickFace(Image image){

        m_ImageBrickFace = new ImageView();
        m_ImageBrickFace.setImage(image);
        m_ImageBrickFace.setFitWidth(m_Size.getWidth());

        double ratio = m_Size.getHeight()/m_Size.getWidth();

        m_ImageBrickFace.setFitHeight(ratio*m_ImageBrickFace.getFitWidth());
        m_ImageBrickFace.xProperty().bind(m_BrickFace.layoutXProperty());
        m_ImageBrickFace.yProperty().bind(m_BrickFace.layoutYProperty());
    };

    /**
     * This function returns the built brick face.
     * @param pos brick's position
     * @param size brick's size with height and width
     * @return built brick face
     */
    protected abstract Shape makeBrickFace(Point2D pos,Dimension2D size);

    /**
     * This function helps get the ball's impact on the brick.
     * @param point ball's point
     * @param dir ball's direction on the brick
     * @return whether the impact has been done
     */
    public abstract boolean setImpact(Point2D point,int dir);

    /**
     * This function helps get the brick's face.
     * @return brick face
     */
    public Shape getM_BrickFace() {
        return m_BrickFace;
    }
    /**
     * This function helps get the brick's size
     * @return brick size
     */
    public Dimension2D getM_Size(){
        return m_Size;
    }

    /**
     * This function helps get the brick's boarder color.
     * @return brick's boarder color
     */

    public Color getBorderColor(){
        return  m_Border;
    }

    /**
     * This function helps get the brick's inner color.
     * @return brick's inner color
     */
    public Color getInnerColor(){
        return m_Inner;
    }

    /**
     * This function helps simulate brick's impact on the ball.
     *
     * @param b ball's movement index
     * @return ball's impact on brick
     */

    public final int findImpact(BallView b){
        if(m_Broken)
            return 0;
        int out  = 0;
        if(m_BrickFace.contains(b.getM_Right()))
            out = LEFT_IMPACT;
        else if(m_BrickFace.contains(b.getM_Left()))
            out = RIGHT_IMPACT;
        else if(m_BrickFace.contains(b.getM_Up()))
            out = DOWN_IMPACT;
        else if(m_BrickFace.contains(b.getM_Down()))
            out = UP_IMPACT;
        return out;
    }

    /**
     * This function helps get the brick's broken status.
     * @return ball's broken status
     */
    public final boolean isBroken(){
        return m_Broken;
    }


    /**
     * This function helps repair the brick for replaying purpose.
     * <pre> {@code
     *   Modified from the original source code:
     *   public void repair() {
     *      broken = false;
     *      strength = fullStrength;
     *   }
     * } </pre>
     */
    public abstract void repair() ;

    /**
     * This function sets the impact of the ball hitting the ball.<br>
     * After impact, the current strength of the brick should minus itself by
     * one and if the current strength drops to <b>0</b>, sets the broken status
     * of the brick to true (broken).
     * <pre> {@code
     *  Modified from the original source code:
     *  public void impact(){
     *      strength--;
     *      broken = (strength == 0);
     *  }
     * } </pre>
     */
    public void impact(){
        m_Strength--;
        m_Broken = (m_Strength == 0);
    }

    /**
     * This function returns the brick image.
     * @return brick image
     */
    public ImageView getM_ImageBrickFace(){
        return m_ImageBrickFace;
    }

    /**
     * This function returns brick's full strength to see whether it is broken.
     * @return ball's full strength
     */
    public int getM_FullStrength(){return m_FullStrength;}

    /**
     * This function sets the ball's inner color.
     * @param inner the expected inner color
     */
    public void setM_Inner(Color inner){
        m_Inner = inner;
        m_BrickFace.setFill(inner);
    }

    /**
     * This function sets the ball's border color
     * @param border the expected border color
     */
    public void setM_Border(Color border){
        m_Border = border;
        m_BrickFace.setStroke(border);
    }

    /**
     * This function sets the broken status.
     * @param value  broken status
     */
    public void setM_Broken(boolean value){
        m_Broken = value;
    }

    /**
     * This function sets the strength to the full value, which is used for strength.
     */
    public void setM_Strength(){
        m_Strength = m_FullStrength;
    }
}





