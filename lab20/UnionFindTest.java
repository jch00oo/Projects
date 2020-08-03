import org.junit.Test;

import static org.junit.Assert.*;

public class UnionFindTest {

    @Test
    public void test1() {
        UnionFind set = new UnionFind(10);
//        System.out.println(set.sizeOf(0));
        set.union(0, 1);
        System.out.println(set.parent(1));
        System.out.println(set.find(1));
//        System.out.println(set.sizeOf(0));
        set.union(1, 4);
//        System.out.println(set.sizeOf(0));
        set.union(0, 5);
//        System.out.println(set.sizeOf(0));
        set.union(6, 7);
//        System.out.println(set.sizeOf(6));
        set.union(0, 6);
//        System.out.println(set.sizeOf(0));
//        System.out.println(set.sizeOf(6));
        System.out.println(set.find(6));
        set.union(6, 0);
//        System.out.println(set.sizeOf(6));
//        System.out.println(set.sizeOf(0));
        System.out.println(set.find(6));
    }

    @Test
    public void test2() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        uf.union(3, 2);
        uf.union(2, 1);
        uf.find(3);
        assertEquals(1, uf.parent(3));
        uf.union(0, 4);
        uf.union(4, 0);
        assertEquals(5, uf.sizeOf(2));
    }

    @Test
    public void test3() {
        UnionFind uf = new UnionFind(2);
        uf.union(0, 1);
        System.out.println(uf.parent(1));
        System.out.println(uf.parent(0));
    }


}