package project.chenlin_17.Views;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import project.chenlin_17.Models.PaddleModel;
/**
 * This is the  concrete class extends from the paddleView class.<br>
 * It creates the paddle with geometry class using color and shape.
 * Note:  It contains changes from the awt to javafx.<br>
 * It contains changes of class variables according to Bob's code convention.
 *
 * @see PaddleView
 *
 * @author Lin CHEN - modified
 * @version Version2.0
 * <br> update: Jan 3rd
 */
    class RegularPaddleView extends PaddleView{
        /**
         * Regular paddle's default color value
         */
    private final Color BORDER_COLOR = Color.GREEN.darker().darker();
    /**
     * Defined inner color
     */
    private static final Color INNER_COLOR = Color.GREEN;

    /**
     * Defined paddle movement amount
     */
    private static final int DEF_MOVE_AMOUNT = 5;

    /**
     * This generates the basic paddle view with defined color.
     * @param model paddle model
     * @param container paddle's movement boundary
     * @param group paddle's viewing group
     * @see PaddleView#PaddleView(PaddleModel, Rectangle, int, Group)
     */
    RegularPaddleView(PaddleModel model, Rectangle container, Group group){
        super(model,container,DEF_MOVE_AMOUNT,group);
        super.setM_Boarder(BORDER_COLOR);
        super.setM_Inner(INNER_COLOR);
        super.getM_Group().getChildren().add(super.getM_PaddleFace());
    }
    /**
     * This generates the basic paddle view with user set color.
     * @param model paddle model
     * @param container paddle's movement boundary
     * @param group paddle's viewing group
     * @param color  paddle's color chosen by the user
     * @see PaddleView#PaddleView(PaddleModel, Rectangle, int, Group)
     */
    RegularPaddleView(PaddleModel model, Rectangle container, Color color,Group group){
        this(model, container, group);
        super.setM_Inner(color);
    }

    /**
     * This function generates the paddle's defined face.
     * @param model paddle model
     * @return paddle's shape generated with basic paddle's settings
     */
    @Override
    public Shape makePaddleFace(PaddleModel model) {

        Point2D paddlePoint = model.getM_PaddlePoint();
        int width = model.getM_Width();
        int height = model.getM_Height();
        Point2D p = new Point2D(paddlePoint.getX() - (width / 2),paddlePoint.getY());
        Rectangle paddleFace = new Rectangle(p.getX(),p.getY(),width,height);
        return  paddleFace;
    }
}
