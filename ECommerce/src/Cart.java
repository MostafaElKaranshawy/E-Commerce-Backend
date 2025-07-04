import java.util.HashMap;

public class Cart {
    private HashMap<Product, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }

    public void addProduct(Product p, int amount) {
        if (amount > p.getQuantity())
            throw new IllegalStateException("Insufficient product quantity");

        items.put(p, items.getOrDefault(p, 0) + amount);
    }

    public HashMap<Product, Integer> getItems() {
        return items;
    }
}