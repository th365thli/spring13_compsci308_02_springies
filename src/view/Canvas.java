package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import simulation.Assembly;
import simulation.Factory;
import simulation.Model;


/**
 * Creates an area of the screen in which the game will be drawn that supports:
 * <UL>
 * <LI>animation via the Timer
 * <LI>mouse input via the MouseListener and MouseMotionListener
 * <LI>keyboard input via the KeyListener
 * </UL>
 * 
 * @author Robert C Duvall
 */
public class Canvas extends JComponent {
    /**
     * animate 25 times per second if possible
     */
    public static final int FRAMES_PER_SECOND = 25;
    /**
     * better way to think about timed events (in milliseconds)
     */
    public static final int ONE_SECOND = 1000;
    /**
     * delay time
     */
    public static final int DEFAULT_DELAY = ONE_SECOND / FRAMES_PER_SECOND;
    /** 
     *  only one so that it maintains user's preferences
     */
    public static final int NO_KEY_PRESSED = -1;
    /**
     * If no mouseis pressed
     */
    public static final Point NO_MOUSE_PRESSED = null;
    
    // default serialization ID
    private static final long serialVersionUID = 1L;
    private static final JFileChooser INPUT_CHOOSER =
            new JFileChooser(System.getProperties().getProperty("user.dir"));
    // input state

    private boolean myMouseClicked = false;
    private Assembly myAssembly;

    // drives the animation
    private Timer myTimer;
    // game to be animated
    private Model mySimulation;
    // input state
    private int myLastKeyPressed;
    private Point myLastMousePosition;
    private Set<Integer> myKeys;

    /**
     * Create a panel so that it knows its size
     * @param size      size of the simulation
     */
    public Canvas (Dimension size) {
        // set size (a bit of a pain)
        setPreferredSize(size);
        setSize(size);
        // prepare to receive input
        setFocusable(true);
        requestFocus();
        setInputListeners();
    }
    
    /**
     * Return initial assembly created
     * @return
     */
    public Assembly getAssembly () {
        return myAssembly;
    }

    /**
     * Paint the contents of the canvas.
     * 
     * Never called by you directly, instead called by Java runtime
     * when area of screen covered by this container needs to be
     * displayed (i.e., creation, uncovering, change in status)
     * 
     * @param pen used to paint shape on the screen
     */
    @Override
    public void paintComponent (Graphics pen) {
        pen.setColor(Color.WHITE);
        pen.fillRect(0, 0, getSize().width, getSize().height);
        // first time needs to be special cased :(
        if (mySimulation != null) {
            mySimulation.paint((Graphics2D) pen);
        }
    }

    /**
     * Returns last key pressed by the user or -1 if nothing is pressed.
     */
    public int getLastKeyPressed () {
        return myLastKeyPressed;
    }

    /**
     * Returns all keys currently pressed by the user.
     */
    public Collection<Integer> getKeysPressed () {
        return Collections.unmodifiableSet(myKeys);
    }

    /**
     * Returns last position of the mouse in the canvas.
     */
    public Point getLastMousePosition () {
        return myLastMousePosition;
    }

    /**
     * Start the animation. Creates a new
     * assembly. Creates a new model.
     */
    public void start () {
        // create a timer to animate the canvas
        myTimer = new Timer(DEFAULT_DELAY,
                            new ActionListener() {
                                @Override
                                public void actionPerformed (ActionEvent e) {
                                    step();
                                }
                            });
        // start animation
        myAssembly = new Assembly(this);
        mySimulation = new Model(this);
        loadModel(myAssembly);
        myTimer.start();
    }

    /**
     * Stop the animation.
     */
    public void stop () {
        myTimer.stop();
    }

    /**
     * Take one step in the animation.
     */
    public void step () {
        mySimulation.update((double) FRAMES_PER_SECOND / ONE_SECOND);
        // indirectly causes paint to be called
        repaint();
    }
    
    /**
     * clear all keys from myKeys
     */
    public void clearKeys () {
        myKeys.clear();
    }
    
    /**
     *  set last key pressed to -1 to clear value
     */
    public void setLastKeyPressed () {
        myLastKeyPressed = -1;
    }
    
    /**
     * Return if mouse was clicked
     * @return
     */
    public boolean getMouseClick() {
        return myMouseClicked;
    }

    private void setInputListeners () {
        // initialize input state
        myLastKeyPressed = NO_KEY_PRESSED;
        myKeys = new TreeSet<Integer>();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                myLastKeyPressed = e.getKeyCode();
                myKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased (KeyEvent e) {
                myLastKeyPressed = NO_KEY_PRESSED;
                myKeys.remove(e.getKeyCode());
            }
        });
        myLastMousePosition = NO_MOUSE_PRESSED;
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged (MouseEvent e) {
                myLastMousePosition = e.getPoint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                myLastMousePosition = e.getPoint();
                myMouseClicked = true;
                
                
            }

            @Override
            public void mouseReleased (MouseEvent e) {
                myLastMousePosition = NO_MOUSE_PRESSED;
                myMouseClicked = false;
            }
        });
    }

    /**
     * load model from file chosen by user
     * @param a         the assembly
     */
    public void loadModel (Assembly a) {
        Factory factory = new Factory();
        int response = INPUT_CHOOSER.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            factory.loadModel(a, INPUT_CHOOSER.getSelectedFile());
        }
    }

}
