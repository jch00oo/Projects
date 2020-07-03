import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    /**Tests AddFirst and addLast methods and compare using printDeque**/
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

    
    }
