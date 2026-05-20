import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsDemo {

    static final List<Product> PRODUCTS = List.of(
        new Product("Laptop Pro",        "Electronics", 1299.99,  5),
        new Product("Wireless Mouse",    "Electronics",   29.99, 42),
        new Product("USB-C Hub",         "Electronics",   49.99,  0),   // out of stock
        new Product("Clean Code",        "Books",          35.00, 18),
        new Product("Effective Java",    "Books",          45.00, 12),
        new Product("OCP Study Guide",   "Books",          55.00,  7),
        new Product("Chef Knife",        "Kitchen",        89.99,  3),
        new Product("Cast Iron Pan",     "Kitchen",        39.99,  0),   // out of stock
        new Product("Cutting Board",     "Kitchen",        22.50, 15),
        new Product("Running Jacket",    "Clothing",       75.00,  9),
        new Product("Cotton T-Shirt",    "Clothing",       18.99, 30)
    );

    public static void main(String[] args) {

        /**
         * STREAMS
         *      - not a data structure, does not store data
         *      - wrap around a data structure and describe what to do with the data inside
         *          - go through each element in sequence to aggregate that data
         */
        //creatingStreams();
        streamFunctions();
        


    }

    static void creatingStreams() {
        // can create streams directly
        Stream<String> letters = Stream.of("a", "b", "c", "d");

        // can create streams from any Collection
        Stream<Product> productsStream = PRODUCTS.stream();

        // can create a stream with Stream.iterate(). - it is INFINITE - use .limit() to prevent that
        // limit() will run the iterate a certain number of times
        Stream.iterate(1, num -> num * 2)
            .limit(12)
            .forEach(element -> System.out.println(element));       // for each uses a Consumer on each element of the stream

        // could use a Predicate in the middle argument instead of .limit()
        // the predicate with run until the value of iterate is some value
        Stream.iterate(1, i -> i <= 2048, num -> num * 2)
            .forEach(element -> System.out.println(element)); 

        // this is similar to above, but without streams
        int count = 1;
        while(true) {
            count *= 2;
            System.out.println(count);
            if(count >= 2048) {
                break;
            }
        }

        // .generate creates a Stream of Suppliers - NEED to use .limit() or it will run infinitely
        System.out.println("Random Numbers:");
        Stream.generate(Math::random)
            .limit(10)
            .map(d -> String.format("  %.4f", d))
            .forEach(System.out::println);


        // Streams are single-use
        System.out.println("All Products:");
        productsStream.forEach(System.out::println);

        // IllegalStateException since productsStream has already been used above
        try {
            productsStream
            .map(product -> product.price() * 2)
            .forEach(System.out::println);
        } catch(IllegalStateException e) {
            System.out.println("Using a used stream throws IllegalStateException.");
        }

        // Streams CAN modify underlying collections if they're mutable
        List<People> people = new LinkedList<>();
        people.add(new People("Austin", 26));
        people.add(new People("John", 23));
        people.add(new People("Onyx", 25));
        people.add(new People("Gina", 23));

        // will modiify the underlying collection
        people.stream()
            .forEach(person -> person.setAge(50));

        // will not modify the underlying collection
        people.stream()
            .forEach(person -> new People(person.getName(), 50));

        System.out.println(people);
    }


    static void streamFunctions() {

        /**
         * FILTER
         *      - takes in a Predicate and returns a new Stream
         *      - any element in the existing stream that returns true from the predicate, will be added to the returned stream
         */
        PRODUCTS.stream()
            .filter(product -> product.price() < 50)
            .forEach(System.out::println);


        /**
         * MAP
         *      - takes in a Function and returns a new Stream (usually of a different type)
         *      - takes each element in a stream and transforms it in some way, then adds it to a new stream that is returned
         */
        PRODUCTS.stream()
            .filter(product -> product.price() < 50)

            // Stream<Product> converted into Stream<String>
            .map(product -> String.format("%s is $%.2f.", product.name(), product.price()))
            .forEach(System.out::println);

        PRODUCTS.stream()
            // Stream<Product> converted into Stream<Product> where each new product has 50 more stock
            .map(product -> new Product(product.name(), product.category(), product.price(), product.stock() + 50))
            .forEach(System.out::println);

        // if the lambda calls a function that throws an exception, the try-catch will be inside the lambda
        PRODUCTS.stream()
            .forEach(product -> {
                try {
                    product.badFunction();
                } catch (Exception e) {
                    System.out.println("Exception caught.");
                }
            });


        /**
         * Stream Pipeline
         *      - List<Product> -> Stream<Product> -> (filter) Stream<Product> -> (map) Stream<String>  
         *      - pipeline isn't executed UNTIL some terminal operation occurs (forEach).
         */
        
        /**
         * TERMINAL OPERATIONS
         *      - functions that conclude a Stream and force the pipeline to execute
         *      - these are Stream functions that DO NOT return another stream
         *  
         *      - forEach
         *      - collect
         *      - count
         *      - findFirst
         *      - findAny
         *      - anyMatch
         *      - allMatch
         *      - noneMatch
         */

        // collect - converts a stream into a NEW collection
        List<Product> highEndProducts = PRODUCTS.stream()
            .filter(product -> product.price() > 50)
            .collect(Collectors.toList());      
        highEndProducts.stream().forEach(System.out::println);

        List<Product> lowEndProducts = PRODUCTS.stream()
            .filter(product -> product.price() < 50)
            .collect(Collectors.toUnmodifiableList());          // immutable collection instead
        lowEndProducts.stream().forEach(System.out::println);

        List<Product> inStockProducts = PRODUCTS.stream()
            .filter(product -> product.stock() > 0)
            .toList();                                          // also returns an immutable List
        inStockProducts.stream().forEach(System.out::println);

        // count - returns the number of elements that were in the most recent stream
        long numInStockProducts = PRODUCTS.stream()
            .filter(product -> product.stock() > 0)
            .count();
        System.out.println("Number of in stock products: " + numInStockProducts);

        // findFirst - returns an Optional for the first element in the stream
        Optional<Product> firstBook = PRODUCTS.stream()
            .filter(product -> product.category().equals("Books"))
            .findFirst();

        // Optionals may or may not contain a value, so check if a value is present before getting
        if(firstBook.isPresent()){
            Product book = firstBook.get();     // throws NoSuchElementException if value is not present
            System.out.println("First book: " + book);
        }
        else {
            System.out.println("No books found.");
        }

        // findAny - returns an Optional of a random element from the stream
        Product randomBook = PRODUCTS.stream()
            .filter(product -> product.category().equals("Books"))
            .findAny()

            // orElse can be used to process an Optional immediately. 
            // Either returns the value of the optional, or the default provided to it
            .orElse(new Product("Default", "Books", 0.0, 0));
        System.out.println("Random book: " + randomBook);


        // anyMatch - returns boolean based on some Predicate if any value in teh stream returns true
        boolean anyItemOOS = PRODUCTS.stream().anyMatch(product -> product.stock() == 0);

        // allMatch - returns boolean based on some Predicate if ALL values in the stream return true
        boolean allItemsInStock = PRODUCTS.stream().allMatch(product -> product.stock() != 0);

        // noneMatch - returns boolean based on some Predicate if NO values in the stream return true
        boolean allItemsOOS = PRODUCTS.stream().noneMatch(product -> product.stock() != 0);

        System.out.printf("Product Summary:\nAny Item OOS?\t\t %b\nAll Items In Stock?\t %b\nAll Items OOS?\t\t %b\n", anyItemOOS, allItemsInStock, allItemsOOS);
    }


}
