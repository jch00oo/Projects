public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given BTree (2-3-4) TREE. */
    public RedBlackTree(BTree<T> tree) {
        Node<T> btreeRoot = tree.root;
        root = buildRedBlackTree(btreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3-4 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }

        if (r.getItemCount() == 1) {
            //TODO: Replace with code to create a 2 node equivalent
            RBTreeNode<T> node = new RBTreeNode<T>((RBTreeNode<T>) r.getItemAt(0));
            node.left = buildRedBlackTree(r.getChildAt(0));
            node.right = buildRedBlackTree(r.getChildAt(1));
            return node;
        } else if (r.getItemCount() == 2) {
            // TODO: Replace with code to create a 3 node equivalent
            RBTreeNode<T> node = new RBTreeNode<T>((RBTreeNode<T>) r.getItemAt(0));
            node.left = buildRedBlackTree(r.getChildAt(0));
            node.right = buildRedBlackTree(r.getChildAt(1));
            node.right.left = buildRedBlackTree(r.getChildAt(0));
            node.right.right=buildRedBlackTree(r.getChildAt(1));
            return node;
        } else {
            // TODO: Replace with code to create a 4 node equivalent
            //r.getItemCount>=3;
            RBTreeNode<T>node= new RBTreeNode((RBTreeNode) r.getItemAt(1));
            node.left=buildRedBlackTree(r.getChildAt(0));
            node.right=buildRedBlackTree(r.getChildAt(2));
            node.left.left=buildRedBlackTree(r.getChildAt(0));
            node.left.right=buildRedBlackTree(r.getChildAt(1));
            node.right.left=buildRedBlackTree(r.getChildAt(2));
            node.right.right=buildRedBlackTree(r.getChildAt(3));
            return node;
        }
    }

    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    /**https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html**/
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> NODE =node.left;
        node.left=NODE.right;
        NODE.right=node;
        return NODE;
    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> NODE = node.right;
        node.right=NODE.left;
        NODE.left=NODE;
        return NODE;
    }

    public void insert(T item) {   
        root = insert(root, item);  
        root.isBlack = true;    
    }

    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // TODO: YOUR CODE HERE
        node=new RBTreeNode<T>((RBTreeNode<T>) item);
       //non-special case
        if (item.compareTo(node.item)<0){
            node.left=insert(node.left,item);
        }else if(item.compareTo(node.item)>0){
            node.right=insert(node.right,item);
        }else{
            node=node;
        }

        //Special cases
        if(isRed(node.left)&&isRed(node.left.left)){ //Case 2B
            node=rotateRight(node);
        }
        else if(isRed(node.right)&&!isRed(node.left)){ //Case 1
            node=rotateLeft(node);
        }
        else{
            //(isRed(node.left)&&isRed(node.right))
            flipColors(node);
        }
        return node;
    }

    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode() {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(RBTreeNode<T> left) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

}
