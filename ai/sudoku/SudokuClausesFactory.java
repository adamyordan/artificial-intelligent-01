package ai.sudoku;

import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.Literal;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Factory class to generate clauses that represent
 * a sudoku game as a SAT problem.
 * @author Adam Jordan 1406567536
 * @version 16.10.25
 */
public class SudokuClausesFactory {

    /**
     * Generate clauses representing the problem of a sudoku game
     * @param size size of sudoku board
     * @return set of clauses
     */
    public static Set<Clause> getClauses(int size) {
        Set<Clause> clauses = new LinkedHashSet<>();

        // Minimal Encoding
        clauses.addAll(generateAtLeastOnceInEachEntry(size));
        clauses.addAll(generateAtMostOnceInEachRow(size));
        clauses.addAll(generateAtMostOnceInEachColumn(size));
        clauses.addAll(generateAtMostOnceInEachGrid(size));

        // Additional clauses for Extended Encoding
        clauses.addAll(generateAtMostOnceInEachEntry(size));
        clauses.addAll(generateAtLeastOnceInEachRow(size));
        clauses.addAll(generateAtLeastOnceInEachColumn(size));
        clauses.addAll(generateAtLeastOnceInEachGrid(size));

        return clauses;
    }

    /**
     * Generate symbols used in a SAT representation of sudoku game
     * @param size size of the sudoku board
     * @return list of symbols used
     */
    public static List<PropositionSymbol> getPropositionalSymbols(int size) {
        List<PropositionSymbol> symbols = new ArrayList<>();
        for (int x = 1; x <= size; x++) {
            for (int y = 1; y <= size; y++) {
                for (int z = 1; z <= size; z++) {
                    symbols.add(new PropositionSymbol(symbolName(x, y, z)));
                }
            }
        }
        return symbols;
    }

    /**
     * Generate symbol name
     * @param x
     * @param y
     * @param z
     * @return symbol name "sxyz"
     */
    public static String symbolName(int x, int y, int z) {
        return "s" + x + y + z;
    }

    /**
     * Generate Proposition Symbol
     * @param x
     * @param y
     * @param z
     * @return proposition symbol of "sxyz"
     */
    public static PropositionSymbol sudokuSymbol(int x, int y, int z) {
        return new PropositionSymbol(symbolName(x, y, z));
    }

    /**
     * Generate positive Literal
     * @param x
     * @param y
     * @param z
     * @return literal of "sxyz"
     */
    public static Literal sudokuLiteral(int x, int y, int z) {
        return new Literal(new PropositionSymbol(symbolName(x, y, z)));
    }

    /**
     * Generate negative literal
     * @param x
     * @param y
     * @param z
     * @return negative literal of "sxyz", i.e "~sxyz"
     */
    public static Literal sudokuNegativeLiteral(int x, int y, int z) {
        return new Literal(new PropositionSymbol(symbolName(x, y, z)), false);
    }

    /**
     * Generate clauses of "at least one number in each entry"
     * @param size size of sudoku board
     * @return set of clauses
     */
    private static Set<Clause> generateAtLeastOnceInEachEntry(int size) {
        Set<Clause> clauses = new LinkedHashSet<>();
        for (int x = 1; x <= size; x++) {
            for (int y = 1; y <= size; y++) {
                Set<Literal> literals = new LinkedHashSet<>();
                for (int z = 1; z <= size; z++) {
                    literals.add(sudokuLiteral(x, y, z));
                }
                clauses.add(new Clause(literals));
            }
        }
        return clauses;
    }

    /**
     * Generate clauses of "at most one number in each row"
     * @param size size of sudoku board
     * @return set of clauses
     */
    private static Set<Clause> generateAtMostOnceInEachRow(int size) {
        Set<Clause> clauses = new LinkedHashSet<>();
        for (int y = 1; y <= size; y++) {
            for (int z = 1; z <= size; z++) {
                for (int x = 1; x <= size-1; x++) {
                    for (int i = x+1; i <= size; i++) {
                        clauses.add(new Clause(
                                sudokuNegativeLiteral(x, y, z),
                                sudokuNegativeLiteral(i, y, z)));
                    }
                }
            }
        }
        return clauses;
    }

    /**
     * Generate clauses of "at most one number in each column"
     * @param size size of sudoku board
     * @return set of clauses
     */
    private static Set<Clause> generateAtMostOnceInEachColumn(int size) {
        Set<Clause> clauses = new LinkedHashSet<>();
        for (int x = 1; x <= size; x++) {
            for (int z = 1; z <= size; z++) {
                for (int y = 1; y <= size-1; y++) {
                    for (int i = y+1; i <= size; i++) {
                        clauses.add(new Clause(
                                sudokuNegativeLiteral(x, y, z),
                                sudokuNegativeLiteral(x, i, z)));
                    }
                }
            }
        }
        return clauses;
    }

