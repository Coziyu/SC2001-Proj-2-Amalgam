import java.io.FileWriter;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class Benchmark {
    public static void main(String[] args) throws IOException {
        // For each dat file, create a csvWriter with the same name, but with a .csv extension
        // For each matrix in the dat file, read the matrix, and run the algorithm on the matrix
        // Write the results to the csv file
        // The csv file should have the following columns: vertices, edges, time(ms), comparisons
        
        // Run larger benchmarks first to make JIT warmup more consistent
        String[] dataFiles = {"LargeDenseGraphs.dat", "LargeSparseGraphs.dat", "SmallDenseGraphs.dat", "SmallSparseGraphs.dat"};
        for (String dataFile : dataFiles) {
            // Create a csv file with the same name as the dat file
            long startTime = System.nanoTime();
            FileWriter csvWriter = new FileWriter("out/" + dataFile.replace(".dat", ".csv"));
            csvWriter.write("implementation,vertices,edges,time(ns),comparisons\n");
            long endTime = System.nanoTime();
            
            // Read the matrices from the dat file
            try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream("data/" + dataFile)))) {
                while (dis.available() > 0) {
                    MatrixData t = MatrixFileHandler.readNextMatrix(dis);
                    int V = t.vertices;
                    int E = t.edges;
                    int[][] matrix = t.matrix;
                    Graph graph = Utils.adjacencyMatrixToGraph(matrix);
                    long comparisons = 0;
                    System.out.println("Vertices: " + V + " Edges: " + E);

                    // Dijkstra with Adjacency Matrix
                    startTime = System.nanoTime();
                    comparisons = DijkstraAdjacencyMatrix.dijkstra(matrix, 0);
                    endTime = System.nanoTime();

                    long timeElapsed = endTime - startTime;
                    // System.out.println("Execution time in nanoseconds: " + timeElapsed);
                    System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);

                    csvWriter.append("a," + V + "," + E + "," + timeElapsed + "," + comparisons + "\n");
                    // Dijkstra with Adjacency List
                    startTime = System.nanoTime();
                    comparisons = DijkstraAdjacencyList.dijkstra(graph, 0);
                    endTime = System.nanoTime();

                    timeElapsed = endTime - startTime;
                    // System.out.println("Execution time in nanoseconds: " + timeElapsed);
                    System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);

                    csvWriter.append("b," + V + "," + E + "," + timeElapsed + "," + comparisons + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            csvWriter.close();
        }
    }
}
