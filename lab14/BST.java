import java.util.LinkedList;
import java.util.Iterator;

public class BST<T> {

    BSTNode<T> root;

    public BST(LinkedList<T> list) {
        root = sortedIterToTree(list.iterator(), list.size());
    }

    /* Returns the root node of a BST (Binary Search Tree) built from the given
       iterator ITER of N items. ITER will output the items in sorted order,
       and ITER will contain objects that will be the item of each BSTNode. */
    private BSTNode<T> sortedIterToTree(Iterator<T> iter, int N) {

        T[] list = (T[]) new Object[N];
        for (int i = 0; i < N; i++) {
            list[i] = (iter.next());
        }
        return sortedIterHelper(list, N);

    }

    private BSTNode<T> sortedIterHelper(T[] list, int N) {
        BSTNode<T> t = new BSTNode<>(null);
        if (N == 0) {
            return null;
        } else if (N == 1) {
            t.item = list[0];
        } else if (N == 2) {
            t.item = list[1];
            t.left = new BSTNode<T>(list[0]);
        } else if (N == 3) {
            t.item = list[1];
            t.left = new BSTNode<T>(list[0]);
            t.right = new BSTNode<T>(list[2]);
        } else {
            int mid = N / 2 + N % 2;
            T[] s1 = (T[]) new Object[mid];
            System.arraycopy(list, 0, s1, 0, mid);
            T[] s2 = (T[]) new Object[N - mid - 1];
            System.arraycopy(list, mid + 1, s2, 0, N - mid - 1);
            t.item = list[mid];
            t.left = sortedIterHelper(s1, s1.length);
            t.right = sortedIterHelper(s2, s2.length);
        }
        return t;
    }

    /* Prints the tree represented by ROOT. */
    private void print() {
        print(root, 0);
    }

    private void print(BSTNode<T> node, int d) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < d; i++) {
            System.out.print("  ");
        }
        System.out.println(node.item);
        print(node.left, d + 1);
        print(node.right, d + 1);
    }

    class BSTNode<T> {
        T item;
        BSTNode<T> left;
        BSTNode<T> right;

        BSTNode(T item) {
            this.item = item;
        }
    }
}