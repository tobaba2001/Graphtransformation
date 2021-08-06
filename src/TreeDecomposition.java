import java.util.ArrayList;
import java.util.List;

public class TreeDecomposition {
    /* TODO: find T such as T = (T, ß) where ß is a mapping ß: V(T) -> 2^(V(G))
     * ß^(-1)(v in V(G)) := {t in V(T): v in ß(t)} != emptyset
     * ß(t) are the Bags (Taschen) of the treedecomposition
     * for every edge in the graph there has to be a vertex t so that the edge is a subset of ß(t) [the bag of that vertex]
     */
    public static void main(String[] args) {
        Graph G = Graph.generate_random_Graph(100);
        List<List<Integer>> bags = new ArrayList<>();
    }
}
