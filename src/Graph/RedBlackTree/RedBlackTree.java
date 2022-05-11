package Graph.RedBlackTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree<T extends Comparable<T>> {

    private final int LEFT = 0;
    private final int RIGHT = 1;

    private TreeNode<T> root;
    private int size;
//    private ArrayList<ArrayList<Node<T>>> adj;

    public RedBlackTree(){
        root = null;
        size = 0;
    }

    private int whichSideToParent(TreeNode<T> n){
        if(n.parent.left == n) return LEFT;
        return RIGHT;
    }

    private TreeNode<T> assignAsChild(TreeNode<T> parent, TreeNode<T> child, int side){
        TreeNode<T> temp;
        if(side == LEFT ){
            temp = parent.left;
            parent.left = child;
        }
        else{
            temp = parent.right;
            parent.right = child;
        }
        child.parent = parent;
        return temp;
    }

    private void rotateWithParent(TreeNode<T> treeNode){
        var parent = treeNode.parent;
        var gp = parent.parent;
        var childLeftBranch = treeNode.left;
        var childRightBranch = treeNode.right;
        int side = whichSideToParent(treeNode);
        if(parent != root)
            assignAsChild(gp, treeNode, whichSideToParent(parent));

        if(side == LEFT){
            assignAsChild(treeNode, parent, RIGHT);
            parent.left = childRightBranch;
            childRightBranch.parent = parent;
        }
        else{
            assignAsChild(treeNode, parent, LEFT);
            parent.right = childLeftBranch;
            childLeftBranch.parent = parent;

        }
        boolean temp = treeNode.color;
        treeNode.color = parent.color;
        parent.color = temp;
        if(parent == root){
            root = treeNode;
            treeNode.parent = null;
//            System.out.println("here");
        }


    }

    private TreeNode<T> getUncle(TreeNode<T> treeNode){
        var parent = treeNode.parent;
        var gp = parent.parent;
        if(whichSideToParent(parent) == LEFT){
            return gp.right;
        }
        return gp.left;
    }

    private TreeNode<T> findNode(T val){
        TreeNode<T> temp = root;
        while(!temp.leaf){
//            System.out.println("sdf");

            int comp = temp.value.compareTo(val);
//            System.out.println(comp);
            if(comp < 0){
                temp = temp.right;
            }
            else if(comp > 0){
                temp = temp.left;
            }
            else{
                break;
            }
        }
//        System.out.println(temp.parent);
        return temp;
    }

    private void fix(TreeNode<T> treeNode){

        var uncle = getUncle(treeNode);
        var gp = uncle.parent;
        if(uncle.color == TreeNode.RED){
            gp.color = TreeNode.RED;
            treeNode.parent.color = TreeNode.BLACK;
            uncle.color = TreeNode.BLACK;
            if(gp == root){
                gp.color = TreeNode.BLACK;
            }
            else if(gp.parent.color == TreeNode.RED )
                fix(gp);
        }
        else{
            int childSide = whichSideToParent(treeNode);
            int parentSide = whichSideToParent(treeNode.parent);
            if(childSide != parentSide){
                rotateWithParent(treeNode);
            }
            else treeNode = treeNode.parent;
            rotateWithParent(treeNode);
        }


    }

    public boolean addNode(T val){
        if(size == 0){
            root = new TreeNode<T>(val, TreeNode.BLACK);
            ++size;
            return true;
        }
        var node = findNode((val));


        if(!node.leaf && node.value.compareTo(val) == 0){
            return false;
        }
        var newNode = new TreeNode<T>(val, TreeNode.RED);
        assignAsChild(node.parent, newNode, whichSideToParent(node));

        if(newNode.parent.color == TreeNode.RED){
            fix(newNode);
        }
        ++size;
        return true;
    }

    public void bfs(){
        ArrayList<ArrayList<TreeNode<T>>> nodesAtHeight = new ArrayList<>();
        nodesAtHeight.add(new ArrayList<>());
        int height = 0;
        Queue<TreeNode<T>> queue = new LinkedList<>();
        var nil = new TreeNode<T>(null);
        queue.add(root);
        queue.add(nil);
        while (!queue.isEmpty()){
            var top = queue.remove();
            System.out.println(top);
            if(top.leaf){
                ++height;
                if(!queue.isEmpty()){
                    queue.add(nil);
                    nodesAtHeight.add(new ArrayList<>());
                }
            }
            else{

                nodesAtHeight.get(height).add(top);
                if(!top.left.leaf)
                    queue.add(top.left);

                if(!top.right.leaf)
                    queue.add(top.right);

            }
        }
        int mxHeight = nodesAtHeight.size();
        System.out.println(mxHeight);
        int leafCount = nodesAtHeight.get(mxHeight - 1).size();
        int gap = leafCount * 2;
        for(int i = 0; i < mxHeight; ++i){
            int currCount = nodesAtHeight.get(i).size();
            int eachGap = gap / currCount;
            String gapS = " ".repeat(eachGap);
            for(var x : nodesAtHeight.get(i)){
                System.out.print(gapS + x.value);
            }
            System.out.println();
        }
    }
    private TreeNode<T> getMinNodeToRight(TreeNode<T> treeNode){
        var temp = treeNode.right;
        while(!temp.left.leaf){
            temp = temp.left;
        }
        return temp;
    }

    private TreeNode<T> getSibling(TreeNode<T> n){

        var parent = n.parent;
        if(parent.left == n){
            return parent.right;
        }
        return parent.left;
    }

    private void fixDelete(TreeNode<T> x){
//        System.out.println("sdf");

        if(x.color == TreeNode.RED){
            x.color = TreeNode.BLACK;
        }
        else if(x == root){
        }
        else{
            var sibling = getSibling(x);
            if(sibling.color == TreeNode.RED){
                rotateWithParent(sibling);
            }
            sibling = getSibling(x);
            if(sibling.left.color == TreeNode.BLACK && sibling.right.color == TreeNode.BLACK){
                sibling.color = TreeNode.RED;
                fixDelete(sibling.parent);
            }
            else{
                var redChild = sibling.left;
                if(redChild.color != TreeNode.RED){
                    redChild = sibling.right;
                }
                int redChildSide = whichSideToParent(redChild);
                int siblingSide = whichSideToParent(sibling);
                if(redChildSide != siblingSide){
                    rotateWithParent(redChild);
                }
                boolean isParentRoot = x.parent == root;
                sibling = getSibling(x);
                rotateWithParent(sibling);
                root.color = TreeNode.BLACK;
//                if(isParentRoot){
//                    root = sibling;
//                }
                getUncle(x).color = TreeNode.BLACK;

            }
        }
    }

    public void deleteVal(T val){
        var node = findNode(val);
        boolean nodeOriginalColor = node.color;
        if(node.leaf || node.value.compareTo(val) != 0) return;
        --size;
        TreeNode<T> x;
        if(node.left.leaf){
            x = node.right;
            if(node == root){
                node.right.parent = null;
                root = node.right;
            }
            else
                assignAsChild(node.parent, node.right, whichSideToParent(node));
        }
        else if(node.right.leaf){
            x = node.left;
            if(node == root){
                node.left.parent = null;
                root = node.left;
            }
            else
                assignAsChild(node.parent, node.left, whichSideToParent(node));
        }
        else{
            var minNode = getMinNodeToRight(node);
            nodeOriginalColor = minNode.color;
            x = minNode.right;
            var zL = node.left;
            var zR = node.right;
            var mnL = minNode.left;
            var mnR = minNode.right;
            var mnParent = minNode.parent;
            if(node != root)
                assignAsChild(node.parent, minNode, whichSideToParent(node));
            else
                root = minNode;

            assignAsChild(minNode, zL,LEFT);
            if(minNode != zR){
                assignAsChild(minNode, zR, RIGHT);
                assignAsChild(mnParent, mnR, LEFT);
            }
            minNode.color = node.color;

        }
        if(nodeOriginalColor == TreeNode.BLACK){
            fixDelete(x);
        }
    }

    public TreeNode<T> getRoot(){
        return root;
    }

}
