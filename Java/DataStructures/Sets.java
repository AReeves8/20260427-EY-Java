import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class Sets {

    public static void main(String[] args) {

        // rather than sifting through a collection of data to remove duplicates manually, just add everyhting to a Set!
        String[] fruits = {"banana", "orange", "strawberry", "apple", "apple", "apple", "strawberry", "grapes"};

        /**
         * HashSet 
         *      - backed by a HashMap
         *      - inserted elements will be "hashed" to determine their location in the set
         *          - not guarenteed to match insertion order
         *      - O(1) access time
         */
        Set<String> fruitSet = new HashSet<>(Arrays.asList(fruits));

        // order that is printed, doesn't always match the REAL order of elements behind the scenes
        System.out.println("FRUIT SET: " + fruitSet);           
        System.out.println("FRUIT SET SIZE: " + fruitSet.size());

        fruitSet.add("dragonfruit");
        fruitSet.add("banana");         // will not be added since dupe
        System.out.println("NEW FRUIT SET: " + fruitSet);

        // enhanced for loops work on Sets, but standard for loops do not work since they are not indexed
        System.out.println("FRUIT LOOP:");  
        for(String fruit : fruitSet) {              
            System.out.println("FRUIT: " + fruit);  
        }

        // can use iterators to loop through sets
        Iterator<String> fruitItr = fruitSet.iterator();
        while(fruitItr.hasNext()) {
            String fruit = fruitItr.next();
            System.out.println("FRUIT ITR: " + fruit);  
        }


        /**
         * LinkedHashSet
         *      - maintain insertion order
         *      - slightly slower than a HashSet
         */
        Set<String> linkedFruitSet = new LinkedHashSet<>(Arrays.asList(fruits));
        linkedFruitSet.add("dragonfruit");      // will always be added to end 
        
        // GUARENTEED to match insertion order
        System.out.println("LINKED FRUIT SET: " + linkedFruitSet);


        /**
         * TreeSet
         *      sorts value based on natural ordering 
         *          - if the object implements compareTo, it will use that
         *              - otherwise, you can give it a Comparator and the TreeSet will use that
         */

        Student alice = new Student("Alice", 3.2);
        Student bob = new Student("Bob", 2.9);
        Student charlie = new Student("Charlie", 3.8);
        Student charlie2 = new Student("Charlie", 3.8);     // same values as charlie
        Student daisy = new Student("Daisy", 4.0);
        Student daisy2 = daisy;                                         // same object as daisy
        Student ethan = new Student("Ethan", 1.4);
        Student ethan2 = new Student("Ethan", 3.4);
        Student fred = new Student("Fred", 3.4);            // same GPA as ethan
        Student[] students = {alice, bob, charlie, daisy, ethan, ethan2, charlie2, daisy2, fred};

        // charlie2, daisy2, and fred are removed for being duplicates
        // TreeSets will use the compareTo to determine dupes, rather than .equals()
        // ANY element that would return 0 from the compareTo() will be seen as a dupe
        Set<Student> studentSet = new TreeSet<>(Arrays.asList(students));   

        // objects are automatically sorted according to Student.compareTo();
        System.out.println("\nSORTED STUDENTS:\n" + studentSet);

    }
}
