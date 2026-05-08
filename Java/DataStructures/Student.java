
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



    /**
     * HASHCODE AND EQUALS
     *      - utility methods used by HashMaps
     *      - hashCode is used as the HashingStrategy for storing this type of object
     *      - equals is used to determine if two different objects are equivalent
     * 
     *      - built in methods from Object, similar to toString()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(gpa);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(gpa) != Double.doubleToLongBits(other.gpa))
            return false;
        return true;
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
