package BinomialHeap;

import java.util.LinkedList;

public class BinomialTreeNode<T extends Comparable<T>> {
    private T item;
    private LinkedList<BinomialTree<T>> childs;

    public BinomialTreeNode(T item) {
        this.item = item;
        childs = new LinkedList<>();
    }

    public T getItem(){
        return item;
    }
    public LinkedList<BinomialTree<T>> getChilds(){
        return childs;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
