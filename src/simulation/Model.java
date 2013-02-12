package simulation;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import view.Canvas;


// test commit

/**
 * Creates Springies universe
 * 
 * @author Jerry Li
 */
public class Model {
    // bounds and input for game
    private Canvas myView;
    // simulation state
    private List<Mass> myMasses;
    private List<Assembly> myAssemblies;

    private static final int LOAD_NEW = KeyEvent.VK_N;
    private static final int GRAVITY_TOGGLE = KeyEvent.VK_G;
    private static final int VISCOSITY_TOGGLE = KeyEvent.VK_V;
    private static final int CENTER_OF_MASS_TOGGLE = KeyEvent.VK_M;
    private static final int TOP_WALL = KeyEvent.VK_1;
    private static final int BOTTOM_WALL = KeyEvent.VK_2;
    private static final int RIGHT_WALL = KeyEvent.VK_3;
    private static final int LEFT_WALL = KeyEvent.VK_4;
    private static final int CLEAR = KeyEvent.VK_C;

    /**
     * Create a game of the given size with the given display for its shapes.
     * 
     * @param canvas the game canvas
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myAssemblies = new ArrayList<>();
        myAssemblies.add(myView.getAssembly());

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
     * Update simulation for this moment, given the time since the last moment.
     * 
     * @param elapsedTime
     */
    public void update (double elapsedTime) {
        int key = myView.getLastKeyPressed();

        toggleGravity(key);
        loadFile(key);
        toggleViscosity(key);
        toggleCenterOfMass(key);
        toggleTopWallRepulsion(key);
        toggleBottomWallRepulsion(key);
        toggleRightWallRepulsion(key);
        toggleLeftWallRepulsion(key);
        clear(key);

        for (Assembly a : myAssemblies) {
            a.update(elapsedTime);
        }
    }

    public void toggleTopWallRepulsion (int key) {
        if (key == TOP_WALL) {
            myView.setLastKeyPressed();
            for (Assembly a : myAssemblies) {
                a.getWallRepulsion().toggleTopRepulsion();
            }
        }
    }

    public void toggleBottomWallRepulsion (int key) {
        if (key == BOTTOM_WALL) {
            myView.setLastKeyPressed();
            for (Assembly a : myAssemblies) {
                a.getWallRepulsion().toggleBottomRepulsion();
            }
        }
    }

    public void toggleRightWallRepulsion (int key) {
        if (key == RIGHT_WALL) {
            myView.setLastKeyPressed();
            for (Assembly a : myAssemblies) {
                a.getWallRepulsion().toggleRightRepulsion();
            }
        }
    }

    public void toggleLeftWallRepulsion (int key) {
        if (key == LEFT_WALL) {
            myView.setLastKeyPressed();
            for (Assembly a : myAssemblies) {
                a.getWallRepulsion().toggleLeftRepulsion();
            }
        }
    }

    public void toggleCenterOfMass (int key) {
        if (key == CENTER_OF_MASS_TOGGLE) {
            myView.setLastKeyPressed();
            for (Assembly a : myAssemblies) {
                a.getCenterOfMass().toggleCenterOfMass();
            }
        }
    }

    public void toggleViscosity (int key) {
        if (key == VISCOSITY_TOGGLE) {
            myView.setLastKeyPressed();
            for (Assembly a : myAssemblies) {
                a.getViscosity().toggleViscosity();
            }
        }
    }

    public void toggleGravity (int key) {
        if (key == GRAVITY_TOGGLE) {
            myView.setLastKeyPressed();
            for (Assembly a : myAssemblies) {
                a.getGravity().toggleGravity();
            }
        }
    }

    public void loadFile (int key) {
        if (key == LOAD_NEW) {
            myView.setLastKeyPressed();
            Assembly assem = new Assembly(myView);
            myAssemblies.add(assem);
            myView.loadModel(assem);
        }
    }
    
    public void clear (int key) {
        if (key == CLEAR) {
            myView.setLastKeyPressed();
            for (Assembly a : myAssemblies) {
                a.clear();
            }
        }
    }

}
