import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamsDemo {

    static final List<Product> PRODUCTS = List.of(
        new Product("Laptop Pro",        "Electronics", 1299.99,  5),
        new Product("Wireless Mouse",    "Electronics",   29.99, 42),
        new Product("USB-C Hub",         "Electronics",   49.99,  0),       // out of stock
        new Product("Clean Code",        "Books",          35.00, 18),
        new Product("Effective Java",    "Books",          45.00, 12),
        new Product("OCP Study Guide",   "Books",          55.00,  7),
        new Product("Chef Knife",        "Kitchen",        89.99,  3),
        new Product("Cast Iron Pan",     "Kitchen",        39.99,  0),      // out of stock
        new Product("Cutting Board",     "Kitchen",        22.50, 15),
        new Product("Running Jacket",    "Clothing",       75.00,  9),
        new Product("Cotton T-Shirt",    "Clothing",       18.99, 30),
        new Product("Cast Iron Pan",     "Kitchen",        39.99,  0),      // dupe
        new Product("Cutting Board",     "Kitchen",        22.50, 15),      // dupe
        new Product("Cutting Board",     "Kitchen",       122.50, 15),      // dupe but different price
        new Product("Running Jacket",    "Clothing",       75.00,  9)       // dupe
    );

    public static void main(String[] args) {

        /**
         * STREAMS
         *      - not a data structure, does not store data
         *      - wrap around a data structure and describe what to do with the data inside
         *          - go through each element in sequence to aggregate that data
         */
        //creatingStreams();
        //streamFunctions();
        //collectingStreams();
        parallelStreams();
        


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


    static void collectingStreams() {

        /**
         * COLLECTORS
         *      - help turn streams into collections
         * 
         *      - toList()
         *      - toUnmodifiableList()
         *      - toSet()
         *      - toMap()
         * 
         *      - groupingby()      
         *      - joining()
         *      - summarizingDouble()
         */

        // toSet - no dupes and insertion order not retained
        System.out.println("--- TO SET ---");
        Set<Product> uniqueProducts = PRODUCTS.stream().collect(Collectors.toSet());
        System.out.println(uniqueProducts);

        // toMap - throws IllegalStateException if duplicate keys are found
        // Map<String, Double> nameToPriceMap = PRODUCTS.stream()
        //     .collect(Collectors.toMap(Product::name, Product::price));
        //System.out.println(nameToPriceMap);

        // toMap with merge function - tells the Collector how to merge duplicate keys
        System.out.println("--- TO MAP ---");
        Map<String, Double> nameToPriceMap = PRODUCTS.stream()
            .collect(Collectors.toMap(Product::name, Product::price, Double::max));
        System.out.println(nameToPriceMap);

        // groupingBy - creates a map that has products sorted by some grouping
        System.out.println("--- GROUPING BY ---");
        Map<String, List<Product>> productCategories = PRODUCTS.stream()
            .collect(Collectors.groupingBy(Product::category));
        System.out.println(productCategories);

        // can add a DOWN STREAM COLLECTOR to consolidate the list
        Map<String, Long> productCategoriesCount = PRODUCTS.stream()
            .collect(Collectors.groupingBy(Product::category, Collectors.counting()));
        System.out.println(productCategoriesCount);

        Map<String, Double> productCategoriesAvgPrice = PRODUCTS.stream()
            .collect(Collectors.groupingBy(Product::category, Collectors.averagingDouble(Product::price)));
        System.out.println(productCategoriesAvgPrice);

        // can do nested groupings to consolidate data based on multiple factors
        Map<String, Map<String, Long>> productCategoriesStockCount = PRODUCTS.stream()
            .collect(Collectors.groupingBy(
                Product::category, 
                Collectors.groupingBy(
                    product -> product.stock() > 0 ? "In Stock" : "Out of Stock", 
                    Collectors.counting()
                )));
        System.out.println(productCategoriesStockCount);

        // joining - joins your collection into a single String
        System.out.println("--- JOINING ---");
        String productNamesPrexifSuffix = PRODUCTS.stream()
            .map(Product::name)
            // prefix and suffix go at the beginning and end of the entire string
            .collect(Collectors.joining(",", "\t", "\n"));  
        System.out.println(productNamesPrexifSuffix);

        String productNames = PRODUCTS.stream()
            .map(Product::name)
            // prefix and suffix go at the beginning and end of the entire string
            .collect(Collectors.joining(", "));
        System.out.println(productNames);


        System.out.println("--- SUMMARIZING DOUBLE ---");
        DoubleSummaryStatistics priceStats = PRODUCTS.stream()
            .collect(Collectors.summarizingDouble(Product::price));

        System.out.println("Count: " + priceStats.getCount());
        System.out.println("Sum: " + priceStats.getSum());
        System.out.println("Min: " + priceStats.getMin());
        System.out.println("Max: " + priceStats.getMax());
        System.out.println("Average: " + priceStats.getAverage());
        System.out.println("Manual Average: " + (priceStats.getSum() / priceStats.getCount()) );

    }


    static void parallelStreams() {

        /**
         * PARALLEL STREAMS
         *      - multiple streams running in parallel
         *      - multithreading with streams - no thread management
         *      - much faster for larger workloads
         * 
         * 
         *      - adds/removes/manages threads from ForkJoinPool 
         *      - you DO still need to worry about thread safety. 
         */

        int largeNum = 500000000;

        long t0 = System.currentTimeMillis();
        // All the primitive classes have ways to make streams of them (IntStream, LongStream, CharStream, etc.)
        long longSeq = LongStream.rangeClosed(1, largeNum)
            .map(i -> i * i % 10000007)
            .sum();
        long t1 = System.currentTimeMillis();
        System.out.println("Operation took " + (t1 - t0) + " milliseconds. SUM: " + longSeq);

         // .parallel() - creates parallel streams - copmutation will be split across CPU cores
        long t2 = System.currentTimeMillis();
        long longSeq2 = LongStream.rangeClosed(1, largeNum)
            .parallel()        
            .map(i -> i * i % 10000007)
            .sum();
        long t3 = System.currentTimeMillis();
        System.out.println("Parallel Operation took " + (t3 - t2) + " milliseconds. SUM: " + longSeq2);

        // be careful mixing .parallel and .sequential - which ever happens LAST on the pipeline will control the pipeline
        long t4 = System.currentTimeMillis();
        long longSeq3 = LongStream.rangeClosed(1, largeNum)
            .parallel()        
            .map(i -> i * i % 10000007)
            .sequential()
            .sum();
        long t5 = System.currentTimeMillis();
        System.out.println("Parallel/Sequential Operation took " + (t5 - t4) + " milliseconds. SUM: " + longSeq3);

        long t6 = System.currentTimeMillis();
        long longSeq4 = LongStream.rangeClosed(1, largeNum)
            .sequential()        
            .map(i -> i * i % 10000007)
            .parallel()
            .sum();
        long t7 = System.currentTimeMillis();
        System.out.println("Sequential/Parallel Operation took " + (t7 - t6) + " milliseconds. SUM: " + longSeq4);

        System.out.println("\n--- THREAD SAFETY ---");
        List<String> productNames = new ArrayList<>();
        PRODUCTS.parallelStream()
            .map(Product::name)
            .forEach(productNames::add);        // collection is not thread safe, some items may be missing
        System.out.println("UNSAFE: " + productNames);

        List<String> safeProductNames = PRODUCTS.parallelStream()
            .map(Product::name)
            .collect(Collectors.toList());      // Collectors.toList will be parallel stream safe
        System.out.println("SAFE: " + safeProductNames);

        // can also use a thread-safe collection to avoid issues
        ArrayBlockingQueue<String> productNamesQueue = new ArrayBlockingQueue<>(15);
        PRODUCTS.parallelStream()
            .map(Product::name)
            .forEach(product -> {
                try {
                    productNamesQueue.put(product);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });        
        System.out.println("QUEUE: " + productNamesQueue);

    }
}