    /**
     * Generate clauses of "at most one number in each grid"
     * @param size size of sudoku board
     * @return set of clauses
     */
    private static Set<Clause> generateAtMostOnceInEachGrid(int size) {
        Set<Clause> clauses = new LinkedHashSet<>();
        for (int z = 1; z <= size; z++) {
            for (int i = 0; i <= (int) Math.sqrt(size) - 1; i++) {
                for (int j = 0; j <= (int) Math.sqrt(size) - 1; j++) {
                    for (int x = 1; x <= (int) Math.sqrt(size); x++) {
                        for (int y = 1; y <= (int) Math.sqrt(size); y++) {
                            for (int k = y+1; k <= (int) Math.sqrt(size); k++) {
                                clauses.add(new Clause(
                                        sudokuNegativeLiteral(3*i+x, 3*j+y, z),
                                        sudokuNegativeLiteral(3*i+x, 3*j+k, z)));
                            }
                        }
                    }
                }
            }
        }
        for (int z = 1; z <= size; z++) {
            for (int i = 0; i <= (int) Math.sqrt(size) - 1; i++) {
                for (int j = 0; j <= (int) Math.sqrt(size) - 1; j++) {
                    for (int x = 1; x <= (int) Math.sqrt(size); x++) {
                        for (int y = 1; y <= (int) Math.sqrt(size); y++) {
                            for (int k = x+1; k <= (int) Math.sqrt(size); k++) {
                                for (int l = 1; l <= (int) Math.sqrt(size); l++) {
                                    clauses.add(new Clause(
                                            sudokuNegativeLiteral(3*i+x, 3*j+y, z),
                                            sudokuNegativeLiteral(3*i+k, 3*j+l, z)));
                                }
                            }
                        }
                    }
                }
            }
        }
        return clauses;
    }

    /**
     * Generate clauses of "at most one number in each entry"
     * @param size size of sudoku board
     * @return set of clauses
     */
    private static Set<Clause> generateAtMostOnceInEachEntry(int size) {
        Set<Clause> clauses = new LinkedHashSet<>();
        for (int x = 1; x <= size; x++) {
            for (int y = 1; y <= size; y++) {
                for (int z = 1; z <= size-1; z++) {
                    for (int i = z+1; i <= size; i++) {
                        clauses.add(new Clause(
                                sudokuNegativeLiteral(x, y, z),
                                sudokuNegativeLiteral(x, y, i)));
                    }
                }
            }
        }
        return clauses;
    }

    /**
     * Generate clauses of "at least one number in each row"
     * @param size size of sudoku board
     * @return set of clauses
     */
    private static Set<Clause> generateAtLeastOnceInEachRow(int size) {
        Set<Clause> clauses = new LinkedHashSet<>();
        for (int y = 1; y <= size; y++) {
            for (int z = 1; z <= size; z++) {
                Set<Literal> literals = new LinkedHashSet<>();
                for (int x = 1; x <= size; x++) {
                    literals.add(sudokuLiteral(x, y, z));
                }
                clauses.add(new Clause(literals));
            }
        }
        return clauses;
    }

    /**
     * Generate clauses of "at least one number in each column"
     * @param size size of sudoku board
     * @return set of clauses
     */
    private static Set<Clause> generateAtLeastOnceInEachColumn(int size) {
        Set<Clause> clauses = new LinkedHashSet<>();
        for (int x = 1; x <= size; x++) {
            for (int z = 1; z <= size; z++) {
                Set<Literal> literals = new LinkedHashSet<>();
                for (int y = 1; y <= size; y++) {
                    literals.add(sudokuLiteral(x, y, z));
                }
                clauses.add(new Clause(literals));
            }
        }
        return clauses;
    }

    /**
     * Generate clauses of "at least one number in each grid"
     * @param size size of sudoku board
     * @return set of clauses
     */
    private static Set<Clause> generateAtLeastOnceInEachGrid(int size) {
        Set<Clause> clauses = new LinkedHashSet<>();
        int sqrt_size = (int) Math.sqrt(size);
        for (int i = 0; i <= sqrt_size - 1; i++) {
            for (int j = 0; j <= sqrt_size - 1; j++) {
                for (int z = 1; z <= size; z++) {
                    Set<Literal> literals = new LinkedHashSet<>();
                    for (int x = 1; x <= sqrt_size; x++) {
                        for (int y = 1; y <= sqrt_size; y++) {
                            literals.add(sudokuLiteral(
                                    sqrt_size * i + x,
                                    sqrt_size * j + y, z));
                        }
                    }
                    clauses.add(new Clause(literals));
                }
            }
        }
        return clauses;
    }

}
