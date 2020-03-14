import java.util.*;

public class MinPathSum {

    private int[][] grid;

    private int rows;

    private int cols;

    private Map<Coord, Node> nodes;

    public int minPathSum(int[][] grid) {

        if (grid == null || grid.length == 0) {
            return 0;
        }

        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
        nodes = new HashMap<>();

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(getRoot(grid));

        while (!pq.isEmpty()) {
            Node min = pq.poll();

            if (min.found()) {
                return min.sum;
            }

            if (min.coord.col != cols - 1) {
                evaluateNode(pq, min, min.coord.row, min.coord.col + 1);
            }
            if (min.coord.row != rows - 1) {
                evaluateNode(pq, min, min.coord.row + 1, min.coord.col);
            }
        }

        return 0;
    }

    private void evaluateNode(PriorityQueue<Node> pq, Node parent, int row, int col) {
        Coord nodeCoord = new Coord(row, col);
        Node node = nodes.get(nodeCoord);
        int rightValue = grid[row][col];
        if (node == null) {
            Node newNode = new Node();
            newNode.coord = nodeCoord;
            newNode.sum = parent.sum + rightValue;
            newNode.prevParent = parent;
            nodes.put(nodeCoord, newNode);
            pq.add(newNode);
        } else if(parent.sum < node.prevParent.sum){
            pq.remove(node);
            node.sum = parent.sum + rightValue;
            pq.add(node);
        }
    }

    private Node getRoot(int[][] grid) {
        Node root = new Node();
        root.coord = new Coord(0, 0);
        root.sum = grid[0][0];
        root.prevParent = null;
        return root;
    }

    private static class Coord {

        int row;

        int col;

        Coord(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return row == coord.row &&
                    col == coord.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    private class Node implements Comparable<Node> {

        private int sum;

        private Coord coord;

        private Node prevParent;

        boolean found() {
            return this.coord.row == rows - 1 && this.coord.col == cols - 1;
        }

        @Override
        public int compareTo(Node node) {
            return sum - node.sum;
        }
    }

    public static void main(String[] args) {

        MinPathSum minPathSum = new MinPathSum();

        int[][] grid = new int[][]{
                new int[]{1, 3, 1},
                new int[]{1, 5, 1},
                new int[]{4, 2, 1}
        };

        System.out.println(minPathSum.minPathSum(grid));
    }

}
