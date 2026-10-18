import java.util.HashMap;
import java.util.Map;
public class ShippingService {
    private int nextTrackingNumber = 1000;
    private Map<String, String> shipments = new HashMap<>();
    public String createShipment(Order order) {
        String trackingNumber = "TRK" + nextTrackingNumber;
        nextTrackingNumber++;
        shipments.put(trackingNumber, "CREATED");
        System.out.println("[Shipping] Shipment " + trackingNumber + " created for order #"
                + order.getId() + " (" + order.getCustomer().getName() + ")");
        return trackingNumber;
    }
    public String getShipmentStatus(String trackingNumber) {
        return shipments.getOrDefault(trackingNumber, "NOT FOUND");
    }
    public void cancelShipment(String trackingNumber) {
        if (shipments.containsKey(trackingNumber)) {
            shipments.put(trackingNumber, "CANCELLED");
            System.out.println("[Shipping] Shipment " + trackingNumber + " cancelled");
        }
    }
}
