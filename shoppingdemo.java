import java.util.*;

abstract class Product {
    String name;
    double price;
    int stock;

    Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    String getName(){
        return name;
    }
    double getPrice(){
        return price;
    }
    int getStock(){
        return stock;
    }

    public void reduceStock(int qty) throws OutOfStockException {
        if (qty > stock) {
            throw new OutOfStockException("Not enough stock for " + name);
        }
        stock -= qty;
    }

    public abstract double applyDiscount();
}

class Electronics extends Product {
    Electronics(String name, double price, int stock) {
        super(name, price, stock);
    }


    @Override
    public double applyDiscount() {
        return price * 0.9; // 10% discount
    }


   


}
 class Clothing extends Product {
    Clothing(String name, double price, int stock) {
        super(name, price, stock);
    }

    @Override
    public double applyDiscount() {
        return price * 0.8; // 20% discount
    }

   
}

class Groceries extends Product {
    Groceries(String name, double price, int stock) {
        super(name, price, stock);
    }

    @Override
    public double applyDiscount() {
        return price; // No discount
    }

 
}
class OutOfStockException extends Exception {
    OutOfStockException(String message) {
        super(message);
    }
}

class shoppingcart {
    private List<Product> cartItems;

    public shoppingcart() {
        cartItems = new ArrayList<>(); 
    }

    public void addProduct(Product p, int qty) throws OutOfStockException {
        p.reduceStock(qty);
        for (int i = 0; i < qty; i++) {
            cartItems.add(p);
        }
    }
    public void showCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("Your Cart:");
        Map<String, Integer> counts = new HashMap<>();
        for (Product p : cartItems) {
            counts.put(p.getName(), counts.getOrDefault(p.getName(), 0) + 1);
        }
        counts.forEach((k, v) -> System.out.println(k + " x " + v));
    }
    public void checkout() throws Exception {
        if (cartItems.isEmpty()) throw new Exception("Cart is empty!");
        double total = 0;
        for (Product p : cartItems) {
            total += p.applyDiscount();
        }
        System.out.println("Final Bill: Rs." + total);
    }

}
 
public class shoppingdemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Catalog of products
        List<Product> electronics = Arrays.asList(
                new Electronics("Laptop", 50000, 5),
                new Electronics("Phone", 30000, 10),
                new Electronics("TV", 40000, 3)
        );

        List<Product> clothing = Arrays.asList(
                new Clothing("Shirt", 1500, 20),
                new Clothing("Jeans", 2500, 15),
                new Clothing("Jacket", 5000, 5)
        );

        List<Product> grocery = Arrays.asList(
                new Groceries("Rice (5kg)", 400, 30),
                new Groceries("Milk (1L)", 50, 50),
                new Groceries("Bread", 40, 25)
        );

        shoppingcart cart = new shoppingcart();

        while (true) {
            System.out.println("\n--- Categories ---");
            System.out.println("1. Electronics\n2. Clothing\n3. Grocery\n4. Show Cart\n5. Checkout\n6. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            if (choice == 6) break;
            try {
                switch (choice) {
                    case 1 -> selectProduct(sc, electronics, cart);
                    case 2 -> selectProduct(sc, clothing, cart);
                    case 3 -> selectProduct(sc, grocery, cart);
                    case 4 -> cart.showCart();
                    case 5 -> cart.checkout();
                    default -> System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }

    private static void selectProduct(Scanner sc, List<Product> products, shoppingcart cart) throws Exception {
        System.out.println("\nAvailable Products:");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " - Rs." + p.getPrice() + " (Stock: " + p.getStock() + ")");
        }
        System.out.print("Choose product: ");
        int idx = sc.nextInt() - 1;
        if (idx < 0 || idx >= products.size()) throw new Exception("Invalid product choice!");

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        cart.addProduct(products.get(idx), qty);
        System.out.println("Added to cart!");
    }
}