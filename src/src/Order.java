public class Order {
    private int id;
    private Cart cart;
    private Customer customer;
    private OrderStatus status;
    private String trackingNumber;
    public Order(int id, Cart cart, Customer customer) {
        this.id = id;
        this.cart = cart;
        this.customer = customer;
        this.status = OrderStatus.NEW;
    }
    public int getId() {
        return id;
    }
    public Cart getCart() {
        return cart;
    }
    public Customer getCustomer() {
        return customer;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public String getTrackingNumber() {
        return trackingNumber;
    }
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}

