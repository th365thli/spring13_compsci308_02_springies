package simulation;

import util.Vector;


public class Gravity {
   
    private double myGravitySpeed;
    private double myRoggleGravitySpeedHolder;
    
    public Gravity(double grav) {
        myGravitySpeed = grav;
        myRoggleGravitySpeedHolder = grav;
    }
    
    public void update(Mass m) {
        applyGravity(m);
    }
    
    public void applyGravity (Mass m) {
        Vector downwardAcceleration = new Vector(90, myGravitySpeed);
        m.applyForce(downwardAcceleration);
    }
    
    public void toggleGravity() {
       if (myGravitySpeed != 0) {
           myGravitySpeed = 0;
       }
       else {
           myGravitySpeed = myRoggleGravitySpeedHolder;
       }
    }

}