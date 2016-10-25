package ai.jarvis;

import aima.core.agent.Action;

/**
 * Created by adam on 23/10/16.
 */
public enum JarvisAction implements Action {
    MoveUp, MoveDown, MoveLeft, MoveRight, TakeStuff;

    @Override
    public boolean isNoOp() {
        return false;
    }
}
