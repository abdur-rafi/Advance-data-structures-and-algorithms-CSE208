package Hashing;

public interface HashTable<K, V> {
    public ReturnPair<Boolean, Integer> insert(K key);
    public ReturnPair<KeyValuePair<K, V>, Integer> search(K key);
    public boolean delete(K key);
    public int getCapacity();
    public int size();
}
