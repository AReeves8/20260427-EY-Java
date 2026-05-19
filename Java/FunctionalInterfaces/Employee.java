public record Employee(
    String name, 
    String department, 
    double salary,
    boolean active
) {

    Employee(String name) {
        this(name, "Unknown", 0.0, true);
    }

    Employee withSalary(double newSalary) {
        return new Employee(name, department, newSalary, active);
    }

    static int compareBySalary(Employee a, Employee b) {
        return Double.compare(a.salary(), b.salary());
    }

}
