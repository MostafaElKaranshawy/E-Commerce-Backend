import java.util.List;

public class ShippingService {
    private static final double SHIPPING_RATE = 10.0; // Shipping rate per kg
    private static final double SHIPPING_FEE = 20.0; // Shipping rate per kg

    public static double getShippingRate() {
        return SHIPPING_RATE;
    }
    public static double getShippingFee() {
        return SHIPPING_FEE;
    }

    public static void ship(List<ShippableProduct> Products) {
        double totalWeight = 0;
        System.out.println("** Shipment notice **");
        System.out.printf("%-20s %10s%n", "Product", "Weight");

        for (ShippableProduct Product : Products) {
            System.out.printf("%-20s %7.0fg%n", Product.getName(), Product.getWeight());
            totalWeight += Product.getWeight();
        }

        System.out.printf("%n%-20s %7.2fkg%n", "Total package weight", totalWeight / 1000.0);

    }
}