package simulation;

import util.Vector;


public class Gravity {
   
    private double gravitySpeed;
    private double toggleGravitySpeedHolder;
    
    public Gravity(double grav) {
        gravitySpeed = grav;
        toggleGravitySpeedHolder = grav;
    }
    
    public void update(Mass m) {
        applyGravity(m);
    }
    
    public void applyGravity (Mass m) {
        Vector downwardAcceleration = new Vector(90, gravitySpeed);
        m.applyForce(downwardAcceleration);
    }
    
    public void toggleGravity() {
       if (gravitySpeed != 0) {
           gravitySpeed = 0;
       }
       else {
           gravitySpeed = toggleGravitySpeedHolder;
       }
    }

}
