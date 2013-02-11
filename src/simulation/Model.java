package simulation;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import util.Vector;
import view.Canvas;
import java.util.*;

//test commit

/**
 * Creates Springies universe
 * @author Jerry Li
 */
public class Model {
    // bounds and input for game
    private Canvas myView;
    // simulation state
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    
    private Gravity myGravity;

    private double myTotalMass;
    private double myTotalXMass;
    private double myTotalYMass;

    private double myCenterXMass;
    private double myCenterYMass;
    
    private double myGravitySpeed = 7;
    
    private boolean myGravityOn = true;

    private double myCenterExponentValue = 2;
    
    private static final int LOAD_NEW = KeyEvent.VK_N;
    private static final int GRAVITY_TOGGLE = KeyEvent.VK_G;

    /**
     * Create a game of the given size with the given display for its shapes.
     * @param canvas the game canvas
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        myGravity = new Gravity(myGravitySpeed);

    }

    /**
     * Return the list of masses instantiated
     * 
     * @return
     */
    public List<Mass> getMasses () {
        return myMasses;
    }

    /**
     * Draw all elements of the simulation.
     * @param pen Graphics2D pen
     */
    public void paint (Graphics2D pen) {
        for (Spring s : mySprings) {
            s.paint(pen);
        }
        for (Mass m : myMasses) {
            m.paint(pen);
        }
    }

    /**
     * Calculate total mass in model
     */
    public void calcTotalMass () {
        for (Mass m : myMasses) {
            double mass = m.getMyMass();
            myTotalMass += mass;
        }
    }

    /**
     * Update simulation for this moment, given the time since the last moment.
     * @param elapsedTime 
     */
    public void update (double elapsedTime) {
        int key = myView.getLastKeyPressed();
        
        if (key == GRAVITY_TOGGLE){
            if (myView.getKeysPressed().contains(key)){
              myGravity.toggleGravity();
            }        
        }
        
        if (key == LOAD_NEW) {
           if (myView.getKeysPressed().contains(key)){
               myView.clearKeys();
               reset();
               myView.loadModel();
           }
        }
        System.out.println(key);
        calculateCenterXMass();
        calculateCenterYMass();

        Dimension bounds = myView.getSize();
        for (Spring s : mySprings) {
            s.update(elapsedTime, bounds);
        }
        for (Mass m : myMasses) {
            if (myGravityOn) {
                myGravity.update(m);
            }
            m.update(elapsedTime, bounds);
            applyCenterOfMass(m);
        }
    }
    
    /**
     * Set the exponent value used in calculating force
     * @param x
     */
    public void setCenterExponent(double x) {
        myCenterExponentValue = x;
    }

    /**
     * Applies a center of mass force that attracts all masses to the calculated
     * center of mass
     * @param m
     */
    public void applyCenterOfMass(Mass m) {
        double xMass = m.getX();
        double yMass = m.getY();

        Point2D target = new Point2D.Double(xMass, yMass);
        Point2D source = new Point2D.Double(myCenterXMass, myCenterYMass);

        double distance =
                Math.sqrt((myCenterXMass - xMass) * (myCenterXMass - xMass) + 
                          (myCenterYMass - yMass) * (myCenterYMass - yMass));
        double centExpVal = myCenterExponentValue;
        double force = 1 / Math.pow(distance, centExpVal);

        Vector v = new Vector(target, source);
        v.setMagnitude(force);

        m.applyForce(v);
    }

    /**
     * Calculate the center of mass, the y coordinate
     */
    public void calculateCenterYMass () {
        calcTotalMass();

        for (Mass m : myMasses) {
            double y = m.getY();
            double mass = m.getMyMass();
            myTotalYMass += y * mass;
        }

        myCenterYMass = myTotalYMass / myTotalMass;
        // System.out.println(centerYMass);
        myTotalYMass = 0;
        myTotalMass = 0;
    }

    /**
     * Calculate the center of mass, the x coordinate
     */
    public void calculateCenterXMass () {
        calcTotalMass();

        for (Mass m : myMasses) {
            double x = m.getX();
            double mass = m.getMyMass();
            myTotalXMass += x * mass;
        }

        myCenterXMass = myTotalXMass / myTotalMass;
        // System.out.println(centerXMass);
        myTotalXMass = 0;
        myTotalMass = 0;
    }

    /**
     * Add given mass to this simulation.
     * @param mass
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }

    /**
     * Add given spring to this simulation.
     * @param spring
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }
    
    public void reset(){
        myMasses.clear();
        mySprings.clear();
    }
}
