import java.util.Random;
import java.util.ArrayList;

public class Utils {
    public static void saveAdjacencyListToFile(int[][] adjMatrix, String filename){
        
    }
    public static Graph adjacencyMatrixToGraph(int[][] adjMatrix){
        Graph graph = new Graph(adjMatrix.length);

        for(int i = 0; i < adjMatrix.length; i++){
            for(int j = 0; j < adjMatrix[i].length; j++){
                if(adjMatrix[i][j] != 0){
                    graph.addEdge(i, j, adjMatrix[i][j]); // Undirected
                    graph.addEdge(j, i, adjMatrix[i][j]);
                }
            }
        }

        return graph;
    }
    // This will return an adjacency matrix by default. Use other method to change to adj list.
    public static int[][] generateRandomConnectedGraph(int V, int E){
        // Clamp E to be at least V - 1, and at most V * (V - 1) / 2

        E = Math.max(E, V - 1);
        E = Math.min(E, V * (V - 1) / 2);

        Random rand = new Random();
        int[][] graph = new int[V][V];

        // Generate a minimum spanning tree first.
        // Use prufer sequence to generate a MST structure
        ArrayList<Integer> pruferSeq = new ArrayList<Integer>(); // Elements must be from 0 to V - 1
        boolean[] isUsed = new boolean[V];
        for(int i = 0; i < V - 2; i++){
            pruferSeq.add(rand.nextInt(V));
        }
        
        for(int j = 0; j < V - 2; j++){
            // Find smallest unused element that is not in prufer
            int L = -1;
            for(int i = 0; i < V; i++){
                if(!isUsed[i] && !pruferSeq.contains(i)){
                    isUsed[i] = true;
                    L = i;
                    break;
                }
            }
            // Connect prufer[j] and L with a random weight
            int rWeight = rand.nextInt(100) + 1;
            graph[pruferSeq.get(0)][L] = rWeight;
            graph[L][pruferSeq.get(0)] = rWeight;
            pruferSeq.remove(0);
        }

        // Add the remaining unconnected vertices to the MST
        {
            int i = 0;
            for(; i < V; i++){
                if(!isUsed[i]){
                    isUsed[i] = true;
                    break;
                }
            }
            int j = i + 1;
            for(; j < V; j++){
                if(!isUsed[j]){
                    isUsed[j] = true;
                    break;
                }
            }
            int rWeight = rand.nextInt(100) + 1;
            graph[i][j] = rWeight;
            graph[j][i] = rWeight;
        }

        // Add the remaining edges this way if E is "small"
        int remainingEdges = E - (V - 1);
        if (remainingEdges < 10*V){
            for(int i = 0; i < remainingEdges; i++){
                int rVertex1;
                int rVertex2;
                do{
                    rVertex1 = rand.nextInt(V);
                    rVertex2 = rand.nextInt(V);
                }while(rVertex1 == rVertex2 || graph[rVertex1][rVertex2] != 0);
    
                int rWeight = rand.nextInt(100) + 1;
                graph[rVertex1][rVertex2] = rWeight;
                graph[rVertex2][rVertex1] = rWeight;
            }
        }
        else{
            // Perform Fisher-Yates shuffle on all remaining edge pairs and take the first remainingEdges
            int[] edgePairs = new int[remainingEdges];
            int k = 0;
            for(int i = 0; i < V && k < remainingEdges; i++){
                for(int j = i + 1; j < V && k < remainingEdges; j++){
                    if(graph[i][j] == 0){
                        edgePairs[k] = i * V + j;
                        k++;
                    }
                }
            }
            FisherYatesShuffle(edgePairs);
            for(int i = 0; i < remainingEdges; i++){
                int rVertex1 = edgePairs[i] / V;
                int rVertex2 = edgePairs[i] % V;
                int rWeight = rand.nextInt(100) + 1;
                graph[rVertex1][rVertex2] = rWeight;
                graph[rVertex2][rVertex1] = rWeight;
            }
        }

        return graph;
    }

    public static void FisherYatesShuffle(int[] array) {
        int n = array.length;
        Random random = new Random();
        // Loop over array.
        for (int i = 0; i < array.length; i++) {
            // Get a random index of the array past the current index.
            // ... The argument is an exclusive bound.
            //     It will not go past the array send.
            int randomValue = i + random.nextInt(n - i);
            // Swap the random element with the present element.
            int randomElement = array[randomValue];
            array[randomValue] = array[i];
            array[i] = randomElement;
        }
    }

    public static void printGraph(int[][] graph){
        for(int i = 0; i < graph.length; i++){
            for(int j = 0; j < graph[i].length; j++){
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean checkIfGraphConnected(int[][] graph){
        int V = graph.length;
        boolean[] visited = new boolean[V];
        connectedDFS(graph, 0, visited);
        for(int i = 0; i < V; i++){
            if(!visited[i]){
                return false;
            }
        }
        return true;
    }

    private static void connectedDFS(int[][] graph, int src, boolean[] visited){
        visited[src] = true;
        for(int i = 0; i < graph.length; i++){
            if(graph[src][i] != 0 && !visited[i]){
                connectedDFS(graph, i, visited);
            }
        }
    }
}
