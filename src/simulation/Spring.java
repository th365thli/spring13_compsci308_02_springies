package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;


/**
 * A class that details a spring that acts upon masses
 * 
 * @author Jerry Li & Bill Muensterman
 */
public class Spring extends Sprite {

    /**
     * Default image for spring
     */
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("spring.gif");
    /**
     * default size of spring
     */
    public static final int IMAGE_HEIGHT = 20;

    private Mass myStart;
    private Mass myEnd;
    private double myLength;
    private double myK;

    /**
     * Constructs spring based on masses its connected to, length, and k values
     * 
     * @param start     first mass
     * @param end       second mass
     * @param length    default length
     * @param kVal      hooke's constant
     */
    public Spring (Mass start, Mass end, double length, double kVal) {
        super(DEFUALT_IMAGE, getCenter(start, end), getSize(start, end));
        myStart = start;
        myEnd = end;
        myLength = length;
        myK = kVal;
    }

    /**
     * Set the length of the spring
     * 
     * @param x         length to set to
     */
    public void setLength (double x) {
        myLength = x;
    }

    /**
     * Return the length of the spring
     * 
     * @return myLength
     */
    public double getLength () {
        return myLength;
    }

    /**
     * Paint spring on canvas
     * 
     * @param pen       graphics pen
     */
    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(getColor(myStart.distance(myEnd) - myLength));
        pen.drawLine((int) myStart.getX(), (int) myStart.getY(), (int) myEnd.getX(),
                     (int) myEnd.getY());
    }

    /**
     * Applies hookes law to mass.
     * Updates sprite values based on attached masses
     * 
     * @param elapsedTime       frames
     * @param bounds            size of simulation
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        double dx = myStart.getX() - myEnd.getX();
        double dy = myStart.getY() - myEnd.getY();
        // apply hooke's law to each attached mass
        Vector force = new Vector(Vector.angleBetween(dx, dy),
                                  myK * (myLength - Vector.distanceBetween(dx, dy)));
        myStart.applyForce(force);
        force.negate();
        myEnd.applyForce(force);
        // update sprite values based on attached masses
        setCenter(getCenter(myStart, myEnd));
        setSize(getSize(myStart, myEnd));
        setVelocity(Vector.angleBetween(dx, dy), 0);
    }

    /**
     * Convenience method.
     * 
     * @param diff
     */
    protected Color getColor (double diff) {
        if (Vector.fuzzyEquals(diff, 0)) {
            return Color.BLACK;
        }
        else if (diff < 0.0) {
            return Color.BLUE;
        }
        else {
            return Color.RED;
        }
    }

    /**
     * Calculate center of spring
     * 
     * @param start
     * @param end
     * @return new Location((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
     */
    private static Location getCenter (Mass start, Mass end) {
        return new Location((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * Compute size of spring
     * 
     * @param start
     * @param end
     * @return new Dimension((int)start.distance(end), IMAGE_HEIGHT);
     */
    private static Dimension getSize (Mass start, Mass end) {
        return new Dimension((int) start.distance(end), IMAGE_HEIGHT);
    }
    
    /**
     * set parameters
     * @param start     mass to set
     * @param end       mass to set
     * @param length    length to set
     * @param kVal      hooke's constant to set
     */
    public void setParameters (Mass start, Mass end, double length, double kVal) {
        myStart = start;
        myEnd = end;
        myLength = length;
        myK = kVal;
    }
}
