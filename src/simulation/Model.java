package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import util.Force;
import view.Canvas;


/**
 * Creates Springies universe
 * 
 * @author Jerry Li & Bill Muensterman
 */
public class Model {
    private static final double GRAVITY_SPEED = 7;
    private static final double VISCOSITY = .9;
    private static final double WALL_REPULSION = -.01;
    private static final int LOAD_NEW = KeyEvent.VK_N;
    private static final int GRAVITY_TOGGLE = KeyEvent.VK_G;
    private static final int VISCOSITY_TOGGLE = KeyEvent.VK_V;
    private static final int CENTER_OF_MASS_TOGGLE = KeyEvent.VK_M;
    private static final int TOP_WALL = KeyEvent.VK_1;
    private static final int BOTTOM_WALL = KeyEvent.VK_2;
    private static final int RIGHT_WALL = KeyEvent.VK_3;
    private static final int LEFT_WALL = KeyEvent.VK_4;
    private static final int CLEAR = KeyEvent.VK_C;
    private static final int INCREASE_SIZE = KeyEvent.VK_UP;
    private static final int DECREASE_SIZE = KeyEvent.VK_DOWN;
    private static final int RESIZE_FACTOR = 10;
    private final double myGravitySpeed = GRAVITY_SPEED;
    private final double myViscosityValue = VISCOSITY;
    private final double myWallRepulsionFactor = WALL_REPULSION;
    
    // bounds and input for game
    private Canvas myView;
    // simulation state
    private List<Mass> myMasses;
    private List<Assembly> myAssemblies;
    private List<Force> myForces;

    private Gravity myGravity;
    private Viscosity myViscosity;
    private WallRepulsion myWallRepulsion;

    /**
     * Create a game of the given size with the given display for its shapes.
     * Adds assembly created in canvas to assembly list. We realize model and canvas
     * interaction is probably not ideal, but this works for now. 
     * @param canvas the game canvas
     */
    public Model (Canvas canvas) {
        myView = canvas;
        initialize();
        myForces.add(myGravity);
        myForces.add(myViscosity);
        myForces.add(myWallRepulsion);
        myAssemblies.add(myView.getAssembly());

    }
    
    /**
     * Create objects and list of objects
     */
    public void initialize() {
        myMasses = new ArrayList<>();
        myAssemblies = new ArrayList<>();
        myForces = new ArrayList<>();
        myGravity = new Gravity(myGravitySpeed);
        myViscosity = new Viscosity(myViscosityValue);
        myWallRepulsion = new WallRepulsion(myWallRepulsionFactor);
    }

    /**
     * Return the list of masses instantiated
     * 
     * @return
     */
    public List<Mass> getMasses () {
        return myMasses;
    }

    /**
     * Draw all elements of the simulation.
     * 
     * @param pen Graphics2D pen
     */
    public void paint (Graphics2D pen) {
        for (Assembly a : myAssemblies) {
            a.paint(pen);
        }
    }

    /**
     * changeCanvasSize
     * 
     * @param key the user input
     */
    public void changeCanvasSize (int key) {
        Dimension bounds = myView.getSize();
        if (key == INCREASE_SIZE) {
            if (myView.getKeysPressed().contains(key)) {
                myView.clearKeys();
                bounds.setSize(bounds.width + RESIZE_FACTOR, bounds.height + RESIZE_FACTOR);
                myView.setSize(bounds);
            }
        }
        if (key == DECREASE_SIZE) {
            if (myView.getKeysPressed().contains(key)) {
                myView.clearKeys();
                bounds.setSize(bounds.width - RESIZE_FACTOR, bounds.height - RESIZE_FACTOR);
                myView.setSize(bounds);
            }
        }
    }

    /**
     * Update simulation for this moment, given the time since the last moment.
     * Checks input. Iterates through each assembly. For each assembly, iterates
     * through each mass
     * 
     * @param elapsedTime framerate
     */
    public void update (double elapsedTime) {
        checkInput();
        Dimension bounds = myView.getSize();
        for (Assembly assem : myAssemblies) {
            assem.update(elapsedTime);
            for (Mass m : assem.getMasses()) {
                for (Force f : myForces) {
                    f.update(bounds, m);
                }
            }
        }
    }
    
    
    /**
     * Checks input from user
     */
    public void checkInput () {
        int key = myView.getLastKeyPressed();
        changeCanvasSize(key);
        loadFile(key);
        toggleGravity(key);
        toggleViscosity(key);
        toggleCenterOfMass(key);
        toggleTopWallRepulsion(key);
        toggleLeftWallRepulsion(key);
        toggleRightWallRepulsion(key);
        toggleBottomWallRepulsion(key);
        clear(key);
    }

    /**
     * Toggles top wall repulsion
     * 
     * @param key input from user
     */
    public void toggleTopWallRepulsion (int key) {
        if (key == TOP_WALL) {
            myView.setLastKeyPressed();
            myWallRepulsion.toggleTopRepulsion();
        }
    }

    /**
     * Toggles bottom wall repulsion
     * 
     * @param key input from user
     */
    public void toggleBottomWallRepulsion (int key) {
        if (key == BOTTOM_WALL) {
            myView.setLastKeyPressed();
            myWallRepulsion.toggleBottomRepulsion();
        }
    }

    /**
     * Toggles right wall repulsion
     * 
     * @param key input from user
     */
    public void toggleRightWallRepulsion (int key) {
        if (key == RIGHT_WALL) {
            myView.setLastKeyPressed();
            myWallRepulsion.toggleRightRepulsion();
        }
    }

    /**
     * toggles left wall repulsion
     * 
     * @param key input from user
     */
    public void toggleLeftWallRepulsion (int key) {
        if (key == LEFT_WALL) {
            myView.setLastKeyPressed();
            myWallRepulsion.toggleLeftRepulsion();
        }
    }

    /**
     * toggles center of mass
     * 
     * @param key input from user
     */
    public void toggleCenterOfMass (int key) {
        if (key == CENTER_OF_MASS_TOGGLE) {
            myView.setLastKeyPressed();
            for (Assembly a : myAssemblies) {
                a.getCenterOfMass().toggleCenterOfMass();
            }
        }
    }

    /**
     * toggles viscosity
     * 
     * @param key input from user
     */
    public void toggleViscosity (int key) {
        if (key == VISCOSITY_TOGGLE) {
            myView.setLastKeyPressed();
            myViscosity.toggleViscosity();
        }
    }

    /**
     * toggles gravity
     * 
     * @param key input from user
     */
    public void toggleGravity (int key) {
        if (key == GRAVITY_TOGGLE) {
            myView.setLastKeyPressed();
            myGravity.toggleGravity();
        }
    }

    /**
     * loads another assembly
     * 
     * @param key input from user
     */
    public void loadFile (int key) {
        if (key == LOAD_NEW) {
            myView.setLastKeyPressed();
            Assembly assem = new Assembly(myView);
            myAssemblies.add(assem);
            myView.loadModel(assem);
        }
    }

    /**
     * clears objects from all assemblies
     * 
     * @param key input from user
     */
    public void clear (int key) {
        if (key == CLEAR) {
            myView.setLastKeyPressed();
            for (Assembly a : myAssemblies) {
                a.clear();
            }
        }
    }

}
