import java.util.Arrays;
import java.util.Comparator;

public class ArraysDemo {

    public static void main(String[] args) {

        /**
         * WAYS TO DECALRE AN ARRAY
         *  
         *      - new int[5]            <-- give the array some maximum size
         *      - {1, 2, 3, 4, 5}       <-- initialize the array with values. Array will be the size of all of those values
         *          - days of the week
         */

        int[] leaderboard = new int[5];     // size is needed to know how much memory to allocate. no size -> no compile.
        
        // can access and modify specific memory locations through indexes.
        leaderboard[0] = 999;
        leaderboard[1] = 807;
        leaderboard[2] = 765;
        leaderboard[3] = 329;
        leaderboard[4] = 25;
        //leaderboard[5] = 0;   // Watch out for going beyond the memory scope of the array. ArrayIndexOutOfBoundsException

        System.out.println("Two highest scores add up to: " + (leaderboard[0] + leaderboard[1]));

        // .length is a variable, NOT a method. no parenthesis needed.
        System.out.println("Leaderboard has " + leaderboard.length + " scores in it.");


        // new arrays are all initialized to their default values for that type.
        int[] intArr = new int[2];      
        System.out.println("intArr[0]: " + intArr[0]);          // 0    

        double[] dubArr = new double[2];      
        System.out.println("dubArr[0]: " + dubArr[0]);          // 0.0
        
        Object[] objArr = new Object[2];
        System.out.println("objArr[0]: " + objArr[0]);          // null

        Integer[] intObjArr = new Integer[2];
        System.out.println("intObjArr[0]: " + intObjArr[0]);    // null

        // all arrays live on the heap and therefore can be assigned to each other
        int[] a = {12, 23, 34, 45, 56};
        int[] b = a;
        System.out.println("b[0]: " + b[0]);    // 12
        b[3] = 98;
        System.out.println("a[3]: " + a[3]);    // 98

        // can reassign arrays to point to new arrays since they're like objects
        int[] c = {12, 23, 34, 45, 56, 67, 78, 89};
        b = c;
        System.out.println("b[7]: " + b[7]);    // 89

        // == checks memory location of arrays, just like with objects
        if(b == c) {
            System.out.println("b and c point to the same memory location");
        }

        int[] d = {12, 23, 34, 45, 56}; // same values as array a, but it is stored in a different memory location. it is a different array
        if(a == d) {
            System.out.println("a and d are the same array");   // does not print
        }


        // ----- 2D ARRAYS -----

        /**
         * 2D arrays are stored in the heap. the outer array contains references to the inner arrays
         * 
         * [ ref -> ( [] ) ]
         * [ ref -> ( [] ) ]
         * [ ref -> ( [] ) ]
         * [ ref -> ( [] ) ]
         * [ ref -> ( [] ) ]
         */
        int[][] grid = new int [5][3];

        // use square bracket to access and modify both dimensions of the array
        grid[0][2] = 5;
        grid[1][1] = 2;
        grid[4][0] = 9;
        grid[3][2] = 1;
        System.out.println("grid[3][2]: " + grid[3][2]);            // 1

        System.out.println("grid.length: " + grid.length);          // 5
        System.out.println("grid[1].length: " + grid[1].length);    // 3


        // Jaggad Arrays - each inner array has a different length
        int[][] triangle = new int[4][];    // cannot access inner array until they are initialized
        for(int i = 0; i < triangle.length; i++) {

            triangle[i] = new int[i+1];     // initializing inner arrays
            for(int j = 0; j < i; j++) {
                triangle[i][j] = i + j;
                System.out.print(triangle[i][j]);    
            }

            System.out.println(); 
        }

        int[][] jaggedGrid = {
            {1, 2, 3},
            {1, 2, 3, 4, 5},
            {1, 2}
        };

        jaggedGrid[2] = jaggedGrid[1];

        if(jaggedGrid[2] == jaggedGrid[1]) {
            System.out.println("jaggedGrid[2] references the same array as jaggedGrid[1]");
        }
        System.out.println(jaggedGrid[1]); 
        System.out.println(jaggedGrid[2]);   // same memory print out as jaggedGrid[1]


        // inner arrays cannot be initilaized before their outer array
        //int[][] incorrectArray = new int[][5];      
        //int[][][] incorrectArray2 = new int[3][][5]; 
        int[][][] jaggedArray3D = new int[3][4][]; 

        // need to initialize inner arrays before you access them
        //jaggedArray3D[1][0][0] = 5;     // NullPointerException



        /**
         * 3D Arrays are references to arrays of references
         * 
         * [ ref -> ( [ ref -> ( [] ) ] ) ]
         * [ ref -> ( [ ref -> ( [] ) ] ) ]
         * [ ref -> ( [ ref -> ( [] ) ] ) ]
         * [ ref -> ( [ ref -> ( [] ) ] ) ]
         * [ ref -> ( [ ref -> ( [] ) ] ) ]
         * 
         */



        // ----- SORTING AND ORDERING ARRAYS -----

        Student alice = new Student("Alice", 3.2);
        Student bob = new Student("Bob", 2.9);
        Student charlie = new Student("Charlie", 3.8);
        Student daisy = new Student("Daisy", 4.0);
        Student ethan = new Student("Ethan", 1.4);
        Student ethan2 = new Student("Ethan", 3.4);
        Student[] students = {alice, bob, charlie, daisy, ethan, ethan2};


        // COMPARABLE
        System.out.println("BEFORE SORTING:\n" + Arrays.toString(students));

        // since the class implements compareTo(), that method will implicitly be used for sorting.
        Arrays.sort(students);      // ClassCastException if class doesn't implement Comparable      
        System.out.println("AFTER SORTING:\n" + Arrays.toString(students));



        // COMPARATOR - standalone sorting functions
        Comparator<Student> byNameAsc = new Comparator<Student>() {         // Anonymous Inner Class
            @Override
            public int compare(Student student1, Student student2) {
                return student1.getName().compareTo(student2.getName());    // using string's copareTo method to help us sort
            }
        };

        // using custom Comparator instead of the natural ordering of Comparable
        Arrays.sort(students, byNameAsc);     
        System.out.println("AFTER NAME SORTING:\n" + Arrays.toString(students));  

       
        /**
         * ANONYMOUS INNER CLASSES
         *      - an interface only has one method, and you can implement it directly as part of your code
         *      - an inner class that has no name and does nothing except implement the single method from the interface
         *          - everything between the curly braces above is the anonymous inner class
         * 
         *      - can use LAMBDAS to implement these in shorthand
         *          - (params) -> { return someValue }
         */
        Comparator<Student> byNameDesc = (Student student1, Student student2) -> { 
            return student2.getName().compareTo(student1.getName()); 
        };


        // Comparator.comparing()   - factory method that lets you simplify the comparison process using Streams
        Comparator<Student> byNameThenGpa = Comparator
            .comparing(Student::getName)            // first sorts by name
            .thenComparingDouble(Student::getGpa);  // then sorts by GPA

         
        Arrays.sort(students, byNameThenGpa);
        System.out.println("AFTER NAME THEN GPA SORTING:\n" + Arrays.toString(students)); 
        

    }
}
