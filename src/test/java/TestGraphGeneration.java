import graphing.Graph;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TestGraphGeneration {
    Graph graph;

    @Test
    @Tag("fast")
    void TestNumberOfEdges() {
        graph = Graph.generate_random_Graph(1000,true, "");
        List<Set<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < graph.V; i++) {
            for (Integer j : graph.N.get(i)) {
                Set<Integer> tmp = new HashSet<>();
                tmp.add(i);
                tmp.add(j);
                if (!edges.contains(tmp)) {
                    edges.add(tmp);
                }
            }
        }
        assertEquals(edges.size(),
                graph.E,
                "testing whether the said number of edges is actually represented in the graph");
    }

    @Test
    @Tag("slow")
    void conn(){
        /*
        conn       = ∀u∀v(comp(u,v))
        comp(u,v)  = ∀X(X(u) ∧ closure(X)) → X(v)
        closure(C) = ∀y∀z(C(y) ∧ E(y,z)) → C(z)
         */
        graph = Graph.generate_random_Graph(8, false, "");
        List<List<Integer>> SubsetsOfA = new ArrayList<>(graph.V);
        for (int j = 0; j < (1 << graph.V); j++) {
            SubsetsOfA.add(new ArrayList<>());
            for (int i = 0; i < graph.V; i++)
                if ((j & (1 << i)) > 0)
                    SubsetsOfA.get(j).add(i);
        }
        for(int u = 0; u < graph.V; u++){
            for(int v = 0; v < graph.V; v++) {
                assertTrue(test_comp(u, v, SubsetsOfA, graph));
            }
        }
    }

    boolean test_comp(int u, int v, List<List<Integer>> SubsetsOfA, Graph graph){
        // comp(u,v)
        for(List<Integer> X : SubsetsOfA){
            // if the list is empty, it shouldn't matter
            if(X.contains(u) && test_closure(X, graph)) {
                if (!X.contains(v)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean test_closure(List<Integer> C, Graph graph){
        // closure(C)
        for(int y = 0; y < graph.V; y++){
            for(int z = 0; z < graph.V; z++){
                if(C.contains(y) && graph.N.get(y).contains(z))
                    if(!C.contains(z)){
                        return false;
                    }
            }
        }
        return true;
    }
}
