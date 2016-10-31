package ai.sudoku;

import aima.core.logic.propositional.inference.DPLLSatisfiable;
import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.Model;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ConvertToConjunctionOfClauses;
import aima.core.util.Util;
import aima.core.util.datastructure.Pair;

import java.util.List;
import java.util.Set;

/**
 * Class extending DPLLSatisfiable and contains the method
 * used to return the satisfying model of a SAT Problem
 * using DPLL Algorithm
 * @author Adam Jordan 1406567536
 * @version 25/10/26
 */
public class DPLLSolver extends DPLLSatisfiable {

    /**
     * DPLL(clauses, symbols, model)
     *
     * @param clauses
     *            the set of clauses.
     * @param symbols
     *            a list of unassigned symbols.
     * @param model
     *            contains the values for assigned symbols.
     * @return model if the model is satisfiable under current assignments, null
     *         otherwise.
     */
    public Model getSatisfyingModel(Set<Clause> clauses, List<PropositionSymbol> symbols,
                                    Model model) {
        // if every clause in clauses is true in model then return true
        if (everyClauseTrue(clauses, model)) {
            return model;
        }
        // if some clause in clauses is false in model then return false
        if (someClauseFalse(clauses, model)) {
            return null;
        }

        // P, value <- FIND-PURE-SYMBOL(symbols, clauses, model)
        Pair<PropositionSymbol, Boolean> pAndValue = findPureSymbol(symbols,
                clauses, model);
        // if P is non-null then
        if (pAndValue != null) {
            // return DPLL(clauses, symbols - P, model U {P = value})
            return getSatisfyingModel(clauses, minus(symbols, pAndValue.getFirst()),
                    model.union(pAndValue.getFirst(), pAndValue.getSecond()));
        }

        // P, value <- FIND-UNIT-CLAUSE(clauses, model)
        pAndValue = findUnitClause(clauses, model);
        // if P is non-null then
        if (pAndValue != null) {
            // return DPLL(clauses, symbols - P, model U {P = value})
            return getSatisfyingModel(clauses, minus(symbols, pAndValue.getFirst()),
                    model.union(pAndValue.getFirst(), pAndValue.getSecond()));
        }

        // P <- FIRST(symbols); rest <- REST(symbols)
        PropositionSymbol p = Util.first(symbols);
        List<PropositionSymbol> rest = Util.rest(symbols);
        // return DPLL(clauses, rest, model U {P = true}) or
        // ...... DPLL(clauses, rest, model U {P = false})

        Model modelCandidate1 = getSatisfyingModel(clauses, rest, model.union(p, true));
        if (modelCandidate1 != null) {
            return modelCandidate1;
        } else {
            return getSatisfyingModel(clauses, rest, model.union(p, false));
        }
    }

}
