
/**
 * 
 * EXCEPTIONS
 *      Unchecked: compilier doesn't require handling. RuntimeExceptions. Can still be caught in a try-catch.
 *      Checked: compilier can check and will force you to handle. Have to use try-catch or throws.
 *      Errors: JVM issues. Happen at runtime. Cannot be handled with a try-catch.
 * 
 * 
 * 
 *      Throwable
 *          - Error                         <-- JVM issues
 *              - StackOverflowError
 *              - OutOfMemoryError
 *          - Exception
 *              - RuntimeException          <-- unchcked exceptions
 *                  - NullPointerException
 *                  - ArithmeticException
 *                  - IndexOutOfBoundsException
 *                      - ArrayIndexOutOfBoundsException
 *                      - StringIndexOutOfBoundsException
 *                  - ClassCastException
 *                  - NumberFormatException
 *                  - IllegalArgumentException
 *                  - IllegalStateException
 *                  - NegativeArraySizeException
 *              - (everything else)          <-- checked exceptions
 * 
 * 
 */





public class ExceptionBasics {
    public static void main(String[] srgs) {

        /**
         * TRY-CATCH-FINALLY
         *      - exception handling
         *      - try wraps around some "risky"
         *      - catch only executes if the specified exception was thrown in the try    
         *          - can have multiple catch blocks
         *              - must catch the most specific class first, and then go more general as you get further down
         *                  - would create unreachable code blocks
         *      - finally always execute, regardless of if an exception was thrown or not
         */
        try {
            int nums[] = {10, 20 , 30};
            String hi = "hi";

            for(int i = 0; i < 5; i++) {
                System.out.println(nums[i]);            // throws ArrayIndexOutOfBoundsException
                System.out.println(hi.charAt(i));       // throws StringIndexOutOfBoundsException
            }
        }
        // catch(Exception e) {            // <- would cause the following catch blocks to be unreachable - compile time error

        // }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException was caught:\n" + e.getMessage());
        }
        catch(IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException was caught:\n" + e.getMessage());
        }
        catch(RuntimeException e) {
            System.out.println("RuntimeException was caught:\n" + e.getMessage());
        }
        finally {
            System.out.println("FINALLY BLOCK WILL ALWAYS RUN");
        }


        // ----- NUMBER FORMAT EXCPETION -----
        try {
            int num = Integer.parseInt("abc");
            System.out.println(num); 
        }
        catch(NumberFormatException e) {
            System.out.println("NumberFormatException: Tried to parse a number out of a String\n" + e.getMessage());
        }


        // ----- CLASS CAST EXCPETION -----
        try {
            Object obj = "I am a String";
            String str = (String) obj;          // <-- No exception. Object can be cast into a string in this scenario
            System.out.println(str); 
            Integer num = (Integer) obj;        // ClassCastException
            System.out.println(num); 
        }
        catch(ClassCastException e) {
            System.out.println("ClassCastException: Tried to cast an object into a class it cannot become\n" + e.getMessage());
        }


        // ----- NEGATIVE ARRAY SIZE EXCPETION -----
        try {
            int[] arr = new int[-3];    // not a compile time error but a runtime exception
            System.out.println(arr); 
        }
        catch(NegativeArraySizeException e) {
            System.out.println("NegativeArraySizeException: Tried to declare an array with a negative size\n" + e.getMessage());
        }

        // ----- ILLEGAL ARGUMENT EXCPETION -----
        try{
            setAge(-5);
        }
        catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: Tried to give a method some data it cannot accept as an argument\n" + e.getMessage());
        }

        // ----- ILLEGAL STATE EXCPETION -----
        try{
            Car car = new Car();
            car.start();            // didn't fuel up the car first
        }
        catch (IllegalStateException e) {
            System.out.println("IllegalStateException: Tried to use an object in an incorrect way\n" + e.getMessage());
        }

        // ----- STACK OVERFLOW ERROR -----
        try{
            recurse(0);
        }
        catch (StackOverflowError e) {
            System.out.println("StackOverflowError: Tried to do infinite (or VERY large) recursion\n" + e.getMessage());
        }

    }


    // ----- HELPER METHODS -----

    static void setAge(int age) {
        if(age < 0) {

            /**
             * THROW
             *      - forces an exception to happen right now
             *          - must be handled by the calling class 
             *              - try-catch
             *              - throws
             */

            // thorw it when you receive invalid data as a method argument
            throw new IllegalArgumentException("Age cannot be negative. Given age: " + age);
        }
        // set some age variable
    }

    static void recurse(int depth) {
        recurse(depth + 1);
    }
}

class Car {
    private boolean hasFuel;

    public Car() {
        hasFuel = false;
    }

    public void fuelUp() {
        this.hasFuel = true;
    }

    public void start() {
        if(!hasFuel) {

            // thorw this when an object's current state doesn't meet the requirements to perform an action
            throw new IllegalStateException("Car must be fueled up before it can start.");
        }
    }
}


