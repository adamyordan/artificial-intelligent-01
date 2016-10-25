package ai.jarvis;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

import java.util.*;

/**
 * Created by adam on 25/10/16.
 */
public class MSTHeuristicFunction implements HeuristicFunction {

    @Override
    public double h(Object state) {
        JarvisEnvironmentState jarvisEnvironmentState = (JarvisEnvironmentState) state;
        XYLocation tonyLocation = jarvisEnvironmentState.getTonyLocation();

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(new Vertex(tonyLocation.getXCoOrdinate(), tonyLocation.getYCoOrdinate()));
        for (XYLocation xyLocation : jarvisEnvironmentState.getStuffLocations()) {
            vertices.add(new Vertex(xyLocation.getXCoOrdinate(), xyLocation.getYCoOrdinate()));
        }

        double totalWeightOfMSP = Kruskal.getVertexDistance(vertices);

        return totalWeightOfMSP;
    }

}
