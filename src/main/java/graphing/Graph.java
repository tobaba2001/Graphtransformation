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

    public static void generate_file(Graph G) {
        String filename = "generated_graphs/" + "Graph-" + System.currentTimeMillis() + ".txt";
        Path path = Paths.get(filename);
        try {
            Files.writeString(path, G.toString(), Charset.defaultCharset(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.out.println("Could not save graph.\n");
            e.printStackTrace();
        }
        System.out.println("Graph saved to: " + path);
    }

    /* public static Graph generate_random_Graph(int MaxNumOfVertices, boolean generateFile) {
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
            N.get(b).add(a);
        }
        Graph G = new Graph(V, E, N);
        if (generateFile) {
            generate_file(G);
        }
        // NOTE: N does not give us the edges but rather the relation of Neighbourhood between vertices.
        return G;
    } */

    public static Graph generate_random_Graph(int MaxNumOfVertices, String Params) {
        return generate_random_Graph(MaxNumOfVertices, false, Params);
    }

    public static Graph generate_random_Graph(int MaxNumOfVertices, boolean generateFile, String Params) {
        Random random = new Random();
        int V = random.nextInt(MaxNumOfVertices) + 1;
        if (V <= 2)
            V = 3;
        int E = 0;
        List<List<Integer>> N = new ArrayList<>(V);
        switch (Params) {
            case "connected":
                // in a connected graph, the minimal number of Edges is n-1 (Path)
                E = random.nextInt(((V * (V - 1) / 2) - (V - 1))) + (V - 1);
                int EdgeCounter = E;
                List<List<Integer>> ConnectedComponents = new ArrayList<>(V);
                for (int i = 0; i < V; i++) {
                    N.add(new ArrayList<>());
                    ConnectedComponents.add(new ArrayList<>());
                    ConnectedComponents.get(i).add(i);
                }
                List<Integer> MainComponent = ConnectedComponents.get(0);
                while (MainComponent.size() != V) {
                    int VertexToBeConnected = MainComponent.get(random.nextInt(MainComponent.size()));
                    int index = random.nextInt((ConnectedComponents.size() - 1)) + 1;
                    List<Integer> RandomList = ConnectedComponents.get(index);
                    MainComponent.add(RandomList.get(0));
                    N.get(RandomList.get(0)).add(VertexToBeConnected);
                    N.get(VertexToBeConnected).add(RandomList.get(0));
                    ConnectedComponents.remove(index);
                    EdgeCounter--;
                }
                if (EdgeCounter > 0) {
                    for (int i = 0; i < EdgeCounter; i++) {
                        int a = random.nextInt(V);
                        int b = random.nextInt(V);
                        if ((N.get(a).contains(b)) || (a == b)) {
                            // System.out.println("going back because the edge {" + a + ", " + b + "} already exists");
                            i--;
                            continue;
                        }
                        N.get(a).add(b);
                        // we can probably still ignore that this would give us a directed graph as we refuse to count the edges twice anyway
                        N.get(b).add(a);
                    }
                }
                break;
            default:
                E = random.nextInt(V * (V - 1) / 2);
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
                    N.get(b).add(a);
                }
                break;
        }
        Graph G = new Graph(V, E, N);
        if (generateFile) {
            generate_file(G);
        }
        // NOTE: N does not give us the edges but rather the relation of Neighbourhood between vertices.
        return G;
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

    public static void main(String[] args) {
        Graph G = generate_random_Graph(100, true, "connected");
    }
}
