package project.chenlin_17.Views;


import javafx.geometry.Point2D;
/**
 * This interface contains movable behaviour.<br>
 * Note:  It is added for presenting object's property
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: Jan 3rd
 */
public interface Movable {
    /**
     * This function allows movable object to move.
     */
   void move();
   /**
    * This function allows movable object move to a specific point.
    * @param p expected point
    */
   void moveTo(Point2D p);
}
