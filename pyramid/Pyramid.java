import java.time.Instant;
import java.util.Arrays;
import java.util.function.IntUnaryOperator;

/**
 * @author Alexandru Mihai
 */
public class Pyramid {

    private static final IntUnaryOperator GO_RIGHT = idx -> idx + 1;

    private static final IntUnaryOperator GO_LEFT = idx -> idx - 1;

    private int cost;

    private int minimumCostPeakIdx;

    private int minimumCostPeakHeight;

    private int[] input;

    public Pyramid(String input) {

        if (input.isEmpty()) {
            throw new IllegalArgumentException("String cannot be empty");
        }

        this.input = parseInput(input);
        System.out.println("Input successfuly parsed. Computing lowest cost for " + Arrays.toString(this.input));
        long start = Instant.now().toEpochMilli();
        findCost();
        long end = Instant.now().toEpochMilli();
        System.out.println("Cost computed in " + (end - start) + " ms");
    }

    public void printMinimumCost(){
        System.out.println("minimum cost: " + cost);
    }

    public void printMinimumCostPyramid(){
        int pyramidStartIdx = minimumCostPeakIdx - minimumCostPeakHeight + 1;
        int pyramidEndIdx = minimumCostPeakIdx + minimumCostPeakHeight;

        int i;
        System.out.println("input:   " + Arrays.toString(input));
        System.out.print("pyramid: [");
        for (i = 0; i < pyramidStartIdx; i++) {
            System.out.print("0, ");
        }

        int pyramidValue = 1;
        for (i = pyramidStartIdx; i < minimumCostPeakIdx; i++) {
            System.out.print(pyramidValue + ", ");
            pyramidValue++;
        }
        for (i = minimumCostPeakIdx; i < pyramidEndIdx; i++) {
            System.out.print(pyramidValue);
            if (i != input.length - 1) {
                System.out.print(", ");
            }
            pyramidValue--;
        }
        for (i = pyramidEndIdx; i < input.length; i++) {
            System.out.print("0");
            if (i != input.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
        System.out.println();
    }



    private int[] parseInput(String input) {
        String[] unparsedNumbers = input.replaceAll("[\\[\\]]", "").split(",");
        int[] numbers = new int[unparsedNumbers.length];
        for (int i = 0; i < unparsedNumbers.length; i++) {
            try {
                numbers[i] = Integer.parseInt(unparsedNumbers[i].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return numbers;
    }

    private void findCost() {
        int totalSum = Arrays.stream(input).sum();
        cost = totalSum;

        for (int i = 0; i < input.length; i++) {
            int pyramidMaxHeight = input[i];
            if (pyramidMaxHeight <= minimumCostPeakHeight) {
                continue;
            }
            pyramidMaxHeight = adjustPyramidHeight(input, i, pyramidMaxHeight, GO_RIGHT, input.length-1);
            pyramidMaxHeight = adjustPyramidHeight(input, i, pyramidMaxHeight, GO_LEFT, 0);
            int costAtIdx =  totalSum - (int)Math.pow(pyramidMaxHeight, 2);
            if (costAtIdx < cost) {
                cost = costAtIdx;
                minimumCostPeakIdx = i;
                minimumCostPeakHeight = pyramidMaxHeight;
            }
        }
    }

    private int adjustPyramidHeight(int[] input, int startingIdx, int pyramidMaxHeight, IntUnaryOperator idxDirection, int idxEnd) {
        int currentHeight = pyramidMaxHeight;
        int idx = startingIdx;
        while (currentHeight > 0) {
            int h = input[idx];
            if (h < currentHeight) {
                pyramidMaxHeight = pyramidMaxHeight - currentHeight + h;
                currentHeight = h - 1;
            } else if(idx == idxEnd && currentHeight != 1){
                pyramidMaxHeight = pyramidMaxHeight - currentHeight + 1;
                currentHeight = 0;
            } else {
                currentHeight--;
            }
            idx = idxDirection.applyAsInt(idx);
        }
        return pyramidMaxHeight;
    }

    public static void main(String[] args) {
        Pyramid pyramid = new Pyramid("[9, 0, 1]");
        pyramid.printMinimumCost();
        pyramid.printMinimumCostPyramid();
//        new Pyramid("[1, 1, 3, 3, 2, 1, 0, 1, 2, 3, 4, 5, 2, 2, 4, 3, 2, 1]")
//        new Pyramid("[1, 1, 3, 3, 2, 1, 0, 1, 2, 3, 4, 5, 2, 2, 4, 3, 2, 1, 3, 4, 5, 2, 2, 4, 3, 2, 1, 0, 0, 0, 8, 9, 12, 3, 4, 22, 33, 44, 66, 5, 4, 2, 0, 1]")
//        new Pyramid("[1, 1, 3, 3, 2, 1, 0, 1, 2, 3, 4, 5, 2, 2, 4, 3, 2, 1, 3, 4, 5, 2, 2, 4, 3, 2, 1, 0, 0, 0, 8, 9, 12, 3, 4, 22, 33, 44, 66, 5, 4, 2, 0, 1, 4, 3, 2, 1, 3, 4, 5, 2, 2, 4, 3, 2, 1, 0, 0, 0, 8, 9, 12, 3, 4, 22, 33, 44, 66, 5, 4, 2, 0, 1, 33, 44, 66, 5, 4, 2, 0, 1]")
//        new Pyramid("[1, 3, 3, 2, 1]");
//        new Pyramid("[2, 3, 10, 7, 8, 3, 2, 1]");
//        new Pyramid("[11, 10, 9, 7, 8, 12, 3, 0]");
//        new Pyramid("[10, 0, 3, 2, 1, 0, 88, 0, 2, 0, 34, 0, 1, 4, 3, 0, 4, 2, 2, 2, 0]");
//        new Pyramid("[9, 9, 9, 9, 9, 9]");
//        new Pyramid("[1, 1, 1, 1, 1, 1, 1]");
    }


}

