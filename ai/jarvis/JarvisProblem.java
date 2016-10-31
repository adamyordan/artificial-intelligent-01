package ai.jarvis;

import aima.core.agent.Action;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;

import java.util.List;

/**
 * Class to represent the Jarvis and Tony Problem, and provide
 * the solution using Iterative Deepening Search and A* strategy.
 * @author Adam Jordan 1406567536
 * @version 16.10.25
 */
public class JarvisProblem {

    public enum Strategy {
        Ids, Astar
    };

    private JarvisEnvironmentState initialState;

    public JarvisProblem(JarvisEnvironmentState initialState) {
        this.initialState = initialState;
    }

    public String run(Strategy strategy) {
        String output = null;
        switch (strategy) {
            case Ids:
                output = runIds();
                break;
            case Astar:
                output = runAstar();
                break;
        }
        return output;
    }

    private String runIds() {
        String output = null;
        try {
            Problem problem = new Problem(
                initialState,
                JarvisFunctionsFactory.getActionsFunction(),
                JarvisFunctionsFactory.getResultFunction(),
                new JarvisGoalTest(),
                JarvisFunctionsFactory.getStepCostFunction());

            Search search = new IterativeDeepeningSearch();
            SearchAgent agent = new SearchAgent(problem, search);

            output = (int) Double.parseDouble(agent.getInstrumentation().getProperty("pathCost")) + "\n";
            output += agent.getInstrumentation().getProperty("nodesExpanded") + "\n";
            output += stringifyActions(agent.getActions());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    private String runAstar() {
        String output = null;
        try {
            Problem problem = new Problem(
                    initialState,
                    JarvisFunctionsFactory.getActionsFunction(),
                    JarvisFunctionsFactory.getResultFunction(),
                    new JarvisGoalTest(),
                    JarvisFunctionsFactory.getStepCostFunction());

            Search search = new AStarSearch(new TreeSearch(), new MSTHeuristicFunction());
            SearchAgent agent = new SearchAgent(problem, search);

            output = (int) Double.parseDouble(agent.getInstrumentation().getProperty("pathCost")) + "\n";
            output += agent.getInstrumentation().getProperty("nodesExpanded") + "\n";
            output += stringifyActions(agent.getActions());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    private String stringifyActions(List<Action> actions) {
        String result = "";
        for (Action action : actions) {
            if (action == JarvisAction.MoveUp) {
                result += "ATAS\n";
            } else if (action == JarvisAction.MoveDown) {
                result += "BAWAH\n";
            } else if (action == JarvisAction.MoveLeft) {
                result += "KIRI\n";
            } else if (action == JarvisAction.MoveRight) {
                result += "KANAN\n";
            } else if (action == JarvisAction.TakeStuff) {
                result += "AMBIL\n";
            }
        }
        return result;
    }

}
