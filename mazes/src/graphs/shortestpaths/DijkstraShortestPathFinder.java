package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 *
 * @see ShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    implements ShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
        You'll also need to change the part of the class declaration that says
        `ArrayHeapMinPQ<T extends Comparable<T>>` to `ArrayHeapMinPQ<T>`.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    public ShortestPath<V, E> findShortestPath(G graph, V start, V end) {
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }
        Map<V, E> edgeToV = new HashMap<>();
        Map<V, Double> distToV = new HashMap<>();
        DoubleMapMinPQ<V> orderedPerimeter = new DoubleMapMinPQ<>();
        orderedPerimeter.add(start, 0.0);
        distToV.put(start, 0.0);
        while (!orderedPerimeter.isEmpty()) {
            V fromVertex = orderedPerimeter.removeMin();
            if (fromVertex.equals(end)) {
                return new ShortestPath.Success<>(
                    createShortestPath(edgeToV, start, end, new ArrayList<>()));
            }
            for (E edge : graph.outgoingEdgesFrom(fromVertex)) {
                V toVertex = edge.to();
                double newDist = distToV.get(fromVertex) + edge.weight();
                if (!distToV.containsKey(toVertex) || newDist < distToV.get(toVertex)) {
                    edgeToV.put(toVertex, edge);
                    distToV.put(toVertex, newDist);
                    if (orderedPerimeter.contains(toVertex)) {
                        orderedPerimeter.changePriority(toVertex, newDist);
                    } else {
                        orderedPerimeter.add(toVertex, newDist);
                    }
                }
            }
        }
        return new ShortestPath.Failure<>();
    }

    private List<E> createShortestPath(Map<V, E> edgeToV, V start, V vertex, List<E> shortestPath) {
        if (vertex.equals(start)) {
            return shortestPath;
        } else {
            E edge = edgeToV.get(vertex);
            vertex = edge.from();
            shortestPath = createShortestPath(edgeToV, start, vertex, shortestPath);
            shortestPath.add(edge);
            return shortestPath;
        }
    }

}
