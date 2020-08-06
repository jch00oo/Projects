//
//import java.util.ArrayList;
//import java.util.NoSuchElementException;
//
///* A MinHeap class of Comparable elements backed by an ArrayList. */
//public class MinHeap<E extends Comparable<E>> {
//
//    /* An ArrayList that stores the elements in this MinHeap. */
//    private ArrayList<E> contents;
//    private int size;
//    // TODO: YOUR CODE HERE (no code should be needed here if not
//    // implementing the more optimized version)
//
//    /* Initializes an empty MinHeap. */
//    public MinHeap() {
//        contents = new ArrayList<>();
//        contents.add(null);
//        size = 0;
//    }
//
//    /* Returns the element at index INDEX, and null if it is out of bounds. */
//    private E getElement(int index) {
//        if (index >= contents.size()) {
//            return null;
//        } else {
//            return contents.get(index);
//        }
//    }
//
//    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
//       enough, add elements until it is the right size. */
//    private void setElement(int index, E element) {
//        while (index >= contents.size()) {
//            contents.add(null);
//        }
//        contents.set(index, element);
//    }
//
//    /* Swaps the elements at the two indices. */
//    private void swap(int index1, int index2) {
//        E element1 = getElement(index1);
//        E element2 = getElement(index2);
//        setElement(index2, element1);
//        setElement(index1, element2);
//    }
//
//    /* Prints out the underlying heap sideways. Use for debugging. */
//    @Override
//    public String toString() {
//        return toStringHelper(1, "");
//    }
//
//    /* Recursive helper method for toString. */
//    private String toStringHelper(int index, String soFar) {
//        if (getElement(index) == null) {
//            return "";
//        } else {
//            String toReturn = "";
//            int rightChild = getRightOf(index);
//            toReturn += toStringHelper(rightChild, "        " + soFar);
//            if (getElement(rightChild) != null) {
//                toReturn += soFar + "    /";
//            }
//            toReturn += "\n" + soFar + getElement(index) + "\n";
//            int leftChild = getLeftOf(index);
//            if (getElement(leftChild) != null) {
//                toReturn += soFar + "    \\";
//            }
//            toReturn += toStringHelper(leftChild, "        " + soFar);
//            return toReturn;
//        }
//    }
//
//    /* Returns the index of the left child of the element at index INDEX. */
//    private int getLeftOf(int index) {
//        return index * 2;
//    }
//
//    /* Returns the index of the right child of the element at index INDEX. */
//    private int getRightOf(int index) {
//        return (2 * index) + 1;
//    }
//
//    /* Returns the index of the parent of the element at index INDEX. */
//    private int getParentOf(int index) {
//        if (index / 2 == 0) {
//            return -1;
//        }
//        return index / 2;
//    }
//
//    /* Returns the index of the smaller element. At least one index has a
//       non-null element. If the elements are equal, return either index. */
//    private int min(int index1, int index2) {
//        E e1 = getElement(index1);
//        E e2 = getElement(index2);
//        if (e1 == null) {
//            return index2;
//        }
//        if (e2 == null) {
//            return index1;
//        }
//        if (e1.compareTo(e2) < 0) {
//            return index1;
//        }
//        return index2;
//    }
//
//    /* Returns but does not remove the smallest element in the MinHeap. */
//    public E findMin() {
//        return getElement(1);
//    }
//
//    /* Bubbles up the element currently at index INDEX. */
//    private void bubbleUp(int index) {
//        if (index <= 1) {
//            return;
//        }
//        E curr = getElement(index);
//        E parent = getElement(getParentOf(index));
//        if (curr.compareTo(parent) >= 0 || curr == findMin()) {
//            return;
//        } else {
//            swap(index, getParentOf(index));
//            bubbleUp(getParentOf(index));
//        }
//    }
//
//    private int getMaxIndex(int index1, int index2) {
//        if (contents.get(index1) == null) {
//            return index2;
//        }
//
//        if (contents.get(index2) == null) {
//            return index1;
//        }
//
//        if (contents.get(index1).compareTo(contents.get(index2)) > 0) {
//            return index1;
//        }
//
//        if (contents.get(index2).compareTo(contents.get(index1)) > 0) {
//            return index2;
//        } else {
//            return 0;
//        }
//    }
//
//    /* Bubbles down the element currently at index INDEX. */
//    private void bubbleDown(int index) {
////        if (index >= contents.size() || contents.get(index) == null) {
////            return;
////        }
////        boolean hasLeft = true;
////        boolean hasRight = false;
////        if (getLeftOf(index) >= contents.size() || contents.get(getLeftOf(index)) == null) {
////            hasLeft = false;
////        }
////        if (getRightOf(index) >= contents.size() || contents.get(getRightOf(index)) == null) {
////            hasRight = false;
////        }
////        if (!hasLeft && !hasRight) {
////            return;
////        }
////        // when item is smaller than left and right; (just do else return;)
////        // when larger than left but not right (or right DNE) *done*
////        // when larger than right but not left (or left DNE) *done*
////        // when larger than both *done*
////        if (hasLeft && hasRight && contents.get(index).compareTo(contents.get(getLeftOf(index))) > 0 && contents.get(index).compareTo(contents.get(getRightOf(index))) > 0) {
////            int toSwap;
////            if (contents.get(getLeftOf(index)).compareTo(contents.get(getRightOf(index))) < 0) {
////                toSwap = getLeftOf(index);
////            } else {
////                toSwap = getRightOf(index);
////            }
////            swap(toSwap, index);
////            bubbleDown(toSwap);
////        } else if (hasLeft && contents.get(index).compareTo(contents.get(getLeftOf(index))) > 0 && ((contents.get(index).compareTo(contents.get(getRightOf(index))) <= 0) || !hasRight)) {
////            swap(getLeftOf(index), index);
////            bubbleDown(getLeftOf(index));
////        } else if (hasRight && contents.get(index).compareTo(contents.get(getRightOf(index))) > 0 && ((contents.get(index).compareTo(contents.get(getLeftOf(index))) <= 0) || !hasLeft)) {
////            swap(getRightOf(index), index);
////            bubbleDown(getRightOf(index));
////        } else {
////            return;
////        }
//        E curr = getElement(index);
//        int minI = min(getLeftOf(index), getRightOf(index));
//        E min = getElement(minI);
//
//        if ((getElement(getRightOf(index))) == null && getElement(getLeftOf(index)) == null) {
//            return;
//        } else if (curr.compareTo(min) <= 0) {
//            return;
//        } else {
//            swap(index, minI);
//            bubbleDown(minI);
//        }
//    }
//
//    /* Returns the number of elements in the MinHeap. */
//    public int size() {
//        return size;
//    }
//
//    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
//       throw an IllegalArgumentException.*/
//    public void insert(E element) {
////        if (!contains(element)) {
////            setElement((size + 1), element);
////            bubbleUp(contents.size() - 1);
////            size++;
////        } else {
////            throw new IllegalArgumentException();
////        }
//        if (contains(element)) {
//            throw new IllegalArgumentException();
//        }
//        size++;
//        int index = size;
//        setElement(index, element);
//        bubbleUp(index);
//    }
//
//    /* Returns and removes the smallest element in the MinHeap. */
//    public E removeMin() {
//        E minimum = findMin();
//        swap(1, size);
//        contents.remove(size);
//        bubbleDown(1);
//        size--;
//        return minimum;
//    }
//
//    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
//       may have been mutated since the initial insert. If a copy of ELEMENT does
//       not exist in the MinHeap, throw a NoSuchElementException. Item equality
//       should be checked using .equals(), not ==. */
//    public void update(E element) {
//        if (contains(element)) {
//            for (int i = 1; i < contents.size(); i++) {
//                if (getElement(i).equals(element)) {
//                    setElement(i, element);
//                    bubbleUp(i);
//                    bubbleDown(i);
//                    return;
//                }
//            }
//        } else {
//            throw new NoSuchElementException();
//        }
//    }
//
//
//    /** good **/
//    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
//       be checked using .equals(), not ==. */
//    public boolean contains(E element) {
////        for (E each : contents) {
////            if (each != null && element.equals(element)) {
////                return true;
////            }
////        }
////        return false;
//        return contents.contains(element);
//    }
//}
//

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;
    // TODO: YOUR CODE HERE (no code should be needed here if not
    // implementing the more optimized version)
    private HashMap<E, Integer> exists;

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
        size = 0;
        exists = new HashMap<E, Integer>();
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);

        /** uh */
        exists.put(element1, index2);
        exists.put(element2, index1);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        // TODO: YOUR CODE HERE
        return index * 2;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        // TODO: YOUR CODE HERE
        return (2 * index) + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        // TODO: YOUR CODE HERE
        if (index / 2 == 0) {
            return -1;
        }
        return index / 2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        // TODO: YOUR CODE HERE
        E e1 = getElement(index1);
        E e2 = getElement(index2);
        if (e1 == null) {
            return index2;
        }
        if (e2 == null) {
            return index1;
        }
        if (e1.compareTo(e2) < 0) {
            return index1;
        }
        return index2;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        // TODO: YOUR CODE HERE
        return getElement(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        if (index <= 1) {
            return;
        }
        E curr = getElement(index);
        E parent = getElement(getParentOf(index));
        if (curr.compareTo(parent) >= 0 || curr == findMin()) {
            return;
        } else {
            swap(index, getParentOf(index));
            bubbleUp(getParentOf(index));
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        E curr = getElement(index);
        int minI = min(getLeftOf(index), getRightOf(index));
        E min = getElement(minI);

        if ((getElement(getRightOf(index))) == null && getElement(getLeftOf(index)) == null) {
            return;
        } else if (curr.compareTo(min) <= 0) {
            return;
        } else {
            swap(index, minI);
            bubbleDown(minI);
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        // TODO: YOUR CODE HERE
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if (contains(element)) {
            throw new IllegalArgumentException();
        }
        size++;
        int index = size;
        setElement(index, element);
        exists.put(element, size);
        bubbleUp(index);
    }

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        // TODO: YOUR CODE HERE
        E minimum = findMin();
        swap(1, size);
        E toDel = getElement(size);
        contents.remove(size);
        bubbleDown(1);
        exists.remove(toDel);
        size--;
        return minimum;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        // TODO: YOUR CODE HERE
        if (contains(element)) {
            int index = exists.get(element);
            setElement(index, element);
            bubbleUp(index);
            bubbleDown(index);
//            for (int i = 1; i < contents.size(); i++) {
//                if (getElement(i).equals(element)) {
//                    setElement(i, element);
//                    bubbleUp(i);
//                    bubbleDown(i);
//                    return;
//                }
//            }
        } else {
            throw new NoSuchElementException();
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        // TODO: YOUR CODE HERE
        return exists.containsKey(element);
//        return contents.contains(element);
    }
}
