package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.UnionBySizeCompressingDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 *
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        //return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        if (graph.allVertices().size() == 0 || graph.allEdges().size() == 0) {
            return new MinimumSpanningTree.Success<>();
        }
        // Here's some code to get you started; feel free to change or rearrange it if you'd like.
        Collection<E> mst = new ArrayList<>();

        DisjointSets<V> disjointSets = createDisjointSets();

        // sort edges in the graph in ascending weight order
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));

        for (V vert : graph.allVertices()) {
            disjointSets.makeSet(vert);
        }
        int mstSize = graph.allVertices().size() - 1;
        for (E edge : edges) {
            if (disjointSets.findSet(edge.from()) != disjointSets.findSet(edge.to())) {
                mst.add(edge);
                disjointSets.union(edge.from(), edge.to());
            }
            if (mst.size() == mstSize) {
                return new MinimumSpanningTree.Success<>(mst);
            }
        }
        return new MinimumSpanningTree.Failure<>();
    }
}
