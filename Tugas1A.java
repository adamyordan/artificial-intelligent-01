import ai.jarvis.JarvisEnvironmentState;
import ai.jarvis.JarvisEnvironmentStateImpl;
import ai.jarvis.JarvisProblem;
import aima.core.util.datastructure.XYLocation;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Main class for Jarvis and Tony problem
 * @author Adam Jordan 1406567536
 * @version 25/10/26
 */
public class Tugas1A {

    private static final String ERROR_ARG_LENGTH = "Invalid argument length. " +
            "Use: java Tugas1A <strategy> <fileinput> <fileoutput>\n";

    private static final String ERROR_STRATEGY = "Invalid strategy. " +
            "Use one of specified below: \n - ids\n - a*\n";

    private static final String ERROR_FILE_INPUT_NOTFOUND = "Invalid File Input. " +
            "File not found.\n";

    /**
     * Main method for Jarvis and Tony problem
     * @param args {strategy, inputFile, outputFile}
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // Validating argument length
        if (args.length != 3) {
            System.err.print(ERROR_ARG_LENGTH);
            System.exit(1);
        }

        final String strategyArg = args[0];
        final String inputFileArg = args[1];
        final String outputFileArg = args[2];

        // Validating strategy argument
        JarvisProblem.Strategy strategy = null;
        if (strategyArg.equalsIgnoreCase("ids")) {
            strategy = JarvisProblem.Strategy.Ids;
        } else if (strategyArg.equalsIgnoreCase("a*")) {
            strategy = JarvisProblem.Strategy.Astar;
        } else {
            System.err.print(ERROR_STRATEGY);
            System.exit(1);
        }

        // Validating input file
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFileArg));
        } catch (FileNotFoundException e) {
            System.err.print(ERROR_FILE_INPUT_NOTFOUND);
            System.exit(1);
        }

        StringTokenizer token;

        // Read cell height and width
        token = new StringTokenizer(bufferedReader.readLine(), ",");
        int n = Integer.parseInt(token.nextToken());
        int m = Integer.parseInt(token.nextToken());

        // Read initial position of Tony
        token = new StringTokenizer(bufferedReader.readLine(), ",");
        int xTony = Integer.parseInt(token.nextToken());
        int yTony = Integer.parseInt(token.nextToken());
        XYLocation tonyLocation = new XYLocation(yTony, xTony);

        // Read positions of stuffs
        token = new StringTokenizer(bufferedReader.readLine(), " ");
        Set<XYLocation> stuffLocations = new LinkedHashSet<>();
        while (token.hasMoreTokens()) {
            String locationTuple = token.nextToken();
            String[] locationArray = locationTuple.substring(1, locationTuple.length() - 1).split(",");
            int xStuff = Integer.parseInt(locationArray[0]);
            int yStuff = Integer.parseInt(locationArray[1]);
            stuffLocations.add(new XYLocation(yStuff, xStuff));
        }

        // Read positions of obstacles
        token = new StringTokenizer(bufferedReader.readLine(), " ");
        Set<XYLocation> obstacleLocations = new LinkedHashSet<>();
        while (token.hasMoreTokens()) {
            String locationTuple = token.nextToken();
            String[] locationArray = locationTuple.substring(1, locationTuple.length() - 1).split(",");
            int xObstacle = Integer.parseInt(locationArray[0]);
            int yObstacle = Integer.parseInt(locationArray[1]);
            obstacleLocations.add(new XYLocation(yObstacle, xObstacle));
        }

        // Setup initial state
        JarvisEnvironmentState initialState = new JarvisEnvironmentStateImpl(
                m,
                n,
                tonyLocation,
                stuffLocations,
                obstacleLocations);

        // Solve the problem
        JarvisProblem jarvisProblem = new JarvisProblem(initialState);
        String output = jarvisProblem.run(strategy);

        // Write result to output file
        BufferedWriter out = new BufferedWriter(new FileWriter(outputFileArg));
        out.write(output);
        out.close();
    }

}
