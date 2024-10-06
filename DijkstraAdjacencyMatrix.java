import java.util.Arrays;

// By Halley
public class DijkstraAdjacencyMatrix {

    private static final int INF = Integer.MAX_VALUE;

    public static long dijkstra(int[][] graph, int src) {
        int V = graph.length;                                                   // Number of vertices in graph
        int[] dist = new int[V];                                                // dist[i] holds shortest distance from node src to node i
        boolean[] visited = new boolean[V];                                     // visited[i] == true if node i is inside shortest path tree

        Arrays.fill(dist, INF);                                                 // Initialises dist from node src to all nodes to INF
        dist[src] = 0;                                                          // Distance of node src to itself is always 0 by definition
        long comparisons = 0;

        // Finding shortest path for all vertices
        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, visited);                                 // Find min. distance vertex from unvisited vertices
            visited[u] = true;                                                  // Mark min. distance vertex as visited

            for (int v = 0; v < V; v++) {
                if (!visited[v]                                                 // If v is unvisited
                        && graph[u][v] != 0                                     // and there exists edge between u and v,
                        && dist[u] != INF && dist[u] + graph[u][v] < dist[v]    // and distance through u is smaller than dist[v],
                    )
                {         
                    dist[v] = dist[u] + graph[u][v];                            // then update dist[v]
                    comparisons++;
                }
            }
        }

        return comparisons;
    }

    // Utility function to find vertex with min. distance value from set of vertices not yet included in shortest path tree
    private static int minDistance(int[] dist, boolean[] visited) {
        int min = INF;
        int min_index = -1;

        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }

        return min_index;
    }

    // Utility function to print solution
//    private static void printSolution(int[] dist) {
//        System.out.println("Vertex \t distance from src");
//        for (int i = 0; i < dist.length; i++)
//            System.out.println(i + "\t\t" + dist[i]);
//    }
}
