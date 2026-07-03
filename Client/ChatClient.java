import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Entry point of the SmartChat client.
 *
 * Coordinates three things: connecting to the server, starting the
 * background MessageReceiver thread, and running the main-thread
 * loop that sends whatever the user types. Console concerns are
 * delegated to ClientUI so this class stays focused on networking.
 */
public class ChatClient {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private MessageReceiver messageReceiver;

    private final ClientUI ui = new ClientUI();

    public void start() {
        ui.printBanner();
        try {
            String username = ui.askUsername();
            String serverIP = ui.askServerIP();
            int port = ui.askPort();

            connectToServer(serverIP, port, username);
            ui.showConnectedMessage();

            startReceivingMessages();
            sendMessagesLoop();
        } catch (IOException e) {
            System.out.println("Unable to connect: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    private void connectToServer(String serverIP, int port, String username) throws IOException {
        socket = new Socket(serverIP, port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
        output.println(username);
    }

    private void startReceivingMessages() {
        messageReceiver = new MessageReceiver(input);
        // Daemon thread: it should never keep the JVM alive on its own
        // once the main sending loop finishes.
        Thread receiverThread = new Thread(messageReceiver, "message-receiver");
        receiverThread.setDaemon(true);
        receiverThread.start();
    }

    private void sendMessagesLoop() throws IOException {
        String message;
        while ((message = ui.readMessage()) != null) {
            if ("/quit".equalsIgnoreCase(message.trim())) {
                break;
            }
            output.println(message);
        }
    }

    private void shutdown() {
        if (messageReceiver != null) {
            messageReceiver.stop();
        }
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.out.println("Error while closing connection: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ChatClient().start();
    }
}
