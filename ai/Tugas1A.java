package ai;

import ai.jarvis.JarvisEnvironmentState;
import ai.jarvis.JarvisEnvironmentStateImpl;
import ai.jarvis.JarvisProblem;
import aima.core.util.datastructure.XYLocation;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by adam on 23/10/16.
 */
public class Tugas1A {

    private static final String ERROR_ARG_LENGTH = "Invalid argument length. " +
            "Use: java Tugas1A <strategy> <fileinput> <fileoutput>\n";

    private static final String ERROR_STRATEGY = "Invalid strategy. " +
            "Use one of specified below: \n - ids\n - a*\n";

    private static final String ERROR_FILE_INPUT_NOTFOUND = "Invalid File Input. " +
            "File not found.\n";

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.print(ERROR_ARG_LENGTH);
            System.exit(1);
        }

        final String strategyArg = args[0];
        final String inputFileArg = args[1];
        final String outputFileArg = args[2];

        JarvisProblem.Strategy strategy = null;
        if (strategyArg.equalsIgnoreCase("ids")) {
            strategy = JarvisProblem.Strategy.Ids;
        } else if (strategyArg.equalsIgnoreCase("a*")) {
            strategy = JarvisProblem.Strategy.Astar;
        } else {
            System.err.print(ERROR_STRATEGY);
            System.exit(1);
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFileArg));
        } catch (FileNotFoundException e) {
            System.err.print(ERROR_FILE_INPUT_NOTFOUND);
            System.exit(1);
        }

        StringTokenizer token;

        token = new StringTokenizer(bufferedReader.readLine(), ",");
        int n = Integer.parseInt(token.nextToken());
        int m = Integer.parseInt(token.nextToken());

        token = new StringTokenizer(bufferedReader.readLine(), ",");
        int xTony = Integer.parseInt(token.nextToken());
        int yTony = Integer.parseInt(token.nextToken());
        XYLocation tonyLocation = new XYLocation(yTony, xTony);

        token = new StringTokenizer(bufferedReader.readLine(), " ");
        Set<XYLocation> stuffLocations = new LinkedHashSet<>();
        while (token.hasMoreTokens()) {
            String locationTuple = token.nextToken();
            String[] locationArray = locationTuple.substring(1, locationTuple.length() - 1).split(",");
            int xStuff = Integer.parseInt(locationArray[0]);
            int yStuff = Integer.parseInt(locationArray[1]);
            stuffLocations.add(new XYLocation(yStuff, xStuff));
        }

        token = new StringTokenizer(bufferedReader.readLine(), " ");
        Set<XYLocation> obstacleLocations = new LinkedHashSet<>();
        while (token.hasMoreTokens()) {
            String locationTuple = token.nextToken();
            String[] locationArray = locationTuple.substring(1, locationTuple.length() - 1).split(",");
            int xObstacle = Integer.parseInt(locationArray[0]);
            int yObstacle = Integer.parseInt(locationArray[1]);
            obstacleLocations.add(new XYLocation(yObstacle, xObstacle));
        }

        JarvisEnvironmentState initialState = new JarvisEnvironmentStateImpl(
                m,
                n,
                tonyLocation,
                stuffLocations,
                obstacleLocations);
        JarvisProblem jarvisProblem = new JarvisProblem(initialState);

        String output = jarvisProblem.run(strategy);

        BufferedWriter out = new BufferedWriter(new FileWriter(outputFileArg));
        out.write(output);
        out.close();
    }

}
