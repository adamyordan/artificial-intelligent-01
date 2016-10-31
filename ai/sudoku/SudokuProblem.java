package ai.sudoku;

import aima.core.logic.propositional.inference.WalkSAT;
import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.Model;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.util.datastructure.XYLocation;

import java.util.*;

/**
 * Class to represent a sudoku game as a SAT problem,
 * and provide the solution using DPLL and WalkSAT strategy.
 * @author Adam Jordan 1406567536
 * @version 16.10.25
 */
public class SudokuProblem {

    public enum Strategy {
        DPLL, WalkSAT
    }

    // Constants for WalkSAT configuration
    public static final int WALKSAT_MAX_FLIPS = -1; // infinite
    public static final double WALKSAT_RANDOMWALK_PROBABILITY = 0.9;

    private final int size;
    private final Set<Clause> initialValues;

    /**
     * Constructor
     * @param size size of the sudoku board
     * @param initialValuesMap initial or preassigned values in sudoku board
     */
    public SudokuProblem(int size, Map<XYLocation, Integer> initialValuesMap) {
        this.size = size;
        this.initialValues = new HashSet<>();

        // Convert map of preassigned values into unit clauses
        for (XYLocation xyKey : initialValuesMap.keySet()) {
            for (int i = 1; i <= size; i++) {
                if (i == initialValuesMap.get(xyKey)) {
                    initialValues.add(new Clause(SudokuClausesFactory.sudokuLiteral(
                            xyKey.getXCoOrdinate(), xyKey.getYCoOrdinate(), i)));
                } else {
                    initialValues.add(new Clause(SudokuClausesFactory.sudokuNegativeLiteral(
                            xyKey.getXCoOrdinate(), xyKey.getYCoOrdinate(), i)));
                }
            }
        }
    }

    /**
     * Solve the problem using specified strategy.
     * @param strategy strategy [DPLL] [SalkSAT]
     * @return String representing solved sudoku problem
     */
    public String run(Strategy strategy) {
        String output = null;
        switch (strategy) {
            case DPLL:
                output = runDpll();
                break;
            case WalkSAT:
                output = runWalkSat();
                break;
        }
        return output;
    }

    /**
     * Solve the problem using strategy: DPLL.
     * @return String representing solved sudoku problem
     */
    private String runDpll() {
        // Generate set of clauses from sudoku
        // extended encoding and preassigned values
        Set<Clause> clauses = SudokuClausesFactory.getClauses(size);
        clauses.addAll(initialValues);

        // Generate symbol used
        List<PropositionSymbol> symbols = SudokuClausesFactory.getPropositionalSymbols(size);

        // Solve this problem using DPLL
        DPLLSolver dpllSolver = new DPLLSolver();
        Model satisfyingModel = dpllSolver.getSatisfyingModel(clauses, symbols, new Model());

        return printSudokuModel(satisfyingModel, size);
    }

    /**
     * Solve the problem using strategy: WalkSAT.
     * @return String representing solved sudoku problem
     */
    private String runWalkSat() {
        // Generate set of clauses from sudoku
        // extended encoding and preassigned values
        Set<Clause> clauses = SudokuClausesFactory.getClauses(size);
        clauses.addAll(initialValues);

        // Solve this problem using WalkSAT
        WalkSAT walkSAT = new WalkSAT();
        Model satisfyingModel = walkSAT.walkSAT(clauses,
                WALKSAT_RANDOMWALK_PROBABILITY, WALKSAT_MAX_FLIPS);

        return printSudokuModel(satisfyingModel, size);
    }

    /**
     * Generate a string representing solved sudoku problem
     * in grids from a model.
     * @param model model of the problem
     * @param size size of the sudoku board
     * @return String representing solved sudoku problem
     */
    private String printSudokuModel(Model model, int size) {
        String value = "";
        final String delimiter = " ";

        // loop through all entry
        for (int x = 1; x <= size; x++) {
            for (int y = 1; y <= size; y++) {
                // print satisfying number for current entry
                int z = 1;
                boolean found = false;
                while (!found && z <= size) {
                    if (model.getValue(SudokuClausesFactory.sudokuSymbol(x, y, z))) {
                        found = true;
                    } else {
                        z++;
                    }
                }
                value += found ? z : '-' + delimiter;
            }
            // trim additional delimiter and add newline character
            value = value.substring(0, value.length()-1);
            value += "\n";
        }
        return value;
    }

}
