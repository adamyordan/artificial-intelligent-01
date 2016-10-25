package ai.sudoku;

import aima.core.logic.propositional.inference.WalkSAT;
import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.Model;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.util.datastructure.XYLocation;

import java.util.*;

/**
 * Created by adam on 23/10/16.
 */
public class SudokuProblem {

    public enum Strategy {
        DPLL, WalkSAT
    }

    public static final int WALKSAT_MAX_FLIPS = -1;
    public static final double WALKSAT_RANDOMWALK_PROBABILITY = 0.5;

    private final int size;
    private final Set<Clause> initialValues;

    public SudokuProblem(int size, Map<XYLocation, Integer> initialValuesMap) {
        this.size = size;
        this.initialValues = new HashSet<>();
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

    private String runDpll() {
        Set<Clause> clauses = SudokuClausesFactory.getClauses(size);
        clauses.addAll(initialValues);
        List<PropositionSymbol> symbols = SudokuClausesFactory.getPropositionalSymbols(size);

        DPLLSolver dpllSolver = new DPLLSolver();
        Model satisfyingModel = dpllSolver.getSatisfyingModel(clauses, symbols, new Model());

        return printSudokuModel(satisfyingModel, size);
    }

    private String runWalkSat() {
        Set<Clause> clauses = SudokuClausesFactory.getClauses(size);
        clauses.addAll(initialValues);

        WalkSAT walkSAT = new WalkSAT();
        Model satisfyingModel = walkSAT.walkSAT(clauses,
                WALKSAT_RANDOMWALK_PROBABILITY, WALKSAT_MAX_FLIPS);

        return printSudokuModel(satisfyingModel, size);
    }

    private String printSudokuModel(Model model, int size) {
        String value = "";
        for (int x = 1; x <= size; x++) {
            for (int y = 1; y <= size; y++) {
                int z = 1;
                boolean found = false;
                while (!found && z <= 9) {
                    if (model.getValue(SudokuClausesFactory.sudokuSymbol(x, y, z))) {
                        found = true;
                    } else {
                        z++;
                    }
                }
                value += found ? z : '-';
            }
            value += "\n";
        }
        return value;
    }

}
