package project.chenlin_17.Views;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import project.chenlin_17.GameApplication;
import javafx.scene.paint.Color;
import project.chenlin_17.Models.BallModel;
import project.chenlin_17.Models.PaddleModel;
import project.chenlin_17.tools.BrickFactory;
import project.chenlin_17.tools.SceneOptions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class is the concrete class extends from the WorldBuilder class.<br>
 * It sets image game settings instead of geometric settings.
 * @author Lin CHEN
 * @version Version1.0
 * <br> update: December 29th
 * @see WorldBuilder
 */
public class AdvancedBuilder extends WorldBuilder {
    /**
     * User's choice for theme topic
     */
    private SceneOptions m_SceneOptions;
    /**
     * Brick center for showing the brick image
     */
    private Point2D m_BrickCenter;
    /**
     * Brick's prefheight
     */
    private double m_BrickHeight;
    /**
     * Brick's prefwidth
     */
    private double m_BrickWidth;
    /**
     * Brick's halfWidth
     */
    private double m_HalfWidth;

    /**
     * Component background
     */
    private ImageView m_Imageview;
    /**
     * Helper of building brick image
     */
    private BrickFactory m_BrickFactory;
    /**
     * Component ball
     */
    private ImageBallView m_BallView;
    /**
     * Component paddle
     */
    private ImagePaddleView m_PaddleView;

    /**
     * The rate height/width
     */
    private final double HW_RATIO = 0.3;

    /**
     * This constructor initializes the player's choice for theme. Since all the later buildings will relate to the theme choice. This step is very important.
     * @param sceneOptions the player's choice for theme
     */
    public AdvancedBuilder(SceneOptions sceneOptions){

        super();
        m_Imageview = new ImageView();
        m_SceneOptions =sceneOptions;
        /*
        set BrickFactory type
         */
        setImageBrick(m_SceneOptions);
    }

    /**
     * This function implements the abstract sound builder step in the parent class.<br>
     * It builds sound according to theme topic.
     * @see WorldBuilder#buildSound()
     */
    @Override
    public void buildSound() {
        m_World.setM_BackgroundSound(m_SceneOptions.GetM_SoundFilePath());
    }

    /**
     * This function implements the abstract wall builder step in the parent class.<br>
     * It sets brick walls for all the levels, not just the level the player choose.
     * @param drawArea the area used for showing bricks
     * @param options player's choice for theme, not used in this builder type
     * @see WorldBuilder#buildWall(Rectangle, int)
     */
    @Override
    public void buildWallGroups(Rectangle drawArea, int options) {
        /*
        set brick views for all levels
         */
        for(int i = 0; i< 3 ; i++) {
            Group brickGroup = new Group();
            ArrayList<Brick> brickWallList = makeBrickWall(m_SceneOptions.GetM_WorldFilePath().get(i), drawArea.getWidth(), brickGroup);
            super.m_BrickWallGroup.add(brickWallList);
            m_WallGroups.add(brickGroup);
        }

        m_World.setM_DrawArea(drawArea);
    }

    /**
     * This function implements the abstract ball builder step in the parent class.<br>
     * It sets the image ball instead of the Ellipse ball face.<br>
     * The ball's speed is also initialized here.
     * @param model ball's model
     * @param color the player chosen color, not used in this type, since it uses the image ball
     * @see WorldBuilder#buildBall(BallModel, Color)
     */

    @Override
    public void buildBall(BallModel model, Color color) {
        Point2D center = model.getM_Center();
        m_World.setM_BallPoint(center);
        m_BallView = new ImageBallView (model,m_SceneOptions.getBallImage(),m_Group);
        m_BallView.resetSpeed();
        m_World.setM_Ball(m_BallView);
    }




    /**
     * This function implements the abstract background builder step in the parent class.<br>
     *  It sets the theme-related background image of the game.
     * @param height background image height
     * @param width background image width
     * @see WorldBuilder#buildBackground(double, double)
     */
    @Override
    public void buildBackground(double height, double width){
        Image image = new Image(GameApplication.class.getResource(m_SceneOptions.GetM_BackgroundPath()).toExternalForm());
        m_Imageview.setImage(image);
        m_Imageview.setPreserveRatio(false);
        m_Imageview.setFitWidth(width);
        m_Imageview.setFitHeight(height);
        m_Group.getChildren().add(m_Imageview);
        m_World.setM_Background(m_Imageview);
    }

    /**
     * It is a helper function of making the file scanner.
     * @param filePath world file name
     * @return scanner reader of the specific type
     */
    private Scanner makeScanner(String filePath) {
        try {
            InputStream worldSource = GameApplication.class.getResourceAsStream(filePath);
            return new Scanner(worldSource);
        } catch (Exception e) {
            System.out.println("File Invalid");
            return null;
        }
    }

