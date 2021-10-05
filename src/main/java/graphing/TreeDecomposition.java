package graphing;

import java.util.ArrayList;
import java.util.List;

public class TreeDecomposition {
    /* TODO: find T such as T = (T, ß) where ß is a mapping ß: V(T) -> 2^(V(G))
     * ß^(-1)(v in V(G)) := {t in V(T): v in ß(t)} != emptyset
     * ß(t) are the Bags (Taschen) of the treedecomposition
     * for every edge in the graph there has to be a vertex t so that the edge is a subset of ß(t) [the bag of that vertex]
     * Die Baumweite ist ein Maß der Ähnlichkeit eines Graphen gegenüber eines Baumes..
     * Wenn also G ein Baum ist, dann ist die Baumweite gleich 1
     * Baumweite einer Baumzerlegung:
     *
     *
     */

    public static Graph find_treedecomposition(Graph G, List<List<Integer>> bags){
        for(int i = 0; i < G.V; i++) {
            for (Integer j : G.N.get(i)){
                // put edges in bags...
                // It would be a td even if every edge was in the same bag. we're trying to find the td
                // with the smallest treewdidth
            }
        }
        return G;
    }
    public static void main(String[] args) {
        Graph G = Graph.generate_random_Graph(100, "");
        List<List<Integer>> bags = new ArrayList<>();
        find_treedecomposition(G, bags);
    }
}
