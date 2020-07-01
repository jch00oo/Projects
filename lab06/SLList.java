/**
 * An SLList is a list of integers, which encapsulates the
 * naked linked list structure.
 */
public class SLList {

    /**
     * IntListNode is a nested class that represents a single node in the
     * SLList, storing an item and a reference to the next IntListNode.
     */
    private static class IntListNode {
        /*
         * The access modifiers inside a private nested class are irrelevant:
         * both the inner class and the outer class can access these instance
         * variables and methods.
         */
        public int item;
        public IntListNode next;

        public IntListNode(int item, IntListNode next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntListNode that = (IntListNode) o;
            return item == that.item;
        }

        @Override
        public String toString() {
            return item + "";
        }

        public void addFirst() {
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private IntListNode sentinel;
    private int size;

    /** Creates an empty SLList. */
    public SLList() {
        sentinel = new IntListNode(42, null);
        sentinel.next = sentinel;
        size = 0;
    }

    public SLList(int x) {
        sentinel = new IntListNode(42, null);
        sentinel.next = new IntListNode(x, null);
        sentinel.next.next = sentinel;
        size = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SLList slList = (SLList) o;
        if (size != slList.size) return false;

        IntListNode l1 = sentinel.next;
        IntListNode l2 = slList.sentinel.next;

        while (l1 != sentinel || l2 != slList.sentinel) {
            if (!l1.equals(l2)) return false;
            l1 = l1.next;
            l2 = l2.next;
        }
        return true;
    }

    @Override
    public String toString() {
        IntListNode l = sentinel.next;
        String result = "";

        while (l != sentinel) {
            result += l + " ";
            l = l.next;
        }

        return result.trim();
    }

    /** Returns an SLList consisting of the given values. */
    public static SLList of(int... values) {
        SLList list = new SLList();
        for (int i = values.length - 1; i >= 0; i -= 1) {
            list.addFirst(values[i]);
        }
        return list;
    }

    /**
     * Returns the size of the list.
     */
    public int size() {
        return size;
    }

    /** Adds x to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new IntListNode(x, sentinel.next);
        size += 1;
    }

    /** Return the value at the given index. */
    public int get(int index) {
        IntListNode p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /** Adds x to the list at the specified index. */
    public void add(int index, int x) {
        size += 1; //INCREASE THE SIZE
        IntListNode pointsatindex = sentinel; //the index pointer starts at sentinal
        while (index > 0) { //we need to insert at index, but we need to find the before and after the index
            index--; //index will be whatever inputted then kept subtracting until 0 then we hit the "before"
            pointsatindex = pointsatindex.next; //ok now actually move down the list with the index pointer
            if (pointsatindex.next == sentinel && index > 0) { //this is the case where the index too big, but we reached the sentinel end already
                pointsatindex.next = new IntListNode(x, sentinel); //sticks it on the end. instead of sentinel it points to x, which then points sentinel
                return; //otherwise big loop no end RIP
            }
        }
        pointsatindex.next = new IntListNode(x, pointsatindex.next); // once we reach that place we need to use order of operations. the .next is rerouted to the new intnode x we made.
        //bc of order of operations; it's still going from objec 1 to 2 bc it evaluates PEMDAS style. so it'll evaluate pointsatindex.next in the function  which is still at object 2. so once it does that it will evaluate rest of expressoin like we want
    }

    /** Destructively reverses this list. */
    public void reverse() {
        sentinel.next = reverseHelper(sentinel.next);
    }
    public IntListNode reverseHelper(IntListNode head) {
        if (head == sentinel || head.next == sentinel) {
            return head;
        }
        else {
            IntListNode reversed = reverseHelper(head.next);
            head.next.next = head;
            head.next = sentinel;
            return reversed;
        }
    }
}