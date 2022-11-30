package project.chenlin_17.Views;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.util.Random;
/**
 * This crack class gets and sets the crack property.<br>
 * It is seperated from the original brick class.
 * Note: 
 * It contains changes from the awt to javafx.<br>
 * It contains changes of class variables according to Bob's code convention.
 * @author Lin CHEN- modified
 * @version Version2.0
 * <br> update: Jan 3rd
 */
 class Crack{

    /**
     * Crack's expected sections
     */
    private final int CRACK_SECTIONS = 3;

    /**
     * Crack's expected jump probability
     */
    private final double JUMP_PROBABILITY = 0.7;
    /**
     * Crack's left impact
     */
    public static final int LEFT = 10;
    /**
     * Crack's right impact
     */
    public static final int RIGHT = 20;
    /**
     * Crack's upper impact
     */
    public static final int UP = 30;
    /**
     * Crack's down impact
     */
    public static final int DOWN = 40;
    /**
     * Value for detecting the ball's vertical impact(either up or down) on the brick and generate crack directions
     */
    private static final int VERTICAL = 100;
    /**
     * Value for detecting the ball's horizontal(either left or right) impact on the brick and generate crack directions
     */
    private static final int HORIZONTAL = 200;


    /**
     * Path generated that accords to linking all the generated points together
     */
    private Path m_Crack;
    /**
     * Crack's depth
     */
    private int m_CrackDepth;
    /**
     * Steps for generating a crack and how many break lines
     */
    private int m_Steps;

    /**
     * Random number that build's the points
     */
    private static Random m_Rnd;
    /**
     * Bounds of the crack
     */
    private Brick m_Brick;


    /**
     * This constructor sets the basic setting for the crack.
     * @param crackDepth crack's depth
     * @param steps steps for making the crack
     * @param brick crack's boundary
     */
    public Crack(int crackDepth, int steps, Brick brick) {
        m_Rnd = new Random();
        m_Crack = new Path();
        m_CrackDepth = crackDepth;
        m_Steps = steps;
        m_Brick = brick;
    }

    /**
     * This function gets the drawn path lines.
     * @return drawn lines
     */
    public Path draw(){
        return m_Crack;
    }


    /**
     * This function resets the crack by making a new one.
     */
    public void reset(){
        /**
         * Original source code:
         * crack.reset();
         */
        m_Crack = new Path();
    }

    /**
     * This function makes a new crack using random points.
     * <pre>{@code
     *  Modified from the original source code:
     *  protected void makeCrack(Point2D point, int direction){
     *             Rectangle bounds = Brick.this.brickFace.getBounds();
     *
     *             Point impact = new Point((int)point.getX(),(int)point.getY());
     *             Point start = new Point();
     *             Point end = new Point();
     *
     *
     *             switch(direction){
     *                 case LEFT:
     *                     start.setLocation(bounds.x + bounds.width, bounds.y);
     *                     end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
     *                     Point tmp = makeRandomPoint(start,end,VERTICAL);
     *                     makeCrack(impact,tmp);
     *
     *                     break;
     *                 case RIGHT:
     *                     start.setLocation(bounds.getLocation());
     *                     end.setLocation(bounds.x, bounds.y + bounds.height);
     *                     tmp = makeRandomPoint(start,end,VERTICAL);
     *                     makeCrack(impact,tmp);
     *
     *                     break;
     *                 case UP:
     *                     start.setLocation(bounds.x, bounds.y + bounds.height);
     *                     end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
     *                     tmp = makeRandomPoint(start,end,HORIZONTAL);
     *                     makeCrack(impact,tmp);
     *                     break;
     *                 case DOWN:
     *                     start.setLocation(bounds.getLocation());
     *                     end.setLocation(bounds.x + bounds.width, bounds.y);
     *                     tmp = makeRandomPoint(start,end,HORIZONTAL);
     *                     makeCrack(impact,tmp);
     *
     *                     break;
     *
     *             }
     *         }

     * }
     * </pre>
     * Modified:   Point -> Point2D<br>
     * Rectangle -> Bounds
     * @param point previous point of the crack
     * @param direction expected direction of the generated crack
     */
    protected void makeCrack(Point2D point, int direction){
        /*
         * Original source code:
         * Rectangle bounds = Brick.this.m_BrickFace.getBounds();
         *
         * Rectangle -> Bounds
         */
        Bounds bounds = m_Brick.getM_BrickFace().getLayoutBounds();

        /*
         * Original source code:
         * Point impact = new Point((int)point.getX(),(int)point.getY());
         * Point start = new Point();
         * Point end = new Point();
         *
         * Point -> Point2D
         * Note: cast is not needed here
         */

        Point2D impact = new Point2D(point.getX(), point.getY());
        Point2D start = new Point2D(0,0);
        Point2D end = new Point2D(0,0);
        Point2D tmp;

        switch(direction){
            case LEFT:
                /*
                 * Original source code:
                 * start.setLocation(bounds.x + bounds.width, bounds.y);
                 * end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                 *
                 * Note: bounds.getMinX()+bounds.getWidth() = bounds.getMaxX();
                 * Note: bounds.getMinX()+bounds.getWidth() = bounds.getMaxY();
                 */
                start =start.add(bounds.getMaxX(),bounds.getMinY());
                end= end.add(bounds.getMaxX(),bounds.getMaxY());

                tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;

            case RIGHT:
                /*
                 * Original source code:
                 * start.setLocation(bounds.getLocation());
                 * end.setLocation(bounds.x, bounds.y + bounds.height);
                 *
                 * Note: bounds.getMinY()+bounds.getHeight() = bounds.getMaxY();
                 */
                start = start.add(bounds.getMinX(),bounds.getMinY());
                end = end.add(bounds.getMinX(),bounds.getMaxY());
                tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);
                break;
            case UP:
                /*
                 * Original source code:
                 * start.setLocation(bounds.x, bounds.y + bounds.height);
                 * end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                 *
                 * Note: bounds.getMinY()+bounds.getHeight() = bounds.getMaxY();
                 * Note: bounds.getMinX()+bounds.getWidth() = bounds.getMaxX();
                 */

                start = start.add(bounds.getMinX(),bounds.getMaxY());
                end = end.add(bounds.getMaxX(),bounds.getMaxY());
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);
                break;
            case DOWN:
                /*
                 * Original source code:
                 * start.setLocation(bounds.getLocation());
                 * end.setLocation(bounds.x + bounds.width, bounds.y);
                 *
                 * Note: bounds.getMinX()+bounds.getWidth() = bounds.getMaxX();
                 */
                start = start.add(bounds.getMinX(),bounds.getMinY());
                end = end.add(bounds.getMaxX(),bounds.getMinY());
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);

                break;

        }
    }

    /**
     * This function makes the crack from a start point to the end point.
     *
     * <pre>{@code
     * Modified from source code:
     *  protected void makeCrack(Point start, Point end){
     *
     *             GeneralPath path = new GeneralPath();
     *
     *
     *             path.moveTo(start.x,start.y);
     *
     *             double w = (end.x - start.x) / (double)steps;
     *             double h = (end.y - start.y) / (double)steps;
     *
     *             int bound = crackDepth;
     *             int jump  = bound * 5;
     *
     *             double x,y;
     *
     *             for(int i = 1; i < steps;i++){
     *
     *                 x = (i * w) + start.x;
     *                 y = (i * h) + start.y + randomInBounds(bound);
     *
     *                 if(inMiddle(i,CRACK_SECTIONS,steps))
     *                     y += jumps(jump,JUMP_PROBABILITY);
     *
     *                 path.lineTo(x,y);
     *
     *             }
     *
     *             path.lineTo(end.x,end.y);
     *             crack.append(path,true);
     *         }
     * }

     * </pre>
     * Modified:  GeneralPath -> Path
     * @param start start point of the crack
     * @param end end point of the crack
     */
    protected void makeCrack(Point2D start, Point2D end){

        /*
         * Original source code:
         * GeneralPath path = new GeneralPath();
         *
         * GeneralPath -> Path
         *
         * Modified: 
         * Original source code:
         * path.moveTo(start.x,start.y);
         *
         * Note: Path includes functions that allow to append new path
         */

        m_Crack.getElements().add(new MoveTo(start.getX(),start.getY()));

        double w = (end.getX() - start.getX()) / (double)m_Steps;
        double h = (end.getY() - start.getY()) / (double)m_Steps;

        int bound = m_CrackDepth;
        int jump  = bound * 5;

        double x,y;

        for(int i = 1; i < m_Steps;i++){

            /*
             * Original source code:
             * x = (i * w) + start.x;
             * y = (i * h) + start.y + randomInBounds(bound);
             */
            x = (i * w) + start.getX();
            y = (i * h) + start.getY() + randomInBounds(bound);

            if(inMiddle(i,CRACK_SECTIONS,m_Steps))
                y += jumps(jump,JUMP_PROBABILITY);
            /*
             * Original source code:
             * path.lineTo(x,y);
             *
             * Note: Path includes functions that allows to append new path
             */
            m_Crack.getElements().add(new LineTo(x,y));
        }
        /*
         * Original source code:
         * path.lineTo(end.x,end.y);
         *
         * Original source code:
         * crack.append(path,true);
         *
         * Path includes functions that allows to append new path
         */

    }

    /**
     * This function generates a random point in the bound
     * @param bound brick boundary value
     * @return randomly assigned crack point value
     */
    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return m_Rnd.nextInt(n) - bound;
    }

    /**
     * This is the helper function that judges whether the position is in bound.
     * @param i current point number
     * @param m_Steps steps for making the crack
     * @param divisions division number
     * @return result of whether in boundary
     */
    private boolean inMiddle(int i,int m_Steps,int divisions){
        int low = (m_Steps / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    /**
     * This is the helper function of setting points in bounds.
     * @param bound bound number
     * @param probability probability
     * @return generate int
     */
    private int jumps(int bound,double probability){

        if(m_Rnd.nextDouble() > probability)
            return randomInBounds(bound);
        return  0;

    }

    /**
     * This is the helper function makes random points according to the direction.
     * Modified:  Point -> Point2D
     * @param from start point
     * @param to end point
     * @param direction expected direction
     * @return generated point
     */
    private Point2D makeRandomPoint(Point2D from,Point2D to, int direction){

        Point2D out = new Point2D(0,0);
        int pos;


        switch(direction){
            case HORIZONTAL:
                /**
                 * Modified: 
                 * Original source code:
                 * pos = m_Rnd.nextInt(to.x - from.x) + from.x;
                 * out.setLocation(pos,to.y);
                 *
                 * Note: setLocation is not usable in javaFX's Point2D package
                 */
                pos = m_Rnd.nextInt((int)(to.getX() - from.getX())) + (int)from.getX();
                out = out.ZERO.add(pos,to.getY());

                break;
            case VERTICAL:
                /**
                 * Modified: 
                 * Original source code:
                 * pos = m_Rnd.nextInt(to.y - from.y) + from.y;
                 * out.setLocation(to.x,pos);
                 *
                 * Note: setLocation is not usable in javaFX's Point2D package
                 */
                pos = m_Rnd.nextInt((int)(to.getY() - from.getY())) + (int)from.getY();
                out= out.ZERO.add(to.getX(),pos);
                break;
        }
        return out;
    }


}
