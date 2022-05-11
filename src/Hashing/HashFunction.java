package Hashing;

public interface HashFunction<K, V> {
    public long function(K key);
    public long auxFunction(K key);
}
