package project.chenlin_17.Views;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import project.chenlin_17.Models.PaddleModel;

/**
 * This is the abstract class for all paddle classes which defines the
 * common attributes and behaviors of all paddle classes.
 * <p>
 * The layout of the view of the paddle binds with the model of the paddle,
 * every time the paddle moves, the layout of the view of the paddle will also
 * be updated. By adding the view of the paddle into container, the paddle could
 * be displayed onto the stage.
 * <p>
 * By extending the abstract class paddle, its extended classes could have various width
 * and height, and the different source images of their views.
 *
 * @see Movable
 * @see PaddleModel
 *
 * @author Lin CHEN- modified
 * @version Version2.0
 * <br> update: Jan 3rd
 */
public abstract class PaddleView {
    /**
     * Paddle's actual movement
     */

    private int m_MoveAmount;
    /**
     * Paddle movement's left bound
     */
    private int m_Min;
    /**
     * Paddle movement's right bound
     */
    private int m_Max;
    /**
     * Paddle's boarder color
     */
    private Color m_Boarder;
    /**
     * Paddle's inner color
     */
    private Color m_Inner;

    /**
     * Paddles' defined movement amount that does not know the
     */
    private int m_DefMoveAmount;

    /**
     * Model that contains basic information of the paddle
     */
    private PaddleModel m_PaddleModel;
    /**
     * Viewing group of the paddle
     */
    private Group m_Group;

    /**
     * Paddle face view
     */
    private Shape m_PaddleFace;

    /**
     * Range that defines the movement boundary of the paddle
     */
    private Rectangle m_Container;


    /**
     * This constructor sets the basic parameter for the paddle. <br>
     * <pre> {@code
     *  Modified from the original source code:
     *  public Paddle(Point ballPoint, int width, int height,
     *  Rectangle container) {
     *      this.ballPoint = ballPoint;
     *      moveAmount = 0;
     *      paddleFace = makeRectangle(width, height);
     *      min = container.x + (width / 2);
     *      max = min + container.width - width;
     *  }
     * } </pre>
     * @param model paddle model
     * @param container defined paddle movement range
     * @param defMoveAmount defined movement
     * @param group defined belonging viewing group
     */
    public PaddleView(PaddleModel model, Rectangle container, int defMoveAmount, Group group){
        m_PaddleModel = model;
        m_MoveAmount = 0;
        m_DefMoveAmount = defMoveAmount;
        m_Group = group;
        m_PaddleFace = makePaddleFace(model);
        setM_Container(container);
    }

    /**
     * This function defines paddle's whole movement and add in the stop status for the paddle when reach the bound.<br>
     * It calculates the latest paddle position.
     * <pre> {@code
     *  Modified from the original source code:
     *  public void move(){
     *      double x = ballPoint.getX() + moveAmount;
     *      if(x < min || x > max)
     *          return;
     *      ballPoint.setLocation(x,ballPoint.getY());
     *      paddleFace.setLocation(ballPoint.x - (int) paddleFace.getWidth()/2,
     *          ballPoint.y);
     *  }
     * } </pre>
     *
     * Note:  setLocation is not usable in javaFX's Point2D package
     * @see Movable#move()
     */
    public void move(){
        Point2D center = m_PaddleModel.getM_PaddlePoint();
        /*
         * Original source code:
         * ballPoint.setLocation(p);
         */
        double x = center.getX() + m_MoveAmount;
        if(x < m_Min || x > m_Max)
            return;
        m_PaddleModel.setM_PaddlePoint(new Point2D(x,center.getY()));
        updatePaddleView();
    }

    /**
     * This function gets the made paddle face.
     * Deleted:  public Rectangle2D makeRectangle(int width,int height){}
     * Modified:  Rectangle2D -> Shape
     * Note:  since paddle face can be different and should not be Rectangles only, therefore, it is reasonable to have multiple Shapes
     * @param model paddle model
     * @return paddleFace made with defined width and height
     *
     */

    public abstract Shape makePaddleFace(PaddleModel model);

    /**
     * This function simulates paddle's interaction with the ball.
     * @param b ball
     * @return whether the ball has hit the paddle
     * @see BallView
     */
    public  boolean impact(BallView b){ return m_PaddleFace.contains(b.getPosition()) && m_PaddleFace.contains(b.getM_Down());}
    /**
     * This function makes paddle view moves to left.
     */
    public void moveLeft(){m_MoveAmount = -m_DefMoveAmount;}


    /**
     * This function makes paddle view moves to the right.
     * Deleted:  movRight(){} for readable function name
     */
    public void moveRight(){m_MoveAmount = m_DefMoveAmount;}

    /**
     * This function simulates the paddle's stop status.
     */
    public void stop(){
        m_MoveAmount = 0;
    }



    /**
     * This is the helper function of updating the view of paddle according to the newly updated model.
     */
    private void updatePaddleView(){
        Point2D center = m_PaddleModel.getM_PaddlePoint();
        double width = m_PaddleModel.getM_Width();
        ((Rectangle)m_PaddleFace).setX(center.getX() -  width / 2);

    };


    /**
     * This function allows the paddle to move to a specific point.
     * <pre> {@code
     *  Modified from the original source code:
     *  public void moveTo(Point p){
     *      ballPoint.setLocation(p);
     *      paddleFace.setLocation(ballPoint.x - (int) paddleFace.getWidth()/2,
     *          ballPoint.y);
     *  }
     * } </pre>
     * Note:  setLocation is not usable in javaFX's Point2D package
     * @param point expected point
     * @see Movable#moveTo(Point2D)
     */
    public void moveTo(Point2D point){
        /*
         * Original source code:
         * ballPoint.setLocation(p);
         */
        m_PaddleModel.setM_PaddlePoint(point);
        updatePaddleView();
    };


    /**
     * This function gets the paddle's face.
     * @return paddle's face
     */
    public Shape getM_PaddleFace(){return m_PaddleFace;}

    /**
     * This function allows to set the color property of the paddle.
     * @param color expected inner color
     */
    public void setM_Inner(Color color){
        m_PaddleFace.setFill(color);
        m_Inner = color;
    }
    /**
     * This function allows to set the color property of the paddle.
     * @param color expected boarder color
     */
    public void setM_Boarder(Color color){
        m_PaddleFace.setFill(color);
        m_Boarder = color;
    }

    /**
     * This function gets the paddle's inner color.
     * @return paddle's inner color
     */
    public Color getM_Inner(){return m_Inner;}

    /**
     * This function gets the paddle's boarder color.
     * @return paddle's boarder color
     */
    public Color getM_Boarder(){return m_Boarder;}
    /**
     * This function gets the paddle's viewing group.
     * @return paddle's viewing group
     */
    public Group getM_Group() {
        return m_Group;
    }
    /**
     * This function gets the paddle's model.
     * @return paddle's model with defined position and radius
     */
    public PaddleModel getM_PaddleModel() {
        return m_PaddleModel;
    }
    /**
     * This function gets the paddle's position.
     * @return paddle's position
     */
     public Point2D getPosition(){
        return  m_PaddleModel.getM_PaddlePoint();
     }

    /**
     * This function sets the new container's boundary.
     * @param rectangle expected movement boundary of the paddle
     */
     public void setM_Container(Rectangle rectangle){
        m_Container = rectangle;
         m_Min = (int)rectangle.getX() + (m_PaddleModel.getM_Width() / 2);
         m_Max = m_Min + (int)rectangle.getWidth() - m_PaddleModel.getM_Width();
     }
}
