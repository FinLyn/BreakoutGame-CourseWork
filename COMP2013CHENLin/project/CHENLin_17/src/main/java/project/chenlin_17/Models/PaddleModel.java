package project.chenlin_17.Models;

import javafx.geometry.Point2D;

/**
 * This is the class of the paddle model, which defines the basic attributes
 * of the paddle, which includes paddle's position and paddle's width property.<br>
 * It also provides methods to access and modify some attributes.<br>
 * Paddle's width and height property is not allowed to change once it has been defined.
 * @author Lin CHEN- modified
 * @version Version2.0
 * <br> update: Jan 3rd
 */

public class PaddleModel {
    /**
     * Paddle's defined position
     */
    private Point2D m_PaddlePoint;
    /**
     * Paddle's defined width
     */
    private int m_Width;
    /**
     * Paddle's defined height
     */
    private int m_Height;


    /**
     * The constructor sets the initial value for the basic attributes.
     * @param ballPoint paddle position
     * @param width paddle width
     * @param height paddle height
     */
    public PaddleModel(Point2D ballPoint, int width, int height){
        m_PaddlePoint = ballPoint;
        m_Width= width;
        m_Height = height;
    }
    /**
     * This function gets the paddle's position.
     * @return paddle's position
     */
    public Point2D getM_PaddlePoint() { return m_PaddlePoint; }

    /**
     * This function gets the paddle's width
     * @return paddle's width
     */
    public int getM_Width() { return m_Width;}
    /**
     * This function gets the paddle's width
     * @return paddle's height
     */
    public int getM_Height(){return m_Height;}
    /**
     * This function sets the paddle's position
     * @param point expected paddle position
     */
    public void setM_PaddlePoint(Point2D point){ m_PaddlePoint = point;}

}
