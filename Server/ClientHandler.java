import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Handles a single connected client on its own thread.
 *
 * Every client gets exactly one ClientHandler. This keeps the
 * server responsive: a slow or misbehaving client only blocks its
 * own thread, never the accept loop or any other client's traffic.
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final List<ClientHandler> clientHandlers;

    private BufferedReader input;
    private PrintWriter output;
    private String username;

    public ClientHandler(Socket clientSocket, List<ClientHandler> clientHandlers) {
        this.clientSocket = clientSocket;
        this.clientHandlers = clientHandlers;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            username = readUsername();
            registerClient();

            ServerLogger.log(username + " connected.");
            broadcastSystemMessage(username + " joined the chat.");

            String message;
            while ((message = input.readLine()) != null) {
                broadcastChatMessage(username, message);
            }
        } catch (IOException e) {
            // A dropped connection is expected behavior, not a fatal error.
            // We log it and let the finally block clean up, so one bad
            // client can never bring the server down.
            ServerLogger.log("Connection error for " + safeUsername() + ": " + e.getMessage());
        } finally {
            disconnectClient();
        }
    }

    private String readUsername() throws IOException {
        String name = input.readLine();
        if (name == null || name.trim().isEmpty()) {
            name = "Guest-" + clientSocket.getPort();
        }
        return name.trim();
    }

    private void registerClient() {
        synchronized (clientHandlers) {
            clientHandlers.add(this);
        }
    }

    private void disconnectClient() {
        boolean wasRegistered;
        synchronized (clientHandlers) {
            wasRegistered = clientHandlers.remove(this);
        }
        closeResources();

        if (wasRegistered && username != null) {
            ServerLogger.log(username + " disconnected.");
            broadcastSystemMessage(username + " left the chat.");
        }
    }

    /** Sends a raw line to this specific client. Package-visible so ChatServer/other handlers can call it during broadcast. */
    void sendRaw(String line) {
        output.println(line);
    }

    private void broadcastChatMessage(String sender, String message) {
        String formatted = "[" + sender + "]" + System.lineSeparator() + message;
        broadcastToAll(formatted);
        ServerLogger.log("[" + sender + "] " + message);
    }

    private void broadcastSystemMessage(String message) {
        broadcastToAll(message);
        ServerLogger.log(message);
    }

    private void broadcastToAll(String formattedMessage) {
        // Synchronized so the list can't be modified (client join/leave)
        // while we are iterating over it to broadcast.
        synchronized (clientHandlers) {
            for (ClientHandler handler : clientHandlers) {
                handler.sendRaw(formattedMessage);
            }
        }
    }

    private void closeResources() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
        } catch (IOException e) {
            ServerLogger.log("Error while closing resources for " + safeUsername() + ": " + e.getMessage());
        }
    }

    private String safeUsername() {
        return username != null ? username : "unregistered client";
    }
}
