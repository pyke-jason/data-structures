package seamcarving;

import graphs.Edge;
import graphs.Graph;
import graphs.shortestpaths.DijkstraShortestPathFinder;
import graphs.shortestpaths.ShortestPath;
import graphs.shortestpaths.ShortestPathFinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DijkstraSeamFinder implements SeamFinder {
    private final ShortestPathFinder<SeamGraph, RelativeVertex, Edge<RelativeVertex>> pathFinder;

    public DijkstraSeamFinder() {
        this.pathFinder = createPathFinder();
    }

    protected <G extends Graph<V, Edge<V>>, V> ShortestPathFinder<G, V, Edge<V>> createPathFinder() {
        /*
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
        */
        return new DijkstraShortestPathFinder<>();
    }

    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        return findSeam(energies, false, energies.length);
    }

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        return findSeam(energies, true, energies[0].length);
    }

    private List<Integer> findSeam(double[][] energies, boolean vertical, int length) {
        List<Integer> result = new LinkedList<>();
        SeamGraph graph = new SeamGraph(energies, vertical);
        RelativeVertex start = new RelativeVertex(vertical, 0, -1);
        RelativeVertex end = new RelativeVertex(vertical, 0, length);
        ShortestPath<RelativeVertex, Edge<RelativeVertex>> res = pathFinder.findShortestPath(graph, start, end);
        if (!res.exists()) {
            return new ArrayList<>();
        }
        for (RelativeVertex rv : res.vertices()) {
            if (rv.isSentinel(length)) {
                continue;
            }
            if (rv.forward > result.size() - 1) {
                result.add(rv.lateral);
            } else {
                result.add(rv.forward, rv.lateral);
            }
        }
        return result;
    }

    public class SeamGraph implements Graph<RelativeVertex, Edge<RelativeVertex>> {
        private double[][] energies;
        private int length;
        private int width;
        private boolean vertical;

        public SeamGraph(double[][] energies, boolean vertical) {
            this.energies = energies;
            this.vertical = vertical;
            length = vertical ? energies[0].length : energies.length;
            width = vertical ? energies.length : energies[0].length;
        }

        @Override
        public Collection<Edge<RelativeVertex>> outgoingEdgesFrom(RelativeVertex vert) {
            Set<Edge<RelativeVertex>> res = new HashSet<>();
            if (vert.forward < 0) {
                for (int i = 0; i < width; i++) {
                    double energy = vertical ? energies[i][0] : energies[0][i];
                    res.add(new Edge<>(vert, new RelativeVertex(vertical, i, 0), energy));
                }
            } else if (vert.forward == length - 1) {
                res.add(new Edge<>(vert, new RelativeVertex(vertical, 0, length), 0));
            } else {
                for (int i = -1; i <= 1; i++) {
                    int x = vert.getX(i, 1);
                    int y = vert.getY(i, 1);
                    if (x >= 0 && y >= 0 && x < energies.length && y < energies[0].length) {
                        RelativeVertex adjacentVert = new RelativeVertex(vertical, vert.lateral + i, vert.forward + 1);
                        res.add(new Edge<>(vert, adjacentVert, energies[x][y]));
                    }
                }
            }
            return res;
        }
    }

    public class RelativeVertex {
        public int lateral;
        public int forward;
        public boolean vertical;

        public boolean isSentinel(int length) {
            return forward < 0 || forward == length;
        }

        public int getX() {
            return getX(0, 0);
        }

        public int getY() {
            return getY(0, 0);
        }

        public int getX(int lateralOffset, int forwardOffset) {
            return vertical ? lateral + lateralOffset : forward + forwardOffset;
        }

        public int getY(int lateralOffset, int forwardOffset) {
            return vertical ? forward + forwardOffset : lateral + lateralOffset;
        }

        public RelativeVertex(boolean vertical, int lateral, int forward) {
            this.vertical = vertical;
            this.lateral = lateral;
            this.forward = forward;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RelativeVertex that = (RelativeVertex) o;
            return lateral == that.lateral &&
                forward == that.forward &&
                vertical == that.vertical;
        }

        @Override
        public int hashCode() {
            return Objects.hash(lateral, forward, vertical);
        }
    }
}
