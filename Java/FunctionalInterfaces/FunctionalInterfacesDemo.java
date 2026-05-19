import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FunctionalInterfacesDemo {

    public static void main(String[] args) {

        List<Employee> employees = List.of(
            new Employee("Alice", "IT", 10000, true),
            new Employee("Bob", "IT", 20000, false),
            new Employee("Charlie", "IT", 30000, true),
            new Employee("Danielle", "Sales", 40000, true),
            new Employee("Ethan", "IT", 50000, true),
            new Employee("Fred", "IT", 60000, false),
            new Employee("Gregory", "IT", 70000, true)
        );



        // Anonymous Inner Class implementation
        SalaryAdjuster tenPercentRaise = new SalaryAdjuster() {
            @Override
            public double adjust(double salary) {
                return salary * 1.1;
            }
        };

        // Lambda implementation - more concise version of above
        SalaryAdjuster twentyPercentRaise = salary -> salary * 1.2;

        double base = 50000;
        System.out.println("Base:\t" + base);
        System.out.println("10% Raise:\t" + tenPercentRaise.adjust(base));
        System.out.println("20% Raise:\t" + twentyPercentRaise.adjust(base));

        // can pass lambdas into functions that take in a functional interface
        applyAndPrint("John Doe", 50000, salary -> salary * 1.3);



        /**
         * LAMBDA SYNTAX
         *      no-params               () -> expression;
         *      single param             x -> expression;
         *                              (x) -> expression;
         *      multi params            (x, y) -> expression;
         *      multi expressions       (x, y) -> { double result = x * y; return result * 2; }
         */

        // no params, single expression
        Runnable greet = () -> System.out.println("Hello from No-Param Lambda!");
        greet.run();

        // single param, single expression
        SalaryAdjuster fiftyPercentRaise = salary -> salary * 1.5;
        System.out.println("50% Raise:\t" + fiftyPercentRaise.adjust(base));

        // multiple params, single expression
        // BiFunction - built-in functional interface - takes two inputs and produces an output
        BiFunction<Double, Double, Double> weightedSalaryAdjustment = (salary, weightFactor) -> salary * weightFactor;
        System.out.println("40% Raise:\t" + weightedSalaryAdjustment.apply(base, 1.4));

        // lambda with code block
        SalaryAdjuster tieredAdjustment = salary -> {
            if(salary > 100000) {
                // 5% raise
                return salary * 1.05;
            }
            else {
                // 10% raise
                return salary * 1.1;
            }
        };
        System.out.println("Tiered Raise:\t" + tieredAdjustment.adjust(base));




        /**
         * java.util.function
         *      - java's way to do functional programming
         *      - a series of different functional interfaces that can handle a wide variety of situations
         *          - you can use these instead of creating your own functional interfaces
         *              - custom functional interfaces: more explicit, easier to read and maintain
         *              - use built-ins if creating an entire interface isn't necessary
         * 
         *      - most have a generic .apply() that will be the actual function you implement.
         */
        Function<Integer, Integer> doubleIt = num -> num * 2;
        System.out.println("Doubled!:\t" + doubleIt.apply(9865));

        // UnaryOperator - same as Function except the param type and the return type are the same
        UnaryOperator<Integer> doubleInt = num -> num * 2;
        System.out.println("Doubled Again!:\t" + doubleInt.apply(12098));


        // BiFunction - a function with 2 inputs and one output
        BiFunction<Employee, Double, Employee> updateEmployeeSalary = 
            (existingEmployee, salary) -> existingEmployee.withSalary(salary);

        Employee employee = new Employee("Austin");
        System.out.println("Starting Salary!:\t" + employee);
        System.out.println("Updated Salary!:\t" + updateEmployeeSalary.apply(employee, 20000.0));

        // BinaryOperator - BiFunction, put both inputs and the output are the same type
        BinaryOperator<Double> multiply = (num1, num2) -> num1 * num2;
        System.out.println("Multiply!:\t" + multiply.apply(4.0, 20000.0));

        /**
         * Consumer - take some value and return nothing
         *      - one of operations that interact outside of your application (logging, save to a db, send an email, etc.)
         *      - use accept() instead of apply()
         * 
         *      - there's also BiConsumer which takes in two inputs
        */   
        Consumer<Employee> logEmployeeCreation = (emp) -> System.out.println("New Employee!:\t" + emp);
        logEmployeeCreation.accept(employee);

        /**
         * Supplier - generate some value with no input
         *      - use .get() instead of .apply()
         */
        Supplier<Employee> defaultEmployee = () -> new Employee("Jane Doe");
        //employees.add(defaultEmployee.get());         // UnsupportedOperationException - List.of() creates immutable List


        /**
         * Predicates
         *      - return booleans
         *      - take in an input, return true or false based on the input
         */
        Predicate<Employee> isActive = Employee::active;
        Predicate<Employee> isHighEarner = emp -> emp.salary() > 50000;
        Predicate<Employee> isITDepartment = emp -> emp.department().equals("IT");

        // can use Predicates on their own
        long numActiveEmployees = employees.stream().filter(isActive).count();
        System.out.println("Num Active Employees!:\t" + numActiveEmployees);

        // predicate that checks multiple things with .and(), .or(), and .not() 
        Predicate<Employee> isActiveHighEarner = isActive.and(isHighEarner);
        System.out.println("Is an active high earner?:\t" + isActiveHighEarner.test(employees.get(6)));
        System.out.println("Is an active high earner?:\t" + isActiveHighEarner.test(employees.get(5)));


        /**
         * METHOD REFERENCES
         *      - shorthands for lambdas
         *      - references (like objects) to functions you want to be able to call later
         *      - ::
         * 
         *      - static method                             ClassName::staticMethod         (args...) -> ClassName.method(args...);
         *      - instance method, arbitrary receiver       ClassName::instanceMethod       (obj, args...) -> obj.method(args...);
         *      - instance method, specific reciever        instance::instanceMethod        (args...) -> instance.method(args...);
         *      - constructors                              ClassName::new                  (args...) -> new ClassName(args...);
         */

        // static method
        Function<String, Integer> parser = Integer::parseInt;
        System.out.println("Parsed Int!:\t" + parser.apply("3456"));

        // static method
        Comparator<Employee> bySalary = Employee::compareBySalary;
        Employee lowestSalary = employees.stream().min(bySalary).orElseThrow();
        System.out.println("Lowest Salary!:\t" + lowestSalary);

        // instance method, arbitrary receiver 
        UnaryOperator<String> toLower = String::toLowerCase;
        employees.stream()
            .map(Employee::name)        // converts Employees to just Strings containing their names
            .map(toLower)               // goes through each string object, gets the arbitrary reference, than passes it into toLower
            .forEach(emp -> System.out.println("Lower Case:\t" + emp));

        // instance method, specific reciever
        BiFunction<Employee, Employee, Integer> higherSalary = bySalary::compare;
        Employee emp1 = employees.get(0);
        Employee emp2 = employees.get(1);
        System.out.println("Higher Salary!:\t" + higherSalary.apply(emp1, emp2));

        // constructor
        Function<String, Employee> createEmployee = Employee::new;
        Employee newHire = createEmployee.apply("Henry");
        System.out.println("New Hire!:\t" + newHire);
    }


    public static void applyAndPrint(String name, double salary, SalaryAdjuster adjuster) {
        System.out.println(name + " has been given a raise to $" + adjuster.adjust(salary));
    }

}
