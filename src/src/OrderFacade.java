import java.util.HashMap;
import java.util.Map;
public class OrderFacade {
    private InventoryService inventoryService;
    private PaymentProcessor paymentProcessor;
    private ShippingService shippingService;
    private NotificationService notificationService;
    private Map<Integer, Order> orders = new HashMap<>();
    private int nextOrderId = 1;
    public OrderFacade() {
        this.inventoryService = new InventoryService();
        this.paymentProcessor = new PaymentProcessor();
        this.shippingService = new ShippingService();
        this.notificationService = new NotificationService();
    }
    public void addProduct(String productName, int quantity) {
        inventoryService.addProduct(productName, quantity);
    }
    public Order placeOrder(Cart cart, Customer customer) {
        Order order = new Order(nextOrderId, cart, customer);
        nextOrderId++;
        orders.put(order.getId(), order);
        System.out.println("--- Placing order #" + order.getId() + " ---");
        if (!inventoryService.checkAvailability(cart)) {
            order.setStatus(OrderStatus.OUT_OF_STOCK);
            notificationService.sendNotification(customer,
                    "Order #" + order.getId() + " failed: some products are out of stock.");
            return order;
        }

        // 2. reserve products
        inventoryService.reserveStock(order.getId(), cart);

        // 3. process payment
        boolean paid = paymentProcessor.processPayment(customer, cart.getTotalPrice());
        if (!paid) {
            // rollback: give the reserved products back
            inventoryService.releaseStock(order.getId());
            order.setStatus(OrderStatus.PAYMENT_FAILED);
            notificationService.sendNotification(customer,
                    "Order #" + order.getId() + " failed: payment was declined.");
            return order;
        }
        order.setStatus(OrderStatus.PAID);

        // 4. create shipment
        String trackingNumber = shippingService.createShipment(order);
        order.setTrackingNumber(trackingNumber);
        order.setStatus(OrderStatus.SHIPPED);

        // 5. notify the customer
        notificationService.sendNotification(customer,
                "Order #" + order.getId() + " is confirmed. Tracking number: " + trackingNumber);
        return order;
    }

    public boolean cancelOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order #" + orderId + " not found");
            return false;
        }
        if (order.getStatus() != OrderStatus.SHIPPED) {
            System.out.println("Order #" + orderId + " cannot be cancelled (status: "
                    + order.getStatus() + ")");
            return false;
        }
        System.out.println("--- Cancelling order #" + orderId + " ---");
        shippingService.cancelShipment(order.getTrackingNumber());
        paymentProcessor.refundPayment(order.getCustomer(), order.getCart().getTotalPrice());
        inventoryService.releaseStock(orderId);
        order.setStatus(OrderStatus.CANCELLED);
        notificationService.sendNotification(order.getCustomer(),
                "Order #" + orderId + " was cancelled and your money was refunded.");
        return true;
    }
    public String getOrderStatus(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            return "NOT FOUND";
        }
        return order.getStatus().toString();
    }
}
