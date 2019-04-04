import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mihai Alexandru
 * @date 04.04.2019
 */
public class SetSumDifference {

    private int smallestDifference = 0;

    private Set<Integer> setA;

    private Set<Integer> setB;

    private Set<Set<Integer>> computed;

    public SetSumDifference(int[] values) {
        Set<Integer> valuesSet = new HashSet<>();
        for (int value : values) {
            boolean added = valuesSet.add(value);
            if (added) {
                smallestDifference += value;
            }
        }
        computed = new HashSet<>();
        setA = new HashSet<>();
        setB = new HashSet<>(valuesSet);
        computeResult(setA, setB, 0, smallestDifference);
    }

    private void computeResult(Set<Integer> a, Set<Integer> b, int aPrevSum, int bPrevSum) {
        //memoization
        if (computed.contains(a) || computed.contains(b)) {
            return;
        }
        if (b.isEmpty()) {
            return;
        }
        for (Integer bElement : b) {
            Set<Integer> newA = new HashSet<>(a);
            Set<Integer> newB = new HashSet<>(b);
            newA.add(bElement);
            newB.remove(bElement);
            int aSum = aPrevSum + bElement;
            int bSum = bPrevSum - bElement;
            int diff = Math.abs(aSum - bSum);
            if (smallestDifference > diff) {
                smallestDifference = diff;
                setA = newA;
                setB = newB;
            }
            computed.add(a);
            computed.add(b);
            computeResult(newA, newB, aSum, bSum);

        }
    }

    @Override
    public String toString() {
        return "smallestDifference=" + smallestDifference +
                ", setA=" + Arrays.toString(setA.toArray()) +
                ", setB=" + Arrays.toString(setB.toArray());
    }

    public static void main(String[] args) {
        System.out.println(new SetSumDifference(new int[]{1, 4, 8, 12, 29, 88}));
        System.out.println(new SetSumDifference(new int[]{8, 24, 28, 33, 49}));
        System.out.println(new SetSumDifference(new int[]{5, 10, 15, 20, 25}));
        System.out.println(new SetSumDifference(new int[]{5, 5, 5, 5, 5}));
        System.out.println(new SetSumDifference(new int[]{90, 22, 22, 31, 5, 46}));
        System.out.println(new SetSumDifference(new int[]{90, 22, 22, 31, 5, 46, 88, 3, 45, 6, 76, 11, 13, 14, 34, 98, 102, 10, 3}));
        System.out.println(new SetSumDifference(new int[]{}));
    }
}
