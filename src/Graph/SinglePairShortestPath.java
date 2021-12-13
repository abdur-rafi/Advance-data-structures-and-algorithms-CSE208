package Graph;

import java.util.ArrayList;

public class SinglePairShortestPath extends Graph<Double> {



    public boolean relaxEdge(ArrayList<EdgeDistancePair>distance, Edge<Double> edge){
        if(distance.get(edge.to).distance > distance.get(edge.from).distance + edge.weight){
            EdgeDistancePair pair = distance.get(edge.to);
            pair.distance = distance.get(edge.from).distance + edge.weight;
            pair.pathEdge = edge;
            return true;
        }
        return false;
    }


    public ArrayList<EdgeDistancePair> BellManFord(int source){

        ArrayList<EdgeDistancePair> edgeDistancePairs = new ArrayList<>();
        double inf = 0;
        for(Edge<Double> e : edges){
            inf += e.weight > 0 ? e.weight : 0;
        }
        int n = adjList.size();
        for(int i = 0; i < n; ++i) {
            edgeDistancePairs.add(new EdgeDistancePair(null, inf));
        }
        edgeDistancePairs.get(source).distance = 0;
        for(int i = 1; i < n; ++i){
            for(Edge<Double> e : edges){
                relaxEdge(edgeDistancePairs, e);
            }
        }
        for(int i = 0; i < n; ++i){
            EdgeDistancePair p = edgeDistancePairs.get(i);
            if(p.pathEdge != null){
                Edge<Double> e = p.pathEdge;
                int u = e.from;
                int v = e.to;
                if(edgeDistancePairs.get(v).distance > edgeDistancePairs.get(u).distance +  e.weight)
                    return null;
            }
        }
        return edgeDistancePairs;

    }



    public ArrayList<EdgeDistancePair> Dijkstra(int source){

        class nodeDistPair implements Comparable<nodeDistPair> {
            public final int node;
            public final Double dist;
            nodeDistPair(int n, double d){
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
                s += v.weight < 0 ? 0 : v.weight;
        }

        int n = adjList.size();
        var pq = new PriorityQueueCustom<nodeDistPair>();


        ArrayList<EdgeDistancePair> distParentList = new ArrayList<>();

        for(int i  = 0; i < n; ++i){
            if(i == source){
                pq.add(new nodeDistPair(i, 0.0));
                distParentList.add(new EdgeDistancePair(null, 0));

            }
            else {
                pq.add(new nodeDistPair(i, s));
                distParentList.add(new EdgeDistancePair(null, s));
            }
        }



        while(!pq.isEmpty()){

            var top = pq.pop();

            for(var v : adjList.get(top.node)){
                var currDist = distParentList.get(v.to).distance;
                var vPair = new nodeDistPair(v.to, currDist);
                if(relaxEdge(distParentList, v)){
                    pq.remove(vPair);
                    nodeDistPair nPair = new nodeDistPair(v.to, distParentList.get(v.to).distance);
                    pq.add(nPair);
                }
            }
        }

        return distParentList;
    }
}
