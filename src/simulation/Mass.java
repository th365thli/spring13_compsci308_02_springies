package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;


/**
 * Details a mass class that has forces acting on it
 * 
 * @author Jerry Li & Bill Muensterman
 */
public class Mass extends Sprite {
   
    
    /**
     * Size of object
     */
    public static final Dimension DEFAULT_SIZE = new Dimension(16, 16);
    /**
     * Picture of object
     */
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("mass.gif");
    /**
     * Angle value to correct original faulty code
     */
    public static final int ANGLE_CORRECT = 180;
    
    private double myMass;
    private Vector myAcceleration;

    /**
     * Constructs a mass based on coordinates, mass value, viscosity, and gravity acting
     * on it
     * @param x         x coordinate
     * @param y         y coordinate
     * @param mass      mass value
     */
    public Mass (double x, double y, double mass) {
        super(DEFUALT_IMAGE, new Location(x, y), DEFAULT_SIZE);
        myMass = mass;
        myAcceleration = new Vector();

    }

    /**
     * Returns the mass of the mass
     * 
     * @return myMass
     */
    public double getMyMass () {
        return myMass;
    }
    
    /**
     * Get the acceleration of oject
     * @return
     */
    public Vector getAcceleration () {
        return myAcceleration;
    }
    
    /**
     * Change acceleration of object
     * @param v         The acceleration changed to
     */
    public void changeAcceleration (Vector v) {
        myAcceleration = v;
    }

    /**
     * Updates mass position based on forces acting upon it.
     * Calls update methods for gravity, viscosity, wall repulsion
     * @param elapsedTime       Framerate
     * @param bounds            size of simulation
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        getBounce(bounds);
        // convert force back into Mover's velocity
        getVelocity().sum(myAcceleration);
        myAcceleration.reset();
        // move mass by velocity
        super.update(elapsedTime, bounds);
    }

    /**
     * Paint mass on canvas
     * @param pen       The graphics pen
     */
    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(Color.BLACK);
        pen.fillOval((int) getLeft(), (int) getTop(), (int) getWidth(), (int) getHeight());
    }

    /**
     * Use the given force to change this mass's acceleration.
     * @param force        The force being applied
     */
    public void applyForce (Vector force) {
        if (myMass >= 0) {
            myAcceleration.sum(force);
        }
    }

    /**
     * Convenience method.
     * @param other          The mass object
     */
    public double distance (Mass other) {
        // this is a little awkward, so hide it
        return new Location(getX(), getY()).distance(new Location(other.getX(), other.getY()));
    }

    /**
     * Check if ball is out of bounds, and "bounces" it if so
     * 
     * @param bounds
     */
    private void getBounce (Dimension bounds) {
        if (getBottom() >= bounds.height || getTop() <= 0) {
            getVelocity().setDirection(-getVelocity().getDirection());
        }
        else if (getRight() >= bounds.width || getRight() <= 0) {
            getVelocity().setDirection(ANGLE_CORRECT - getVelocity().getDirection());
        }
    }
}
