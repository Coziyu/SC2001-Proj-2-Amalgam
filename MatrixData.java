// Custom class to hold the matrix along with its metadata
class MatrixData {
    public int[][] matrix;  // The adjacency matrix
    public int vertices;    // Number of vertices
    public int edges;       // Number of edges

    public MatrixData(int[][] matrix, int vertices, int edges) {
        this.matrix = matrix;
        this.vertices = vertices;
        this.edges = edges;
    }
}
