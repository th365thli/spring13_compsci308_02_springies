package simulation;

import java.awt.Dimension;
import util.Force;
import util.Vector;

/**
 * Details gravitational force that affects
 * all masses in simulation
 * @author Jerry Li & Bill Muensterman
 *
 */
public class Gravity extends Force {
    
    private static final int DOWN_DIRECTION = 90;
    private double myGravitySpeed;
    private double myToggleGravitySpeedHolder;
    
    /**
     * Constructs a Gravity object that 
     * applies force to all masses in
     * simulation
     * @param grav      Magnitude of the force of gravity
     */
    public Gravity (double grav) {
        super();
        myGravitySpeed = grav;
        myToggleGravitySpeedHolder = grav;
    }
    
    /**
     * Update the force being applied to the mass
     * @param bounds    size of simulation
     * @param m         mass object
     */
    @Override 
    public void update  (Dimension bounds, Mass m) {
        applyGravity(m);
    }
    
    /**
     * Applies the force to the mass
     * @param m         The mass object
     */
    public void applyGravity (Mass m) {
        Vector downwardAcceleration = new Vector(DOWN_DIRECTION, myGravitySpeed);
        m.applyForce(downwardAcceleration);
    }
    
    /**
     * Turns gravity on or off depending 
     * on state
     */
    public void toggleGravity () {
        if (myGravitySpeed != 0) {
            myGravitySpeed = 0;
        }
        else {
            myGravitySpeed = myToggleGravitySpeedHolder;
        }
    }

}
