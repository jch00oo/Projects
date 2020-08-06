import java.util.ArrayList;

public class UnionFind {

    public int[] Parent;

    /* Creates a UnionFind data structure holding N vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int N) {
        Parent = new int[N];
        for (int i = 0; i < N; i++) {
            Parent[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    /** find root and multiply by -1 */
    public int sizeOf(int v) {
        return (-1) * Parent[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return Parent[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    /** use find to see if they have the same root */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid vertices are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        ArrayList<Integer> index = new ArrayList<>();
        if (Parent.length <= v || v < 0) {
            throw new IllegalArgumentException();
        } else {
            while (Parent[v] >= 0) {
                index.add(v);
                v = Parent[v];
            }
            for (int bruh: index) {
                Parent[bruh] = v;
            }
            return v;
        }
    }

    /* Connects two elements V1 and V2 together. V1 and V2 can be any element,
       and a union-by-size heuristic is used. If the sizes of the sets are
       equal, tie break by connecting V1's root to V2's root. Union-ing a vertex
       with itself or vertices that are already connected should not change the
       structure. */
    public void union(int v1, int v2) {
        /** if same or connected */
        if (connected(v1, v2)) {
            return;
        }
        int rt1 = find(v1);
        int rt2 = find(v2);
        /** if same size, connect v1 root to v2 root */
        if (sizeOf(v1) <= (sizeOf(v2))) {
            Parent[rt2] += Parent[rt1];
            Parent[rt1] = rt2;
        } else {
            Parent[rt1] += Parent[rt2];
            Parent[rt2] = rt1;
        }
    }

}
