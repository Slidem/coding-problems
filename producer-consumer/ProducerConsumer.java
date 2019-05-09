import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Alexandru Mihai
 */
public class ProducerConsumer {

    private final int maxSize;

    private final Deque<Job> sharedBuffer;

    public ProducerConsumer(int maxSize) {
        this.maxSize = maxSize;
        sharedBuffer = new LinkedList<>();
    }

    public Job get(){
        synchronized (sharedBuffer) {
            while (sharedBuffer.isEmpty()) {
                System.out.println("buffer empty; awaiting producer...");
                tryWait();
            }
            Job job = sharedBuffer.pollLast();
            System.out.println("consuming " + job);
            sharedBuffer.notify();
            return job;
        }
    }

    public void add(Job job){
        synchronized (sharedBuffer) {
            while (sharedBuffer.size() == maxSize) {
                System.out.println("buffer full; awaiting consumer...");
                tryWait();
            }
            System.out.println("producing " + job);
            sharedBuffer.addFirst(job);
            sharedBuffer.notify();
        }
    }

    private void tryWait(){
        try {
            sharedBuffer.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Job {

        final int id;

        public Job() {
            this.id = (int) (Math.random() * 100);
        }

        @Override
        public String toString() {
            return "Job{" +
                    "id=" + id +
                    '}';
        }
    }

    public static void main(String[] args) {

        ProducerConsumer producerConsumer = new ProducerConsumer(10);

        Thread producer = new Thread(){
            @Override
            public void run() {
                while(true){
                    trySleep();
                    producerConsumer.add(new Job());
                }
            }
        };

        Thread consumer = new Thread(){
            @Override
            public void run() {
                while(true){
                    trySleep();
                    producerConsumer.get();
                }
            }
        };

        producer.start();
        consumer.start();
    }

    static void trySleep(){
        try {
            Thread.sleep((long) (Math.random() * 400) + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

