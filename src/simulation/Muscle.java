package simulation;

import java.awt.Dimension;
import util.Pixmap;


/**
 * Details a muscle class that extends Spring
 * The only difference is the change in length based
 * on equation
 * 
 * @author Jerry Li & Bill Muensterman
 * 
 */

public class Muscle extends Spring {
    
    /**
     * picture of spring
     */
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("spring.gif");
    /**
     * initial length of muscle
     */
    public static final int IMAGE_HEIGHT = 20;

    private double myRestLength;
    private double myAmp;
    private double myDelay;
    private double myFreq;

    /**
     * Takes in masses, length, hookes constant, amplitude, delay value, and frequency
     * to construct a muscle.
     * 
     * @param start     first mass
     * @param end       second mass     
     * @param length    initial length of spring
     * @param kVal      hooke's constant
     * @param amp       amplitude
     * @param delay     delay
     * @param freq      frequency
     */
    public Muscle (Mass start, Mass end, double length, double kVal,
                   double amp, double delay, double freq) {
        super(start, end, length, kVal);
        myAmp = amp;
        myDelay = delay;
        myFreq = freq;
    }

    /**
     * Updates the length of the muscle based on harmonic oscillation equation.
     * 
     * @param elapsedTime       framerate
     * @param bounds            size of simulation
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        myRestLength = getLength() * (1 + myAmp * Math.sin((myFreq * elapsedTime) + myDelay));
        setLength(myRestLength);
        super.update(elapsedTime, bounds);
    }

}
