/**
 * @author Alexandru Mihai
 */
public class SubtreeFinder {

    private Node treeRoot;

    private Node subtreeRoot;

    private boolean found;

    SubtreeFinder(Node treeRoot, Node subtreeRoot) {
        this.treeRoot = treeRoot;
        this.subtreeRoot = subtreeRoot;
        search();
    }

    public boolean found(){
        return found;
    }

    private void search() {
        found = search(treeRoot, subtreeRoot) == Result.FOUND;
    }

    private Result search(Node treeNode, Node subtreeNode) {
        if(treeNode == null && subtreeNode == null){
            return Result.VALID;
        }

        if(treeNode == null){
            return Result.INVALID;
        }

        boolean currentNodeValid = true;
        Result left;
        Result right;
        if(subtreeNode == null || treeNode.value != subtreeNode.value){
            left = search(treeNode.left, subtreeRoot);
            right = search(treeNode.right, subtreeRoot);
            currentNodeValid = false;
        } else {
            left = search(treeNode.left, subtreeNode.left);
            right = search(treeNode.right, subtreeNode.right);
        }

        if(left == Result.FOUND || right == Result.FOUND){
            return Result.FOUND;
        }

        if(left == Result.VALID && right == Result.VALID && currentNodeValid){

            if(subtreeNode == subtreeRoot){
                return Result.FOUND;
            } else {
                return Result.VALID;
            }

        } else {
            return Result.INVALID;
        }
    }

    // ------------------------ HELPERS

    static class Node {

        int value;

        Node left;

        Node right;

    }

    enum Result {
        VALID, INVALID, FOUND
    }

    // ------------------------- TEST

    public static void main(String[] args) {

        Node tree = new Node();
        tree.value = 1;
        tree.left = new Node();
        tree.right = new Node();
        tree.left.value = 2;
        tree.right.value = 4;
        tree.right.right = new Node();
        tree.right.left = new Node();
        tree.right.right.value = 7;
        tree.right.left.value = 10;

        Node subtree = new Node();
        subtree.value = 4;
        subtree.left = new Node();
        subtree.right = new Node();
        subtree.right.value = 7;
        subtree.left.value = 10;

        System.out.println(new SubtreeFinder(tree, subtree).found);
    }

}

