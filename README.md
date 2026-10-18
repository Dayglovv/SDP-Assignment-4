# OrderProcessingFacade

## Project description
A simple online order processing system written in Java (JDK 17).
The program checks stock, reserves products, takes payment, creates a shipment
and notifies the customer. All of this is hidden behind one class: `OrderFacade`.
Payments and delivery are simulated (no real payment provider or delivery company).

## What is the Facade Pattern?
Facade is a structural design pattern. It gives one simple class with simple methods
(here `placeOrder()` and `cancelOrder()`) in front of a group of complex classes.
The client does not need to know which services exist or in what order to call them.
It only talks to the Facade.

## Classes and their responsibilities
| Class | Responsibility |
|---|---|
| `Main` | Client. Runs the test scenarios and uses only `OrderFacade`. |
| `OrderFacade` | Facade. Calls all services in the right order, does rollback. |
| `InventoryService` | Checks stock, reserves products, releases reservations. |
| `PaymentProcessor` | Takes payment from the customer's balance, declines if not enough money, refunds. |
| `ShippingService` | Creates a shipment with tracking number, stores its status, cancels it. |
| `NotificationService` | Sends (prints) messages to the customer and stores them. |
| `Cart`, `CartItem` | Shopping cart and its items. |
| `Customer` | Customer name, email and card balance. |
| `Order`, `OrderStatus` | Order data and its current status. |

## How to run
**IntelliJ IDEA:** open the project, make sure JDK 17 is selected, open `src/Main.java` and press Run.

**Command line:**
```
cd src
javac -d ../out *.java
java -cp ../out Main
```

## Example output

Successful order:
```
--- Placing order #1 ---
[Inventory] All products are available
[Inventory] Stock reserved for order #1
[Payment] Paid 1250.0 by Alice, new balance: 750.0
[Shipping] Shipment TRK1000 created for order #1 (Alice)
[Notification] To alice@mail.com: Order #1 is confirmed. Tracking number: TRK1000
Result: SHIPPED
```

Failed payment (rollback):
```
--- Placing order #2 ---
[Inventory] All products are available
[Inventory] Stock reserved for order #2
[Payment] Declined for Bob: need 2400.0, balance is 100.0
[Inventory] Stock released for order #2
[Notification] To bob@mail.com: Order #2 failed: payment was declined.
Result: PAYMENT_FAILED
```
Note that `ShippingService` is not called in the second case.
