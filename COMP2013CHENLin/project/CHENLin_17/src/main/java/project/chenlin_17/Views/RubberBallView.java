package project.chenlin_17.Views;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import project.chenlin_17.Models.BallModel;
/**
 * This is the  concrete class extends from the ballView class.<br>
 * It creates the ball view with geometry class using color and shape.
 * Note:  It contains changes from the awt to javafx.<br>
 * It contains changes of class variables according to Bob's code convention.
 *
 * @see BallView
 *
 * @author Lin CHEN - modified
 * @version Version2.0
 * <br> update: Jan 3rd
 */
    class RubberBallView extends BallView{
        /**
         * Defined value for rubber ball's inner color
         */
    private final Color DEF_INNER_COLOR = Color.rgb(62, 146, 206);
        /**
         * Defined value for rubber ball's boarder color
         */
    private final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();


    /**
     * Ball's belonging viewing group
     */
    private Group m_Group;

    /**
     * This constructor generates the RubberBall with defined color.
     * @param model expected ball model with basic settings
     * @param group expected viewing group of the ball
     * @see BallView#BallView(BallModel, Group)
     */
    public RubberBallView(BallModel model, Group group){
        super(model, group);
        setM_Inner(DEF_INNER_COLOR);
        setM_Boarder(DEF_BORDER_COLOR);
        super.getM_Group().getChildren().add(super.getM_BallFace());
    }
    /**
     * This constructor generates the RubberBall with user defined color.
     * @param model expected ball model with basic settings
     * @param group expected viewing group of the ball
     * @param color user defined color
     * @see RubberBallView#RubberBallView(BallModel,Group)
     */
    public RubberBallView(BallModel model, Color color, Group group){
        this(model,group);
        setM_Inner(color);
    }

    /**
     * This function inherits its parent class and returns the new ball view of an Ellipse.
     * Note:  Double conversion is not needed in the javaFX API
     */
    @Override
    protected Shape makeBall(BallModel model) {
        Point2D center = model.getM_Center();
        double radiusA = model.getM_RadiusA();
        double radiusB = model.getM_RadiusB();
        double HALF = 0.5;
        double x = center.getX() - radiusA * HALF;
        double y = center.getY() - radiusB * HALF;
        /*
         * Original source code:
         * return new Ellipse2D.Double(x,y,radiusA,radiusB);
         */
        return new Ellipse(x,y,radiusA,radiusB);
    }
}
