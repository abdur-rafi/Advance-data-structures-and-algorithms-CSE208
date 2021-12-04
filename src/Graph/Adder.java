package Graph;

interface Adder<T> {
    T zero();
    T add(T lhs, T rhs);
    T sub(T lhs, T rhs);
    int comp(T lhs, T rhs);
}
