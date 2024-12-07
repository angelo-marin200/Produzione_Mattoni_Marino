import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Mixer implements Runnable {
    private final int id;
    private final BlockingQueue<Integer> outputQueue;
    private final BufferedWriter logWriter;

    public Mixer(int id, BlockingQueue<Integer> outputQueue, BufferedWriter logWriter) {
        this.id = id;
        this.outputQueue = outputQueue;
        this.logWriter = logWriter;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 2; i++) { // Each mixer works twice for 200 bricks each time
                log("Impastatrice " + id + " inizia impasto.");
                Thread.sleep(1000); // Simulate mixing time
                outputQueue.put(200); // Produces 200 bricks
                log("Impastatrice " + id + " ha completato un impasto.");
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
