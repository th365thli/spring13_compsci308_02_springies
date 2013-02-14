package util;

import java.awt.Dimension;
import simulation.Mass;

/**
 * Details an abstract class that is an
 * abstract "force"
 * @author Jerry Li & Bill Muensterman
 *
 */
public abstract class Force {
    
    /**
     * Empty constructor. Each subclass has its own
     * specific constructor
     */
    public Force () {
        //ForceFactor = factor;
    }
    
    /**
     * The important method. Updates the force.
     * Empty because each "force" subclass has its own 
     * update method
     * @param bounds    size of simulation
     * @param m         mass object
     */
    public void update (Dimension bounds, Mass m) {
        
    }

}
