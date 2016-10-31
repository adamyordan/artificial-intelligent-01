package ai.jarvis;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

import java.util.*;

/**
 * Heuristic Function for Jarvis Problem.
 * Using MST (Minimum Spanning Tree) to calculate the
 * distance for Tony to move toward all the stuffs
 * @author Adam Jordan 1406567536
 * @version 16.10.25
 */
public class MSTHeuristicFunction implements HeuristicFunction {

    /**
     * Heuristic Function:
     * Total of weight of all the MST's edge
     * @param state current state
     * @return heuristic value
     */
    @Override
    public double h(Object state) {
        JarvisEnvironmentState jarvisEnvironmentState = (JarvisEnvironmentState) state;
        XYLocation tonyLocation = jarvisEnvironmentState.getTonyLocation();

        // Vertices are Tony's location and stuffs' locations
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(new Vertex(tonyLocation.getXCoOrdinate(), tonyLocation.getYCoOrdinate()));
        for (XYLocation xyLocation : jarvisEnvironmentState.getStuffLocations()) {
            vertices.add(new Vertex(xyLocation.getXCoOrdinate(), xyLocation.getYCoOrdinate()));
        }

        double totalWeightOfMSP = Kruskal.getVertexDistance(vertices);

        return totalWeightOfMSP;
    }

}
