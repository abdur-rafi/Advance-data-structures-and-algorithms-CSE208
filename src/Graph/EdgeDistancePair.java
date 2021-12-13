package Graph;

public class EdgeDistancePair{
    public Edge<Double> pathEdge;
    public double distance;

    public EdgeDistancePair(Edge<Double> pathEdge, double distance) {
        this.pathEdge = pathEdge;
        this.distance = distance;
    }
}