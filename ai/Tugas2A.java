package ai;

import ai.sudoku.SudokuProblem;
import aima.core.util.datastructure.XYLocation;

import java.io.*;
import java.util.*;

/**
 * Created by adam on 23/10/16.
 */
public class Tugas2A {

    private static final String ERROR_ARG_LENGTH = "Invalid argument length. " +
            "Use: java Tugas2A <strategy> <fileinput> <fileoutput>\n";

    private static final String ERROR_STRATEGY = "Invalid strategy. " +
            "Use one of specified below: \n - dpll\n - walksat\n";

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

        SudokuProblem.Strategy strategy = null;
        if (strategyArg.equalsIgnoreCase("dpll")) {
            strategy = SudokuProblem.Strategy.DPLL;
        } else if (strategyArg.equalsIgnoreCase("walksat")) {
            strategy = SudokuProblem.Strategy.WalkSAT;
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

        token = new StringTokenizer(bufferedReader.readLine(), " ");
        int size = Integer.parseInt(token.nextToken());

        Map<XYLocation, Integer> initialValues = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            token = new StringTokenizer(bufferedReader.readLine(), " ");
            for (int j = 1; j <= size; j++) {
                int val = Integer.parseInt(token.nextToken());
                if (val >= 1 && val <= 9) {
                    initialValues.put(new XYLocation(i, j), val);
                }
            }
        }

        SudokuProblem sudokuProblem = new SudokuProblem(size, initialValues);

        String output = sudokuProblem.run(strategy);

        BufferedWriter out = new BufferedWriter(new FileWriter(outputFileArg));
        out.write(output);
        out.close();
    }

}
