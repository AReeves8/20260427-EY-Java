import java.util.LinkedList;
import java.util.List;

public class Generics {

    /**
     * GENERICS
     *      - placeholder data types
     * 
     *      - you can really use any letter you want, these are some common ones:
     *          - T: some general type
     *          - E: element (used by Collections)
     *          - K/V: key/value (used by Maps)
     *          - F/S: first/second.
     *          - ?: wildcard. "any type" but can only be used for READ operations
     * 
     *      - introduced in Java 4
     *          - before, all collections were of type Object
     *              - this made catching data types a runtime issue
     *          - generic data types CAN be checked at compile time
     * 
     *      - TYPE ERASURE
     *          - during compile time, java will remove data types, and only look at the raw type
     *              - that way, code will be compatible on older java versions
     *              - LinkedList<String> -> LinkedList
     * 
     */



    public static void main(String[] args) {

        // collections all use Generic types
        LinkedList<String> test = new LinkedList<>();
        test.add("Hello");
        test.add("Hi");
        test.add("Howdy");
        printList(test);

        LinkedList<Integer> test2 = new LinkedList<>();
        test2.add(1);
        test2.add(2);
        test2.add(3);
        printList(test2);



        Box<String> stringBox = new Box<>();
        stringBox.setValue("Austin");

        Box<Double> doubleBox = new Box<>();
        doubleBox.setValue(5677.345678);


        NumberBox<Integer> numberBox = new NumberBox<>();
        numberBox.setNum(3);

        // Invalid data type: will not compile.
        // NumberBox<String> numberBox = new NumberBox<>();
        // numberBox.setNum("some string");


    } 

    // can use wildcard to sit in place of a real data type, but cannot reference it as a type
    public static void printList(List<?> list) {
        for(Object item : list) {
            System.out.println(item);
        }
    }

}

