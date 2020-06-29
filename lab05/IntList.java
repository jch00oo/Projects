

/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */

public class IntList {

    /**
     * The integer stored by this node.
     */
    public int item;
    /**
     * The next node in this IntList.
     */
    public IntList next;

    /**
     * Constructs an IntList storing ITEM and next node NEXT.
     */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /**
     * Constructs an IntList storing ITEM and no next node.
     */
    public IntList(int item) {
        this(item, null);
    }

    /**
     * Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints 1 2 3
     */
    public static IntList of(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        IntList copls = this;
        int N = 1;
        while (copls.next != null) {
            copls = copls.next;
            N += 1;
        }
        if (position == 0) {
            return this.item;
        } else if (position < 0 || position >= N) {
            throw new IllegalArgumentException("Position is out of range");
        } else {
            IntList copls2 = this;
            int i = 0;
            while (i < position) {
                copls2 = copls2.next;
                i += 1;
            }
            return copls2.item;
        }
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        IntList newlst = this;
        String returnthis = "";
        while (newlst != null) {
            returnthis += newlst.item + " ";
            newlst = newlst.next;
        }
        returnthis = returnthis.trim();
        return returnthis;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof IntList)) {
            return false;
        }
        IntList copls = this;
        int N = 1;
        while (copls.next != null) {
            copls = copls.next;
            N += 1;
        }
        int secondN = 1;
        IntList copobj = ((IntList) obj);
        while (copobj.next != null) {
            copobj = copobj.next;
            secondN += 1;
        }
        if (!(N == secondN)) {
            return false;
        }
        IntList copls2 = this;
        IntList copobj2 = ((IntList) obj);
        while ((copls2.next != null) && (copobj2.next != null)) {
            if (copls2.item != copobj2.item) {
                return false;
            }
            copls2 = copls2.next;
            copobj2 = copobj2.next;
        }
        return true;
    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        IntList a = new IntList(value);
        a.item = value;
        a.next = null;
        IntList l = this;
        while (l.next != null) {
            l = l.next;
        }
        l.next = a;
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        IntList copls = this;
        int curr = this.item;
        while (copls.next != null) {
            curr = Math.min(curr, copls.item);
            copls = copls.next;
        }
        curr = Math.min(curr, copls.item);
        return curr;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        IntList copls = this;
        int sum = 0;
        while (copls.next != null) {
            sum += copls.item * copls.item;
            copls = copls.next;
        }
        sum += copls.item * copls.item;
        return sum;
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        while (L != null) {
            L.item = L.item * L.item;
            L = L.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList dcatenate(IntList A, IntList B) {
        if (A == null && B == null) {
            return null;
        }
        else if (A == null) {
            return B;
        }
        IntList a = A;
        while (a.next != null) {
            a = a.next;
        }
        a.next = B;
        return A;
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList catenate(IntList A, IntList B) {
        if (A == null && B == null) {
            return null;
        }
        IntList result = new IntList(0);
        IntList a = A;
        IntList b = B;
        if (A == null) {
            result = new IntList(B.item);
            b = b.next;
        } else {
            result = new IntList(A.item);
            a = a.next;
        }
        IntList aa = result;
        while (a != null) {
            aa.next = new IntList(a.item);
            aa = aa.next;
            a = a.next;
        }
        while (b != null) {
            aa.next = new IntList(b.item);
            aa = aa.next;
            b = b.next;
        }
        return result;
    }
}