package Graph.MaxFlowMinCut;


import Graph.Edge;
import Graph.Graph;

import java.util.ArrayList;

public class MaxFlowMinCut {

    public Graph<Double> flowNetwork;
    public Graph<Double> residualNetwork;

    public MaxFlowMinCut(Graph<Double> g){
        flowNetwork = new Graph<Double>(g.adjList.size());
        residualNetwork = new Graph<Double>(g.adjList.size());
        for(Edge<Double> e : g.getEdges()){
            Edge<Double> e1 = new Edge<Double>(e.from, e.to, e.weight);
            e1.flow = 0.0;
//            System.out.println("w: " + e.weight);
            flowNetwork.addEdge(e1, false);
            e1 = new Edge<Double>(e.from, e.to, e.weight);
            residualNetwork.addEdge(e1, false);
            e1 = new Edge<Double>(e.to, e.from, 0.0);
            residualNetwork.addEdge(e1, false);
        }
    }

    public void updateNetworks(ArrayList<Edge<Double>> residualGraphPath, int s, int t){

        ArrayList<Integer> pathEdges = new ArrayList<Integer>();
        for(int i = 0; i < residualGraphPath.size(); ++i) pathEdges.add(-1);
        int curr = t;
        double minWeight = Double.POSITIVE_INFINITY;
        while(curr != s){
            Edge<Double> e = residualGraphPath.get(curr);
            pathEdges.set(e.from, e.to);
            curr = e.from;
            minWeight = Math.min(minWeight, e.weight);
        }
        for(Edge<Double> e : flowNetwork.getEdges()){
            if(e.to == pathEdges.get(e.from)){
                e.flow += minWeight;
                if(e.flow < 0){
                    System.out.println("problemf " + minWeight);
                }
            }
            else if(pathEdges.get(e.to) == e.from){
                e.flow -= minWeight;
            }
        }
        for(Edge<Double> e : residualNetwork.getEdges()){
            if(e.to == pathEdges.get(e.from)){
                e.weight -= minWeight;
            }
            else if(pathEdges.get(e.to) == e.from){
                e.weight += minWeight;
            }
        }

    }

    public ArrayList<ArrayList<Integer>> MinCut(int t){
        ArrayList<Integer> sourceSet = new ArrayList<>();
        ArrayList<Integer> sinkSet = new ArrayList<>();
        ArrayList<Edge<Double>> pathEdges = residualNetwork.bfsReturnEdgeIgnoreZero(t, 0.0);
//        System.out.println(residualNetwork.adjList.get(t));
        for(int i = 0; i < residualNetwork.nodeCount(); ++i){
            if( i != t && pathEdges.get(i) == null){
                sinkSet.add(i);
            }
            else
                sourceSet.add(i);
        }
        ArrayList<ArrayList<Integer>> sets = new ArrayList<>();
        sets.add(sourceSet);
        sets.add(sinkSet);
        return sets;
    }

    public double MaxFlow(int s, int t){
        while(true){
            ArrayList<Edge<Double>> residualGraphPath = residualNetwork.bfsReturnEdgeIgnoreZero(s, 0.);
            if(residualGraphPath.get(t) == null) break;
            updateNetworks(residualGraphPath, s, t);
        }
        double flow = 0;
        boolean doesSaturate = true;
        for(Edge<Double> e : flowNetwork.adjList.get(s)){
            flow += e.flow;
//            System.out.println(e.flow);
            doesSaturate &= e.flow.equals(e.weight);
        }
        return flow;
    }
    public double edgeCapacityFromNode(int node){
        double capacity = 0;
        for(Edge<Double> e : flowNetwork.adjList.get(node)){
            capacity += e.weight;
        }
        return capacity;
    }
}
