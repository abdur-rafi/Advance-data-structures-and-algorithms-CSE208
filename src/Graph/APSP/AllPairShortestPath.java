package Graph.APSP;

import Graph.Edge;
import Graph.EdgeDistancePair;
import Graph.GraphAdjMat;
import Graph.SPSP.SinglePairShortestPath;

import java.util.ArrayList;

public class AllPairShortestPath extends GraphAdjMat {


    public AllPairShortestPath(int n) {
        super(n);
    }

    public double[][] FloydWarshall(){

        int n = adjMat.length;
        double[][] distancePr = new double[n][n];
        for(int i = 0; i < n; ++i){
            System.arraycopy(adjMat[i], 0, distancePr[i], 0, n);
        }
        for(int k = 0; k < n; ++k){
            double[][] distanceK = new double[n][n];
            for(int i = 0; i < n; ++i){
                for(int j = 0; j < n; ++j){
                    distanceK[i][j] = Math.min(distancePr[i][j],distancePr[i][k] + distancePr[k][j]);
                }
            }
            distancePr = distanceK;
        }
        return distancePr;
    }



    public double[][] Johnson(){
        int n = adjMat.length;
        SinglePairShortestPath spsp = new SinglePairShortestPath(n);
        for(Edge<Double> edge : edges){
            spsp.addEdge(edge, false);
        }
        spsp.addNode();
        for(int i = 0; i < n; ++i){
            spsp.addEdge(new Edge<>(n, i, 0.0), false);
        }
        ArrayList<EdgeDistancePair> minDist = spsp.BellManFord(n);
        if(minDist == null){
            return null;
        }
        ArrayList<Double> h = new ArrayList<>();
        for(int i = 0; i <= n; ++i) h.add(0.0);
        for(int i = 0;i < n; ++i) h.set(i, minDist.get(i).distance);
        for(Edge<Double> e : edges){
            e.weight += h.get(e.from) - h.get(e.to);
        }
        double[][] allPairDist = new double[n][n];
        for(int i = 0; i < n; ++i){
            ArrayList<EdgeDistancePair> minDistFromI = Dijkstra(i);
            for(int j = 0; j < n; ++j){
                allPairDist[i][j] = minDistFromI.get(j).distance + h.get(j) - h.get(i);
            }
//            for(EdgeDistancePair e : minDistFromI){
//                allPairDist[i][e.pathEdge.to] = e.distance + h.get(e.pathEdge.to) - h.get(i);
//            }
        }
        return allPairDist;
//        System.out.println(Arrays.deepToString(allPairDist));
    }
}
