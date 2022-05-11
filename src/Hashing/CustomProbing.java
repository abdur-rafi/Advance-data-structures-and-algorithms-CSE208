package Hashing;

import java.util.ArrayList;

public class CustomProbing implements HashTable<String, Integer> {

    private int N;
    private int size;
    private final long C1;
    private final long C2;
    private static class TableItem{
        public KeyValuePair<String , Integer> item;
        public boolean deleted;

        public TableItem(KeyValuePair<String, Integer> item, boolean deleted) {
            this.item = item;
            this.deleted = deleted;
        }
    }

    private final ArrayList<TableItem> hashTable;
    private final HashFunction<String , Integer> hashFunction;

    public CustomProbing(int N, HashFunction<String, Integer> hashFunction, long C1, long C2) {
        this.N = N;
        this.hashTable = new ArrayList<>();
        for(int i = 0; i < N; ++i) hashTable.add(null);
        this.hashFunction = hashFunction;
        this.C1 = C1;
        this.C2 = C2;
    }

//    private int hashValue(String key, int i){
//        return (int)((hashFunction.function(key) + i * hashFunction.auxFunction(key)) % N);
//    }

    @Override
    public ReturnPair<Boolean, Integer> insert(String key) {
        long mainHashValue = hashFunction.function(key);
        long auxHashValue = hashFunction.auxFunction(key);
        int probeCount = 0;

        TableItem deletedItem = null;
        for(int i = 0; i < N; ++i){
            ++probeCount;
            int currHashValue = (int) ((mainHashValue + C1 * i * auxHashValue + C2 * i * i) % N);
            TableItem currItem = hashTable.get(currHashValue);
            if(currItem == null){
                ++size;
                if(deletedItem != null){
                    deletedItem.item = new KeyValuePair<>(key, size);
                    deletedItem.deleted = false;
                }
                else{
                    hashTable.set(currHashValue, new TableItem(new KeyValuePair<String, Integer>(key, size), false));
                }
                return new ReturnPair<>(true, probeCount);
            }
            else if(currItem.deleted){
                deletedItem = currItem;
            }
            else if(currItem.item.key.equals(key)){
                System.out.println("item exists");
                return new ReturnPair<>(false, probeCount);
            }
        }
        if(deletedItem != null){
            ++size;
            deletedItem.item = new KeyValuePair<>(key, size);
            deletedItem.deleted = false;
            return new ReturnPair<>(true, probeCount);
        }
        System.out.println("No Empty Space Found");
        return new ReturnPair<>(false, probeCount);

    }


    @Override
    public ReturnPair<KeyValuePair<String, Integer>, Integer> search(String key) {
        long mainHashValue = hashFunction.function(key);
        long auxHashValue = hashFunction.auxFunction(key);
        int probeCount = 0;
        for(int i = 0; i < N; ++i){
            ++probeCount;
            int currHashValue = (int) ((mainHashValue + C1 * i * auxHashValue + C2 * i * i) % N);
            TableItem currItem = hashTable.get(currHashValue);
            if(currItem == null) return new ReturnPair<>(null, probeCount);
            if(!currItem.deleted && currItem.item.key.equals(key)){
                return new ReturnPair<>(currItem.item, probeCount);
            }
        }
        return new ReturnPair<>(null, probeCount);
    }

    @Override
    public boolean delete(String key) {
        long mainHashValue = hashFunction.function(key);
        long auxHashValue = hashFunction.auxFunction(key);
        for(int i = 0; i < N; ++i){
            int currHashValue = (int) ((mainHashValue + C1 * i * auxHashValue + C2 * i * i) % N);
            TableItem currItem = hashTable.get(currHashValue);
            if(currItem == null) return false;
            if(!currItem.deleted && currItem.item.key.equals(key)){
                currItem.deleted = true;
                return true;
            }
        }
//
        return false;
    }

    @Override
    public int getCapacity() {
        return N;
    }

    @Override
    public int size() {
        return size;
    }
}
