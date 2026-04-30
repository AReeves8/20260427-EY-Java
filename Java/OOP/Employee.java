public class Employee {

    private int id;
    private String name;
    private String department;

    // DEFAULT CONSTRUCTOR - does not show up if you declare ANY type of constructor in your class
    public Employee() {
        
        /**
         * CONSTRUCTOR CHAINING: calling constructors from other constructors in a chain
         * 
         * super() 
         *      - calls a constructor in the parent class
         *      - if there is no parent, then it calls Object();
         *      - if you don't include this in your constructor, java will implicitly call it for you
         * 
         * this()
         *      - calls a constructor within the same class
         * 
         * 
         * one of these has to be the first line of your constructor (if neither is present, java adds super())
         */
        this(1, "John Doe", "IT");        
    }

    // PARAMETERIZED CONSTRUCTOR
    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }



    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }


    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", department=" + department + "]";
    }


    public void doWork() {
        System.out.println("Working away....");
    }




    public static void main(String[] args) {
        Employee emp = new Employee();
        System.out.println(emp.toString());
        emp.doWork();

        ITEmployee emp2 = new ITEmployee();
        System.out.println(emp2.toString());
        emp2.doWork();

        // Java looks for the most exact match first
        emp2.doWork(4);         // will invoke the primitive int function 
        emp2.doWork(Integer.valueOf(8));     // will invoke the Integer object function     

        emp2.doWork(3.14);      // will invoke the double function
        emp2.doWork(1.23f);     // will invoke the float function


        /**
         * if the primitive int function doesn't exist, it will invoke the function with the next largest size
         *      - because of implicit widening in Java
         *          - int > long > float > double > Integer
         *      - Java will widen the variable to be the next largest in size
         */
        emp2.doWork(4);      
        
        // Java will shift bits and cutoff floating point bits as needed to make a value fit in a variable
        emp2.floatTest(Integer.MAX_VALUE + 0.1f);
    }




    
}
/**
 * INHERITANCE 
 *      - extends to inherit a class
 *          - can only "extends" one class
 *      - implements to inherit from interfaces (ABSTRACTION)
 *          - can "implements" multiple interfaces
 * 
 *      - gets all of the properties and methods from the parent class
 *          - can only access public and protected members from the parent (maybe default members as well if in the same package)
 *          
 */
class ITEmployee extends Employee {

    private int bugsSolvedToday;

    public ITEmployee() {

        // calls the parameterized constructor in the parent class
        super(2, "Jane Doe", "IT");
        this.bugsSolvedToday = 2;
        System.out.println("IT WORKER SOLVED " + this.bugsSolvedToday + " BUGS TODAY.");

        //System.out.println(id);       // cannot access private properties on parent
        System.out.println(getId());    // can access public and protected members however

    }

    /**
     * METHOD OVERRIDDING - POLYMORPHISM
     *      - changing the implementation of a method from the parent class in the child class
     *      - @Override isn't necessary but is best practice to include
     * 
     *      - happens at run time
     *          - the EXACT SAME SIGNATURE as the parent method, so compiler cannot tell the difference between them
     */
    @Override
    public void doWork() {
        System.out.println("Solving bugs!");
    }

    /**
     * METHOD OVERLOADING - POLYMORPHISM
     *      - having a method with the same name in the same class, but with a different signature
     * 
     */
    public void doWork(String task) {
        System.out.println("Solving " + task + "!");
    }

    // return type is NOT considered part of the signature.. so the Java compiler still sees this as a dupe method
    // THIS IS NOT METHOD OVERLOADING - THIS IS A COMPILE-TIME ERROR
    // public String doWork() {
    //     return "Soliving bugs!";
    // }

    // public void doWork(int tasksCompleted) {
    //     System.out.println("Solving " + tasksCompleted + " tasks!");
    // }

    public void doWork(Integer tasksCompleted) {
        System.out.println("INTEGER OBJECT: Solving " + tasksCompleted + " tasks!");
    }

    public void doWork(double tasksCompleted) {
        System.out.println("DOUBLE: Solving " + tasksCompleted + " tasks!");
    }

    public void doWork(float tasksCompleted) {
        System.out.println("FLOAT: Solving " + tasksCompleted + " tasks!");
    }

    public void doWork(short tasksCompleted) {
        System.out.println("SHORT: Solving " + tasksCompleted + " tasks!");
    }

    public void doWork(long tasksCompleted) {
        System.out.println("LONG: Solving " + tasksCompleted + " tasks!");
    }


    public void floatTest(float num) {
        System.out.println("GIVEN NUMBER " + num);
    }
}
