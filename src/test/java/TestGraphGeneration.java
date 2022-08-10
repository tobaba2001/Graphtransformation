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
    void TestConnected() {
        graph = Graph.generate_random_Graph(8, true, "connected");
        List<List<Integer>> SubsetsOfA = new ArrayList<>(graph.V);
        for (int j = 0; j < (1 << graph.V); j++) {
            SubsetsOfA.add(new ArrayList<>());
            for (int i = 0; i < graph.V; i++)
                if ((j & (1 << i)) > 0)
                    SubsetsOfA.get(j).add(i);
        }
        SubsetsOfA.remove(0);

        for (int i = 0; i < graph.V; i++) {
            for (int j = 0; j < graph.V; j++) {
                if (i == j)
                    continue;
                for (List<Integer> X : SubsetsOfA) {
                    System.out.println("Looking for closure regarding E in Subset X: " + X);
                    boolean isclosure = X.size() == 1;
                    if(isclosure)
                        continue;
                    for (int k = 0; k < X.size(); k++) {
                        for (int l = 0; l < X.size(); l++) {
                            if (k == l)
                                continue;
                            for (int m = 0; m < graph.N.get(k).size(); m++) {
                                if (graph.N.get(k).get(m) == l) {
                                    isclosure = true;
                                    break;
                                }
                            }
                        }
                    }
                    // Dies würde nur die Prämisse der Implikation prüfen.
                    assertTrue(isclosure);
                }
            }
        }
    }
    @Test
    @Tag("slow")
    void TestConnected2() {
        graph = Graph.generate_random_Graph(8, true, "");
        List<List<Integer>> SubsetsOfA = new ArrayList<>(graph.V);
        boolean cl = false;
        boolean component= false;
        int clcounter = 0;

        for (int j = 0; j < (1 << graph.V); j++) {
            SubsetsOfA.add(new ArrayList<>());
            for (int i = 0; i < graph.V; i++)
                if ((j & (1 << i)) > 0)
                    SubsetsOfA.get(j).add(i);
        }
        SubsetsOfA.remove(0);
        for(int u = 0; u < graph.V; u++) {
            for(int v = 0; v < graph.V; v++) {
                for (List<Integer> X : SubsetsOfA) {
                    System.out.println("Looking for closure regarding E in Subset X: " + X);
                    for (int y = 0; y < graph.V; y++) {
                        for (int z = 0; z < graph.V; z++) {
                            if (!(X.contains(u) && graph.N.get(y).contains(z)) || X.contains(z)) {
                                clcounter++;
                            }
                        }
                    }
                    if(clcounter == graph.V)
                        cl = true;
                    if((!(X.contains(u) && cl)) || X.contains(v))
                        component = true;
                    else
                        System.out.println("here component");
                    assertTrue(component);
                    cl = false;
                    component = false;
                }
            }
        }
    }
}
