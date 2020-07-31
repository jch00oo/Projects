import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {
    @Test
    public void test1() { //insert/remove test
        MinHeap<Character> h = new MinHeap<>();
        h.insert('f');
        h.insert('h');
        h.insert('d');
        h.insert('b');
        h.insert('c');
        h.removeMin();
        h.removeMin();
        h.toString();
    }

    @Test
    public void test2(){ //find min, remove it, new min-check bubbleUP
        MinHeap<String> h = new MinHeap<>();
        h.insert("f");
        h.insert("h");
        h.insert("d");
        h.insert("b");
        h.insert("c");
        assertEquals(h.findMin(), "b");
        assertEquals(h.removeMin(), "b");
        assertEquals(h.findMin(), "c");
    }

    @Test
    public void test3(){
        MinHeap<Character> h = new MinHeap<>();
        h.insert('f');
        h.insert('h');
        h.insert('d');
        h.insert('b');
        h.insert('c');
        assertEquals(h.size(), 5);
    }

    @Test
    public void test4(){ //
        MinHeap<String> h = new MinHeap<>();
        h.insert("f");
        h.insert("h");
        h.insert("d");
        h.insert("b");
        h.insert("c");
        assertEquals(h.removeMin(), "b");
        assertTrue(h.contains("f"));
    }

    @Test
    public void test5(){ //
        MinHeap<Character> h = new MinHeap<>();
        h.insert('f');
        h.insert('h');
        h.insert('d');
        h.insert('b');
        h.insert('c');
        assertFalse(h.contains('k'));
    }

    @Test
    public void test6(){ //
        MinHeap<Character> h = new MinHeap<>();
        h.insert('f');
        h.insert('h');
        h.insert('d');
        h.insert('b');
        h.insert('c');
        h.contains('c');
        h.update('c');
    }

}