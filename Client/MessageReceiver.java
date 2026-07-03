import java.io.BufferedReader;
import java.io.IOException;

/**
 * Continuously listens for messages coming from the server and
 * prints them to the console.
 *
 * This runs on its own thread so that reading from the socket never
 * blocks the user from typing a new message (and vice versa). The
 * two operations - listening and typing - are inherently
 * independent, so they belong on independent threads.
 */
public class MessageReceiver implements Runnable {

    private final BufferedReader input;
    private volatile boolean running = true;

    public MessageReceiver(BufferedReader input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            String message;
            while (running && (message = input.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            if (running) {
                System.out.println("Lost connection to server.");
            }
        }
    }

    /** Allows ChatClient to stop this thread cleanly when the user quits. */
    public void stop() {
        running = false;
    }
}
