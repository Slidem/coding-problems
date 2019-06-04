import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.*;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

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

    String target;

    Set<String> deadEnds;

    Set<Move> computed;

    Move found;

    List<Move> minimumMoves;

    @SuppressWarnings("unchecked")
    public CircularLock(String jsonInput) {
        Map<String, Object> mapInput = GSON.fromJson(jsonInput, new TypeToken<Map<String, Object>>(){}.getType());
        target = (String) mapInput.get("target");
        deadEnds = new HashSet<>((Collection<? extends String>) mapInput.get("deadends"));
        find();
        prepareResult();
    }

    public CircularLock(String target, Set<String> deadEnds) {
        this.target = target;
        this.deadEnds = deadEnds;
        find();
        prepareResult();
    }

    public void printMinimumNumberOfMoves(){
        if (Objects.isNull(found)) {
            return;
        }
        System.out.println("Minimum number of moves required: " + minimumMoves.size());
    }

    public void printMinimumMoves(){
        if (Objects.isNull(found)) {
            System.out.println("None");
            return;
        }
        System.out.println(minimumMoves.stream().map(Object::toString).collect(Collectors.joining(" -> ")) + " -> " + target);
    }

    private void find() {
        computed = new HashSet<>();
        PriorityQueue<Move> pq = new PriorityQueue<>();
        pq.add(new Move(target));
        while (!pq.isEmpty()) {
            Move m = pq.poll();
            if (m.value.equals("000")) {
                found = m;
                return;
            }
            pq.addAll(computeNextPossibleMoves(m));
        }
    }

    private void prepareResult() {
        if (Objects.isNull(found)) {
            return;
        }
        minimumMoves = new LinkedList<>();
        Move m = found;
        while (Objects.nonNull(m.prev)) {
            minimumMoves.add(m);
            m = m.prev;
        }
    }

    private Collection<? extends Move> computeNextPossibleMoves(Move m) {
        List<Move> nextPossibleMoves = new ArrayList<>();
        leftUp(m, nextPossibleMoves);
        middleUp(m, nextPossibleMoves);
        rightUp(m, nextPossibleMoves);
        leftDown(m, nextPossibleMoves);
        middleDown(m, nextPossibleMoves);
        rightDown(m, nextPossibleMoves);
        return nextPossibleMoves;
    }

    private void leftUp(Move m, List<Move> nextPossibleMoves) {
        rotate(m, nextPossibleMoves, ROTATE_UP_FUNCTION, 0);
    }

    private void middleUp(Move m, List<Move> nextPossibleMoves) {
        rotate(m, nextPossibleMoves, ROTATE_UP_FUNCTION, 1);
    }

    private void rightUp(Move m, List<Move> nextPossibleMoves) {
        rotate(m, nextPossibleMoves, ROTATE_UP_FUNCTION, 2);
    }

    private void leftDown(Move m, List<Move> nextPossibleMoves) {
        rotate(m, nextPossibleMoves, ROTATE_DOWN_FUNCTION, 0);
    }

    private void middleDown(Move m, List<Move> nextPossibleMoves) {
        rotate(m, nextPossibleMoves, ROTATE_DOWN_FUNCTION, 1);
    }

    private void rightDown(Move m, List<Move> nextPossibleMoves) {
        rotate(m, nextPossibleMoves, ROTATE_DOWN_FUNCTION, 2);
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

    class Move implements Comparable<Move> {

        String value;

        Move prev;

        Move(String value) {
            this(value, null);
        }

        Move(String value, Move prev) {
            this.value = value;
            this.prev = prev;
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
            int thisLeftValue = computeDistanceToZero(parseInt(String.valueOf(this.value.charAt(0))));
            int thisMiddleValue = computeDistanceToZero(parseInt(String.valueOf(this.value.charAt(1))));
            int thisRightValue = computeDistanceToZero(parseInt(String.valueOf(this.value.charAt(2))));

            int thatLeftValue = computeDistanceToZero(parseInt(String.valueOf(that.value.charAt(0))));
            int thatMiddleValue = computeDistanceToZero(parseInt(String.valueOf(that.value.charAt(1))));
            int thatRightValue = computeDistanceToZero(parseInt(String.valueOf(that.value.charAt(2))));

            if(thisLeftValue < thatLeftValue){
                return -1;
            }
            if (thisLeftValue > thatLeftValue) {
                return 1;
            }
            if (thisMiddleValue < thatMiddleValue) {
                return -1;
            }
            if (thisMiddleValue > thatMiddleValue) {
                return 1;
            }
            return thisRightValue - thatRightValue;
        }

        private int computeDistanceToZero(int value) {
            return value > 5 ? 10 - value : value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

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


