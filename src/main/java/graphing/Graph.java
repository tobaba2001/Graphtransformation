package graphing;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Graph {
    public int V;
    public int E;
    public final List<List<Integer>> N;


    public Graph(int V, int E, List<List<Integer>> N) {
        this.V = V;
        this.E = E;
        this.N = N;
    }

    public static Graph generate_random_Graph(int MaxNumOfVertices, boolean generateFile) {
        Random random = new Random();
        int V = random.nextInt(MaxNumOfVertices) + 1;
        if (V <= 2)
            V = 3;
        // upper bound for edges in a graph with MaxNumOfVertices vertices:
        //      MaxNumOfVertices * ((MaxNumOfVertices - 1) / 2)
        int E = random.nextInt(V * (V - 1) / 2);
        List<List<Integer>> N = new ArrayList<>(V);
        System.out.println("generating " + V + " vertices...");
        for (int i = 0; i < V; i++)
            N.add(new ArrayList<>());
        System.out.println("generating " + E + " edges...");
        for (int i = 0; i < E; i++) {
            int a = random.nextInt(V);
            int b = random.nextInt(V);
            if ((N.get(a).contains(b)) || (a == b)) {
                // System.out.println("going back because the edge {" + a + ", " + b + "} already exists");
                i--;
                continue;
            }
            // add to both neighbourhood relations, as we want to have an undirected graph
            System.out.println("Adding edge {" + a + ", " + b + "}");
            N.get(a).add(b);
            // we can probably still ignore that this would give us a directed graph as we refuse to count the edges twice anyway
            N.get(b).add(a);
        }
        Graph G = new Graph(V, E, N);
        if (generateFile) {
            String filename = "Graph-" + System.currentTimeMillis() + ".txt";
            Path path = Paths.get(filename);
            try {
                Files.writeString(path, G.toString(), Charset.defaultCharset(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            } catch (IOException e) {
                System.out.println("Could not save graph.\n");
                e.printStackTrace();
            }
            System.out.println("Graph saved to: " + path);
        }
        // NOTE: N does not give us the edges but rather the relation of Neighbourhood between vertices.
        return G;
    }

    public static Graph generate_random_Graph(int i) {
        return generate_random_Graph(i, false);
    }

    @Override
    public String toString() {
        String finalString;
        StringBuilder builder = new StringBuilder();
        List<Set<Integer>> edges = new ArrayList<>();
        builder.append("[");
        for (int i = 0; i < V; i++) {
            builder.append(i);
            if (i != V - 1)
                builder.append(", ");
            List<Integer> IndexOfi = N.get(i);
            for (Integer integer : IndexOfi) {
                Set<Integer> tmp = new HashSet<>();
                tmp.add(i);
                tmp.add(integer);
                if (!edges.contains(tmp))
                    edges.add(tmp);
            }
        }
        builder.append("]\n[");
        for (int i = 0; i < edges.size(); i++) {
            builder.append(edges.get(i).toString());
            if (i != edges.size() - 1)
                builder.append(", ");
        }
        builder.append("]");
        finalString = builder.toString();
        return finalString;
    }
}
