package simulation;

import java.awt.Dimension;
import util.Vector;

/**
 * Details class that applies wall repulsion to
 * all masses
 * @author Jerry Li & Bill Muensterman
 *
 */
public class WallRepulsion {
    
    private final int myDownDirectionN = 270;
    private final int myUpDirection = 90;
    private final int myRightDirection = 0;
    private final int myLeftDirection = 180;
    
    private double myTempWallRepulsionFactor;

    private double myTopWallRepulsionFactor;
    private double myRightWallRepulsionFactor;
    private double myLeftWallRepulsionFactor;
    private double myBottomWallRepulsionFactor;
    
    /**
     * Constructs an object that applies force
     * to each mass in simulation
     * @param repulsion         repulsion force
     */
    public WallRepulsion (double repulsion) {
        myTopWallRepulsionFactor = repulsion;
        myRightWallRepulsionFactor = repulsion;
        myLeftWallRepulsionFactor = repulsion;
        myBottomWallRepulsionFactor = repulsion;
        myTempWallRepulsionFactor = repulsion;
    }
    
    /**
     * Updates state of object for each mass
     * @param bounds    size of simulation
     * @param m         the mass object
     */
    public void update (Dimension bounds, Mass m) {
        repel(bounds, m);
    }
    
    /**
     * Constructs appropriate vectors
     * @param bounds    size of simulation
     * @param m         mass object
     */
    public void wallRepulsion (Dimension bounds, Mass m) {

        double leftProximity = proximityToLeftWall(bounds, m);
        double rightProximity = proximityToRightWall(bounds, m);
        double topProximity = proximityToTopWall(bounds, m);
        double bottomProximity = proximityToBottomWall(bounds, m);

        Vector leftRepulsion =
                leftRepulsion(calculateRepulsion(leftProximity, myLeftWallRepulsionFactor));
        Vector rightRepulsion =
                rightRepulsion(calculateRepulsion(rightProximity, myRightWallRepulsionFactor));
        Vector topRepulsion =
                topRepulsion(calculateRepulsion(topProximity, myTopWallRepulsionFactor));
        Vector bottomRepulsion =
                bottomRepulsion(calculateRepulsion(bottomProximity, myBottomWallRepulsionFactor));

        m.getAcceleration().sum(leftRepulsion);
        m.getAcceleration().sum(rightRepulsion);
        m.getAcceleration().sum(topRepulsion);
        m.getAcceleration().sum(bottomRepulsion);

    }
    
    /**
     * applies repel force to mass object
     * @param bounds            size of simulation
     * @param m                 mass object
     */
    public void repel (Dimension bounds, Mass m) {
        if ((m.getLeft() > 0) &&
            (m.getRight() < bounds.width) &&
            (m.getTop() > 0) &&
            (m.getBottom() < bounds.height)) {
            wallRepulsion(bounds, m);
        }
    }

    /**
     * Gets distance from left wall
     * 
     * @param bounds    size of simulation
     * @param m         mass object
     * @return 
     */
    public double proximityToLeftWall (Dimension bounds, Mass m) {
        return bounds.width - m.getLeft();
    }

    /**
     * Gets distance from right wall
     * 
     * @param bounds    size of simulation
     * @param m         mass object
     * @return 
     */
    public double proximityToRightWall (Dimension bounds, Mass m) {
        return m.getRight();
    }

    /**
     * Gets distance from top wall
     * 
     * @param bounds    size of simulation
     * @param m         mass object
     * @return  
     */
    public double proximityToTopWall (Dimension bounds, Mass m) {
        return bounds.height - m.getBottom();
    }

    /**
     * Gets distance from bottom wall
     * 
     * @param bounds    size of simulation
     * @param m         mass of object
     * @return 
     */
    public double proximityToBottomWall (Dimension bounds, Mass m) {
        return m.getBottom();
    }

    /**
     * Calculate repulsion force
     * 
     * @param proximity proximity of mass to wall
     * @param force     force factor
     * @return 
     */
    public double calculateRepulsion (double proximity, double force) {
        return 1 / Math.pow(proximity, force);
    }

    /**
     * left wall vector
     * @param force     force factor
     * @return 
     */
    public Vector leftRepulsion (double force) {
        Vector repulsion = new Vector(myRightDirection, force);
        return repulsion;
    }
    
    /**
     * right wall vector
     * @param force     force factor
     * @return
     */
    public Vector rightRepulsion (double force) {
        Vector repulsion = new Vector(myLeftDirection, force);
        return repulsion;
    }

    /**
     * top wall vector
     * @param force     force factor
     * @return
     */
    public Vector topRepulsion (double force) {
        Vector repulsion = new Vector(myDownDirectionN, force);
        return repulsion;
    }
    
    /**
     * bottom wall vector
     * @param force     force factor
     * @return
     */
    public Vector bottomRepulsion (double force) {
        Vector repulsion = new Vector(myUpDirection, force);
        return repulsion;
    }
    
    /**
     * toggle  top wallrepulsion
     */
    public void toggleTopRepulsion () {
        if (myTopWallRepulsionFactor != 0) {
            myTopWallRepulsionFactor = 0;
        }
        else {
            myTopWallRepulsionFactor = myTempWallRepulsionFactor;
        }

    }
    
    /**
     * toggle right repulsion
     */
    public void toggleRightRepulsion () {
        if (myRightWallRepulsionFactor != 0) {
            myRightWallRepulsionFactor = 0;
        }
        else {
            myRightWallRepulsionFactor = myTempWallRepulsionFactor;
        }

    }
    
    /**
     * toggle left repulsion
     */
    public void toggleLeftRepulsion () {
        if (myLeftWallRepulsionFactor != 0) {
            myLeftWallRepulsionFactor = 0;
        }
        else {
            myLeftWallRepulsionFactor = myTempWallRepulsionFactor;
        }

    }
    
    /**
     * toggle bottom repulsion
     */
    public void toggleBottomRepulsion () {
        if (myBottomWallRepulsionFactor != 0) {
            myBottomWallRepulsionFactor = 0;
        }
        else {
            myBottomWallRepulsionFactor = myTempWallRepulsionFactor;
        }

    }
}
