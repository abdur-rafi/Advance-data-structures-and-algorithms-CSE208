package BinomialHeap;

import java.util.LinkedList;
import java.util.Queue;

public class BinomialTree<T extends Comparable<T>> {
    private final BinomialTreeNode<T> head;
    private int k;
    private BinomialTree<T> parent;

    public BinomialTree<T> getParent() {
        return parent;
    }

    public BinomialTree(BinomialTreeNode<T> head, int k) {
        this.head = head;
        this.k = k;
        parent = null;
    }


    public BinomialTree(BinomialTreeNode<T> head, int k, BinomialTree<T> parent) {
        this.head = head;
        this.k = k;
        this.parent = parent;
    }

    public void setParent(BinomialTree<T> parent) {
        this.parent = parent;
    }

    public BinomialTreeNode<T> getHead() {
        return head;
    }

    public int getK() {
        return k;
    }

    public BinomialTree<T> mergeTree(BinomialTree<T> t1) throws Exception {
        if(this.k != t1.k){
            throw new Exception("Can not be merged");
        }
        if(head.getItem().compareTo(t1.getHead().getItem()) > 0){
            head.getChilds().addFirst(t1);
            k++;
            t1.setParent(this);
            return this;
        }
        else{
            t1.head.getChilds().addFirst(this);
            t1.k++;
            this.setParent(t1);
            return t1;
        }
    }

    public void printTree(){
        Queue<BinomialTree<T>> queue = new LinkedList<>();
        queue.add(null);
        queue.add(this);
        int level = -1;
        while(!queue.isEmpty()){
            var front = queue.remove();
            if(front == null){
                ++level;
                if(queue.size() != 0){
                    if(level != 0)
                        System.out.println();
                    queue.add(null);
                    System.out.print("Level " + level + " : ");
                }
                else{
                    System.out.println();
                }
            }
            else{
                System.out.print(front.getHead().getItem() + " ");
                queue.addAll(front.getHead().getChilds());
            }
        }
    }
}
