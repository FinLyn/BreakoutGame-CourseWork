package project.chenlin_17.Views;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import project.chenlin_17.Models.BallModel;

import java.util.Random;

/**
 * This is the abstract base class for all ball;s view and relates it to
 * the movements of the ball.
 * <p>
 * The layout of the view of the ball binds with the model of the ball, every
 * time the ball moves, the layout of the view of the ball will also be updated.
 * By adding the view of the ball into container, the ball could be displayed
 * onto the stage.
 * <p>
 * By extending the abstract class BallView, image ball and rubberBall could have their
 * own way of showing.
 *
 * @see Movable
 * @see BallModel
 *
 * @author Lin CHEN- modified
 * @version Version2.0
 * <br> update: Jan 3rd
 */
public abstract class BallView implements Movable{
    /**
     * The value related to change the direction of the ball
     */
    private final int  INVERSE = -1;
    /**
     * Ball face view
     */
    private Shape m_BallFace;

    /**
     * Ball's boarder color
     */
    private Color m_Boarder;
    /**
     * Ball's inner color
     */
    private Color m_Inner;
    /**
     * Ball's speed for x value
     */
    private int m_SpeedX;
    /**
     * Ball's speed for y value
     */
    private int m_SpeedY;
    /**
     * Model contains the basic setting of the ball
     */
    private BallModel m_BallModel;
    /**
     * Ball's viewing group
     */
    private Group m_Group;

    /**
     * The half value for configuring the ball
     */
    private final double HALF = 0.5;


    /**
     * This constructor receives the ball model
     * and adds makes its view according to the defined values.
     * @param model ball's general settings
     * @param group ball's viewing group
     */
    public BallView(BallModel model,Group group){
        m_BallModel = model;
        m_Group = group;
        m_BallFace = makeBall(model);

    }


    /**
     * This function moves the ball by speed and updates the layout of ball's view.
     * <p>
     * The center point of the ball will firstly be moved, and then the other
     * four feature points and the layout of the view of the ball will be
     * updated.
     * <pre> {@code
     *  Modified from the original source code:
     *  public void move(){
     *      RectangularShape tmp = (RectangularShape) ballFace;
     *      center.setLocation((center.getX()+speedX),(center.getY()+speedY));
     *      double w = tmp.getWidth();
     *      double h = tmp.getHeight();
     *
     *      tmp.setFrame((center.getX()-(w / 2)),(center.getY()-(h / 2)),w,h);
     *      setPoints(w,h);
     *
     *      ballFace = tmp;
     *  }
     * } </pre>
     *
     * @see Movable#move()
     */
    @Override
    public void move() {
        Point2D center = m_BallModel.getM_Center();
        m_BallModel.setM_Center(new Point2D(center.getX() + m_SpeedX, center.getY()+m_SpeedY));
        m_BallModel.updatePoints();
        updateBallView();
    }

    /**
     * This function moves the ball to specified position and updates the layout of ball's
     * view.
     * <p>
     * The center point of the ball will firstly be moved to the specified
     * point, then the other four feature points of the ball and the layout of
     * the view of the ball will be updated.
     * <p>
     * It is used to recover ball's position when ball is lost.
     * <pre> {@code
     *  Modified from the original source code:
     *  public void moveTo(Point p){
     *      center.setLocation(p);
     *
     *      RectangularShape tmp = (RectangularShape) ballFace;
     *      double w = tmp.getWidth();
     *      double h = tmp.getHeight();
     *
     *      tmp.setFrame((center.getX()-(w / 2)),(center.getY()-(h / 2)),w,h);
     *      ballFace = tmp;
     *  }
     * } </pre>
     *
     * @param p the expected point of the ball center
     * @see Movable#moveTo(Point2D)
     */
    @Override
    public void moveTo(Point2D p){
        m_BallModel.setM_Center(p);
        m_BallModel.updatePoints();
        updateBallView();
    }

    /**
     * This is the helper function tha helps to update the ball's view.
     *
     */
    private void updateBallView(){
        Point2D center = m_BallModel.getM_Center();
        double radiusA= m_BallModel.getM_RadiusA()*HALF;
        double radiusB= m_BallModel.getM_RadiusB()*HALF;
        ((Ellipse)m_BallFace).setCenterX(center.getX()- radiusA);
        ((Ellipse)m_BallFace).setCenterY(center.getY()- radiusB);
    };

