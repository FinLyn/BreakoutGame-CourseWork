package project.chenlin_17.Models;

import javafx.geometry.Point2D;

/**
 * This is the class of the ball, which defines the basic attributes
 * of the ball, which includes the radius, ball's bound and the ball's position property.<br>
 * It also provides methods to access and modify some attributes.<br>
 * Ball's radius property is not allowed to change once it has been defined.
 * @author Lin CHEN- modified
 * @version Version2.0
 * <br> update: Jan 3rd
 */

public class BallModel {

    /**
     * Ball center
     */
    private Point2D m_Center;

    /**
     * Ball's upper bound
     * Modified:  Point -> Point2D
     */
    private Point2D m_Up;
    /**
     * Ball's lower bound
     * Modified:  Point -> Point2D
     */
    private Point2D m_Down;
    /**
     * Ball's left bound
     * Modified:  Point -> Point2D
     */
    private Point2D m_Left;
    /**
     * Ball's right bound
     * Modified:  Point -> Point2D
     */
    private Point2D m_Right;
    /**
     * Ball's radius for x
     */
    private int m_RadiusA;
    /**
     * Ball's radius for y
     */
    private int m_RadiusB;

    /**
     * Half of the radius
     */
    private final double HALF=0.5;

    /**
     * This constructor accepts the basic ball settings and stores the value.<br>
     * After this constructor, the setting of the ball is done.
     * @param center ball's expected start center
     * @param radiusA ball's x radius
     * @param radiusB ball's y radius
     */

    public BallModel(Point2D center,int radiusA,int radiusB) {
        m_Center = center;
        m_RadiusA = radiusA;
        m_RadiusB = radiusB;
        double x = m_Center.getX();
        double y = m_Center.getY();

        m_Up = new Point2D(x, y - (radiusB * HALF));
        m_Down = new Point2D(x, y + (radiusB * HALF));

        m_Left = new Point2D(x - (radiusA * HALF),y);
        m_Right =new Point2D(x + (radiusA * HALF),y);
    }


    /**
     * This function gets the ball's x radius value.
     * @return ball's x radius value
     */
    public int getM_RadiusA(){return m_RadiusA;};
    /**
     * This function gets the ball's y radius value.
     * @return ball's y radius value
     */
    public int getM_RadiusB(){return m_RadiusB;}
    /**
     * This function gets the ball's center.
     * @return ball's center
     */
    public Point2D getM_Center(){return m_Center;}
    /**
     * This function gets the ball's up bound.
     * @return ball's up bound
     */
    public Point2D getM_Up(){return m_Up;}
    /**
     * This function gets the ball's down bound.
     * @return ball's down bound
     */
    public Point2D getM_Down(){return m_Down;}
    /**
     * This function gets the ball's left bound.
     * @return ball's left bound
     */
    public Point2D getM_Left(){return m_Left;}
    /**
     * This function gets the ball's right bound.
     * @return ball's right bound
     */
    public Point2D getM_Right(){return m_Right;}


        /**
         * This function updates the position of the ball.
         *
         * <pre> {@code
         *  Modified from the original source code:
         *  public void setPoints(double width,double height){
         *      up.setLocation(center.getX(),center.getY()-(height / 2));
         *      down.setLocation(center.getX(),center.getY()+(height / 2));
         *
         *      left.setLocation(center.getX()-(width / 2),center.getY());
         *      right.setLocation(center.getX()+(width / 2),center.getY());
         *  }
         * } </pre>
         */
        public void updatePoints() {
            double x = m_Center.getX();
            double y = m_Center.getY();

            m_Up = new Point2D(x, y - (m_RadiusB * HALF));
            m_Down = new Point2D(x, y + (m_RadiusB * HALF));

            m_Left = new Point2D(x - (m_RadiusA * HALF),y);
            m_Right =new Point2D(x + (m_RadiusA * HALF),y);
        }

    /**
     * This function resets the ball's center.
     * @param center ball's new center
     */
        public void setM_Center(Point2D center){
            m_Center = center;
        }

}