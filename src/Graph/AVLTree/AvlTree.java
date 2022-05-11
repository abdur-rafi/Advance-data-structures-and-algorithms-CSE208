package Graph.AVLTree;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class AvlTree<T extends Comparable<T>> {

    private final int LEFT = 0;
    private final int RIGHT = 1;

    private TreeNode<T> root;
    private int size;

    public AvlTree(){
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

    public TreeNode<T> isZigZag(TreeNode<T> node){
        int greaterSide = LEFT;
        TreeNode<T> greaterChild = node.left;
        if(node.left.height < node.right.height){
            greaterSide = RIGHT;
            greaterChild = node.right;
        }
        if(greaterChild.left.height == greaterChild.right.height) return null;
        else{
            int childGreaterSide = LEFT;
            TreeNode<T> childGreaterNode = greaterChild.left;
            if(greaterChild.right.height > greaterChild.left.height){
                childGreaterSide = RIGHT;
                childGreaterNode = greaterChild.right;
            }
            if(childGreaterSide == greaterSide) return null;
            return childGreaterNode;
        }
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
        if(parent == root){
            root = treeNode;
            treeNode.parent = null;
        }
        parent.height = 1 + max(parent.left.height, parent.right.height);
        treeNode.height = 1 + max(treeNode.left.height, treeNode.right.height);
        if(gp != null)
            gp.height = 1 + max(gp.left.height, gp.right.height);


    }

    private TreeNode<T> findNode(T val){
        TreeNode<T> temp = root;
        while(!temp.leaf){

            int comp = temp.value.compareTo(val);
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
        return temp;
    }

    public void fixHeight(TreeNode<T> node){
//        if(node.parent == null) return;
//
//        if(node.height + 1 > node.parent.height){
//            node.parent.height = node.height + 1;
//            fixHeight(node.parent);
//        }
        if(node == null) return;
        int l = node.left.height;
        int r = node.right.height;
        int mx = max(l, r);
        if(mx + 1 != node.height){
            node.height = mx + 1;
            fixHeight(node.parent);
        }
    }


    public boolean balance(TreeNode<T> node, boolean afterDelete){
        if(node == null) return false;
        if(abs(node.left.height - node.right.height) > 1){
            TreeNode<T> z = isZigZag(node);
            TreeNode<T> greaterNode = node.left;
            if(node.left.height < node.right.height) greaterNode = node.right;
            if(z == null){
                rotateWithParent(greaterNode);
            }
            else{
                rotateWithParent(z);
                rotateWithParent(z);
            }
            if(afterDelete){
                balance(node.parent, true);
            }
            return true;
        }
        else{
            return balance(node.parent, afterDelete);
        }
    }

    public boolean Insert(T val){
        if(size == 0){
            root = new TreeNode<T>(val);
            ++size;
            printTree(root);
            return true;
        }
        var node = findNode((val));

        if(!node.leaf && node.value.compareTo(val) == 0){
            return false;
        }
        var newNode = new TreeNode<T>(val);
        assignAsChild(node.parent, newNode, whichSideToParent(node));
        ++size;
        fixHeight(newNode.parent);
        boolean violated = balance(newNode.parent, false);
        if(violated) System.out.print("Height invariant violated.\nAfter rebalancing: ");
        System.out.println(printTree(root));
        return true;
    }

    public String printTree(TreeNode<T> node){
        if(node.leaf) return "";
        if(node.left.leaf && node.right.leaf) return node.value.toString();
        return node.value + "(" + printTree(node.left) + ")" + "(" + printTree(node.right) + ")";
    }

    private TreeNode<T> getMinNodeToRight(TreeNode<T> treeNode){
        var temp = treeNode.right;
        while(!temp.left.leaf){
            temp = temp.left;
        }
        return temp;
    }


    public void Delete(T val){
        var node = findNode(val);
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
            x = minNode.right;
            var zL = node.left;
            var zR = node.right;
            var mnL = minNode.left;
            var mnR = minNode.right;
            var mnParent = minNode.parent;
            if(node != root)
                assignAsChild(node.parent, minNode, whichSideToParent(node));
            else{
                root = minNode;
                root.parent = null;
            }

            assignAsChild(minNode, zL,LEFT);
            if(minNode != zR){
                assignAsChild(minNode, zR, RIGHT);
                assignAsChild(mnParent, mnR, LEFT);
            }
        }
        if(x.parent != null){
            fixHeight(x.parent);
            boolean violated = balance(x.parent, true);
            if(violated) System.out.print("Height invariant violated.\nAfter rebalancing: ");
            System.out.println(printTree(root));

        }
    }

    public boolean Find(T key){
        if(root == null){
            return false;
        }
        var n = findNode(key);
        return !n.leaf;
    }

    public TreeNode<T> getRoot(){
        return root;
    }

}
