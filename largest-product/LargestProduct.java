import java.util.Arrays;
import java.util.Objects;

public class LargestProduct {

    public static void main(String[] args) {

        // test [-10, -10, 5, 2] -> 500 (-10 * -10 * 5)
        System.out.println(largestProductBruteForce(new int[]{-10, -10, 5, 2}));
        System.out.println(largestProductOrdered(new int[]{-10, -10, 5, 2}));
        System.out.println(largestProductLinear(new int[]{-10, -10, 5, 2}));

        // test [4, 6, 2, 6, -2, -2, -10, -1] -> 144 (6 * 6 * 4)
        System.out.println(largestProductBruteForce(new int[]{4, 6, 2, 6, -2, -2, -10, -1}));
        System.out.println(largestProductOrdered(new int[]{4, 6, 2, 6, -2, -2, -10, -1}));
        System.out.println(largestProductLinear(new int[]{4, 6, 2, 6, -2, -2, -10, -1}));

        // test [-3, -1, -1, -1] -> -1 (-1 * -1 * -1)
        System.out.println(largestProductBruteForce(new int[]{-3, -1, -1, -1}));
        System.out.println(largestProductOrdered(new int[]{-3, -1, -1, -1}));
        System.out.println(largestProductLinear(new int[]{-3, -1, -1, -1}));

    }

    // first naive solution is use a brute force approach, where we user 3 for loops,
    // and compute all posible combinations of 3 numbers,
    // and check which is the biggest product
    // complexity : O(n^3)
    static int largestProductBruteForce(int[] numbers) {
        if (Objects.isNull(numbers) || numbers.length < 3) {
            return 0;
        }

        int maxProduct = numbers[0] * numbers[1] * numbers[2];

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                for (int z = 0; z < numbers.length; z++) {
                    if (i == j || j == z || i == z) {
                        continue;
                    }
                    int product = numbers[i] * numbers[j] * numbers[z];
                    if (product > maxProduct) {
                        maxProduct = product;
                    }
                }
            }
        }
        return maxProduct;
    }

    // this first sorts the numbers, and than compares the first two and last element of the array.
    // if sorted in ascending order, negative numbers should be first, and positive last
    // if we don't find at least 2 negative numbers, than just return the last 3 number's product
    //
    // if there are at least two negative numbers,
    // second negative number should be bigger than the second to last postive number in order to return a product between 2 negative numbers and one positive
    // otherwise return last 3 number's product
    // Complexity : O(nlogn)
    static int largestProductOrdered(int[] numbers) {
        if (Objects.isNull(numbers) || numbers.length < 3) {
            return 0;
        }

        //ascending sort
        Arrays.sort(numbers);

        int firstNegative = numbers[0];
        int secondNegative = numbers[1];
        int lastPositive = numbers[numbers.length - 1];
        int secondToLastPositive = numbers[numbers.length - 2];
        int thirdToLastPositive = numbers[numbers.length - 3];

        if ((firstNegative < 0 || secondNegative < 0) && Math.abs(secondNegative) > secondToLastPositive && secondToLastPositive >= 0) {
            return firstNegative * secondNegative * lastPositive;
        } else {
            return lastPositive * secondToLastPositive * thirdToLastPositive;
        }
    }

    // we only need to know the maximum 3 positive numbers, and also the smallest 2 negative numbers
    // we can do this in O(n) time
    static int largestProductLinear(int[] numbers){

        int maxNegative = numbers[0];
        int secondToMaxNegative = numbers[0];
        int maxPositive = numbers[0];
        int secondToMaxPositive = numbers[0];
        int thirdToMaxPositive = numbers[0];

        for(int i=0; i<numbers.length; i++){
        int number = numbers[i];
            if(number >= maxPositive){
                thirdToMaxPositive = secondToMaxPositive;
                secondToMaxPositive = maxPositive;
                maxPositive = number;
            }
            if(number < 0 && number <= maxNegative){
                secondToMaxNegative = maxNegative;
                maxNegative = number;
            }
        }

        if( (maxNegative < 0 || secondToMaxNegative < 0) && Math.abs(secondToMaxNegative) > secondToMaxPositive && secondToMaxPositive >= 0){
            return maxNegative * secondToMaxNegative * maxPositive;
        } else {
            return maxPositive * secondToMaxPositive * thirdToMaxPositive;
        }
    }

}
