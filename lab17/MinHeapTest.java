import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {
//stupid junit isn't running
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
        //   d
        //f      h
    }

    @Test
    public void test2(){ //find min, remove it, new min-check bubbleUP
        MinHeap<Character> h = new MinHeap<>();
        h.insert('f');
        h.insert('h');
        h.insert('d');
        h.insert('b');
        h.insert('c');
        h.findMin();
        h.removeMin();
        h.findMin();
        h.toString();
    }

    @Test
    public void test3(){
        MinHeap<Character> h = new MinHeap<>();
        h.insert('f');
        h.insert('h');
        h.insert('d');
        h.insert('b');
        h.insert('c');
        h.size();
    }

    @Test
    public void test4(){ //
        MinHeap<Character> h = new MinHeap<>();
        h.insert('f');
        h.insert('h');
        h.insert('d');
        h.insert('b');
        h.insert('c');
        h.removeMin();
        h.contains('f');
    }

    @Test
    public void test5(){ //
        MinHeap<Character> h = new MinHeap<>();
        h.insert('f');
        h.insert('h');
        h.insert('d');
        h.insert('b');
        h.insert('c');
        h.contains('k');
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
