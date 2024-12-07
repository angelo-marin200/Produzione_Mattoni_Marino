import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BrickProduction {

    private static final int TOTAL_BRICKS = 1600;
    private static final int NUM_MIXERS = 8;
    private static final int BRICKS_PER_MIXER = 200;
    private static final int OVEN_CAPACITY = 400;

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> mixerQueue = new ArrayBlockingQueue<>(NUM_MIXERS);
        BlockingQueue<Integer> moldingQueue = new ArrayBlockingQueue<>(TOTAL_BRICKS);
        BlockingQueue<Integer> ovenQueue = new ArrayBlockingQueue<>(OVEN_CAPACITY);
        BlockingQueue<Integer> packagingQueue = new ArrayBlockingQueue<>(TOTAL_BRICKS);

        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter("production_log.txt"))) {
            // Start mixer threads
            Thread[] mixers = new Thread[NUM_MIXERS];
            for (int i = 0; i < NUM_MIXERS; i++) {
                mixers[i] = new Thread(new Mixer(i + 1, mixerQueue, logWriter));
                mixers[i].start();
            }

            // Start the other phase threads
            Thread molding = new Thread(new Molding(mixerQueue, moldingQueue, logWriter));
            molding.start();

            Thread oven = new Thread(new Oven(moldingQueue, ovenQueue, logWriter));
            oven.start();

            Thread packaging = new Thread(new Packaging(ovenQueue, packagingQueue, logWriter));
            packaging.start();

            // Wait for threads to complete
            for (Thread mixer : mixers) {
                mixer.join();
            }
            molding.join();
            oven.join();
            packaging.join();

            logWriter.write("Process completed.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
