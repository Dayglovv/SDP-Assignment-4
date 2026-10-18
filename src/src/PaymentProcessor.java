public class PaymentProcessor {
    public boolean processPayment(Customer customer, double amount) {
        if (customer.getBalance() < amount) {
            System.out.println("[Payment] Declined for " + customer.getName()
                    + ": need " + amount + ", balance is " + customer.getBalance());
            return false;
        }
        customer.setBalance(customer.getBalance() - amount);
        System.out.println("[Payment] Paid " + amount + " by " + customer.getName()
                + ", new balance: " + customer.getBalance());
        return true;
    }
    public void refundPayment(Customer customer, double amount) {
        customer.setBalance(customer.getBalance() + amount);
        System.out.println("[Payment] Refunded " + amount + " to " + customer.getName());
    }
}
