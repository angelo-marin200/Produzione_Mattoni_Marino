import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Oven implements Runnable {
    private final BlockingQueue<Integer> inputQueue;
    private final BlockingQueue<Integer> outputQueue;
    private final BufferedWriter logWriter;

    public Oven(BlockingQueue<Integer> inputQueue, BlockingQueue<Integer> outputQueue, BufferedWriter logWriter) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.logWriter = logWriter;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 4; i++) { // Oven processes 400 bricks at a time
                int batch = inputQueue.take();
                log("Forno inizia cottura per " + batch + " mattoni.");
                Thread.sleep(2000); // Simulate cooking time
                outputQueue.put(batch);
                log("Forno ha completato la cottura per " + batch + " mattoni.");
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
