public class ShippableDecorator extends ProductDecorator implements ShippableProduct {
    private double weight;

    public ShippableDecorator(Product product, double weight) {
        super(product);
        this.weight = weight;
    }

    @Override
    public boolean requiresShipping() {
        return true;
    }

    @Override
    public boolean isExpired() {
        return product.isExpired();
    }

    @Override
    public double getWeight() {
        return weight;
    }
}