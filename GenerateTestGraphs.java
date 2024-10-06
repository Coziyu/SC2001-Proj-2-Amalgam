import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class GenerateTestGraphs {
    public static void generateTestGraphs(int V, int E, String fileName) {
        try {
            MatrixFileHandler.appendMatrices(fileName, Utils.generateRandomConnectedGraph(V, E), V, E);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static void main(String[] args) {
        // Check if file exists, and prompt overwrite
        File file = new File("SmallSparseGraphs.dat");
        Scanner scanner = new Scanner(System.in);
        if (file.exists()) {
            System.out.println("File SmallSparseGraphs.dat already exists. Overwrite? (y/n)");
            String response = scanner.nextLine();
            if (response.equals("y")) {
                file.delete();
            }
        }
        file = new File("SmallDenseGraphs.dat");
        if (file.exists()) {
            System.out.println("File SmallDenseGraphs.dat already exists. Overwrite? (y/n)");
            String response = scanner.nextLine();
            if (response.equals("y")) {
                file.delete();
            }
        }
        file = new File("LargeSparseGraphs.dat");
        if (file.exists()) {
            System.out.println("File LargeSparseGraphs.dat already exists. Overwrite? (y/n)");
            String response = scanner.nextLine();
            if (response.equals("y")) {
                file.delete();
            }
        }
        file = new File("LargeDenseGraphs.dat");
        if (file.exists()) {
            System.out.println("File LargeDenseGraphs.dat already exists. Overwrite? (y/n)");
            String response = scanner.nextLine();
            if (response.equals("y")) {
                file.delete();
            }
        }
        file = new File("VeryLargeSparseGraphs.dat");
        if (file.exists()) {
            System.out.println("File VeryLargeSparseGraphs.dat already exists. Overwrite? (y/n)");
            String response = scanner.nextLine();
            if (response.equals("y")) {
                file.delete();
            }
        }
        file = new File("VeryLargeDenseGraphs.dat");
        if (file.exists()) {
            System.out.println("File VeryLargeDenseGraphs.dat already exists. Overwrite? (y/n)");
            String response = scanner.nextLine();
            if (response.equals("y")) {
                file.delete();
            }
        }


        Random rand = new Random();
        // Generate Small Sparse Graphs, and Dense Graphs
        // V = 10, 11, ..., 500; E proportional V and E proportional to V^2, 1 sample
        System.out.println("Generating Small Sparse Graphs...");
        for (int V = 10; V <= 500; V += 1) {
            int E = V - 1 + rand.nextInt(2*V + 1); // Constant 2 is arbitrary, we just want need a linear relationship between number of vertices and edges
            generateTestGraphs(V, E, "SmallSparseGraphs.dat");

        }
        System.out.println("Generating Small Dense Graphs...");
        for (int V = 10; V <= 500; V += 1) {
            int E = V * (V - 1) / 2 - rand.nextInt(2*V + 1);
            generateTestGraphs(V, E, "SmallDenseGraphs.dat");
        }
        
        // Generate Large Sparse Graphs, and Dense Graphs
        // V = 1000, 2000, ..., 10000; 1 sample // 1.5gb
        System.out.println("Generating Large Sparse Graphs, this may take a while...");
        for (int V = 1000; V <= 10000; V += 1000) {
            int E = V - 1 + rand.nextInt(2*V + 1); 
            generateTestGraphs(V, E, "LargeSparseGraphs.dat");
        }

        System.out.println("Generating Large Dense Graphs, this will take even longer...");
        for (int V = 1000; V <= 10000; V += 1000) {
            int E = V * (V - 1) / 2 - rand.nextInt(2*V + 1);
            generateTestGraphs(V, E, "LargeDenseGraphs.dat");
        }
        
        // // Generate Very Large Sparse Graphs, and Dense Graphs
        {
            System.out.println("Generating Very Large Sparse Graphs, this may take a while...");
            int V = 30000;
            int E = V - 1 + rand.nextInt(2*V + 1);
            generateTestGraphs(V, E, "VeryLargeSparseGraphs.dat");
            
            System.out.println("Generating Very Large Dense Graphs, this will take even longer...");
            E = V * (V - 1) / 2 - rand.nextInt(2*V + 1);
            generateTestGraphs(V, E, "VeryLargeDenseGraphs.dat");
        }

        System.out.println("Graphs generated successfully.");
        scanner.close();
    }
}
