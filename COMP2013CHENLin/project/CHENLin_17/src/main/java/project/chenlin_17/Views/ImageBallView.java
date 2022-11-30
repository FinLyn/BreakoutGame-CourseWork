package project.chenlin_17.Views;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import project.chenlin_17.Models.BallModel;

/**
 * This image ball class is the concrete class extends from the ball class.<br>
 * It creates the ball with set image.
 * Note:  It contains changes from the awt to javafx.<br>
 * It contains changes of class variables according to Bob's code convention.
 *
 * @see BallView
 *
 * @author Lin CHEN
 * @version Version2.0
 * <br> update: Jan 3rd
 */

public class ImageBallView extends BallView{

    /**
     * Ball's image view
     */
    private ImageView m_ImageBall;
    /**
     * Ball's layout x property
     */
    private SimpleDoubleProperty m_XProperty;
    /**
     * Ball's layout y property
     */
    private SimpleDoubleProperty m_YProperty;

    /**
     * Ball's image's height
     */
    private double m_ImageHeight;
    /**
     * Ball's image's width
     */
    private double m_ImageWidth;


    /**
     * This constructor initializes the ball view.<br>
     * It sets the basic value for the ball setting variable by calling its parent class.
     * @param model ball's model
     * @param image player set ball image
     * @param group ball's belonging world setting group
     * @see BallView#BallView(BallModel, Group) 
     */
    public ImageBallView(BallModel model, Image image, Group group){
        super(model, group);
        makeImageBall(image);
    }

    /**
     * This function helps adds the ball's image on the page.<br>
     * The ball's image position is closely combined with the ball center position's movement by calculation.<br>
     * @param image ball's image
     */
    private void makeImageBall(Image image) {
        m_ImageBall = new ImageView();
        m_ImageBall.setImage(image);
        m_ImageBall.setPreserveRatio(true);
        /*
        calculate image center
         */
        m_ImageHeight = image.getHeight()/2;
        m_ImageWidth =image.getWidth()/2;
        /*
        the ball's image presenting movement will be closely combined with the ball center position's movement
         */
        m_ImageBall.layoutXProperty().bind(m_XProperty.add(-m_ImageHeight));
        m_ImageBall.layoutYProperty().bind(m_YProperty.add(-m_ImageWidth));
        /*
        add ball's image view
         */
        super.getM_Group().getChildren().add(m_ImageBall);
    }

    /**
     * This function implements the method defined in the parent class. <br>
     * It helps initializes the ball image view's position property and make tha ellipse ball face as well.
     * Note:  Double conversion is not needed in the javaFX API
     * @param model ball model
     * @return the built ball with center and specified radius
     */
    @Override
    protected Shape makeBall(BallModel model) {
        m_XProperty = new SimpleDoubleProperty();
        m_YProperty = new SimpleDoubleProperty();
        Point2D center = model.getM_Center();
        double radiusA = model.getM_RadiusA();
        double radiusB = model.getM_RadiusB();
        updateProperty(model);

        /*
         * Original source code:
         * return new Ellipse2D.Double(x,y,radiusA,radiusB);
         */
        return new Ellipse(center.getX(),center.getY(),radiusA,radiusB);
    }

    /**
     * This function implements the method defined in the super class.<br>
     * It helps update and adjust the ball image's view by updating the property that stands for the image position.
     * @param p designed position
     */
    @Override
    public void moveTo(Point2D p){
        super.moveTo(p);
        updateProperty(super.getM_BallModel());

    }

    /**
     * This is a helper function that sets the new image position property by getting the new center.
     * @param model ball's new position
     */
    private void updateProperty(BallModel model) {
        Point2D center = model.getM_Center();
        double radiusA = model.getM_RadiusA();
        double radiusB = model.getM_RadiusB();
        double newy= center.getY()-radiusB/2;
        double newx= center.getX()-radiusA/2;
        m_YProperty.set(newy);
        m_XProperty.set(newx);
    }

    /**
     * This function implements the method defined in the super class.<br>
     * It helps update and adjust the ball image's view by updating the property that stands for the image position.
     */
    @Override
    public void move(){
        super.move();
        updateProperty(super.getM_BallModel());
    }

    /**
     * This function gets the image of the ball.
     * @return ball image
     */
    public ImageView getM_ImageBall(){
        return m_ImageBall;
    }
}

