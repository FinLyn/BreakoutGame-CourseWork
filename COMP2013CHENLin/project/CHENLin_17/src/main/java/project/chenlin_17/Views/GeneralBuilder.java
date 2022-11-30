package project.chenlin_17.Views;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import project.chenlin_17.GameApplication;
import project.chenlin_17.Models.BallModel;
import project.chenlin_17.Models.PaddleModel;
import project.chenlin_17.tools.BrickFactory;
import project.chenlin_17.tools.SoundEffect;

import java.util.ArrayList;
/**
 * This class is the builder using geometry class to build the scene.
 * @author Lin CHEN - modified
 * @version Version1.0
 * <br> update: December 29th
 */
public class GeneralBuilder extends WorldBuilder {
    /**
     * Use number 1 to stand for CLAY Brick type.<br>
     * Increases readability for brick type.
     */
    private static final int CLAY = 1;
    /**
     * Use number 2 to stand for STEEL Brick type.<br>
     * Increases readability for brick type.
     */
    private static final int STEEL = 2;
    /**
     * Use number 3 to stand for CEMENT Brick type.<br>
     * Increases readability for brick type.
     */
    private static final int CEMENT = 3;

    /**
     * whole brickcount is set for defined value 30
     */
    private static final int BRICKCOUNT= 30;
    /**
     * brickline is set for defined value 3
     */
    private static final int BRICKLINE = 3;
    /**
     * ratio is set for defined value 3
     */
    private static final double RATIO =3;


    /**
    Component ball
     */
   private BallView m_BallView;
    /**
     Component paddle
    */
    private PaddleView m_PaddleView;
    /**
     Component background image
  */
    private ImageView m_Imageview;
    /**
     * Option for wall set choosing
     */
    private int m_Options;
    /**
     * Image for wall viewing
     */
    private ArrayList<Image> m_Image;



    /**
     *  This constructor initializes the basic setting for general builder.
     */
    public GeneralBuilder(){
        super();
        m_Image= new ArrayList<>();
        m_Imageview = new ImageView();
    }
    /**
     * This function implements the abstract sound builder step in the parent class.<br>
     * It uses the default background sound.
     */
    @Override
    public void buildSound() {
        m_World.setM_BackgroundSound(SoundEffect.BACKGROUND);
    }

    /**
     * This function implements the abstract wall builder step in the parent class.<br>
     * It sets brick walls for all the levels, not just the level the player choose.
     * @param drawArea the area used for showing bricks
     * @param options player's choice for wall set choose
     */
    @Override
    public void buildWallGroups(Rectangle drawArea, int options) {
        m_Options = options;
        /*
        if is not the first option, make the image brick instead of the normal brick
         */
        if(options!=0) makeImageList(options);
        /*
        set brick views for all level
         */
        for(int i = 0; i< 3 ; i++) {
            Group brickGroup = new Group();
            ArrayList<Brick> brickWallList = makeLevel(i,drawArea,BRICKCOUNT,BRICKLINE,RATIO,brickGroup);
            m_BrickWallGroup.add(brickWallList);
            m_WallGroups.add(brickGroup);
        }
        m_World.setM_DrawArea(drawArea);
    }

    /**
     * This function implements the abstract ball builder step in the parent class.<br>
     * It sets the geometry ball view.<br>
     * The ball's speed is also initialized here.
     * @param model initial model for ball
     * @param color the player chosen color
     */
    @Override
    public void buildBall(BallModel model, Color color) {
        Point2D center = model.getM_Center();
        m_World.setM_BallPoint(center);
        m_BallView = new RubberBallView(model,color,m_Group);
        /*
        set random ball speed
         */
        int speedX,speedY;
        do{
            speedX = m_Rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -m_Rnd.nextInt(3);
        }while(speedY == 0);

        m_BallView.setSpeed(speedX,speedY);
        m_World.setM_Ball(m_BallView);
    }

    /**
     * This function implements the abstract paddle builder step in the parent class.<br>
     * It sets the paddle color according to the player defined color.
     * @param model paddle model
     * @param color the default paddle color for image paddle
     * @param image player chosen paddle image
     * @param drawArea paddle container to define the movement
     */

    @Override
    public void buildPaddle(PaddleModel model, Color color, Image image, Rectangle drawArea) {
        m_PaddleView = new RegularPaddleView(model,drawArea,color,m_Group);
        m_World.setM_PlayerView(m_PaddleView);
    }

    /**
     * This function implements the abstract background builder step in the parent class.<br>
     *  It sets the default background image of the game.
     * @param height background image height
     * @param width background image width
     */
    @Override
    public void buildBackground(double height, double width) {
        Image image = new Image(GameApplication.class.getResource("Pictures/Background/starW.png").toExternalForm());
        m_Imageview.setImage(image);
        m_Imageview.setFitHeight(height);
        m_Imageview.setFitWidth(width);
        m_Imageview.preserveRatioProperty();
        m_Group.getChildren().add(m_Imageview);
        m_World.setM_Background(m_Imageview);
    }


    /**
     * This function gets the builder's type.<br>
     * It is used for character option page.
     * @return the builder type
     */
    @Override
    public String getType() {
        return "General";
    }

