package Graph.AVLTree;

public class TreeNode<T> {

    public T value;
    boolean leaf;
    public TreeNode<T> parent;
    public TreeNode<T> left;
    public TreeNode<T> right;
    public int height;

    public TreeNode(TreeNode<T> parent) {

        this.parent = parent;
        this.leaf = true;
        this.height = -1;

    }



    public TreeNode(T value, TreeNode<T> parent) {
        this.value = value;
        this.leaf = false;
        this.parent = parent;
        this.left = new TreeNode<T>(this);
        this.right = new TreeNode<T>(this);
        this.height = 0;

    }



    public TreeNode(T value) {
        this.value = value;
        this.leaf = false;
        this.left = new TreeNode<T>(this);
        this.right = new TreeNode<T>(this);
        this.parent = null;
        this.height = 0;
    }

    public TreeNode(T value, TreeNode<T> parent, TreeNode<T> left, TreeNode<T> right) {
        this.value = value;
        this.leaf = false;
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.height = 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", leaf=" + leaf +
                '}';
    }

}
