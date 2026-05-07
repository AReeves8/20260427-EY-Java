
/**
 * COMPARABLE INTERFACE
 *      - specify how to compare different objects from a class
 *      - natural ordering - other utility classes will use the method implicitly
 */
public class Student implements Comparable<Student> {

    private String name;
    private double gpa;

    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getGpa() {
        return gpa;
    }
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", gpa=" + gpa + "]";
    }

    /**
     * From comparable interface
     * 
     *  returns an int
     *      negative - this comes BEFORE the other
     *      zero - the EQUALS the other
     *      positive - this comes AFTER the other
     * 
     */
    @Override
    public int compareTo(Student other) {

        // sorting from lowest GPA to highest (Ascending)
        // return (int) (this.gpa - other.getGpa());            // has floating point rounding errors

        double difference = this.gpa - other.getGpa();
        if(difference > 0) {
            return 1;
        }
        else if(difference < 0) {
            return -1;
        }
        else {
            return 0;
        }
    }

    

}
