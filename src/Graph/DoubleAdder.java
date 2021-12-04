package Graph;

public class DoubleAdder implements Adder<Double> {

    @Override
    public Double zero() {
        return 0.;
    }

    @Override
    public Double add(Double lhs, Double rhs) {
        return lhs + rhs;
    }

    @Override
    public Double sub(Double lhs, Double rhs) {
        return lhs - rhs;
    }

    @Override
    public int comp(Double lhs, Double rhs) {
        if(lhs < rhs) return -1;
        else if(lhs > rhs) return 1;
        return 0;
    }
}
