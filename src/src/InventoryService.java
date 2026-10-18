import java.util.HashMap;
import java.util.Map;
public class InventoryService {
    private Map<String, Integer> stock = new HashMap<>();
    private Map<Integer, Cart> reservations = new HashMap<>();
    public void addProduct(String productName, int quantity) {
        stock.put(productName, quantity);
    }
    public int getStock(String productName) {
        return stock.getOrDefault(productName, 0);
    }
    public boolean checkAvailability(Cart cart) {
        for (CartItem item : cart.getItems()) {
            int available = getStock(item.getProductName());
            if (available < item.getQuantity()) {
                System.out.println("[Inventory] Not enough " + item.getProductName()
                        + " (need " + item.getQuantity() + ", have " + available + ")");
                return false;
            }
        }
        System.out.println("[Inventory] All products are available");
        return true;
    }
    public void reserveStock(int orderId, Cart cart) {
        for (CartItem item : cart.getItems()) {
            int available = getStock(item.getProductName());
            stock.put(item.getProductName(), available - item.getQuantity());
        }
        reservations.put(orderId, cart);
        System.out.println("[Inventory] Stock reserved for order #" + orderId);
    }
    public void releaseStock(int orderId) {
        Cart cart = reservations.remove(orderId);
        if (cart == null) {
            System.out.println("[Inventory] No reservation found for order #" + orderId);
            return;
        }
        for (CartItem item : cart.getItems()) {
            int available = getStock(item.getProductName());
            stock.put(item.getProductName(), available + item.getQuantity());
        }
        System.out.println("[Inventory] Stock released for order #" + orderId);
    }
}
