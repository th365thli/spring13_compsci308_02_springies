package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import view.Canvas;


// test commit

/**
 * Creates Springies universe
 * 
 * @author Jerry Li
 */
public class Model {
    // bounds and input for game
    private Canvas myView;
    // simulation state
    private List<Mass> myMasses;
    private List<Spring> mySprings;

    private Gravity myGravity;
    private Viscosity myViscosity;
    private WallRepulsion myWallRepulsion;
    private CenterOfMass myCenterOfMass;

    private double myTotalMass;
    private double myTotalXMass;
    private double myTotalYMass;

    private double myCenterXMass;
    private double myCenterYMass;

    private double myGravitySpeed = 0;
    private double myViscosityValue = .5;
    private double myWallRepulsionFactor = -.01;

    private double myCenterExponentValue = .01;

    private static final int LOAD_NEW = KeyEvent.VK_N;
    private static final int GRAVITY_TOGGLE = KeyEvent.VK_G;
    private static final int VISCOSITY_TOGGLE = KeyEvent.VK_V;
    private static final int CENTER_OF_MASS_TOGGLE = KeyEvent.VK_M;
    private static final int TOP_WALL = KeyEvent.VK_1;
    private static final int BOTTOM_WALL = KeyEvent.VK_2;
    private static final int RIGHT_WALL = KeyEvent.VK_3;
    private static final int LEFT_WALL = KeyEvent.VK_4;

    /**
     * Create a game of the given size with the given display for its shapes.
     * 
     * @param canvas the game canvas
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        myGravity = new Gravity(myGravitySpeed);
        myViscosity = new Viscosity(myViscosityValue);
        myWallRepulsion = new WallRepulsion(myWallRepulsionFactor);
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
     * Draw all elements of the simulation.
     * 
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
     * 
     * @param elapsedTime
     */
    public void update (double elapsedTime) {
        int key = myView.getLastKeyPressed();
        calculateCenterXMass();
        calculateCenterYMass();

        toggleGravity(key);
        loadFile(key);
        toggleViscosity(key);
        toggleCenterOfMass(key);
        toggleTopWallRepulsion(key);
        toggleBottomWallRepulsion(key);
        toggleRightWallRepulsion(key);
        toggleLeftWallRepulsion(key);

        Dimension bounds = myView.getSize();
        for (Spring s : mySprings) {
            s.update(elapsedTime, bounds);
        }
        for (Mass m : myMasses) {
            myWallRepulsion.update(bounds, m);
            myGravity.update(m);
            myViscosity.update(m);
            myCenterOfMass.update(m, myCenterXMass, myCenterYMass);
            m.update(elapsedTime, bounds);
        }
    }

    public void toggleTopWallRepulsion (int key) {
        if (key == TOP_WALL) {
            myView.setLastKeyPressed();
            myWallRepulsion.toggleTopRepulsion();
        }
    }

    public void toggleBottomWallRepulsion (int key) {
        if (key == BOTTOM_WALL) {
            myView.setLastKeyPressed();
            myWallRepulsion.toggleBottomRepulsion();
        }
    }

    public void toggleRightWallRepulsion (int key) {
        if (key == RIGHT_WALL) {
            myView.setLastKeyPressed();
            myWallRepulsion.toggleRightRepulsion();
        }
    }

    public void toggleLeftWallRepulsion (int key) {
        if (key == LEFT_WALL) {
            myView.setLastKeyPressed();
            myWallRepulsion.toggleLeftRepulsion();
        }
    }

    public void toggleCenterOfMass (int key) {
        if (key == CENTER_OF_MASS_TOGGLE) {
            myView.setLastKeyPressed();
            myCenterOfMass.toggleCenterOfMass();
        }
    }

    public void toggleViscosity (int key) {
        if (key == VISCOSITY_TOGGLE) {
            myView.setLastKeyPressed();
            myViscosity.toggleViscosity();
        }
    }

    public void toggleGravity (int key) {
        if (key == GRAVITY_TOGGLE) {
            myView.setLastKeyPressed();
            myGravity.toggleGravity();
        }
    }

    public void loadFile (int key) {
        if (key == LOAD_NEW) {
            myView.setLastKeyPressed();
            // reset();
            myView.loadModel();
        }
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
        // System.out.println(centerXMass);
        myTotalXMass = 0;
        myTotalMass = 0;
    }

    /**
     * Add given mass to this simulation.
     * 
     * @param mass
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }

    /**
     * Add given spring to this simulation.
     * 
     * @param spring
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }

    public void reset () {
        myMasses.clear();
        mySprings.clear();
    }
}
