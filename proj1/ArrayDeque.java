public class ArrayDeque {



    /* adds item of type T to the front of the deque */
    public void addFirst(T item)

    /* adds item of type T to the back of the deque */
    public void addLast(T item)

    /* return true if deque is empty, false otherwise */
    public boolean isEmpty()

    /*returns number of items in the deque */
    public int size()

    /* prints items in the deque from first to last,
    print out new line once all items have been printed */
    public void printDeque()

    /* removes and returns item at front of the deque;
    return null if no such item exists */
    public T removeFirst()

    /* removes and returns the item at the back of the deque.
    returns null if no such item exists */
    public T removeLast()

    /* gets the item at the given index, where 0 is the front,
    1 is the next item, and so forth. return null if nosuch item exists.
    must not alter the deque */
    public T get(int index)
}
