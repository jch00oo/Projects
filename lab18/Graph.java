import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Stack;
import java.util.HashSet;

public class Graph implements Iterable<Integer> {

    private static LinkedList<Edge>[] adjLists;
    private int vertexCount;


    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        // TODO: YOUR CODE HERE
        for (Edge e : adjLists[v1]){
            if (e.to == v2){
                e.weight = weight;
                break;
            }
        }
        Edge edgeadd = new Edge(v1,v2,weight);
        adjLists[v1].add(edgeadd);
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        // TODO: YOUR CODE HERE
        addEdge(v1,v2,weight);
        addEdge(v2,v1,weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        // TODO: YOUR CODE HERE
        for (Edge e : adjLists[from]){
            if (e.to == to){
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        // TODO: YOUR CODE HERE
        List<Integer> listToReturn = new ArrayList<Integer>();
        for (int k = 0; k < vertexCount; k++) {
            if (isAdjacent(k, v)) {
                listToReturn.add(k);
            }
        }
        return listToReturn;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        // TODO: YOUR CODE HERE
        int count = 0;
        for (int k = 0; k < vertexCount; k++) {
            if (isAdjacent(k, v)) {
                count += 1;
            }
        }
        return count;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /* A class that iterates through the vertices of this graph, starting with
       vertex START. If the iteration from START has no path from START to some
       vertex v, then the iteration will not include v. */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        DFSIterator(int start) {
            fringe = new Stack<>();
            fringe.push(start);
            visited = new HashSet<>();
        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        public Integer next() {
            if (fringe.isEmpty()) {
                return null;
            }
            int point = fringe.pop();
            for (Edge e: adjLists[point]) {
                if (!visited.contains(e.to) && !fringe.contains(e.to)) {
                    fringe.push(e.to);
                }
            }
            visited.add(point);
            return point;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        // TODO: YOUR CODE HERE
        List<Integer> sequence = dfs(start);
        return sequence.contains(stop);
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        // TODO: YOUR CODE HERE
        List<Integer> rpath = new ArrayList<>();
        if (pathExists(start, stop)) {
            rpath.add(stop);
            int current = stop;
            while (!isAdjacent(start, current)) {
                for (int v : neighbors(current)) {
                    if (pathExists(start, v)) {
                        rpath.add(0, v);
                        current = v;
                        break;
                    }
                }
            }
            rpath.add(0, start);
        }
        return rpath;
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private List<Integer> currentInDegree;

        TopologicalIterator() {
            // TODO: YOUR CODE HERE
            fringe = new Stack<Integer>();
            currentInDegree = new ArrayList<Integer>();
            for (int k = 0; k < vertexCount; k++) {
                currentInDegree.add(k,inDegree(k));
                if (currentInDegree.get(k) == 0) {
                    fringe.push(k);
                    currentInDegree.set(k,1);
                }
            }
        }

        public boolean hasNext() {
            // TODO: YOUR CODE HERE
            return !fringe.isEmpty();
        }

        public Integer next() {
            // TODO: YOUR CODE HERE
            int current = fringe.pop();
            for (int k = 0; k < vertexCount; k++) {
                if (isAdjacent(current, k)) {
                    currentInDegree.set(k,currentInDegree.get(k)-1);
                }
            }
            for (int k = 0; k < vertexCount; k++) {
                if (currentInDegree.get(k) == 0) {
                    fringe.push(k);
                    currentInDegree.set(k,1);
                }
            }
            return current;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int getTo() {
            return to;
        }

        public int getFrom() {
            return from;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

    }

    private void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();
    }
}