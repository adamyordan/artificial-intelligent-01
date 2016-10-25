package ai.jarvis;

import aima.core.search.framework.problem.GoalTest;

/**
 * Created by adam on 23/10/16.
 */
public class JarvisGoalTest implements GoalTest {
    @Override
    public boolean isGoalState(Object state) {
        JarvisEnvironmentState jarvisEnvironmentState = (JarvisEnvironmentState) state;
        return jarvisEnvironmentState.getNumberOfStuffsLeft() == 0;
    }
}
