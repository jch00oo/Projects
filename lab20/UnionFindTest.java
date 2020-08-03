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
}