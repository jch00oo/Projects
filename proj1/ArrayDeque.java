public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int startsize = 8;

    public ArrayDeque() {
        items = (T[]) new Object[startsize];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int oldi = (nextFirst + 1) % items.length;
        for (int newi = 0; newi <= size; newi += 1) {
            a[newi] = items[oldi];
            oldi = (oldi + 1) % items.length;
        }
        items = a;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    /* adds item of type T to the front of the deque */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = ((nextFirst - 1) + items.length)  % items.length;
        size += 1;
    }

    /* adds item of type T to the back of the deque */
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size += 1;
    }

    /* return true if deque is empty, false otherwise */
    public boolean isEmpty() {
        return size == 0;
    }

    /*returns number of items in the deque */
    public int size() {
        return size;
    }

    /* prints items in the deque from first to last,
    print out new line once all items have been printed */
    public void printDeque() {
        int tracker = (nextFirst + 1) % items.length;
        if (size == items.length) {
            for (int i = tracker; i < items.length; i= i + 1)
                System.out.print(items[i] + " ");
            for (int i = tracker; i < items.length; i= i + 1)
                System.out.print(items[i] + " ");
            System.out.println("");
        }
        for (int i = tracker; i != nextLast; i = (i+1) % items.length)
            System.out.print(items[i] + " ");
        System.out.println("");
    }

    /* removes and returns item at front of the deque;
    return null if no such item exists */
    public T removeFirst() {
        if (!(isEmpty())) {
            nextFirst = (nextFirst + 1) % items.length;
            items[nextFirst] = null;
            size -= 1;
            T firstItems = items[nextFirst];
            if ((double) size / items.length < 0.25 && items.length >= startsize) {
                resize(items.length / 2);
            }
            return firstItems;
        }
        return null;
    }


    /* removes and returns the item at the back of the deque.
    returns null if no such item exists */
    public T removeLast() {
        if (!(isEmpty())) {
            nextLast = ((nextLast - 1) + items.length) % items.length;
            T lastItems = items[nextLast];
            items[nextLast] = null;
            size -= 1;
            if (size < items.length * 0.25 && items.length >= startsize) {
                resize(items.length / 2);
            }
            return lastItems;
        }
        return null;
    }

    /* gets the item at the given index, where 0 is the front,
    1 is the next item, and so forth. return null if no such item exists.
    must not alter the deque */
    public T get(int index) {
        if (index >= items.length || index < 0) {
            return null;
        }
        int arrayindex = nextFirst + 1;
        while (index > 0) {
            arrayindex += 1;
            index -= 1;
        }
        return items[arrayindex % items.length];
    }

    public T getItem(int index) {
        return items[index];
    }

    public int getLength() {
        return items.length;
    }
}