package simulation;

import java.awt.Dimension;
import util.Pixmap;


/**
 * Details a muscle class that extends Spring
 * The only difference is the change in length based
 * on equation
 * 
 * @author Jerry Li
 * 
 */

public class Muscle extends Spring {

    public static final Pixmap DEFUALT_IMAGE = new Pixmap("spring.gif");
    public static final int IMAGE_HEIGHT = 20;

    private double myRestLength;
    private double myAmp;
    private double myDelay;
    private double myFreq;

    /**
     * Takes in masses, length, hookes constant, amplitude, delay value, and frequency
     * to construct a muscle.
     * 
     * @param start
     * @param end
     * @param length
     * @param kVal
     * @param amp
     * @param delay
     * @param freq
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
     * @param elapsedTime
     * @param bounds
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        myRestLength = getLength() * (1 + myAmp * Math.sin((myFreq * elapsedTime) + myDelay));
        setLength(myRestLength);
        super.update(elapsedTime, bounds);
    }

}
