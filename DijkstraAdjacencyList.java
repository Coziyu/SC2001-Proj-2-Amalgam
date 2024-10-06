import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

// By QiEn
public class DijkstraAdjacencyList {
    
    public static long dijkstra(Graph graph , int startVert) {
        long comparisons = 0;
        int numOfVert = graph.numOfVert;
        int[] dist = new int[numOfVert];
        boolean[] visited = new boolean[numOfVert];
        
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[startVert] = 0;
        
        PriorityQueue<Node> pq = new PriorityQueue<>(numOfVert);
        pq.offer(new Node(startVert,0));
        
        while(!pq.isEmpty()) {
            Node currentNode = pq.poll();
            int u = currentNode.vert;
            
            if (visited[u]) continue;
            visited[u] = true;
            
            for(Edge neighbor : graph.adjList[u]) {
                int v = neighbor.getVertex();
                int weight = neighbor.getWeight();
                
                if(dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.offer(new Node(v,dist[v]));
                    
                    comparisons++;
                }
            }
        }
        return comparisons;
    }
}

class Edge {
    int vert, weight;
    
    public Edge(int vert, int weight) {
        this.vert = vert;
        this.weight = weight;
    }
    
    public int getVertex() {
        return vert;
    }
    
    public int getWeight() {
        return weight;
    }
    
}

class Graph {
    int numOfVert;
    ArrayList<Edge>[] adjList;
    
    @SuppressWarnings("unchecked")
    public Graph(int numOfVert) {
        this.numOfVert = numOfVert;
        adjList = new ArrayList[numOfVert];
        for(int i = 0;i<numOfVert;i++) {
            adjList[i] = new ArrayList<>();
        }
    }
    
    public void addEdge(int src, int dest, int weight) {
        adjList[src].add(new Edge(dest,weight));
    }
    
}
    
class Node implements Comparable<Node>{
    int vert;
    int dist;
    
    public Node(int vert, int dist) {
        this.vert = vert;
        this.dist = dist;
    }
    
    
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.dist, other.dist);
    }
    
}

