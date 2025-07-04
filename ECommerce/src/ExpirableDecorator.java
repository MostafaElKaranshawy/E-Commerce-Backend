import java.time.LocalDate;

public class ExpirableDecorator extends ProductDecorator {
    private LocalDate expiryDate;

    public ExpirableDecorator(Product product, LocalDate expiryDate) {
        super(product);
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public boolean requiresShipping() {
        return product.requiresShipping();
    }
}