    /**
     * This function helps build the viewing of all the bricks for the wall
     * @param level game level
     * @param drawArea wall size
     * @param brickCount brick number
     * @param lineCount brick line
     * @param brickDimensionRatio brick height/ brick width
     * @param group wall group
     * @return arrayLists with built
     */
    public ArrayList<Brick> makeLevel(int level,Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio,Group group){
        int brickContant = brickCount;
        brickContant -= brickContant % lineCount;
        int brickOnLine = brickContant / lineCount;
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickDimensionRatio;
        brickContant += lineCount / 2;
        Dimension2D brickSize = new Dimension2D((int) brickLen,(int) brickHgt);

        ArrayList<Brick> tmp;

        /*
        make different level
         */
        switch (level){
            case 0:
                tmp = makeBrickLevel(brickContant,brickOnLine,brickSize,CLAY,CLAY,group);
                break;
            case 1:
                tmp = makeBrickLevel(brickContant,brickOnLine,brickSize,CLAY,CEMENT,group);
                break;
            case 2:
                tmp = makeBrickLevel(brickContant,brickOnLine,brickSize,CLAY,STEEL,group);
                break;
            case 3:
                tmp = makeBrickLevel(brickContant,brickOnLine,brickSize,STEEL,CEMENT,group);
                break;
            default:
                throw new IllegalStateException("Unexpected value: "+ level);
        }
        return tmp;
    }

    /**
     * This function helps build the wall level by level
     * Modified:  makeSingleLevel, makeChessLevel are combined to this function. There is no need for seperating single level
     * @param brickCount total brick number
     * @param brickOnLine brick number per line
     * @param brickSize contains brick's width and height property
     * @param typeA expected brick type
     * @param typeB expected brick type
     * @param group expected viewing group
     * @return walls with built bricks
     */
    public ArrayList<Brick> makeBrickLevel(int brickCount, int brickOnLine, Dimension2D brickSize, int typeA, int typeB, Group group){
        // if brickCount is not divisible by line count, brickCount is adjusted to the biggest multiple of lineCount smaller then brickCount
        double HALF = 0.5;
        int centerLeft = (int)(brickOnLine * HALF) - 1;
        int centerRight = (int)(brickOnLine * HALF) + 1;

        ArrayList<Brick> tmp= new ArrayList<>();

        Point2D p = new Point2D(0,0);

        int i;
        for(i = 0; i < brickCount; i++){
            int line = i / brickOnLine;
            if((line+1)*brickOnLine>brickCount)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickSize.getWidth();
            x = isOdd(line) ? x : (x - (brickSize.getWidth()*HALF));
            double y = (line) * brickSize.getHeight();
            /*
             * Original source code:
             * p.setLocation(x,y);
             */
            p = p.ZERO.add(x,y);
            boolean b = ((isOdd(line) && isOdd(i)) || (isOdd(line) && posX > centerLeft && posX <= centerRight));
            tmp.add(b ?  makeBrick(p,brickSize,typeA,group) : makeBrick(p,brickSize,typeB,group));
        }
        int DOUBLE = 2;
        for(double y = brickSize.getHeight();i < brickCount;i++, y += DOUBLE *brickSize.getHeight()){
            double x = (brickOnLine * brickSize.getWidth()) - (brickSize.getWidth()*HALF);
            /*
             * Original source code:
             * p.setLocation(x,y);
             */
            p = p.ZERO.add(x,y);
            tmp.add(makeBrick(p,brickSize,typeA,group));
        }
        return tmp;
    }
    /**
     * This function makes the brick view for the wall.<br>
     * If it is the first option, the geometry brick is built.<br>
     * Else the image brick is built.
     * @param point brick center point
     * @param size brick height and brick width
     * @param type brick type
     * @param group expected viewing group
     * @return built brick with defined center, height, width and type
     * Modified: 
     */
    public Brick makeBrick(Point2D point, Dimension2D size, int type, Group group){
        Brick out;
        switch(type){
            case CLAY:
                /*
                 * Original source code:
                 * out = new Brick2(point,size);
                 */
                out = m_Options ==0? new ClayBrick(point, size, group): new ClayBrick(point,size,m_Image.get(0),group);
                break;
            case STEEL:
                /*
                 * Original source code:
                 * out = new Brick3(point,size);
                 */
                out = m_Options ==0? new SteelBrick(point, size, group): new SteelBrick(point,size,m_Image.get(2),group);
                break;
            case CEMENT:
                /*
                 * Original source code:
                 * out = new Brick1(point, size);
                 */
                out = m_Options ==0? new CementBrick(point, size, group): new CementBrick(point,size,m_Image.get(1),group);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }

    /**
     * This helper function helps set all the brick image set according to the chosen wall set index.
     * @param options player chosen wall set index
     */
    private void makeImageList(int options){
        switch (options){
            case 1:
                m_Image = BrickFactory.Space.getM_Image();
                break;
            case 2:
                m_Image = BrickFactory.Soccer.getM_Image();
                break;
            case 3:
                m_Image = BrickFactory.City.getM_Image();

                return;

        }

    }

    /**
     * This is a helper function for judging whether the number is odd or not.
     * @param number received number
     * @return judgement result
     */
    private boolean isOdd(int number){
        return number % 2 == 0;
    }

}
