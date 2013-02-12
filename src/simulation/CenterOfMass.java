package simulation;

import java.awt.geom.Point2D;
import util.Vector;


public class CenterOfMass {

    private double myCenterOfMassForce;
    private double myTempForce;

    public CenterOfMass (double force) {
        myCenterOfMassForce = force;
        myTempForce = force;
    }

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
        System.out.println(myCenterOfMassForce);
    }

    public void toggleCenterOfMass () {
        if (myCenterOfMassForce != 0) {
            myCenterOfMassForce = 0;
        }
        else {
            myCenterOfMassForce = myTempForce;
        }
    }
}
