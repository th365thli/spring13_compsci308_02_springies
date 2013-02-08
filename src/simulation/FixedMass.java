package simulation;

/**
 * Fixed Mass class that extends Mass. Virtually the same thing
 * except no forces act upon fixed mass
 * 
 * @author Jerry Li
 * 
 */
public class FixedMass extends Mass {

    /**
     * Constructs mass based on coordinates, mass value, and viscosity value
     * 
     * @param x
     * @param y
     * @param mass
     * @param viscosity
     * @param gravity
     */
    public FixedMass (double x, double y, double mass, double viscosity,
                      double gravity) {
        super(x, y, mass, viscosity, gravity);

    }

    /**
     * Empty update method because no forces act upon mass
     */
    public void update () {

    }

}
