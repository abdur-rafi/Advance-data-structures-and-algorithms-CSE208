package Graph;

import java.util.ArrayList;
import java.util.Comparator;

public class GraphMST extends Graph<Double> {

    public GraphMST(int n){
        super(n);
    }

    public GraphMST(){
        super();
    }


    public ArrayList<Edge<Double>> MSTkrushkal(){
        ArrayList<Edge<Double>> edges = new ArrayList<>();
        for(var u : adjList){
            edges.addAll(u);
        }
        edges.sort(Comparator.comparing(e -> e.weight));
        DisJointSet set = new DisJointSet(adjList.size());
        ArrayList<Edge<Double>> mstEdges = new ArrayList<>();
        for (Edge<Double> edge : edges) {
            if (!set.isInSameSet(edge.from, edge.to)) {
                mstEdges.add(edge);
                set.union(edge.from, edge.to);
            }
            if (mstEdges.size() == adjList.size() - 1) break;
        }

        return mstEdges;
    }

    public ArrayList<Edge<Double>> MSTPrim(){

        class nodeDistPair implements Comparable<nodeDistPair> {
            public final int node;
            public final Double dist;
            nodeDistPair(int n, Double d){
                node = n;
                dist = d;
            }

            @Override
            public int compareTo(nodeDistPair o) {

                if(dist.equals(o.dist)){
                    return -1 * Integer.compare(node, o.node);
                }
                return -1 * dist.compareTo(o.dist);
            }

            @Override
            public String toString() {
                return "node : " + node + " w : " + dist;
            }



        }
        double s = 0;
        for(var u : adjList){
            for(var v : u)
                s += v.weight;
        }

        int n = adjList.size();
        var pq = new PriorityQueueCustom<nodeDistPair>();

        ArrayList<Edge<Double>> lastEdges = new ArrayList<>();
        ArrayList<Double> dist = new ArrayList<>();

        dist.add(0.0);
        lastEdges.add(null);
        pq.add(new nodeDistPair(0, 0.0));

        for(int i  = 1; i < n; ++i){
            pq.add(new nodeDistPair(i, s));
            dist.add(s);
            lastEdges.add(null);
        }

        ArrayList<Edge<Double>> mstEdges = new ArrayList<>();

        for(int i = 0; i < n; ++i) lastEdges.add(null);


        while(!pq.isEmpty()){

            var top = pq.pop();

            if(lastEdges.get(top.node) != null){
                mstEdges.add(lastEdges.get(top.node));
            }

            for(var v : adjList.get(top.node)){
                var currDist = dist.get(v.to);
                var vPair = new nodeDistPair(v.to, currDist);
                if(pq.has(vPair) && v.weight < currDist){
                    dist.set(v.to, v.weight);
                    pq.remove(vPair);
                    pq.add(new nodeDistPair(v.to, v.weight));
                    lastEdges.set(v.to, v);
                }
            }
        }

        return mstEdges;
    }
}
