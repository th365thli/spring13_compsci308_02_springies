package simulation;

import java.awt.geom.Point2D;
import util.Vector;

/**
 * Details a force that attracts masses
 * to the center of mass
 * 
 * @author Jerry Li & Bill Muensterman
 *
 */
public class CenterOfMass {

    private double myCenterOfMassForce;
    private double myTempForce;
    
    /**
     * Constructs an object that pulls
     * masses toward the center of mass
     * @param force     The power of the attraction
     */
    public CenterOfMass (double force) {
        myCenterOfMassForce = force;
        myTempForce = force;
    }
    
    /**
     * Applies the force to the mass
     * @param m                 The mass object
     * @param myCenterXMass     Center of mass x coordinate
     * @param myCenterYMass     Center of mass y coordinate
     */
    public void update (Mass m, double myCenterXMass, double myCenterYMass) {
        applyCenterOfMass(m, myCenterXMass, myCenterYMass);
    }

    /**
     * Applies a center of mass force that attracts all masses to the calculated
     * center of mass
     * 
     * @param m The mass
     * @param myCenterXMass Center mass x coordinate
     * @param myCenterYMass Center mass y coordinate
     */
    public void applyCenterOfMass (Mass m, double myCenterXMass, double myCenterYMass) {
        double xMass = m.getX();
        double yMass = m.getY();

        Point2D target = new Point2D.Double(xMass, yMass);
        Point2D source = new Point2D.Double(myCenterXMass, myCenterYMass);

        double distance =
                Math.sqrt((myCenterXMass - xMass) * (myCenterXMass - xMass) +
                          (myCenterYMass - yMass) * (myCenterYMass - yMass));
        double centExpVal = myCenterOfMassForce;
        double force = 1 / Math.pow(distance, centExpVal);

        Vector v = new Vector(target, source);
        v.setMagnitude(force);

        m.applyForce(v);
    }
    
    /**
     * Toggles center of mass force on or off
     * by changing force value
     */
    public void toggleCenterOfMass () {
        if (myCenterOfMassForce != 0) {
            myCenterOfMassForce = 0;
        }
        else {
            myCenterOfMassForce = myTempForce;
        }
    }
}
