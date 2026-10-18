public class Main {
    public static void main(String[] args) {
        OrderFacade orderFacade = new OrderFacade();

        orderFacade.addProduct("Laptop", 5);
        orderFacade.addProduct("Mouse", 10);

        System.out.println("===== Test 1: Successful order =====");
        Customer alice = new Customer("Alice", "alice@mail.com", 2000);
        Cart cart1 = new Cart();
        cart1.addItem("Laptop", 1, 1200);
        cart1.addItem("Mouse", 2, 25);
        Order order1 = orderFacade.placeOrder(cart1, alice);
        System.out.println("Result: " + order1.getStatus());
        System.out.println();

        System.out.println("===== Test 2: Failed payment =====");
        Customer bob = new Customer("Bob", "bob@mail.com", 100);
        Cart cart2 = new Cart();
        cart2.addItem("Laptop", 2, 1200);
        Order order2 = orderFacade.placeOrder(cart2, bob);
        System.out.println("Result: " + order2.getStatus());
        System.out.println();

        System.out.println("===== Test 3: Out of stock =====");
        Cart cart3 = new Cart();
        cart3.addItem("Mouse", 50, 25);
        Order order3 = orderFacade.placeOrder(cart3, alice);
        System.out.println("Result: " + order3.getStatus());
        System.out.println();

        System.out.println("===== Test 4: Cancel order =====");
        orderFacade.cancelOrder(order1.getId());
        System.out.println("Result: " + orderFacade.getOrderStatus(order1.getId()));
    }
}
