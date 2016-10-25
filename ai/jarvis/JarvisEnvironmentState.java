package ai.jarvis;

import aima.core.agent.EnvironmentState;
import aima.core.util.datastructure.XYLocation;

import java.util.Set;

/**
 * Created by adam on 23/10/16.
 */
public interface JarvisEnvironmentState extends EnvironmentState {

    JarvisEnvironmentState getClone();

    int getNumberOfStuffsLeft();

    void removeStuff(XYLocation location);

    boolean isLocationExplorable(XYLocation location);

    boolean isLocationContainsStuff(XYLocation location);

    XYLocation getTonyLocation();

    void setTonyLocation(XYLocation tonyLocation);

    Set<XYLocation> getStuffLocations();

}
