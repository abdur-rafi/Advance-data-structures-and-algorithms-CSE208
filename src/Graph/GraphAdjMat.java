package Graph;

import java.util.Arrays;

public class GraphAdjMat extends SinglePairShortestPath {
    protected final double[][] adjMat;
    public GraphAdjMat(int n){
        super(n);
        adjMat = new double[n][n];
        for(int i = 0; i < n; ++i){
            for(int j = 0; j < n; ++j){
                if(i == j) adjMat[i][j] = 0;
                else adjMat[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    @Override
    public void addEdge(Edge<Double> e, boolean addReverse) {
        super.addEdge(e, addReverse);
        adjMat[e.from][e.to] = e.weight;
        if(addReverse){
            adjMat[e.to][e.from] = e.weight;
        }

    }


}
