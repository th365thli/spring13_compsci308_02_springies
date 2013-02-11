package simulation;

import java.awt.Dimension;
import util.Vector;

public class WallRepulsion {
    
    private double wallRepulsionFactor;
    
    public WallRepulsion (double repulsion) {
        wallRepulsionFactor = repulsion;
    }
    
    public void update(Dimension bounds, Mass m) {
        repel(bounds, m);
    }
    
    public void wallRepulsion (Dimension bounds, Mass m) {

        double leftProximity = proximityToLeftWall(bounds, m);
        double rightProximity = proximityToRightWall(bounds, m);
        double topProximity = proximityToTopWall(bounds, m);
        double bottomProximity = proximityToBottomWall(bounds, m);

        Vector leftRepulsion = leftRepulsion(calculateRepulsion(leftProximity, wallRepulsionFactor));
        Vector rightRepulsion = rightRepulsion(calculateRepulsion(rightProximity, wallRepulsionFactor));
        Vector topRepulsion = topRepulsion(calculateRepulsion(topProximity, wallRepulsionFactor));
        Vector bottomRepulsion = bottomRepulsion(calculateRepulsion(bottomProximity, wallRepulsionFactor));

        m.getAcceleration().sum(leftRepulsion);
        m.getAcceleration().sum(rightRepulsion);
        m.getAcceleration().sum(topRepulsion);
        m.getAcceleration().sum(bottomRepulsion);

    }
    
    public void repel (Dimension bounds, Mass m) {
        if ((m.getLeft() > 0) &&
            (m.getRight() < bounds.width) &&
            (m.getTop() > 0) &&
            (m.getBottom() < bounds.height)) {
             wallRepulsion(bounds, m);
        }
    }
    
    /**
     * Gets distance from left wall
     * 
     * @param bounds
     * @return bounds.width - getLeft();
     */
    public double proximityToLeftWall (Dimension bounds, Mass m) {
        return bounds.width - m.getLeft();
    }

    /**
     * Gets distance from right wall
     * 
     * @param bounds
     * @return getRight();
     */
    public double proximityToRightWall (Dimension bounds, Mass m) {
        return m.getRight();
    }

    /**
     * Gets distance from top wall
     * 
     * @param bounds
     * @return bounds.height - getBottom();
     */
    public double proximityToTopWall (Dimension bounds, Mass m) {
        return bounds.height - m.getBottom();
    }

    /**
     * Gets distance from bottom wall
     * 
     * @param bounds
     * @return getBottom();
     */
    public double proximityToBottomWall (Dimension bounds, Mass m) {
        return m.getBottom();
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
}
