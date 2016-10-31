package ai.jarvis;

import aima.core.agent.Action;

/**
 * Jarvis problem's Action
 * @author Adam Jordan 1406567536
 * @version 25/10/26
 */
public enum JarvisAction implements Action {
    MoveUp, MoveDown, MoveLeft, MoveRight, TakeStuff;

    @Override
    public boolean isNoOp() {
        return false;
    }
}
