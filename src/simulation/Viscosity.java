package simulation;

import util.Vector;


public class Viscosity {
    
    private double myViscosityValue;
    
    public Viscosity (double visc) {
        myViscosityValue = visc;
    }
    
    public void update(Mass m){
        double viscosity = m.getAcceleration().getMagnitude() * myViscosityValue;
        Vector newAcceleration = new Vector(m.getAcceleration().getDirection(),
                                            m.getAcceleration().getMagnitude() - viscosity);
            
        m.changeAcceleration(newAcceleration);
    }
    
    
}