    /**
     * This function generates different kind of ball views.
     * @param model current ball model
     * @return generated ball face
     */
    protected abstract Shape makeBall(BallModel model);

    /**
     * This function allows players to set the speed of the ball.
     * @param x expected speed for moving parallel
     */
    public void setXSpeed(int x){
        m_SpeedX = x;
    }


    /**
     * This function allows players to set the speed of the ball.
     * @param y expected speed for moving vertical
     */
    public void setYSpeed(int y){
        m_SpeedY = y;
    }

    /**
     * This function allows the ball to change its horizontal direction.
     */
    public void reverseX(){
        m_SpeedX *= INVERSE;
    }

    /**
     * This function allows the ball to change its vertical direction.
     */
    public void reverseY(){
        m_SpeedY *= INVERSE;
    }

    /**
     * This function gets the boarder color of the ball.
     * @return boarder color of the ball
     */
    public Color getBorderColor(){return m_Boarder;}

    /**
     * This function gets the inner color of the ball.
     * @return inner color of the ball
     */
    public Color getInnerColor(){ return m_Inner; }
    /**
     * This function gets the position of the ball.
     * @return position of the ball
     */
    public Point2D getPosition(){return m_BallModel.getM_Center();}
    /**
     * This function gets the horizontal speed  of the ball.
     * @return horizontal speed of the ball
     */
    public int getSpeedX(){
        return m_SpeedX;
    }
    /**
     * This function gets the vertical speed of the ball.
     * @return vertical speed of the ball
     */
    public int getSpeedY(){
        return m_SpeedY;
    }

    /**
     * This function allows players to set the inner color of the ball.
     * @param color ball's expected inner color
     */
    public void setM_Inner(Color color){
        m_BallFace.setFill(color);
        m_Inner = color;
    }

    /**
     * This function allows players to set the boarder color of the ball.
     * @param color ball's expected boarder color
     */
    public void setM_Boarder(Color color){
        m_BallFace.setStroke(color);
        m_Boarder = color;
    }

    /**
     * This function gets the view of the ball.
     * @return made view of the ball
     */
    public Shape getM_BallFace(){return m_BallFace;}
    /**
     * This function gets the model of the ball, which contains its basic attributes.
     * @return ball model with basic attributes
     */
    public BallModel getM_BallModel(){return  m_BallModel;}
    /**
     * This function gets the made view group of the ball.
     * @return ball' belonging view group
     */
    public Group getM_Group(){return m_Group;}
    /**
     * This function allows players to set the speed of the ball.
     * @param speedX ball's expected horizontal speed
     * @param speedY ball's expected vertical speed
     */
    public void setSpeed(int speedX, int speedY){
        setXSpeed(speedX);
        setYSpeed(speedY);
    };
    /**
     * This function gets the left bound of the ball.
     * @return ball's left bound
     */
    public Point2D getM_Left(){return m_BallModel.getM_Left();}
    /**
     * This function gets the right bound of the ball.
     * @return ball's right bound
     */
    public Point2D getM_Right(){return m_BallModel.getM_Right();}
    /**
     * This function gets the upper bound of the ball.
     * @return ball's upper bound
     */
    public Point2D getM_Up(){return m_BallModel.getM_Up();}
    /**
     * This function gets the lowest bound of the ball.
     * @return ball's lowest bound
     */
    public Point2D getM_Down(){return m_BallModel.getM_Down();}
    /**
     * This function resets the moving speed of ball.
     * <p>
     * After resetting, the speed on x-axis will range from [<b>-2,2</b>]
     * (excluding 0), and the speed on y-axis will range from [<b>-2, 0</b>).
     *
     * <pre> {@code
     *  Modified from the original source code:
     *         int speedX,speedY;
     *         do{
     *             speedX = rnd.nextInt(5) - 2;
     *         }while(speedX == 0);
     *         do{
     *             speedY = -rnd.nextInt(3);
     *         }while(speedY == 0);
     *
     *         ball.setSpeed(speedX,speedY);
     * } </pre>
     */
    public void resetSpeed(){
        int speedX,speedY;
        Random rnd = new Random();

        int X_RANGE = 5;
        int X_OFFSET = 2;
        int Y_RANGE = 3;

        do{
            speedX = rnd.nextInt(X_RANGE) - X_OFFSET;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(Y_RANGE);
        }while(speedY == 0);

        setSpeed(speedX,speedY);
    };
}
