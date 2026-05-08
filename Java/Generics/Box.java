
/**
 * this class can have data of any type
 * 
 */
public class Box<T> {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Box [value=" + value + "]";
    }

}
