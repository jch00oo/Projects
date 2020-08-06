package bearmaps.utils.graph;

import bearmaps.utils.pq.MinHeapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

//    In pseudocode, this memory optimized version of A* is given below:
//
//    Create a PQ where each vertex v will have priority value p equal to the sum of v’s distance from the source plus the heuristic estimate from v to the goal, i.e. p = distTo[v] + h(v, goal).
//    Insert the source vertex into the PQ.
//            Repeat while the PQ is not empty, PQ.peek() is not the goal, and timeout is not exceeded:
//    p = PQ.poll()
//    relax all edges outgoing from p

    private SolverOutcome outcome;
    private List<Vertex> solution;
    private double solutionWeight;
    private int numStatesExplored;
    private double explorationTime;

//    /* ignores timeout since algorithm is so fast. */
//    public LazySolver(AStarGraph<Vertex> G, Vertex start, Vertex goal, double timeout) {
//        Stopwatch sw = new Stopwatch();
//        List<WeightedEdge<Vertex>> neighborEdges = G.neighbors(start);
//        for (WeightedEdge<Vertex> e : neighborEdges) {
//            if (e.to().equals(goal)) {
//                solution = List.of(start, goal);
//                solutionWeight = e.weight();
//                outcome = SolverOutcome.SOLVED;
//                timeSpent = sw.elapsedTime();
//                return;
//            }
//        }
//        outcome = SolverOutcome.UNSOLVABLE;
//        timeSpent = sw.elapsedTime();
//    }

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
        HashMap<Vertex, Double> distTo = new HashMap<>();
        distTo.put(start, 0.0);

        solution = new LinkedList<>();
        solutionWeight = 0;

        MinHeapPQ<Vertex> fringe = new MinHeapPQ<>();
        fringe.insert(start, input.estimatedDistanceToGoal(start, end));

        //* while not empty
        while (fringe.size() > 0) {
            //* remove from smallest
            Vertex target = fringe.poll();
            if (target.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                break;
            }
            /** relax */
            // p = e.from(), q = e.to(), w = e.weight()
            for (WeightedEdge<Vertex> e: input.neighbors(target)) {
                Vertex p = e.from();
                Vertex q = e.to();
                double w = e.weight();

                // if distTo[p] + w < distTo[q]:
                if (distTo.get(p) + w < distTo.getOrDefault(q, Double.POSITIVE_INFINITY)) {
                    // distTo[q] = distTo[p] + w
                    distTo.put(q, distTo.get(p) + w);
                    edgeTo.put(q, p);
                    double priorityValue = distTo.get(q) + input.estimatedDistanceToGoal(q, end);
                    // if q is in the PQ: PQ.changePriority(q, distTo[q] + h(q, goal))
                    if (fringe.contains(q)) {
                        fringe.changePriority(q, priorityValue);
                    } else {
                        // if q is not in PQ: PQ.insert(q, distTo[q] + h(q, goal))
                        fringe.insert(q, priorityValue);
                    }
                }
            }
            numStatesExplored ++;
            if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                break;
            }
        }

        if (outcome == SolverOutcome.SOLVED) {
            solutionWeight = distTo.get(end);
            Vertex v = end;
            while (! v.equals(start)) {
                solution.add(0, v);
                v = edgeTo.get(v);
            }
            solution.add(0, start);
        } else if (outcome != SolverOutcome.TIMEOUT) {
            outcome = SolverOutcome.UNSOLVABLE;
        }
        explorationTime = sw.elapsedTime();
    }

    public SolverOutcome outcome() {
        return outcome;
    }

    public List<Vertex> solution() {
        return solution;
    }

    public double solutionWeight() {
        return solutionWeight;
    }

    public int numStatesExplored() {
        return numStatesExplored;
    }

    public double explorationTime() {
        return explorationTime;
    }
}
