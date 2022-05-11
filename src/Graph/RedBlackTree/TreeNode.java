package Graph.RedBlackTree;

import Graph.Graph;

public class TreeNode<T> implements Graph.TreePrinter.PrintableNode {

    public static final boolean BLACK = false;
    public static final boolean RED = true;
    public T value;
    boolean color;
    boolean leaf;
    public TreeNode<T> parent;
    public TreeNode<T> left;

    public TreeNode(TreeNode<T> parent) {

        this.color = BLACK;
        this.parent = parent;
        this.leaf = true;

    }



    public TreeNode(T value, boolean color, TreeNode<T> parent) {
        this.value = value;
        this.color = color;
        this.leaf = false;
        this.parent = parent;
        this.left = new TreeNode<T>(this);
        this.right = new TreeNode<T>(this);
    }

    public TreeNode<T> right;


    public TreeNode(T value, boolean color) {
        this.value = value;
        this.color = color;
        this.leaf = false;
        this.left = new TreeNode<T>(this);
        this.right = new TreeNode<T>(this);
        this.parent = null;
    }

    public TreeNode(T value, boolean color, TreeNode<T> parent, TreeNode<T> left, TreeNode<T> right) {
        this.value = value;
        this.color = color;
        this.leaf = false;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", color=" + color +
                ", leaf=" + leaf +
                '}';
    }

    @Override
    public Graph.TreePrinter.PrintableNode getLeft() {
        return left;
    }

    @Override
    public Graph.TreePrinter.PrintableNode getRight() {
        return right;
    }

    @Override
    public String getText() {
        if(value == null) return "nB";
        if (color == RED) return  value.toString()+"R";
        return value.toString() + "B";
    }

}
