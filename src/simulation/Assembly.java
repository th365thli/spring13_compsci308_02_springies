package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.ArrayList;
import java.util.List;
import view.Canvas;


/**
 * Details a class that contains springs and masses
 * and center of mass force
 * 
 * @author Jerry Li & Bill Muensterman
 * 
 */
public class Assembly {

    private double myCenterExponentValue = 2;

    private Canvas myView;

    // simulation state
    private List<Mass> myMasses;
    private List<Spring> mySprings;

    private CenterOfMass myCenterOfMass;

    private double myTotalMass;
    private double myTotalXMass;
    private double myTotalYMass;

    private double myCenterXMass;
    private double myCenterYMass;

    // private PointerInfo myPointer = MouseInfo.getPointerInfo();
    // private Point myPoint = myPointer.getLocation();
    private double myMouseX;
    private double myMouseY;

    private Mass myMouseMass = new FixedMass(myMouseX, myMouseY, -1);
    private Mass myClosestMassToMouse = new Mass(0, 0 ,0);
    private Spring myMouseSpring = new Spring(myMouseMass, myMouseMass, 0, 0);

    /**
     * Constructs Assembly that is displayed on canvas
     * 
     * @param canvas The view
     */
    public Assembly (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        myCenterOfMass = new CenterOfMass(myCenterExponentValue);
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
     * Returns the center of mass
     * 
     * @return
     */

    public CenterOfMass getCenterOfMass () {
        return myCenterOfMass;
    }

    /**
     * Draw all elements of the simulation.
     * 
     * @param pen Graphics2D pen
     */
    public void paint (Graphics2D pen) {
        for (Spring s : mySprings) {
            s.paint(pen);
        }
        for (Mass m : myMasses) {
            if (m != myMouseMass) {
                m.paint(pen);
            }
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
     * Updating consists of recalculating center of mass, checking mouse input,
     * and updating springs and masses
     * 
     * @param elapsedTime Framerate
     */
    public void update (double elapsedTime) {
        calculateCenterXMass();
        calculateCenterYMass();
        createSpring();
        
        Dimension bounds = myView.getSize();

        for (Spring s : mySprings) {
            s.update(elapsedTime, bounds);
        }
        for (Mass m : myMasses) {
            myCenterOfMass.update(m, myCenterXMass, myCenterYMass);
            m.update(elapsedTime, bounds);
        }
    }
    
    /**
     * Creates a pseudo mass and generates a spring to the closest
     * simulation mass. 
     */
    public void createSpring () {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        myMouseX = b.getX();
        myMouseY = b.getY();
        myMouseMass.setCenter(myMouseX, myMouseY);
        
        if (!myView.getMouseClick()) {
            findClosestMassToMouse();
        }
        myMouseSpring.setParameters(myMouseMass, myClosestMassToMouse,
                                    myMouseMass.distance(myClosestMassToMouse) / 2, 1);

        drag();
    }
    
    /**
     * Adds a pseudo mass to help generate spring. generates a spring
     * that is shown when mouse is clicked
     */
    public void drag () {
        if (myView.getMouseClick()) {
            if (!myMasses.contains(myMouseMass)) {
                myMasses.add(myMouseMass);
            }
            if (!mySprings.contains(myMouseSpring)) {
                mySprings.add(myMouseSpring);

            }
        }
        if (!myView.getMouseClick()) {
            if (myMasses.contains(myMouseMass)) {
                myMasses.remove(myMouseMass);
            }
            if (mySprings.contains(myMouseSpring)) {
                mySprings.remove(myMouseSpring);
            }
        }
    }
    
    /**
     * Finds the closest mass to mouse pointer
     * @return
     */
    public void findClosestMassToMouse () {
        double shortestDistance = -1;
        Mass closestMass = new Mass(0, 0, 0);
        for (Mass m : myMasses) {
            if (m.distance(myMouseMass) < shortestDistance || shortestDistance < 0) {
                shortestDistance = m.distance(myMouseMass);
                closestMass = m;
            }
        }
        myClosestMassToMouse = closestMass;
    }

    /**
     * Set the exponent value used in calculating force
     * 
     * @param x Center of mass force value
     */
    public void setCenterExponent (double x) {
        myCenterExponentValue = x;
    }

    /**
     * Applies a center of mass force that attracts all masses to the calculated
     * center of mass
     * 
     * @param m
     */

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
        myTotalXMass = 0;
        myTotalMass = 0;
    }

    /**
     * Add given mass to this simulation.
     * 
     * @param mass The mass object
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }

    /**
     * Add given spring to this simulation.
     * 
     * @param spring spring object
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }

    /**
     * clears Assembly of all objects
     */
    public void clear () {
        myMasses.clear();
        mySprings.clear();
    }

}
