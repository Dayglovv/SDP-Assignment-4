import java.util.ArrayList;
import java.util.List;
public class Cart {
    private List<CartItem> items = new ArrayList<>();
    public void addItem(String productName, int quantity, double price) {
        items.add(new CartItem(productName, quantity, price));
    }
    public List<CartItem> getItems(){
            return items;
    }
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
