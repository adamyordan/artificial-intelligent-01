package ai.jarvis;

import aima.core.util.datastructure.XYLocation;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Implementation of Jarvis problem's state
 * @author Adam Jordan 1406567536
 * @version 25/10/26
 */
public class JarvisEnvironmentStateImpl implements JarvisEnvironmentState {

    private int boardWidth;
    private int boardHeight;

    private XYLocation tonyLocation;
    private Set<XYLocation> stuffLocations;
    private Set<XYLocation> obstacleLocations;

    public JarvisEnvironmentStateImpl(int boardWidth,
                                      int boardHeight,
                                      XYLocation tonyLocation,
                                      Set<XYLocation> stuffLocations,
                                      Set<XYLocation> obstacleLocations) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tonyLocation = tonyLocation;

        this.stuffLocations = stuffLocations;
        this.obstacleLocations = obstacleLocations;
    }

    public JarvisEnvironmentState getClone() {
        XYLocation clonedTonyLocation = new XYLocation(
                this.getTonyLocation().getXCoOrdinate(),
                this.getTonyLocation().getYCoOrdinate());

        Set<XYLocation> clonedStuffLocations = new LinkedHashSet<>();
        clonedStuffLocations.addAll(this.getStuffLocations());
        Set<XYLocation> clonedObstacleLocations = new LinkedHashSet<>();
        clonedObstacleLocations.addAll(this.getObstacleLocations());
        return new JarvisEnvironmentStateImpl(
                this.getBoardWidth(),
                this.getBoardHeight(),
                clonedTonyLocation,
                clonedStuffLocations,
                clonedObstacleLocations);
    }

    public int getNumberOfStuffsLeft() {
        return stuffLocations.size();
    }

    public void removeStuff(XYLocation location) {
        stuffLocations.remove(location);
    }

    public boolean isLocationExplorable(XYLocation location) {
        return isLocationInBoard(location) && !isLocationContainsObstacle(location);
    }

    public boolean isLocationContainsStuff(XYLocation location) {
        for (XYLocation stuffLocation : stuffLocations) {
            if (stuffLocation.equals(location)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLocationInBoard (XYLocation location) {
        return location.getXCoOrdinate() >= 1 && location.getXCoOrdinate() <= boardWidth &&
               location.getYCoOrdinate() >= 1 && location.getYCoOrdinate() <= boardHeight;

    }

    public boolean isLocationContainsObstacle (XYLocation location) {
        for (XYLocation obstacleLocation : obstacleLocations) {
            if (obstacleLocation.equals(location)) {
                return true;
            }
        }
        return false;
    }

    public XYLocation getTonyLocation() {
        return tonyLocation;
    }

    public void setTonyLocation(XYLocation tonyLocation) {
        this.tonyLocation = tonyLocation;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public Set<XYLocation> getStuffLocations() {
        return stuffLocations;
    }

    public void setStuffLocations(Set<XYLocation> stuffLocations) {
        this.stuffLocations = stuffLocations;
    }

    public Set<XYLocation> getObstacleLocations() {
        return obstacleLocations;
    }

    public void setObstacleLocations(Set<XYLocation> obstacleLocations) {
        this.obstacleLocations = obstacleLocations;
    }
}
