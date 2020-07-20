public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. */
    public BinarySearchTree() {
        // TODO: YOUR CODE HERE
        super();
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode root) {
        // TODO: YOUR CODE HERE
        super(root);
    }

    /* Returns true if the BST contains the given KEY. */
    //https://stackoverflow.com/questions/32752598/bst-contains-method-not-working-properly-in-java
    public boolean contains(T key) {
        // TODO: YOUR CODE HERE
        return containsHelp(key, root);
    }

    public boolean containsHelp(T key, TreeNode root) {
        if (key.compareTo(root.item) < 0) {
            if (root.left != null) {
                return containsHelp(key, root.left);
            } else {
                return false;
            }
        } else if (key.compareTo(root.item) > 0) {
            //go to right branch
            if (root.right != null) {
                return containsHelp(key, root.right);
            } else {
                return false;
            }
        } else {
            return true;
        }
        /* Adds a node for KEY iff KEY isn't in the BST already. */
        public void add(T key) {
            if (!(this.contains(key))) {
                root = addHelper(root, key);
            }
        }
        public TreeNode addHelper(TreeNode t,T key) {
            if (t == null) {
                return new TreeNode(key);
            } else if (key.compareTo(t.item) < 0) {
                t.left = addHelper(t.left, key);
                return t;
            } else {
                t.right = addHelper(t.right, key);
                return t;
            }
        }
        /* Adds a node for KEY iff KEY isn't in the BST already. */
        /**public void add(T key) {
         if (key.compareTo(root.item) < 0) {
         if(root.left!=null){
         add(key, root.left);
         }
         else{
         root.left= new TreeNode(key);
         }
         } else if(key.compareTo(root.item) > 0) {
         //go to right branch
         if (root.right!=null){
         add (key,root.right);
         }
         else{
         root.right= new TreeNode(key);
         }
         } else {
         System.out.println("Key is already in tree");
         }
         } **/

        /* Deletes a node from the BST.
         * Even though you do not have to implement delete, you
         * should read through and understand the basic steps.
         */
        public T delete (T key){
            TreeNode parent = null;
            TreeNode curr = root;
            TreeNode delNode = null;
            TreeNode replacement = null;
            boolean rightSide = false;

            while (curr != null && !curr.item.equals(key)) {
                if (((Comparable<T>) curr.item).compareTo(key) > 0) {
                    parent = curr;
                    curr = curr.left;
                    rightSide = false;
                } else {
                    parent = curr;
                    curr = curr.right;
                    rightSide = true;
                }
            }
            delNode = curr;
            if (curr == null) {
                return null;
            }

            if (delNode.right == null) {
                if (root == delNode) {
                    root = root.left;
                } else {
                    if (rightSide) {
                        parent.right = delNode.left;
                    } else {
                        parent.left = delNode.left;
                    }
                }
            } else {
                curr = delNode.right;
                replacement = curr.left;
                if (replacement == null) {
                    replacement = curr;
                } else {
                    while (replacement.left != null) {
                        curr = replacement;
                        replacement = replacement.left;
                    }
                    curr.left = replacement.right;
                    replacement.right = delNode.right;
                }
                replacement.left = delNode.left;
                if (root == delNode) {
                    root = replacement;
                } else {
                    if (rightSide) {
                        parent.right = replacement;
                    } else {
                        parent.left = replacement;
                    }
                }
            }
            return delNode.item;
        }
    }
}
