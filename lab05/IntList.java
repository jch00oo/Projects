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
        // YOUR CODE HERE
        IntList copls = this;
        int N = 1;
        while (copls.next != null) {
            copls = copls.next;
            N += 1;
        }
        if (position == 0) {
            return this.item;
        } else if (position < 0 || position >= N) {
            throw new IllegalArgumentException("git fucked");
        } else {
            IntList pos = this;
            int i = 0;
            while (i < position) {
                pos = pos.next;
                i += 1;
            }
            return pos.item;
        }
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        // YOUR CODE HERE
        IntList f = this;
        String make_empty_array = "";
        while (f != null) {
            make_empty_array = make_empty_array + f.item + " ";
            f = f.next;
        }
        make_empty_array = make_empty_array.trim();
        return make_empty_array;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        // not same type
        if (!(obj instanceof IntList)){
            return false;
        }
        //check length of first array
        IntList k=this;
        int count=0;
        while (k.next!=null){
            k=k.next;
            count+=1;
        }
        //check length of second array
        int count2=0;
        IntList l= (IntList) obj;
        while (l.next!=null){
            l=l.next;
            count2+=1;
        }
        //are the two lengths equal
        if (!(count==count2)){
            return false;
        }
        //is everything the same inside
        IntList k2 = this; //this
        IntList l2 = ((IntList)obj); //that
        while ((k2.next!=null)&&(l2.next!=null)){
            k2=k2.next;
            l2=l2.next;
            if (k2.item!=l2.item){
                return false;
            }
        }
        return true;
    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        // YOUR CODE HERE.
        IntList k = this;
        while (k.next!=null){
            k=k.next; }
        k.next = new IntList(value);
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        // YOUR CODE HERE
        IntList j = this;
        int min = j.item;
        while (j != null) {
            if (min > j.item) {
                min = j.item;
            }
            j = j.next;
        }
        return min;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        // YOUR CODE HERE
        int sum = 0;
        IntList k = this;
        while (k != null) {
            sum += (k.item * k.item);
            k = k.next;
        }
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
        // destroy list A by just adding onto it
        IntList a = A;
        if (A==null){
            A=B;
            return A;
        }
        while (a.next!=null) {
            a = a.next; //this is just overwriting the old list
        }
        a.next= B;
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
        IntList a =A;
        IntList b=B;
        IntList c= new IntList (0);
        IntList d=c;
        if (A==null){
            return B;
        }
        while (a!=null){
            d.next= new IntList(a.item);
            d=d.next;
            a=a.next;
            }
        while (b!=null){
            d.next= new IntList(b.item);
            d=d.next;
            b=b.next;
        }
        c=c.next;
        return c;
    }
}
