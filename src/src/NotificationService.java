import java.util.ArrayList;
import java.util.List;
public class NotificationService {
    private List<String> sentMessages = new ArrayList<>();
    public void sendNotification(Customer customer, String message) {
        String text = "To " + customer.getEmail() + ": " + message;
        sentMessages.add(text);
        System.out.println("[Notification] " + text);
    }
    public List<String> getSentMessages() {
        return sentMessages;
    }
}
