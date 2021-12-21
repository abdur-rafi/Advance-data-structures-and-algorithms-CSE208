package Graph;

import java.util.TreeSet;

public class PriorityQueueCustom<T extends Comparable<T>> {

    public final TreeSet<T> tr;
    public PriorityQueueCustom(){
        tr = new TreeSet<T>();
    }

    public void add(T e){
        tr.add(e);
    }
    public T top(){
        return tr.last();
    }

    public int size(){
        return tr.size();
    }

    public void remove(T e){
        tr.remove(e);
    }


    public T pop(){
        T lst = tr.last();
        tr.remove(lst);
        return lst;
    }

    public boolean isEmpty(){
        return tr.size() == 0;
    }

    public boolean has(T e){
        return tr.contains(e);
    }



}
