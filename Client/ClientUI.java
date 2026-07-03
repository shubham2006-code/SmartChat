import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Owns all console interaction for the client (prompts, banners,
 * reading user input). Keeping this separate from ChatClient means
 * the networking class doesn't need to know anything about how
 * input/output is presented - it could later be swapped for a GUI
 * or a test harness without touching connection logic.
 */
public class ClientUI {

    private final BufferedReader consoleReader;

    public ClientUI() {
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void printBanner() {
        System.out.println("====================================");
        System.out.println("          SMART CHAT CLIENT");
        System.out.println("====================================");
    }

    public String askUsername() throws IOException {
        System.out.print("Enter Username: ");
        return consoleReader.readLine();
    }

    public String askServerIP() throws IOException {
        System.out.print("Server IP: ");
        return consoleReader.readLine();
    }

    public int askPort() throws IOException {
        System.out.print("Port: ");
        String raw = consoleReader.readLine();
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            throw new IOException("\"" + raw + "\" is not a valid port number.");
        }
    }

    public String readMessage() throws IOException {
        return consoleReader.readLine();
    }

    public void showConnectedMessage() {
        System.out.println("Connected Successfully!");
        System.out.println("Type your message: (or /quit to leave)");
    }
}
