package simulation;

import util.Vector;

/**
 * Details gravitational force that affects
 * all masses in simulation
 * @author Jerry Li & Bill Muensterman
 *
 */
public class Gravity {
    
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
        myGravitySpeed = grav;
        myToggleGravitySpeedHolder = grav;
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
