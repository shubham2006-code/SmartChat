import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Entry point of the SmartChat server.
 *
 * Responsibility is intentionally narrow: open the server socket,
 * accept incoming connections, and hand each one off to a dedicated
 * ClientHandler thread. All chat/business logic (broadcasting,
 * join/leave announcements) lives in ClientHandler, keeping this
 * class focused purely on connection management.
 */
public class ChatServer {

    private static final int DEFAULT_PORT = 5000;

    private final int port;

    // CopyOnWriteArrayList is safe to iterate (broadcast) while other
    // threads add/remove clients, without needing external locking
    // for reads. ClientHandler still synchronizes on it for the
    // add/remove + iterate sequences that must be atomic together.
    private final List<ClientHandler> clientHandlers = new CopyOnWriteArrayList<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        printBanner();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ServerLogger.log("Server started on port " + port);
            ServerLogger.log("Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                spawnClientThread(clientSocket);
            }
        } catch (IOException e) {
            ServerLogger.log("Server failed to start on port " + port + ": " + e.getMessage());
        }
    }

    private void spawnClientThread(Socket clientSocket) {
        ClientHandler handler = new ClientHandler(clientSocket, clientHandlers);
        Thread clientThread = new Thread(handler, "client-" + clientSocket.getPort());
        clientThread.start();
    }

    private void printBanner() {
        System.out.println("====================================");
        System.out.println("          SMART CHAT SERVER");
        System.out.println("====================================");
    }

    public static void main(String[] args) {
        int port = parsePort(args);
        new ChatServer(port).start();
    }

    private static int parsePort(String[] args) {
        if (args.length == 0) {
            return DEFAULT_PORT;
        }
        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port argument \"" + args[0] + "\". Using default port " + DEFAULT_PORT + ".");
            return DEFAULT_PORT;
        }
    }
}
