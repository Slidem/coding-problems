public class SetSumDifference {

    private int smallestDifference = 0;

    private Set<Integer> setA;

    private Set<Integer> setB;

    public SetSumDifference(int[] values) {
        Set<Integer> valuesSet = new HashSet<>();
        for (int value : values) {
            boolean added = valuesSet.add(value);
            if (added) {
                smallestDifference += value;
            }
        }
        setA = new HashSet<>();
        setB = new HashSet<>(valuesSet);
        computeResult(setA, setB, 0, smallestDifference);
    }

    private void computeResult(Set<Integer> a, Set<Integer> b, int aPrevSum, int bPrevSum) {
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
        System.out.println(new SetSumDifference(new int[]{}));
    }


}