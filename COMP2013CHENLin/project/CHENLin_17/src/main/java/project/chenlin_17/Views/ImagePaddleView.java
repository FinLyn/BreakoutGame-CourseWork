package project.chenlin_17.Views;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import project.chenlin_17.Models.PaddleModel;

/**
 * This image ball class is the concrete class extends from the ball class.<br>
 * It creates the ball with set image.
 * Note:  It contains changes from the awt to javafx.<br>
 * It contains changes of class variables according to Bob's code convention.
 *
 * @see PaddleView
 *
 * @author Lin CHEN
 * @version Version2.0
 * <br> update: Jan 3rd
 */
public class ImagePaddleView extends PaddleView{
    /**
     * Defined paddle movement amount
     */
    private static final int DEF_MOVE_AMOUNT = 5;

    /**
     * Image associated with paddle
     */
    private ImageView m_ImagePaddleFace;
    /**
     * Paddle's layout x property
     */
    private SimpleDoubleProperty m_XProperty;
    /**
     * Paddle's layout y property
     */
    private SimpleDoubleProperty m_YProperty;

    /**
     * This constructor creates the regular paddle type, whose viewing is expected to be a rectangle with an image attaches to it.
     * @param model paddle model
     * @param container defined paddle movement range
     * @param image expected paddle image
     * @param group expected viewing group
     * @see PaddleView#PaddleView(PaddleModel, Rectangle, int, Group)
     */
    public ImagePaddleView(PaddleModel model, Rectangle container, Image image, Group group){
       super(model,container,DEF_MOVE_AMOUNT,group);
        makePaddleFaceImage(model,image);
    }

    /**
     * This function helps create the associated paddle image.
     * @param model paddle model
     * @param image expected image
     */
    protected void makePaddleFaceImage(PaddleModel model,Image image){

        m_ImagePaddleFace = new ImageView();
        m_ImagePaddleFace.setImage(image);
        m_ImagePaddleFace.setFitWidth(model.getM_Width());
        m_ImagePaddleFace.setPreserveRatio(true);

        double ratio = image.getHeight()/image.getWidth();
        double height = ratio * model.getM_Width();

        m_ImagePaddleFace.layoutXProperty().bind(m_XProperty);
        /*
        set the image above the paddle face
         */
        m_ImagePaddleFace.layoutYProperty().bind(m_YProperty.add(-height));
        super.getM_Group().getChildren().add(super.getM_PaddleFace());
        super.getM_Group().getChildren().add(m_ImagePaddleFace);
    }

    /**
     * This function makes the associated paddle face.
     * @param model paddle model
     * @return built paddle face with defined width and height
     * @see PaddleView#makePaddleFace(PaddleModel)
     */
    @Override
    public Shape makePaddleFace(PaddleModel model){

        m_XProperty = new SimpleDoubleProperty();
        m_YProperty = new SimpleDoubleProperty();
        Point2D ballPoint = model.getM_PaddlePoint();
        int width = model.getM_Width();
        int height = model.getM_Height();
        Point2D p = new Point2D((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        Rectangle paddleFace = new Rectangle(p.getX(),p.getY(),width,height);
        m_XProperty.set(p.getX());
        m_YProperty.set(p.getY());
        return paddleFace ;
    }





    /**
     * This function implements the method defined in the super class.<br>
     * It helps update and adjust the paddle image's view by updating the property that stands for the image position.
     * @param p designed position
     * @see PaddleView#moveTo(Point2D)
     */
    @Override
    public void moveTo(Point2D p) {

        super.moveTo(p);
        Point2D paddlePoint = super.getM_PaddleModel().getM_PaddlePoint();
        updateView(paddlePoint);
    }

    /**
     * This function implements the method defined in the super class.<br>
     * It helps update and adjust the paddle image's view by updating the property that stands for the image position.
     * @see PaddleView#move()
     */
    @Override
    public void move(){
        super.move();
        Point2D paddlePoint = super.getM_PaddleModel().getM_PaddlePoint();
        updateView(paddlePoint);

    }

    /**
     * This is the helper function to update the paddle's viewing position as well as its related image's position.
     * @param paddlePoint paddle's point
     */
    private void updateView(Point2D paddlePoint) {
        Rectangle paddleFace =(Rectangle) super.getM_PaddleFace();
        m_XProperty.set(paddleFace.getX());
        m_YProperty.set(paddleFace.getY());
    }

    /**
     * This function gets the image associated with the paddle face.
     * @return image associated with the paddle face
     */
    public ImageView getM_ImagePaddleFace(){
        return m_ImagePaddleFace;
    }
}
