import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * JAVA COLLECTIONS
 * 
 *      Iterable
 *          - Collection
 *              - List                  -> ordered, indexed, allow duplicates
 *                  - ArrayList
 *                  - LinkedList
 *              - Set                   -> unordered, not indexed, do not allow duplicates
 *                  - HashSet       
 *                  - LinkedHashSet     -> preserves insertion order
 *                  - TreeSet           -> sorted order
 *              - Queue
 *                  - can treat other data structures as a Queue (like a linked list)
 *                  - PriorityQueue
 *                  - Dequeue
 * 
 *      Map                             -> key-value pairs, unordered
 *          - HashMap                   
 *          - LinkedHashMap             -> preserves insertion order 
 *          - TreeMap                   -> sorted order
 * 
 */
public class Lists {

    public static void main(String[] args) {


        
        /**
         * ----- ARRAY LISTS -----
         * 
         *      - backed by a resizable array
         *          - default size is 10 (pre-allocated memory)
         *              - .size() on a newly created ArrayList with no elements -> returns 0
         *          - grows by 50% (or 1.5x) when capacity is reached
         *              - create a new array with the new increased capacity, then copy everything over
         * 
         *      - indexed means O(1) access for elements
         *      - inserting in the middle is O(n) because you have to shift the rest of the elements back one
         *      
         *      - BEST when you know you will be accessing elements frequently 
         *          or when you know the exact number of elements you will hold
         */


        List<String> fruits = new ArrayList<>();
        fruits.add("peach");         // add elements with .add()
        fruits.add("dragonfruit"); 
        fruits.add("banana"); 
        fruits.add("orange"); 
        fruits.add("mango"); 

        // .get() to retreive the value at an index
        System.out.println("Fruit at index 3: " + fruits.get(3));    
        
        // .size() to see the umber of elements added to the list
        System.out.println("# of Fruits in the Smoothie: " + fruits.size());   

        // .contains() to see if a value exists
        System.out.println("Smoothie has apple?: " + fruits.contains("apple"));

        // .remove() to take an element out - can give it a value or an index
        fruits.remove("mango");   
        fruits.remove(1);    

        System.out.println("FRUITS ARRAY: " + fruits);

        // collections only work with objects, not primitives
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 2, 3, 2, 3));      // can initialize a list with another list
        numbers.remove(2);                   // 2 is an int, so it will remove by index
        System.out.println("NUMBERS 1: " + numbers);
        
        // removing by value ONLY removes the first found instance
        numbers.remove(Integer.valueOf(2));      // creates an object to remove by value
        System.out.println("NUMBERS 2: " + numbers);


        // 2D List
        List<List<String>> arrayList2d = new ArrayList<>();
        arrayList2d.add(new ArrayList<>());
        arrayList2d.add(new LinkedList<>());


        /**
         * ----- LINKED LISTS -----
         *      - backed by a doubly linked list
         *          - o <-> o <-> o <-> o
         *          - each node has three things: a value, a reference to the next node, and a reference to the previous node
         * 
         *      - no indexes
         *          - accessing elements is slow
         *          - you have to start at the neginning, and to through each ref until you find the value
         * 
         *      - fast inserts because all you have to do is update references of the nodes around it
         * 
         *      - most utility methods come from List and therefore are similar to ArrayList
         */

        List<String> names = new LinkedList<>(); 
        names.add("Gina");
        names.addFirst("John");         // unique to LinkedList - adds at beginning is list
        names.addLast("Sandhya");       // unique to LinkedList - adds at end of list
        names.add("Andrew");            // .add() inserts at the end of the list anyways
        System.out.println("NAME: " + names);
    }

}
