import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;


public class MatrixFileHandler {
    // Write matrices to a data file
    public static void appendMatrices(String fileName, int[][] matrix, int V, int E) throws IOException {

        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName, true)))) {
            int rows = matrix.length;
            int cols = matrix[0].length;
            dos.writeInt(V);  // Write number of vertices
            dos.writeInt(E);  // Write number of edges
            dos.writeInt(rows);  // Write number of rows
            dos.writeInt(cols);  // Write number of columns
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    dos.writeInt(matrix[i][j]);  // Write matrix elements
                }
            }
        }
    }

    // Reads the next matrix from a data file
    public static MatrixData readNextMatrix(DataInputStream dis) throws IOException {
        int V = dis.readInt();  // Read number of vertices
        int E = dis.readInt();  // Read number of edges
        int rows = dis.readInt();  // Read number of rows
        int cols = dis.readInt();  // Read number of columns
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = dis.readInt();  // Read matrix elements
            }
        }
        return new MatrixData(matrix, V, E);
    }

    public static void main(String[] args) throws IOException {
        // Example: Array of 2 matrices
        int[][] matrix1 = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9} };
        int[][] matrix2 = { {7, 8}, {9, 10}};

        appendMatrices("matrices.dat", matrix2, 2, 4);  // Store matrices in the file
        appendMatrices("matrices.dat", matrix1, 3, 9);  // Store matrices in the file

        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream("matrices.dat")))) {
            while (dis.available() > 0) {
                MatrixData t = readNextMatrix(dis);
                int[][] matrix = t.matrix;
                System.out.println("Vertices: " + t.vertices + " Edges: " + t.edges);
                // Process or print matrix as needed
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        System.out.print(matrix[i][j] + " ");
                    }
                    System.out.println();
                }
                System.out.println("----");
            }
        }
    }
}
