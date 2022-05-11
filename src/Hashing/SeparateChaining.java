package Hashing;

import java.util.ArrayList;
import java.util.LinkedList;

public class SeparateChaining implements HashTable<String, Integer> {

    private final ArrayList<LinkedList<KeyValuePair<String, Integer>>> hashTable;
    private final HashFunction<String, Integer> hashFunction;
    private final int N;
    private int itemCount = 0;

    public SeparateChaining(int N, HashFunction<String, Integer> hashFunction){
        hashTable = new ArrayList<>();
        for(int i = 0; i < N; ++i) hashTable.add(null);
        this.hashFunction = hashFunction;
        this.N = N;
    }




    @Override
    public ReturnPair<Boolean, Integer> insert(String key) {
        int hashValue = (int)(hashFunction.function(key) % N);

        if(hashTable.get(hashValue) == null){
            hashTable.set(hashValue,new LinkedList<KeyValuePair<String, Integer>>());
        }
        var r = search(key);
        if(r.first != null){
            return new ReturnPair<>(false, r.second);
        }
        ++itemCount;
        hashTable.get(hashValue).addFirst(new KeyValuePair<>(key, itemCount));
        return new ReturnPair<>(true, r.second);
    }

    @Override
    public ReturnPair<KeyValuePair<String, Integer>, Integer> search(String key) {
        int hashValue = (int)(hashFunction.function(key) % N);
        int probeCount = 1;
        KeyValuePair<String, Integer> r = null;
        if(hashTable.get(hashValue) != null){
            for(KeyValuePair<String, Integer> pair : hashTable.get(hashValue)){
                ++probeCount;
                if(pair.key.equals(key)){
                    r = pair;
                    break;
                }
            }
        }

        return new ReturnPair<>(r, probeCount);
    }

    @Override
    public boolean delete(String key) {
        KeyValuePair<String, Integer> r = search(key).first;
        if(r == null) return false;
        int hashValue = (int)(hashFunction.function(key) % N);
        hashTable.get(hashValue).remove(r);
        --itemCount;
        return true;
    }

    @Override
    public int getCapacity() {
        return N;
    }

    @Override
    public int size() {
        return itemCount;
    }
}
