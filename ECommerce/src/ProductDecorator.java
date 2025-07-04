public abstract class ProductDecorator extends Product {
    protected Product product;

    public ProductDecorator(Product product) {
        super(product.name, product.price, product.quantity);
        this.product = product;
    }

    @Override
    public String getName() {
        return product.getName();
    }
    @Override
    public double getPrice() {
        return product.getPrice();
    }
    @Override
    public int getQuantity() {
        return product.getQuantity();
    }
    @Override
    public void reduceQuantity(int amount) {
        product.reduceQuantity(amount);
    }
}