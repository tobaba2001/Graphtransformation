import graphing.Graph;

import org.junit.jupiter.api.BeforeEach;
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
        graph = Graph.generate_random_Graph(100, "");
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
    void TestConnected() {
        graph = Graph.generate_random_Graph(10, "connected");
        List<List<Integer>> SubsetsOfA = new ArrayList<>(graph.V);
        for (int j = 0; j < (1 << graph.V); j++) {
            SubsetsOfA.add(new ArrayList<>());
            for (int i = 0; i < graph.V; i++)
                if ((j & (1 << i)) > 0)
                    SubsetsOfA.get(j).add(i);
        }
        SubsetsOfA.remove(0);
        //assertEquals(SubsetsOfA.size(), Math.pow(2, graph.V) - 1);
        boolean isSatisfied = false;
        for (List<Integer> A : SubsetsOfA) {
            isSatisfied = false;
            System.out.println("A: " + A);
            for (List<Integer> B : SubsetsOfA) {
                System.out.println("B: " + B);
                Set<Integer> Atmp = new HashSet<>(A);
                Set<Integer> Btmp = new HashSet<>(B);
                if (Atmp.containsAll(Btmp) || Btmp.containsAll(Atmp)) {
                    isSatisfied = true;
                    continue;
                }
                for (Integer x : A) {
                    for (Integer y : B) {
                        if (graph.N.get(x).contains(y)) {
                            isSatisfied = true;
                            break;
                        }
                    }
                }
            }
            assertTrue(isSatisfied,
                    "Testing whether the condition is satisfied, that both components have an edge");
        }
    }
}
