import java.time.LocalDate;

public class Main {
    public static void NormalTest() {
        Customer customer = new Customer("Mustafa", 1000);
        Cart cart = new Cart();

        Product cheese = new ShippableDecorator(
                new ExpirableDecorator(
                        new BasicProduct("Cheese", 100, 5),
                        LocalDate.now().plusDays(1)),
                200);

        Product biscuits = new ShippableDecorator(
                new ExpirableDecorator(
                        new BasicProduct("Biscuits", 150, 2),
                        LocalDate.now().plusDays(2)),
                700);

        Product tv = new ShippableDecorator(
                new BasicProduct("TV", 5000, 1),
                10000);

        Product scratchCard = new BasicProduct("Scratch Card", 50, 10);

        Product milk = new ExpirableDecorator(
                new BasicProduct("Milk", 70, 3),
                LocalDate.now().plusDays(2));

        cart.addProduct(cheese, 2);
        cart.addProduct(biscuits, 1);
        cart.addProduct(scratchCard, 1);
        cart.addProduct(milk, 1);

        CheckoutService.checkout(customer, cart);
    }

    public static void ExpiredProductTest() {
        Customer customer = new Customer("Mustafa", 1000);
        Cart cart = new Cart();

        Product expiredMilk = new ExpirableDecorator(
                new BasicProduct("Milk", 70, 3),
                LocalDate.now().minusDays(1) // Already expired
        );


        try {
            cart.addProduct(expiredMilk, 1);
            CheckoutService.checkout(customer, cart);
        } catch (IllegalStateException e) {
            System.out.println("Expected failure: " + e.getMessage());
        }
    }
    public static void OutOfStockTest() {
        Customer customer = new Customer("Mustafa", 1000);
        Cart cart = new Cart();

        Product scratchCard = new BasicProduct("Scratch Card", 50, 2);


        try {
            cart.addProduct(scratchCard, 5); // more than stock
            CheckoutService.checkout(customer, cart);
        } catch (IllegalStateException e) {
            System.out.println("Expected failure: " + e.getMessage());
        }
    }

    public static void InsufficientBalanceTest() {
        Customer customer = new Customer("Mustafa", 100); // not enough
        Cart cart = new Cart();

        Product tv = new ShippableDecorator(
                new BasicProduct("TV", 5000, 1),
                10000
        );


        try {
            cart.addProduct(tv, 1);
            CheckoutService.checkout(customer, cart);
        } catch (IllegalStateException e) {
            System.out.println("Expected failure: " + e.getMessage());
        }
    }

    public static void EmptyCartTest() {
        Customer customer = new Customer("Mustafa", 1000);
        Cart cart = new Cart(); // no items

        try {
            CheckoutService.checkout(customer, cart);
        } catch (IllegalStateException e) {
            System.out.println("Expected failure: " + e.getMessage());
        }
    }

    public static void NonShippableOnlyTest() {
        Customer customer = new Customer("Mustafa", 500);
        Cart cart = new Cart();

        Product ebook = new BasicProduct("E-Book", 100, 2);
        cart.addProduct(ebook, 2); // No shipping fee expected

        CheckoutService.checkout(customer, cart);
    }

    public static void main(String[] args) {
        System.out.println("Running Normal Test:");
        NormalTest();
        System.out.println("\nRunning Expired Product Test:");
        ExpiredProductTest();
        System.out.println("\nRunning Out of Stock Test:");
        OutOfStockTest();
        System.out.println("\nRunning Insufficient Balance Test:");
        InsufficientBalanceTest();
        System.out.println("\nRunning Empty Cart Test:");
        EmptyCartTest();
        System.out.println("\nRunning Non-Shippable Only Test:");
        NonShippableOnlyTest();
    }
}