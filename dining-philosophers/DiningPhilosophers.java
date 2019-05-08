import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Alexandru Mihai
 */
public class DiningPhilosophers {

    public static void main(String[] args) {
        simulateTableOf(5);
    }

    private static void simulateTableOf(int nbOfPhilosophers){
        System.out.println("Creating table of " + nbOfPhilosophers + " philosphers...");
        List<Philosopher> philosophers = new ArrayList<>();
        List<Object> chopsticks = IntStream.range(0, nbOfPhilosophers).mapToObj(i -> new Object()).collect(Collectors.toList());

        for(int i = 0; i < nbOfPhilosophers; i++){
            Object leftChopstick = chopsticks.get(i);
            Object rightChopstick = (i == nbOfPhilosophers - 1) ? chopsticks.get(0) : chopsticks.get(i + 1);
            if(i == nbOfPhilosophers - 1){
                //switch chopstick order for one philosopher in order to avoid deadlock
                Object aux = leftChopstick;
                leftChopstick = rightChopstick;
                rightChopstick = aux;
            }
            philosophers.add(new Philosopher(leftChopstick, rightChopstick, String.valueOf(i)));
        }
        System.out.println("Starting simulation !");
        philosophers.forEach(Thread::start);
    }

    static class Philosopher extends Thread{

        final Object leftChopstick;

        final Object rightChopstick;

        final String name;

        public Philosopher(Object leftChopstick, Object rightChopstick, String name) {
            this.leftChopstick = leftChopstick;
            this.rightChopstick = rightChopstick;
            this.name = name;
        }

        @Override
        public void run() {

            while(true){
                printState("thinking");
                randomWait();
                synchronized (rightChopstick){
                    synchronized (leftChopstick){
                        printState("eating");
                        randomWait();
                    }
                }
            }
        }


        private void printState(String state){
            System.out.println("Philosopher " + name + " " + state);
        }

        private void randomWait(){
            // sleep between 100 and 2000 milliseconds
            long millisToSleep = (long)((Math.random() * 20 + 1) * 100);
            try {
                Thread.sleep(millisToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



}

