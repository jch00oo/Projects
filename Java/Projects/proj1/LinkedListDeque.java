public class LinkedListDeque<T> implements Deque<T> {

    private int size;
    private IntNode sentinel;
    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Returns item at given int index using recursion. */
    public T getRecursive(int index) {
        if (index < size && index >= 0) {
            /* do we need to decrement index? */
            return getRecursiveHelper(index, sentinel.next);
        }
        return null;
    }

    /** A helper method for getRecursive that takes in int index
    and an IntNode n. */
    private T getRecursiveHelper(int index, IntNode n) {
        if (index == 0) {
            return n.item;
        } else {
            return getRecursiveHelper(index - 1, n.next);
        }
    }

    /** Takes in and adds item of type T to the front of the deque. */
    public void addFirst(T item) {
        IntNode first = new IntNode(sentinel, item, sentinel.next);
        sentinel.next = first;
        first.next.prev = first;
        size += 1;
    }

    /** Takes in and adds item of type T to the back of the deque. */
    public void addLast(T item) {
        IntNode last = new IntNode(sentinel.prev, item, sentinel);
        sentinel.prev = last;
        last.prev.next = last;
        size += 1;
    }

    /** Returns number of items in the deque as an int. */
    public int size() {
        return size;
    }

    /** Prints items in the deque from first to last,
    printing out a new line once all items have been printed. */
    public void printDeque() {
        IntNode copy = sentinel.next;
        while (copy != sentinel) {
            System.out.print(copy.item + " ");
            copy = copy.next;
        }
    }

    /** Removes and returns item at front of the deque;
    returns null if no such item exists. */
    public T removeFirst() {
        IntNode oldfirst = sentinel.next;
        T first = oldfirst.item;
        sentinel.next = oldfirst.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return first;
    }

    /** Removes and returns the item at the back of the deque.
    Returns null if no such item exists. */
    public T removeLast() {
        IntNode oldlast = sentinel.prev;
        T last = oldlast.item;
        sentinel.prev = oldlast.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return last;
    }

    /** Returns the item at the given int index;
    returns null if no such item exists.
    Must not alter the deque. */
    public T get(int index) {
        if (index < 0 || index >= size || isEmpty()) {
            return null;
        } else {
            IntNode curr = sentinel.next;
            int i = 0;
            while (i <= index) {
                if (i == index) {
                    return curr.item;
                }
                curr = curr.next;
                i += 1;
            }
            return curr.item;
        }
    }

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
}
