import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

    /**
     * Creates an empty Deque. Also inspired by whoever wrote lab 6 skeleton
     */
    @Test
    public void isemptytest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        System.out.println("Checking if empty");
        if (lld1.size() != 0) {
            System.out.println("Wrong! Expected:" + 0 + "Actual:" + lld1.size());
        }
        System.out.println("Right! Expected:" + 0 + "Actual:" + lld1.size());
    }

    /**
     * Adds an item of type T to the front of the deque. This SPECIFICALLY ONLY CHECKS FIRST AND RECURSIVE GET
     **/
    @Test
    public void addFirstTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<Integer>();
        try {
            assertTrue(lld1.isEmpty());
            lld1.addFirst("Jihee");
            assertEquals("Jihee", lld1.getRecursive(0));
            assertTrue(lld1.size() == 1);

            lld1.addFirst("I'm");
            assertEquals("I'm", lld1.getRecursive(0));
            assertTrue(lld1.size() == 2);

        } finally {
            // The deque will be printed at the end of this test
            // or after the first point of failure.
            lld1.printDeque();
            lld2.printDeque();
        }
    }

    /**
     * Adds an item of type T to the front of the deque. This SPECIFICALLY ONLY CHECKS LAST AND NORMAL GET
     **/
    @Test
    public void addLastTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();

        try {
            assertTrue(lld1.isEmpty());
            lld1.addLast("Jihee");
            assertEquals("Jihee", lld1.get(0));
            assertTrue(lld1.size() == 1);

            lld1.addLast("am I");
            assertEquals("am I", lld1.get(1));
            assertTrue(lld1.size() == 2);

        } finally {
            // The deque will be printed at the end of this test
            // or after the first point of failure.
            System.out.println("Check! ");
            lld1.printDeque();
        }
    }

    /**
     * This tests first, last, and size was given to us
     **/
    @Test
    public void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test.");
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();

        // Java will try to run the below code.
        // If there is a failure, it will jump to the finally block before erroring.
        // If all is successful, the finally block will also run afterwards.
        try {

            assertTrue(lld1.isEmpty());

            lld1.addFirst("front");
            assertEquals(1, lld1.size());
            assertFalse(lld1.isEmpty());

            lld1.addLast("middle");
            assertEquals(2, lld1.size());

            lld1.addLast("back");
            assertEquals(3, lld1.size());

        } finally {
            // The deque will be printed at the end of this test
            // or after the first point of failure.
            System.out.println("Printing out deque: ");
            lld1.printDeque();
        }
    }

    /**
     * THIS IS THE REMOVES FIRST TEST
     */
    @Test
    public void addRemoveTest() {
        System.out.println("Running add/remove FIRST test.");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        try {
            assertTrue(lld1.isEmpty());

            lld1.addFirst(10);
            assertFalse(lld1.isEmpty());

            lld1.removeFirst();
            assertTrue(lld1.isEmpty());
        } finally {
            System.out.println("Printing out deque: ");
            lld1.printDeque();
        }
    }

    /**
     * THIS IS THE REMOVES LAST TEST
     */
    @Test
    public void addRemoveLastTest() {
        System.out.println("Running add/remove LAST test.");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        try {
            assertTrue(lld1.isEmpty());
            lld1.addLast(9);
            assertFalse(lld1.isEmpty());

            lld1.removeLast();
            assertTrue(lld1.isEmpty());
        } finally {
            System.out.println("Printing out deque: ");
            lld1.printDeque();
        }
    }

    @Test
    public void doesremovereturnright() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        lld1.addFirst("not answer");
        lld1.addFirst("yes answer");
        assertEquals("yes answer", lld1.removeFirst());
    }


    /**
     * check are the get and recursive gets equal
     **/
    public boolean getrecursiveget() {
        System.out.println("Does the recursive get and the normal get print out the same thing?");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        assertTrue(lld1.isEmpty());
        lld1.addFirst(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addLast(4);
        lld1.addLast(5);
        if (lld1.getRecursive(3) == lld1.get(3)) {
            return true;
        } else {
            System.out.println("WRONG WRONG WRONG");
            System.out.println(lld1.getRecursive(3));
            System.out.println(lld1.get(3));
            return false;
        }
    }
}
