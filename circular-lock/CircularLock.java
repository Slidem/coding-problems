import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.Instant;
import java.util.*;
import java.util.function.IntUnaryOperator;

import static java.lang.Integer.parseInt;

/**
 * You are given a circular lock with three wheels, each of which display the numbers 0 through 9 in order. Each of these wheels rotate clockwise and counterclockwise.
 * <p>
 * In addition, the lock has a certain number of "dead ends", meaning that if you turn the wheels to one of these combinations, the lock becomes stuck in that state and cannot be opened.
 * <p>
 * Let us consider a "move" to be a rotation of a single wheel by one digit, in either direction.
 * Given a lock initially set to 000, a target combination, and a list of dead ends,
 * write a function that returns the minimum number of moves required to reach the target state,
 * or None if this is impossible.
 *
 * @author Alexandru Mihai
 */
public class CircularLock {

    private static final Gson GSON = new Gson();

    private static final IntUnaryOperator ROTATE_DOWN_FUNCTION = v -> v == 0 ? 9 : v - 1;

    private static final IntUnaryOperator ROTATE_UP_FUNCTION = v -> v == 9 ? 0 : v + 1;

    private static final String ZERO = "000";

    private String target;

    private Set<String> deadEnds;

    private Set<Move> computed;

    private Move found;

    CircularLock(String jsonInput) {
        Map<String, Object> mapInput = GSON.fromJson(jsonInput, new TypeToken<Map<String, Object>>(){}.getType());
        target = (String) mapInput.get("target");
        deadEnds = new HashSet<>((Collection<? extends String>) mapInput.get("deadends"));
        findWithTimePrint();
    }

    CircularLock(String target, Set<String> deadEnds) {
        this.target = target;
        this.deadEnds = deadEnds;
        findWithTimePrint();
    }

    // -------------------------------------------------------------------------------------------

    void printMinimumNumberOfMoves(){
        if (Objects.isNull(found)) {
            System.out.println("None");
            return;
        }
        System.out.println("Minimum number of moves required: " + (found.getRootPathLenght() - 1));
    }

    void printMinimumMoves(){
        if (Objects.isNull(found)) {
            System.out.println("None");
            return;
        }
        Move m = found;
        while (Objects.nonNull(m)) {
            System.out.print(m.value);
            if (!m.value.equals(target)) {
                System.out.print(" -> ");
            } else {
                System.out.println();
            }
            m = m.prev;
        }
    }

    // --------------------------------------------------------------------------- COMPUTATION

    private void findWithTimePrint(){
        long start = Instant.now().toEpochMilli();
        find();
        long end = Instant.now().toEpochMilli();
        System.out.println("Computed in " + (end - start) + " ms");
    }

    private void find() {
        if (deadEnds.contains(target)) {
            return;
        }

        computed = new HashSet<>();
        PriorityQueue<Move> pq = new PriorityQueue<>();
        pq.add(new Move(target, null));
        while (!pq.isEmpty()) {
            Move m = pq.poll();
            if (m.value.equals(ZERO)) {
                found = m;
                return;
            }
            pq.addAll(computeNextPossibleMoves(m));
        }
    }

    private Collection<? extends Move> computeNextPossibleMoves(Move m) {
        List<Move> nextPossibleMoves = new ArrayList<>();
        rotate(m, nextPossibleMoves, ROTATE_UP_FUNCTION, 0);
        rotate(m, nextPossibleMoves, ROTATE_UP_FUNCTION, 1);
        rotate(m, nextPossibleMoves, ROTATE_UP_FUNCTION, 2);
        rotate(m, nextPossibleMoves, ROTATE_DOWN_FUNCTION, 0);
        rotate(m, nextPossibleMoves, ROTATE_DOWN_FUNCTION, 1);
        rotate(m, nextPossibleMoves, ROTATE_DOWN_FUNCTION, 2);
        return nextPossibleMoves;
    }

    private void rotate(Move m, List<Move> nextPossibleMoves, IntUnaryOperator rotateFunction, int idx) {
        int value = parseInt(String.valueOf(m.value.charAt(idx)));
        int rotatedValue = rotateFunction.applyAsInt(value);
        Move rotatedMove = new Move(replaceAtIndex(m.value, idx, rotatedValue), m);
        if (computed.add(rotatedMove) && !deadEnds.contains(rotatedMove.value)) {
            nextPossibleMoves.add(rotatedMove);
        }
    }

    private String replaceAtIndex(String str, int idx, int value){
        char[] chars = str.toCharArray();
        chars[idx] = String.valueOf(value).charAt(0);
        return String.valueOf(chars);
    }

    // ------------------------------------------------------------------- Helper class

    class Move implements Comparable<Move> {

        String value;

        Move prev;

        Move(String value, Move prev) {
            this.value = value;
            this.prev = prev;
        }

        int getRootPathLenght(){
            int length = 0;
            Move m = this;
            while (Objects.nonNull(m)) {
                length++;
                m = m.prev;
            }
            return length;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Move move = (Move) o;
            return value.equals(move.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public int compareTo(Move that) {
            int distanceToZeroDiff = computeDistanceToZero(this.value) - computeDistanceToZero(that.value);
            return distanceToZeroDiff != 0 ? distanceToZeroDiff : (this.getRootPathLenght() - that.getRootPathLenght());
        }

        private int computeDistanceToZero(String value) {
            return computeDistanceToZero(parseInt(String.valueOf(value.charAt(0)))) +
                    computeDistanceToZero(parseInt(String.valueOf(value.charAt(1)))) +
                    computeDistanceToZero(parseInt(String.valueOf(value.charAt(2))));
        }

        private int computeDistanceToZero(int value) {
            return value > 5 ? 10 - value : value;
        }
    }

    // ---------------------------------------------------------------------------------------- TEST

    public static void main(String[] args) {
        CircularLock circularLock = new CircularLock("{\n" +
                "\t\"target\":\"477\",\n" +
                "\t\"deadends\":[\"114\",\"233\",\"299\",\"305\"]\n" +
                "} ");

//        CircularLock circularLock = new CircularLock("{\n" +
//                "\t\"target\":\"609\",\n" +
//                "\t\"deadends\":[\"114\",\"233\",\"700\",\"305\",\"909\",\"009\",\"919\",\"901\",\"619\",\"600\",\"699\",\"698\",\"608\"]\n" +
//                "} ");

//        CircularLock circularLock = new CircularLock("{\n" +
//                "\t\"target\":\"302\",\n" +
//                "\t\"deadends\":[\"300\",\"299\",\"401\",\"198\"]\n" +
//                "} ");

        circularLock.printMinimumMoves();
        circularLock.printMinimumNumberOfMoves();
    }

}


