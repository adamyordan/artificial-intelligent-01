package ai.jarvis;

import aima.core.search.framework.problem.GoalTest;

/**
 * Goal Test for Jarvis problem
 * @author Adam Jordan 1406567536
 * @version 25/10/26
 */
public class JarvisGoalTest implements GoalTest {

    /**
     * Check if current state is goal state
     * @param state current state
     * @return true if current state, false otherwise
     */
    @Override
    public boolean isGoalState(Object state) {
        JarvisEnvironmentState jarvisEnvironmentState = (JarvisEnvironmentState) state;
        return jarvisEnvironmentState.getNumberOfStuffsLeft() == 0;
    }
}
