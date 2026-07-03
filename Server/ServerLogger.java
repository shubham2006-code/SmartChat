import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Small utility class responsible for printing consistent,
 * timestamped log messages to the server console.
 *
 * Kept separate from ChatServer / ClientHandler so that logging
 * logic (format, destination) can change later without touching
 * networking code.
 */
public class ServerLogger {

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private ServerLogger() {
        // Utility class - no instances needed
    }

    public static void log(String message) {
        String timestamp = TIME_FORMAT.format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }
}
