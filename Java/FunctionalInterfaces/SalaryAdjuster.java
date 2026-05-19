
/**
 * @FunctionalInterface
 *      - annotation to enforce a compile time check that the interface only has one method
 */
@FunctionalInterface
public interface SalaryAdjuster {

    // functional interfacers only have one abstract method
    double adjust(double salary); 

    // adding a 2nd method breaks @FunctionalInterface
    //int adjust2(int salary);


    // HOWEVER, static and default methods are still allowed since they already have implementations
    default String describe() {
        return "SalaryAdjuster: FunctionalInterface";
    }

    static String describe2() {
        return "SalaryAdjuster: FunctionalInterface";
    }

}
