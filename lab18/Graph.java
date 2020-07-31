import java.util.*;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
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
        adjLists[v1].add(new Edge(v1,v2,weight));
//        LinkedList<Edge> lst = adjLists[v1];
//        for (Edge edge: lst) {
//            if (edge.to == v2) {
//                edge.weight = weight;
//                return;
//            }
//        }
//        lst.add(new Edge(v1, v2, weight));

//        Edge add = new Edge(v1, v2, weight);
//        Edge remove = null;
//        for (Edge edge: adjLists[v1]) {
//            if (edge.to == v2) {
//                remove = edge;
//            }
//        }
//        if (remove != null) {
//            adjLists[v1].remove(remove);
//        }
//        adjLists[v1].addLast(add);
//        adjLists[v1].sort(((o1, o2) -> o2.to - o1.to));
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        // TODO: YOUR CODE HERE
//        addEdge(v1, v2, weight);
//        addEdge(v2, v1, weight);
        adjLists[v1].add(new Edge(v1, v2, weight));
        adjLists[v2].add(new Edge(v2, v1, weight));
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        // TODO: YOUR CODE HERE
//        LinkedList<Edge> lst = adjLists[from];
//        for (Edge edge: lst) {
//            if (edge.to == to) {
//                return true;
//            }
//        }
//        return false;
        Iterator<Edge> edges = adjLists[from].iterator();
        while (edges.hasNext()) {
            Edge e = edges.next();
            if (e.to == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        // TODO: YOUR CODE HERE
        List<Integer> lst = new ArrayList<>();
        for (Edge edge: adjLists[v]) {
            lst.add(edge.to);
        }
        return lst;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        // TODO: YOUR CODE HERE
        int count = 0;
        for (int i = 0; i <= vertexCount; i++){
//            if (neighbors(i).contains(v)) {
//                count = count + 1;
//            }

//            for (Edge edge: adjLists[i]) {
//                if (edge.to == v) {
//                    count ++;
//                }
//            }
            if (isAdjacent(v, i)) {
                count ++;
            }
        }
        return count;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /**
     *  A class that iterates through the vertices of this graph,
     *  starting with a given vertex. Does not necessarily iterate
     *  through all vertices in the graph: if the iteration starts
     *  at a vertex v, and there is no path from v to a vertex w,
     *  then the iteration will not include w.
     */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                while (visited.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            int curr = fringe.pop();
            ArrayList<Integer> lst = new ArrayList<>();
            for (int i : neighbors(curr)) {
                lst.add(i);
            }
            lst.sort((Integer i1, Integer i2) -> -(i1 - i2));
            for (Integer e : lst) {
                fringe.push(e);
            }
            visited.add(curr);
            return curr;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
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
        if (start==stop) {
            return true;
        }
        List<Integer> paths = dfs(start);
//        for (int i =0; i<paths.size();i++){
//            if (paths.get(i)==stop){
//                return true;
//            }
//        }
//        return false;

//        for (int i : dfs(start)) {
        for (Integer i : paths) {
            if (i == stop) {
                return true;
            }
        }
        return false;
//        }
//        return false;

//        List<Integer> curr = dfs(start);
//        return start == stop || curr.contains(stop);
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        // TODO: YOUR CODE HERE
//        ArrayList<Integer> past = new ArrayList<Integer>();
//        ArrayList<Integer> path = new ArrayList<Integer>();
//        Iterator<Integer> iter = new DFSIterator(start);

        List finalLst = new LinkedList<>();
        if (!pathExists(start,stop)){
            return finalLst;
        } else if (start == stop) {
            finalLst.add(start);
            return finalLst;
        } else {
            Iterator<Integer> iter = new DFSIterator(start);
            List<Integer> temp = new LinkedList();
            while (iter.hasNext()) {
                int v = iter.next();
                if (v != stop) {
                    temp.add(v);
                } else {
                    break;
                }
            }
            int end = stop;
            finalLst.add(end);
            for (int i = temp.size() - 1; i >= 0; i--) {
                if (temp.get(i) == start && isAdjacent(start, end)) {
                    finalLst.add(temp.get(i));
                    break;
                } else if (isAdjacent(temp.get(i), end)) {
                    finalLst.add(temp.get(i));
                    end = temp.get(i);
                }
            }
        }
        Collections.reverse(finalLst);
        return finalLst;

//        while (iter.hasNext()){
//            if (iter.next()==stop){
//                break;
//            }else{
//                past.add(iter.next());
//            }
//        }
//        //path.add(stop); add last val
//        //make a loop to see if is a past key and is able to connect
//        return past; //just here for compiler purposes
//    }
//
//        ArrayList<Integer> path = new ArrayList<>();
//        if (!pathExists(start, stop)) {
//            return new ArrayList<>();
//        } else if (start == stop) {
//            ArrayList<Integer> toReturn = new ArrayList<>();
//            toReturn.add(start);
//            return toReturn;
//        } else {
//            ArrayList<Integer> toReturn = new ArrayList<>();
//            int curr = dfs(start).indexOf(stop);
//            int pos = curr - 1;
//            toReturn.add(0, stop);
//            while (curr > 0) {
//                int pathFrom = dfs(start).get(pos);
//                int pathTo = dfs(start).get(curr);
//                if (isAdjacent(pathFrom, pathTo)) {
//                    toReturn.add(0, dfs(start).get(pos));
//                    curr = pos;
//                }
//                pos--;
//            }
//            return toReturn;
//        }

//        List<Integer> result = new ArrayList<>();
//        Iterator<Integer> iter = new DFSIterator(start);
//        ArrayList<Integer> visited = new ArrayList<>();
//        if (!pathExists(start, stop)) {
//            return result;
//        }
//        while (iter.hasNext()) {
//            Integer n = iter.next();
//            if (!visited.contains(n)) {
//                visited.add(n);
//            }
//            if (n == stop) {
//                break;
//            }
//        }
//        result.add(stop);
//        while (true) {
//            for (Integer before: visited) {
//                if (isAdjacent(before, stop)
//                        && visited.indexOf(before) < visited.indexOf(stop)) {
//                    result.add(before);
//                    stop = before;
//                    break;
//                }
//            }
//            if (stop == start) {
//                break;
//            }
//        }
//        Collections.reverse(result);
//        return result;
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
        private Integer[] inDegree;
        private HashSet<Integer> visited;

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            // TODO: YOUR CODE HERE
            visited = new HashSet<>();
            inDegree = new Integer[vertexCount];
            for (int i = 0; i < vertexCount; i++) {
                inDegree[i] = inDegree(i);
                if (inDegree(i) == 0) {
                    fringe.push(i);
                }
            }
        }

        public boolean hasNext() {
            // TODO: YOUR CODE HERE
            return !fringe.isEmpty();
        }

        public Integer next() {
            // TODO: YOUR CODE HERE
            Integer v = fringe.pop();
            for (Edge e : adjLists[v]) {
                inDegree[v] = inDegree[v] - 1;
            }
            visited.add(v);
            for (int i = 0; i < vertexCount; i++) {
                if (!visited.contains(v) && !fringe.contains(v)
                        && inDegree[i] == 0) {
                    fringe.push(i);
                }
            }
            return v;
//            Integer result = fringe.pop();
//            for (Edge e : adjLists[result]) {
//                currentInDegree[e.to]--;
//            }
//            visited.add(result);
//            for (int i = 0; i < vertexCount; i++) {
//                if (!visited.contains(i) && !fringe.contains(i) && currentInDegree[i] == 0) {
//                    fringe.push(i);
//                }
//            }
//            return result;
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
            return this.to;
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