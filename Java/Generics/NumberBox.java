
/**
 * Generic box, that can only take in numbers
 *      - can be ANY type of number
 *          - but it has to be a number
 */
public class NumberBox<T extends Number> {

    private T num;

    public T getNum() {
        return num;
    }

    public void setNum(T num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "NumberBox [num=" + num + "]";
    }
}
