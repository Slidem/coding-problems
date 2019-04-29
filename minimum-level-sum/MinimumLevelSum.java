import java.util.*;

/**
 * @author Alexandru Mihai
 */
public class MinimumLevelSum {

    public static void main(String[] args) {
        Node root = new Node();

        root.left = new Node();
        root.right = new Node();
        root.left.left = new Node();
        root.left.right = new Node();
        root.left.left.left = new Node();
        root.left.right = new Node();
        root.left.right.left = new Node();
        root.left.right.right = new Node();
        root.right.right = new Node();

        root.value = 22;
        root.left.value = 3;
        root.right.value = 7;
        root.right.right.value = 9;
        root.left.left.value = 2;
        root.left.right.value = 4;
        root.left.left.left.value = 10;
        root.left.right.left.value = 1;
        root.left.right.right.value = 2;

        LevelSum result = getMinimumSumLevelBFS(root);
        result.printLevelSums();
        result.printMinimumSumLevel();
    }

    // -------------------------------- BFS IMPLEMENTATION

    public static LevelSum getMinimumSumLevelBFS(Node root){
        Deque<Node> nodes = new LinkedList<>();
        root.level = 1;
        nodes.push(root);
        LevelSum levelSum = new LevelSum();
        while(!nodes.isEmpty()){
            Node n = nodes.pop();
            levelSum.add(n.level, n.value);
            addNode(nodes, n.left, n.level + 1);
            addNode(nodes, n.right, n.level + 1);
        }
        return levelSum;
    }

    private static void addNode(Deque<Node> nodes, Node n, int level){
        if(n != null){
            n.level = level;
            nodes.push(n);
        }
    }

    // -------------------------------- DFS IMPLEMENTATION

    public static LevelSum getMinimumSumLevelDFS(Node root){
        if(root == null){
            throw new IllegalArgumentException();
        }
        LevelSum levelSum = new LevelSum();
        traverseTree(root, 1, levelSum);
        return levelSum;
    }

    private static void traverseTree(Node n, int currentLevel, LevelSum levelSum){
        if(n == null){
            return;
        }
        levelSum.add(currentLevel, n.value);
        traverseTree(n.left, currentLevel + 1, levelSum);
        traverseTree(n.right, currentLevel + 1, levelSum);
    }

    static class Node {

        int value;

        int level;

        Node left;

        Node right;
    }

    static class LevelSum {

        Map<Integer, Integer> levelSum;

        public LevelSum() {
            this.levelSum = new HashMap<>();
        }

        void add(int level, int value){
            this.levelSum.compute(level, (lvl, sum) -> {
                if(sum == null){
                    return value;
                }
                return sum + value;
            });
        }

        void printLevelSums(){
            System.out.println(levelSum);
        }

        void printMinimumSumLevel(){
            int min = 1;
            for(Map.Entry<Integer, Integer> lvlSumEntry : levelSum.entrySet()){
                int lvl = lvlSumEntry.getKey();
                int sum = lvlSumEntry.getValue();
                if(sum < levelSum.get(min)){
                    min = lvl;
                }
            }
            System.out.println(min);
        }
    }

}

