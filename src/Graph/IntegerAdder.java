package Graph;

public class IntegerAdder implements Adder<Integer> {

    @Override
    public Integer zero() {
        return 0;
    }

    @Override
    public Integer add(Integer lhs, Integer rhs) {
        return lhs + rhs;
    }

    @Override
    public Integer sub(Integer lhs, Integer rhs) {
        return lhs - rhs;
    }

    @Override
    public int comp(Integer lhs, Integer rhs) {
        return sub(lhs, rhs);
    }
}
