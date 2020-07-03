import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void Testfirstlast() {
        ArrayDeque<Integer> first= new ArrayDeque<>();
        ArrayDeque<Integer> last= new ArrayDeque<>();
        first.addFirst(5);
        first.addFirst(4);
        first.addFirst(3);
        first.addFirst(2);
        first.addFirst(1);
        last.addLast(1);
        last.addLast(2);
        last.addLast(3);
        last.addLast(4);
        last.addLast(5);
        first.printDeque();
        last.printDeque();
        assertEquals((Integer)5,(Integer)first.size());
        assertEquals((Integer)5,(Integer)last.size());
    }

    /**Checks the resize and a combo of add first and last **/
    @Test
    public void TrivialResizeTest() {
        ArrayDeque<Integer> combo= new ArrayDeque<>();
        combo.addLast(5);
        combo.addFirst(4);
        combo.addLast(6);
        combo.addFirst(3);
        combo.addLast(7);
        assertEquals((Integer)5,(Integer)combo.size());
        combo.printDeque();
        combo.removeFirst();
        assertEquals((Integer)4,(Integer)combo.size());
        assertEquals((Integer)4,(Integer)combo.getItem(0));
        combo.printDeque();
        combo.removeLast();
        assertEquals((Integer)3,(Integer)combo.size());
        assertEquals((Integer)6,(Integer)combo.getItem(2));
        combo.printDeque();
    }


    /**Print only test**/
    @Test
    public void testPrint(){
        ArrayDeque<Integer> J = new ArrayDeque<>();
        J.addFirst(1);
        J.addFirst(2);
        J.addFirst(3);
        J.addFirst(4);
        J.printDeque();
    }

    @Test
    public void testGet(){
        ArrayDeque<Integer> K= new ArrayDeque<>();
        K.addFirst(1);
        //normal cases
        assertEquals((Integer) 1,(Integer) K.get(0));
        K.addFirst(2);
        K.addFirst(3);
        K.addFirst(4);
        K.addFirst(5);
        assertEquals((Integer) 5,(Integer) K.get(0));
        assertEquals((Integer) 4,(Integer) K.get(1));
        assertEquals((Integer) 3,(Integer) K.get(2));
        assertEquals((Integer) 2,(Integer) K.get(3));
        assertEquals((Integer) 1,(Integer) K.get(4));
        //out of bounds
        assertNull(K.get(500));
        assertNull(K.get(-500));
        //extra
        K.addFirst(1);
        K.removeFirst();
        assertEquals((Integer) 5, (Integer) K.get(0));
    }

    @Test
    public void Trickyremoves() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        lld1.addFirst(1);
        lld1.addLast(2);
        lld1.removeFirst();
        lld1.printDeque();
        assertEquals((Integer)1,(Integer)lld1.size());
        lld1.removeFirst();
        lld1.printDeque();

        lld2.addFirst(1);
        lld2.addLast(2);
        lld2.removeFirst();
        lld2.removeFirst();
        lld2.removeFirst();
        lld2.printDeque();
        assertEquals((Integer)0,(Integer)lld2.size());
    }
    }
