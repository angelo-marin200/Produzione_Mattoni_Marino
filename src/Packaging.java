import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Packaging implements Runnable {
    private final BlockingQueue<Integer> inputQueue;
    private final BlockingQueue<Integer> outputQueue;
    private final BufferedWriter logWriter;

    public Packaging(BlockingQueue<Integer> inputQueue, BlockingQueue<Integer> outputQueue, BufferedWriter logWriter) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.logWriter = logWriter;
    }

    @Override
    public void run() {
        try {
            int totalPackaged = 0;
            while (totalPackaged < 1600) { // Process all 1600 bricks
                int batch = inputQueue.take();
                log("Imballaggio inizia per " + batch + " mattoni.");
                Thread.sleep(1000); // Simulate packaging time
                totalPackaged += batch;
                log("Imballaggio completato. Mattoni imballati: " + totalPackaged);
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
