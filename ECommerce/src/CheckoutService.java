import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckoutService {
    public static void checkout(Customer customer, Cart cart) {
        if (cart.getItems().isEmpty()) throw new IllegalStateException("Cart is empty");

        double subtotal = 0, TotalWeight = 0;
        List<ShippableProduct> shippables = new ArrayList<>();

        for (HashMap.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int amount = entry.getValue();

            if (p.isExpired()) throw new IllegalStateException(p.getName() + " is expired");
            if (amount > p.getQuantity()) throw new IllegalStateException(p.getName() + " is out of stock");

            subtotal += p.getPrice() * amount;
            if (p.requiresShipping() && p instanceof ShippableProduct) {
                for (int i = 0; i < amount; i++)
                    shippables.add((ShippableProduct)p);

                TotalWeight += ((ShippableProduct) p).getWeight() * amount;
            }
        }

        double shipping = shippables.isEmpty() ?
                0 : (TotalWeight/1000.0) * ShippingService.getShippingRate() + ShippingService.getShippingFee();
        double total = subtotal + shipping;

        if (customer.getBalance() < total)
            throw new IllegalStateException("Insufficient balance");

        ShippingService.ship(shippables);

        System.out.println("** Checkout receipt **");
        System.out.printf("%-20s %-5s %10s%n", "Product", "Qty", "Total");
        System.out.println("----------------------------------------");

        for (HashMap.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int amount = entry.getValue();
            System.out.printf("%-20s %2dx   %10.2f%n", p.getName(), amount, p.getPrice() * amount);
            p.reduceQuantity(amount);
        }

        System.out.println("----------------------------------------");
        System.out.printf("%-26s %10.2f%n", "Subtotal", subtotal);
        System.out.printf("%-26s %10.2f%n", "Shipping", shipping);
        System.out.println("----------------------------------------");
        System.out.printf("%-26s %10.2f%n", "Amount", total);
        customer.deductAmount(total);
        System.out.println("----------------------------------------");
        System.out.printf("%-26s %10.2f%n", "Remaining Balance", customer.getBalance());

    }
}