package simulation;

import util.Vector;

/**
 * Details a class that applies a resistant 
 * viscosity force
 * @author Jerry Li & Bill Muensterman
 *
 */
public class Viscosity {

    private double myViscosityValue;
    private double myTempViscosityValue;
    
    /**
     * Constructs an object that applies resistive
     * force to masses
     * @param visc      the force magnitude
     */
    public Viscosity (double visc) {
        myViscosityValue = visc;
        myTempViscosityValue = visc;
    }
    
    /**
     * Applies the force to mass
     * @param m         the mass
     */
    public void update (Mass m) {
        double viscosity = m.getAcceleration().getMagnitude() * myViscosityValue;
        Vector newAcceleration = new Vector(m.getAcceleration().getDirection(),
                                            m.getAcceleration().getMagnitude() - viscosity);

        m.changeAcceleration(newAcceleration);
    }
    
    /**
     * toggles viscosity
     */
    public void toggleViscosity () {
        if (myViscosityValue != 0) {
            myViscosityValue = 0;
        }
        else {
            myViscosityValue = myTempViscosityValue;
        }
    }

}
