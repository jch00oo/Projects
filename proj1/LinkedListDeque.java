public class LinkedListDeque<T> {

    private class IntNode {
        T item;
        IntNode prev;
        IntNode next;

        IntNode(IntNode prev, T item, IntNode next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private class of(___)
    private class equals(___)

    private int size;
    private IntNode sentinel;

    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public T getRecursive(int index) {
        if (index < size && index >= 0) {
            /* do we need to decrement index? */
            return getRecursiveHelper(index, sentinel.next);
        }
        return null;
    }

    private T getRecursiveHelper(int index, IntNode n) {
        if (index == 0) {
            return n.item;
        }
        else {
            return getRecursiveHelper(index-1, n.next);
        }
    }

    /* adds item of type T to the front of the deque */
    public void addFirst(T item) {
        IntNode first = new IntNode(sentinel, item, sentinel.next);
        sentinel.next = first;
        first.next.prev = first;
        size += 1;
    }

    /* adds item of type T to the back of the deque */
    public void addLast(T item) {
        IntNode last = new IntNode(item, sentinel, sentinel.prev); /*not sure abt last part */
        sentinel.prev = last;
        last.prev.next = last;
        size += 1;
    }

    /* return true if deque is empty, false otherwise */
    public boolean isEmpty() {
        return size == 0; /* bruh? */
    }

    /*returns number of items in the deque */
    public int size() {
        return size;
    }

    /* prints items in the deque from first to last,
    print out new line once all items have been printed */
    public void printDeque()

    /* removes and returns item at front of the deque;
    return null if no such item exists */
    public T removeFirst() {
        IntNode oldFirst = sentinel.next;
        T First = oldFirst.item;
        sentinel.next = oldFirst.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return First;
    }

    /* removes and returns the item at the back of the deque.
    returns null if no such item exists */
    public T removeLast() {
        IntNode oldLast = sentinel.prev;
        T Last = oldLast.item;
        sentinel.prev = oldLast.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return Last;
    }

    /* gets the item at the given index, where 0 is the front,
    1 is the next item, and so forth. return null if no such item exists.
    must not alter the deque */
    public T get(int index) {

    }


}
