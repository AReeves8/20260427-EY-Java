import java.util.LinkedList;
import java.util.List;

public class ProductRegistry {
    /**
     * Product Registry exists to keep a master list of all products and to audit any changes to that list
     */
    private List<Product> productList;



    


    /**
     * Singleton Design Pattern
     *      - ensure there will only ever be ONE instance of an object
     *      
     *      - private constructor
     *      - create a static instance variable of the class
     *      - create a getInstance() that returns the instance
     */
    private static ProductRegistry instance = null;

    // Eager Loading - create something as soon as possible so it is ready to use when needed
    // static {
    //     instance = new ProductRegistry();
    // }


    private ProductRegistry() {
        productList = new LinkedList<>();
    }

    public static ProductRegistry getInstance() {

        // Lazy Loading - only create something, when it is asked for
        if(instance == null) {
            instance = new ProductRegistry();
        }
        return instance;
    }



    // all products get registered with this method
    public void registerProduct(int id, String name, double price) {

        for(Product product : productList) {
            if(product.id() == id) {
                return;     // return early if the new product already exists
            }
        }

        productList.add(new Product(id, name, price));

    }

    
    @Override
    public String toString() {
        return "ProductRegistry [productList=" + productList + "]";
    }

    public static void main(String[] args) {

        ProductRegistry registry1 = ProductRegistry.getInstance();
        registry1.registerProduct(1, "Laptop", 999.99);
        registry1.registerProduct(2, "Monitor", 99.99);
        registry1.registerProduct(3, "RAM", 9999999999.99);
        registry1.registerProduct(1, "GPU", 99999999.99);
        System.out.println(registry1.toString());


        // is the same object as registry1 so the Product List will be the same
        ProductRegistry registry2 = ProductRegistry.getInstance();
        System.out.println(registry2.toString());

    }
}