    /**
     * This helper function helps build the bricks for the specific level.<br>
     * It is achieved through reading file. The world file defines the brick type and its position.<br>
     * Besides, the brick height and width is calculated using the number stored in the file.<br>
     * After reading the file, there is a convention for converting those read characters to image.
     * @param worldFilePath world file name for the bricks building
     * @param width width for wall
     * @param brickGroup brick container for the current level
     * @return bricks for current level
     */
    private ArrayList<Brick> makeBrickWall(String worldFilePath, double width, Group brickGroup){
        Scanner scanner = makeScanner(worldFilePath);
        m_BrickCenter = Point2D.ZERO;
        /*
        The first number in the file stands for how many columns for the wall
         */
        int widthLine= scanner.nextInt();
        int heightLine = scanner.nextInt();
        scanner.nextLine();
        /*
        calculate the brick width using the column number
         */
        m_BrickWidth= width/ (double)(widthLine-1);
        m_HalfWidth = m_BrickWidth * 0.5;
        /*
        brick height is associated with brick width
         */
        m_BrickHeight= m_BrickWidth * HW_RATIO;

        int makeBrickLineNumber = 0;
        /*
        read world file and make bricks
         */
        ArrayList<Brick> bricks = new ArrayList<>();
        while (scanner != null && scanner.hasNextLine()) {
            m_BrickCenter=  m_BrickCenter.ZERO.add(0,m_BrickHeight * makeBrickLineNumber);
            String rowOfBrickStrings = scanner.nextLine();
            for (int i =0 ; i < rowOfBrickStrings.length();i++) {
                /*
                _ stands for whole gap in the viewing
                 */
                if (rowOfBrickStrings.charAt(i) == '_'){
                    m_BrickCenter = m_BrickCenter.add(m_BrickWidth,0);
                }
                /*
                 stands for half gap in the viewing
                 */
                else if(rowOfBrickStrings.charAt(i) == ' '){
                    m_BrickCenter = m_BrickCenter.add(m_HalfWidth,0);
                }
                else{
                    /*
                    not the gap, make brick images according to the defined type
                     */
                    Brick newBrick = getBrick(rowOfBrickStrings.charAt(i),brickGroup);
                    bricks.add(newBrick);
                }
            }
            /*
            make new lines
             */
            makeBrickLineNumber++;
        }
        scanner.close();
       return bricks;
    }

    /**
     * This function builds the brick view according to the position and type read from the world file.<br>
     * It contains three basic types: Cement, Steel and Clay.<br>
     * It contains two different size: half and full.
     * @param brickType character read from the world file
     * @param brickGroup brick groups for each level
     * @return built brick with its type and image added
     */
    private Brick getBrick(Character brickType, Group brickGroup){
        Dimension2D halfBrickSize = new Dimension2D(m_HalfWidth,m_BrickHeight);
        Dimension2D brickSize = new Dimension2D(m_BrickWidth,m_BrickHeight);
        Image cement = m_BrickFactory.getCement();
        Image clay= m_BrickFactory.getClay();
        Image steel = m_BrickFactory.getSteel();
        Image additional = m_BrickFactory.getAdditional();

        Brick brick = null;
        /*
        half-sized cement
         */
        if(brickType == 'c'){
            brick = new CementBrick(m_BrickCenter,halfBrickSize,cement,brickGroup);
            m_BrickCenter=m_BrickCenter.add(m_HalfWidth,0);
        }
        /*
        full sized cement
         */
        else if (brickType== 'C'){
            brick = new CementBrick(m_BrickCenter,brickSize,cement,brickGroup);
            m_BrickCenter=m_BrickCenter.add(m_BrickWidth,0);
        }
        /*
        half sized steel type
         */
        else if(brickType == 's'){
            brick = new SteelBrick(m_BrickCenter,halfBrickSize,steel,brickGroup);
            m_BrickCenter=m_BrickCenter.add(m_HalfWidth,0);
        }
        /*
        full sized steel type
         */
        else if (brickType == 'S'){
            brick = new SteelBrick(m_BrickCenter,brickSize,steel,brickGroup);
            m_BrickCenter=m_BrickCenter.add(m_BrickWidth,0);
        }
        /*
        half sized clay type
         */
        else if(brickType == 'l'){
            brick = new ClayBrick(m_BrickCenter,halfBrickSize,clay,brickGroup);
            m_BrickCenter=m_BrickCenter.add(m_HalfWidth,0);
        }
        /*
        full sized clay type
         */
        else if (brickType == 'L'){
            brick = new ClayBrick(m_BrickCenter,brickSize,clay,brickGroup);
            m_BrickCenter=m_BrickCenter.add(m_BrickWidth,0);
        }
        /*
        half sized additional type
         */
        else if(brickType == 'a'){
            brick = new ClayBrick(m_BrickCenter,halfBrickSize,additional,brickGroup);
            m_BrickCenter=m_BrickCenter.add(m_HalfWidth,0);
        }
        /*
        full sized additional type
         */
        else if (brickType == 'A'){
            brick = new ClayBrick(m_BrickCenter,brickSize,additional,brickGroup);
            m_BrickCenter=m_BrickCenter.add(m_BrickWidth,0);
        }
        else{
            m_BrickCenter=m_BrickCenter.add(m_BrickWidth,0);
        }
        return brick;
    }

    /**
     * This function gets the builder's type.<br>
     * It is used for character option page.
     * @return the builder type
     */
    @Override
    public String getType(){
        return "Advanced";
    }

    /**
     * This helper function helps set all the brick image set according to the chosen theme.
     * @param sceneOptions player chosen theme
     */
    private void setImageBrick(SceneOptions sceneOptions){
        if(sceneOptions == SceneOptions.City)
            m_BrickFactory = BrickFactory.City;
        else if(sceneOptions == SceneOptions.Soccer)
            m_BrickFactory = BrickFactory.Soccer;
        else 
            m_BrickFactory = BrickFactory.Space;
        
    }
    /**
     * This function implements the abstract paddle builder step in the parent class.<br>
     * It connects the paddle image to the paddle face and sets it above the paddle face.
     * @param model the paddle model
     * @param color the expected paddle color for image paddle
     * @param image player chosen paddle image
     * @param drawArea paddle container to define the movement
     * @see WorldBuilder#buildPaddle(PaddleModel, Color, Image, Rectangle)
     */
    @Override
    public void buildPaddle(PaddleModel model, Color color, Image image, Rectangle drawArea) {

        m_PaddleView= new ImagePaddleView(model,drawArea,image,m_Group);
        m_PaddleView.setM_Inner(color);
        m_World.setM_PlayerView(m_PaddleView);
    }
}
