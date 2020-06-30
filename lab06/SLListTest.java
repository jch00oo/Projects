import org.junit.Test;
import static org.junit.Assert.*;

public class SLListTest {

    @Test
    public void testSLListAdd() {
        SLList test1 = SLList.of(1, 3, 5);
        SLList test2 = new SLList();

        test1.add(1, 2);
        test1.add(3, 4);

        assertEquals(5, test1.size());
        assertEquals(3, test1.get(2));
        assertEquals(4, test1.get(3));

        test2.add(1, 1);
        assertEquals(1, test2.get(0));
        assertEquals(1, test2.size());

        test2.add(10, 10);
        assertEquals(10, test2.get(1));
        test1.add(0, 0);
        assertEquals(SLList.of(0, 1, 2, 3, 4, 5), test1);
    }

    @Test
    public void testSLListReverse() {
        /* test when list doesn't have anything in it */
        SLList a = SLList.of();
        a.reverse();
        assertEquals(SLList(), ); /*idk bruh */

        /* test when list only has one element */
        SLList b = SLList.of(18);
        b.reverse();
        assertEquals(18, b.get(0));

        /* test normal case */
        SLList c = SLList.of(1,2,3);
        c.reverse();
        assertEquals(3, c.get(0));
        assertEquals(2, c.get(1));
        assertEquals(1, c.get(2));
    }

    }

