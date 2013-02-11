package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;


/**
 * Details a mass class that has forces acting on it
 * 
 * @author Jerry Li
 */
public class Mass extends Sprite {
    // reasonable default values
    public static final Dimension DEFAULT_SIZE = new Dimension(16, 16);
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("mass.gif");

    private double myMass;
    private Vector myAcceleration;
    private double viscosityValue;
//    private double gravitySpeed;

    /**
     * Constructs a mass based on coordinates, mass value, viscosity, and gravity acting
     * on it
     */
    public Mass (double x, double y, double mass, double viscosity, double gravity) {
        super(DEFUALT_IMAGE, new Location(x, y), DEFAULT_SIZE);
        myMass = mass;
        myAcceleration = new Vector();
        viscosityValue = viscosity;
        
    }

    /**
     * Returns the mass of the mass
     * 
     * @return myMass
     */
    public double getMyMass () {
        return myMass;
    }

    /**
     * Updates mass position based on forces acting upon it.
     * Calls update methods for gravity, viscosity, wall repulsion
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        getBounce(bounds);
        // convert force back into Mover's velocity
        applyViscosity(bounds);
        repel(bounds);
        getVelocity().sum(myAcceleration);
        myAcceleration.reset();
        // move mass by velocity
        super.update(elapsedTime, bounds);
    }

    /**
     * Applies viscosity resistance to mass
     * 
     * @param bounds
     */
    public void applyViscosity (Dimension bounds) {
        double viscosity = myAcceleration.getMagnitude() * viscosityValue;
        myAcceleration = new Vector(myAcceleration.getDirection(),
                                    myAcceleration.getMagnitude() - viscosity);
    }

    /**
     * Applies repulsion force from walls on mass
     * 
     * @param bounds
     */
    public void wallRepulsion (Dimension bounds) {

        double leftProximity = proximityToLeftWall(bounds);
        double rightProximity = proximityToRightWall(bounds);
        double topProximity = proximityToTopWall(bounds);
        double bottomProximity = proximityToBottomWall(bounds);

        Vector leftRepulsion = leftRepulsion(calculateRepulsion(leftProximity, -.01));
        Vector rightRepulsion = rightRepulsion(calculateRepulsion(rightProximity, -.01));
        Vector topRepulsion = topRepulsion(calculateRepulsion(topProximity, -.01));
        Vector bottomRepulsion = bottomRepulsion(calculateRepulsion(bottomProximity, -.01));

        myAcceleration.sum(leftRepulsion);
        myAcceleration.sum(rightRepulsion);
        myAcceleration.sum(topRepulsion);
        myAcceleration.sum(bottomRepulsion);

    }

    /**
     * Only calls wallrepulsion if mass is within bounds
     * 
     * @param bounds
     */
    public void repel (Dimension bounds) {
        if ((getLeft() > 0) &&
            (getRight() < bounds.width) &&
            (getTop() > 0) &&
            (getBottom() < bounds.height)) {
            wallRepulsion(bounds);
        }
    }

    /**
     * Gets distance from left wall
     * 
     * @param bounds
     * @return bounds.width - getLeft();
     */
    public double proximityToLeftWall (Dimension bounds) {
        return bounds.width - getLeft();
    }

    /**
     * Gets distance from right wall
     * 
     * @param bounds
     * @return getRight();
     */
    public double proximityToRightWall (Dimension bounds) {
        return getRight();
    }

    /**
     * Gets distance from top wall
     * 
     * @param bounds
     * @return bounds.height - getBottom();
     */
    public double proximityToTopWall (Dimension bounds) {
        return bounds.height - getBottom();
    }

    /**
     * Gets distance from bottom wall
     * 
     * @param bounds
     * @return getBottom();
     */
    public double proximityToBottomWall (Dimension bounds) {
        return getBottom();
    }

    /**
     * Calculate repulsion force
     * 
     * @param proximity
     * @param force
     * @return (1/Math.pow(proximity, force));
     */
    public double calculateRepulsion (double proximity, double force) {
        return (1 / Math.pow(proximity, force));
    }

    /**
     * Creates vectors based on proximity to specified wall and force
     * 
     * @param force
     * @return repulsion
     */
    public Vector leftRepulsion (double force) {
        final Vector repulsion = new Vector(0, force);
        return repulsion;
    }

    public Vector rightRepulsion (double force) {
        final Vector repulsion = new Vector(180, force);
        return repulsion;
    }

    public Vector topRepulsion (double force) {
        final Vector repulsion = new Vector(90, force);
        return repulsion;
    }

    public Vector bottomRepulsion (double force) {
        final Vector repulsion = new Vector(270, force);
        return repulsion;
    }

    /**
     * Paint mass on canvas
     */
    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(Color.BLACK);
        pen.fillOval((int) getLeft(), (int) getTop(), (int) getWidth(), (int) getHeight());
    }

    /**
     * Use the given force to change this mass's acceleration.
     */
    public void applyForce (Vector force) {
        if (myMass >= 0) {
            myAcceleration.sum(force);
        }
    }

    /**
     * Convenience method.
     */
    public double distance (Mass other) {
        // this is a little awkward, so hide it
        return new Location(getX(), getY()).distance(new Location(other.getX(), other.getY()));
    }

    /**
     * Check if ball is out of bounds, and "bounces" it if so
     * 
     * @param bounds
     */
    private void getBounce (Dimension bounds) {
        if (getBottom() >= bounds.height || getTop() <= 0) {
            getVelocity().setDirection(-getVelocity().getDirection());
        }
        else if (getRight() >= bounds.width || getRight() <= 0) {
            getVelocity().setDirection(180 - getVelocity().getDirection());
        }
    }
}
