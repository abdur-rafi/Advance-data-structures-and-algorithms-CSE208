package Graph;

public class Edge <T> {
    public int from, to;
    public T weight;

    public T flow;
    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Edge(int from, int to, T weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Edge(int from, int to, T weight, T flow) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.flow = flow;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                ", weight=" + weight +
                ", flow=" + flow +
                '}';
    }

}
