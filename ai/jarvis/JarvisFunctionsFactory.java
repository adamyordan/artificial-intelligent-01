package ai.jarvis;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import aima.core.search.framework.problem.ResultFunction;
import aima.core.search.framework.problem.StepCostFunction;
import aima.core.util.datastructure.XYLocation;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Jarvis problem's funtions factory
 * Generating
 *      - Actions Function
 *      - Result Function
 *      - Step Cost Function
 * @author Adam Jordan 1406567536
 * @version 25/10/26
 */
public class JarvisFunctionsFactory {

    private static ActionsFunction _actionsFunction;
    private static ResultFunction _resultFunction;
    private static StepCostFunction _stepCostFunction;

    /**
     * Get Actions Function
     * @return actionsFunction
     */
    public static ActionsFunction getActionsFunction() {
        if (_actionsFunction == null) {
            _actionsFunction = new JarvisActionsFunction();
        }
        return _actionsFunction;
    }

    /**
     * Get Result Function
     * @return resultFunction
     */
    public static ResultFunction getResultFunction() {
        if (_resultFunction == null) {
            _resultFunction = new JarvisResultFunction();
        }
        return _resultFunction;
    }

    /**
     * Get Step Cost Function
     * @return stepCostFunction
     */
    public static StepCostFunction getStepCostFunction() {
        if (_stepCostFunction == null) {
            _stepCostFunction = new JarvisStepCostFunction();
        }
        return _stepCostFunction;
    }

    /**
     * Jarvis problem's Actions Function
     */
    private static class JarvisActionsFunction implements ActionsFunction {

        /**
         * Actions(state)
         * @param state current state
         * @return set of possible actions under current state
         */
        @Override
        public Set<Action> actions(Object state) {
            JarvisEnvironmentState jarvisEnvironmentState = (JarvisEnvironmentState) state;
            XYLocation tonyLocation = jarvisEnvironmentState.getTonyLocation();

            Set<Action> actions = new LinkedHashSet<>();
            if (jarvisEnvironmentState.isLocationContainsStuff(tonyLocation)) {
                actions.add(JarvisAction.TakeStuff);
            } else {
                if (jarvisEnvironmentState.isLocationExplorable(tonyLocation.up())) {
                    actions.add(JarvisAction.MoveUp);
                }
                if (jarvisEnvironmentState.isLocationExplorable(tonyLocation.down())) {
                    actions.add(JarvisAction.MoveDown);
                }
                if (jarvisEnvironmentState.isLocationExplorable(tonyLocation.left())) {
                    actions.add(JarvisAction.MoveLeft);
                }
                if (jarvisEnvironmentState.isLocationExplorable(tonyLocation.right())) {
                    actions.add(JarvisAction.MoveRight);
                }
            }

            return actions;
        }
    }

    /**
     * Jarvis problem's Result Function
     */
    private static class JarvisResultFunction implements ResultFunction {

        /**
         * Result(state, action)
         * @param state current state
         * @param action actions taken
         * @return next state
         */
        @Override
        public Object result(Object state, Action action) {
            JarvisEnvironmentState newState = ((JarvisEnvironmentState) state).getClone();
            if (action == JarvisAction.TakeStuff) {
                newState.removeStuff(newState.getTonyLocation());
            } else if (action == JarvisAction.MoveUp) {
                newState.setTonyLocation(newState.getTonyLocation().up());
            } else if (action == JarvisAction.MoveDown) {
                newState.setTonyLocation(newState.getTonyLocation().down());
            } else if (action == JarvisAction.MoveLeft) {
                newState.setTonyLocation(newState.getTonyLocation().left());
            } else if (action == JarvisAction.MoveRight) {
                newState.setTonyLocation(newState.getTonyLocation().right());
            }
            return newState;
        }
    }

    /**
     * Jarvis problem's Step Cost Function
     */
    private static class JarvisStepCostFunction implements StepCostFunction {

        /**
         * Cost (state, action, stateDelta):
         * TakeStuff                 = 0
         * Move [up|down|left|right] = 1
         */
        @Override
        public double c(Object state, Action action, Object sDelta) {
            if (action == JarvisAction.TakeStuff) {
                return 0;
            } else if (action == JarvisAction.MoveUp) {
                return 1;
            } else if (action == JarvisAction.MoveDown) {
                return 1;
            } else if (action == JarvisAction.MoveLeft) {
                return 1;
            } else if (action == JarvisAction.MoveRight) {
                return 1;
            }
            return 0;
        }
    }

}
