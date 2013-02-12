import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import view.Canvas;


// git test 123
/**
 * Creates window that can be moved, resized, and closed by the user.
 * 
 * @author Jerry Li & Bill Muensterman
 */
public class Main {
    // constants
    
    /**
     * Size of View
     */
    public static final Dimension SIZE = new Dimension(800, 600);
    /**
     * Title of Program
     */
    public static final String TITLE = "Springies!";

    private Main () {
        // does not make sense to construct this class
    }

    /**
     * main --- where the program starts
     * @param args      Command line arguments
     */
    public static void main (String[] args) {
        // view of user's content
        Canvas display = new Canvas(SIZE);
        // container that will work with user's OS
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components
        frame.getContentPane().add(display, BorderLayout.CENTER);
        // display them
        frame.pack();
        frame.setVisible(true);
        // start animation
        display.start();
    }
}
