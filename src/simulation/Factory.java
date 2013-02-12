package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * An assembly class that creates
 * The masses, springs, and muscles
 * 
 * @author Robert C. Duvall
 */
public class Factory {
    // data file keywords
    private static final String MASS_KEYWORD = "mass";
    private static final String SPRING_KEYWORD = "spring";
    private static final String MUSCLE_KEYWORD = "muscle";
    private static final String FIXED_MASS_KEYWORD = "fixedMass";

    // mass IDs
    private Map<Integer, Mass> myMasses = new HashMap<Integer, Mass>();

    /**
     * load the model
     * 
     * @param model the game model
     * @param modelFile the model file
     */
    public void loadModel (Model model, File modelFile) {
        try {
            Scanner input = new Scanner(modelFile);
            while (input.hasNext()) {
                Scanner line = new Scanner(input.nextLine());
                while (line.hasNext()) {
                    String type = line.next();
                    if (MASS_KEYWORD.equals(type)) {
                        model.add(massCommand(line));
                    }
                    else if (SPRING_KEYWORD.equals(type)) {
                        model.add(springCommand(line));
                    }
                    else if (MUSCLE_KEYWORD.equals(type)) {
                        model.add(muscleCommand(line));
                    }
                    else if (FIXED_MASS_KEYWORD.equals(type)) {
                        model.add(fixMassCommand(line));
                    }
                }
            }
            input.close();
        }
        catch (FileNotFoundException e) {
            // should not happen because File came from user selection
            e.printStackTrace();
        }
    }

    /**
     * Creates a muscle object
     * 
     * @param line
     * @return result
     */
    private Muscle muscleCommand (Scanner line) {
        Mass m1 = myMasses.get(line.nextInt());
        Mass m2 = myMasses.get(line.nextInt());
        double length = line.nextDouble();
        double ks = line.nextDouble();
        double amp = line.nextDouble();
        double delay = line.nextDouble();
        double freq = line.nextDouble();
        return new Muscle(m1, m2, length, ks, amp, delay, freq);
    }

    /**
     * Creates a Fixed Mass
     * 
     * @param line
     * @return result
     */
    private FixedMass fixMassCommand (Scanner line) {
        int id = line.nextInt();
        double x = line.nextDouble();
        double y = line.nextDouble();
        double mass = line.nextDouble();
        FixedMass result = new FixedMass(x, y, mass, 0, 5);
        myMasses.put(id, result);
        return result;
    }

    /**
     * Creates a mass
     * 
     * @param line
     * @return result
     */
    private Mass massCommand (Scanner line) {
        int id = line.nextInt();
        double x = line.nextDouble();
        double y = line.nextDouble();
        double mass = line.nextDouble();
        Mass result = new Mass(x, y, mass, 0, 5);
        myMasses.put(id, result);
        return result;
    }

    /**
     * creates a spring
     * 
     * @param line
     * @return result
     */
    private Spring springCommand (Scanner line) {
        Mass m1 = myMasses.get(line.nextInt());
        Mass m2 = myMasses.get(line.nextInt());
        double restLength = line.nextDouble();
        double ks = line.nextDouble();
        return new Spring(m1, m2, restLength, ks);
    }
}
