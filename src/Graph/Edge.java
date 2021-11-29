package Graph;

import java.util.Objects;

public class Edge <T> {
    public int from, to;
    public T weight;

    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Edge(int from, int to, T weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                ", weight=" + weight +
                '}';
    }

}
