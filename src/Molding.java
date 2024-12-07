import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Molding implements Runnable {
    private final BlockingQueue<Integer> inputQueue;
    private final BlockingQueue<Integer> outputQueue;
    private final BufferedWriter logWriter;

    public Molding(BlockingQueue<Integer> inputQueue, BlockingQueue<Integer> outputQueue, BufferedWriter logWriter) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.logWriter = logWriter;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 8; i++) { // Process 8 batches of 200 bricks
                int batch = inputQueue.take();
                log("Stampaggio in corso per " + batch + " mattoni.");
                Thread.sleep(500); // Simulate molding time
                outputQueue.put(batch);
                log("Stampaggio completato per " + batch + " mattoni.");
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void log(String message) throws IOException {
        synchronized (logWriter) {
            logWriter.write(message + "\n");
            logWriter.flush();
        }
        System.out.println(message); // Stampa anche sulla console
    }
}
