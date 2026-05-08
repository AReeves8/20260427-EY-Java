import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Maps {

    public static void main(String[] args) {

        /**
         * MAPS
         *      - key-value pairs
         *          - keys MUST be unique
         *              - null IS allowed (only once of course)
         *          - values can be anything
         *          - if you insert a new key-value pair, and the key you're inserting already exists, then it will override the value at that key
         *              - [abc:123][xyz:123][def:456]
         *                      insert abc:789
         *                [abc:789][xyz:123][def:456]
         * 
         *      - O(1) access time
         *      
         *      HASH MAPS
         *          - hash table and a map
         *              - HASH TABLES:
         *                  new values come in to the table and are hashed to some location. 
         *                  if two values hash to the same location, then a collission occurs. 
         *                      - [][][abc][o][][]       <- xyz
         *                                  |
         *                                  o
         *                                  |
         *                                  o
         * 
         *          - use LinkedLists as the collision stratgey
         *          - look at an object's hashCode function for the hashing strategy
         * 
         *          - unordered (insertion order is not guarenteed to be maintained)
         * 
         *      TREE MAP
         *          - sort KEYS based on the object's Comparable, or a Comparator function
         *          - do NOT allow null keys
         *          - insertion/retrieval O(log n)
         * 
         *      LINKED HASH MAP
         *          - preserves insertion order of key-value pairs
         */


        Map<String, Integer> hashMap = new HashMap<>();

        // .put() to add new key value pairs
        hashMap.put("Michalis", 3);
        hashMap.put("Onyx", 4);
        hashMap.put("Abdullah", 8);     // 8 is the best number

        // .get() to retrieve a value at a key
        System.out.println("The best number is " + hashMap.get("Abdullah"));
        System.out.println("The best number is " + hashMap.get("Austin"));      // returns null if key doesn't exist

        // change the value at a given key by re-inserting it
        hashMap.put("Michalis", 42);
        System.out.println("Michalis' number is " + hashMap.get("Michalis"));   // now is 42

        // checks if the key exists first, only inserts if it doesn't exist
        hashMap.putIfAbsent("Michalis", 89);
        System.out.println("Michalis' number is " + hashMap.get("Michalis"));   // still be 42

        // get all keys and values
        Set<String> keys = hashMap.keySet();
        List<Integer> values = new LinkedList<Integer>(hashMap.values());            // .values() returns a Collection

        // .remove() to delete a key-value pair
        hashMap.remove("Michalis");

        // enhanced for loop can loop through maps using the Entry subclass
        for(Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println("\t" + entry.getKey() + " -> " + entry.getValue());
        }

        // can loop through just the keys or the values of a map
        for(Integer i : hashMap.values()) {
            System.out.println("Value: " + i);
        }

    }
}
