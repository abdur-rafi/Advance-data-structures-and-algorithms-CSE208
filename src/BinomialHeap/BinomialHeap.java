package BinomialHeap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Integer.max;

public class BinomialHeap<T extends Comparable<T>> {


    private LinkedList<BinomialTree<T>> trees;
    private Map<T, BinomialTree<T>> mp;



    public BinomialHeap(LinkedList<BinomialTree<T>> trees) {
        this.trees = trees;
        mp = new TreeMap<>();
    }

    public BinomialHeap() {
        this.trees = new LinkedList<>();
        mp = new TreeMap<>();

    }

    public BinomialHeap(BinomialTree<T> item){
        this.trees = new LinkedList<>();
        trees.addFirst(item);
        mp = new TreeMap<>();

    }

    public void union(BinomialHeap<T> heap2){
        int mxK = 0;
        for(var t : trees){
            mxK = max(mxK, t.getK());
        }
        for(var t : heap2.trees){
            mxK = max(mxK, t.getK());
        }

        ++mxK;

        ArrayList<BinomialTree<T>> A = new ArrayList<>();
        ArrayList<BinomialTree<T>> B = new ArrayList<>();
        for(int i = 0; i <= mxK; ++i){
            A.add(null);
            B.add(null);
        }

        for(var t : trees){
            A.set(t.getK(), t);
        }
        for(var t : heap2.trees){
            B.set(t.getK(), t);
        }
        BinomialTree<T> carry = null;

        LinkedList<BinomialTree<T>> newTrees = new LinkedList<>();

        for(int i = 0; i <= mxK; ++i){
            ArrayList<BinomialTree<T>> nonNullTrees = new ArrayList<>();
            int a = 0;
            int b =  0;
            int c = 0;

            if(A.get(i) != null){
                a = 1;
                nonNullTrees.add(A.get(i));
            }
            if(B.get(i) != null){
                b = 1;
                nonNullTrees.add(B.get(i));
            }
            if(carry != null){
                c = 1;
                nonNullTrees.add(carry);
            }
            int s = (a ^ b ^ c);

            if(s != 0){
                newTrees.addFirst(nonNullTrees.get(0));
            }
            int nextCarry = (a & b) | ( b & c) | (c & a);
//            System.out.println("c: " + c);
            if(nextCarry != 0){
                carry = nonNullTrees.get(s);
                for(int j = s + 1; j < nonNullTrees.size(); ++j) {
                    try{
                        carry = carry.mergeTree(nonNullTrees.get(j));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            else{
                carry = null;
            }

        }
        this.trees = newTrees;

    }

    public BinomialTree<T> insert(T item){
        BinomialTreeNode<T> node = new BinomialTreeNode<>(item);
        BinomialTree<T> nTree = new BinomialTree<T>(node, 0);
        BinomialHeap<T> nHeap = new BinomialHeap<T>(nTree);
        mp.put(item, nTree);
        union(nHeap);
        return nTree;
    }

    public T findMax(){
        T min = null;
        for(var tr : trees){
            if(min == null){
                min = tr.getHead().getItem();
            }
            else if(min.compareTo(tr.getHead().getItem()) < 0){
                min = tr.getHead().getItem();
            }
        }
        return min;
    }

    public T extractMax(){
        T mx = findMax();

        for(var tree : trees){
            if(tree.getHead().getItem().compareTo(mx) == 0){
                BinomialHeap<T> bh = new BinomialHeap<T>(tree.getHead().getChilds());
                trees.remove(tree);
                mp.remove(tree.getHead().getItem());
                union(bh);
                break;
            }
        }
        return mx;
    }

    public void print(){
        System.out.println("Printing Binomial Heap...");
        System.out.println("------------------------------------------------");
        var it = trees.descendingIterator();
        while(it.hasNext()){
            var tree  = it.next();
            System.out.println("Binomial Tree, B" + tree.getK() );
            tree.printTree();

        }
        System.out.println("------------------------------------------------");
    }

    public boolean increaseKey(T oldKey, T newKey) throws Exception {
        var tree = mp.get(oldKey);
        if(tree == null){
            System.out.println("No key found");
            return false;
        }
        if(tree.getHead().getItem().compareTo(newKey) > 0){
            throw new Exception("New key is smaller than previous");

        }
        tree.getHead().setItem(newKey);
        mp.put(newKey, tree);

        var curr = tree;
        while (curr.getParent() != null && curr.getParent().getHead().getItem().compareTo(curr.getHead().getItem()) < 0){
            var temp = curr.getHead().getItem();
            curr.getHead().setItem(curr.getParent().getHead().getItem());
            mp.put(curr.getHead().getItem(), curr);
            curr.getParent().getHead().setItem(temp);
            mp.put(curr.getParent().getHead().getItem(), curr.getParent());
            curr = curr.getParent();
        }

        return true;

    }




}
