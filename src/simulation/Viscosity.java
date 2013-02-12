package simulation;

import util.Vector;


public class Viscosity {

    private double myViscosityValue;
    private double myTempViscosityValue;

    public Viscosity (double visc) {
        myViscosityValue = visc;
        myTempViscosityValue = visc;
    }

    public void update (Mass m) {
        double viscosity = m.getAcceleration().getMagnitude() * myViscosityValue;
        Vector newAcceleration = new Vector(m.getAcceleration().getDirection(),
                                            m.getAcceleration().getMagnitude() - viscosity);

        m.changeAcceleration(newAcceleration);
    }

    public void toggleViscosity () {
        if (myViscosityValue != 0) {
            myViscosityValue = 0;
        }
        else {
            myViscosityValue = myTempViscosityValue;
        }
    }

}
