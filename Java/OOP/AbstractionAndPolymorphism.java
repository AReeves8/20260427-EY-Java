public class AbstractionAndPolymorphism {
    public static void main(String[] args) {

        Circle circle = new Circle("red", 4);
        circle.describe();      // calls describe method from Shape class
        circle.draw();

        Rectangle rectangle = new Rectangle("purple", 4.32, 7);
        rectangle.describe();

        Square square = new Square("blue", 9);

        // when you call a method on a reference, Java looks for that method in that class, then moves up the hierarchy if it cannot find it
        square.describe();

        // --------------------------------------------------------------------------
        // Cannot instantiate abstract classes or interfaces
        //Shape shape = new Shape("orange");           

        // Covaraince - Polymorphism - when a child object can be referenced as a parent object
        Shape shape = new Square("yellow", 567.87);

        // With covariance, a child can be referenced by a parent class, but not the other way around
        //Square rectangle2 = new Rectangle("pink", 12, 98.123);      

        // you can cast a parent to be a child which will work at COMPILE TIME, but it could create RUN TIME issues
        //Square rectangle3 = (Square) new Rectangle("pink", 12, 98.123);      // ClassCastException - trying to cast a class into something it cannot become

        // no ClassCastException since a Suare is a Rectangle
        Shape square2 = (Rectangle) new Square("lime", 3);
        System.out.println("--- SQUARE 2: ---");
        square2.describe();

        Shape[] shapes = {circle, rectangle, square};

        System.out.println("--- DESCRIBING ALL SHAPES ---");
        for(Shape s : shapes) {
            describeShape(s);

            // istanceof lets you check the instance type of a variable. does not check the reference type.
            if(s instanceof Circle) {
                ((Circle)s).draw();
            }

        }


    }


    /**
     * this method can take in ANY shape (circle, rectangle, square, etc.)
     */
    public static void describeShape(Shape shape) {  
        System.out.printf("I am a %s %s with an area of %.2f!\n", shape.color, shape.getClass().getSimpleName(), shape.calculateArea());
    }


}



/**
 * ABSTRACTION
 *      - hide implementation details
 *      - reduce complexity
 *      - increase flexibility
 *      - define what something is supposed to do
 * 
 *      - ABSTRACT CLASSES 
 *          - a class that can contain abstract methods, which are methods without implementions. Implementations will be added by the child class. 
 *              - abstract methods CAN ONLY be in an abstract class
 *          - uses extends keyword
 * 
 *      - INTERFACES
 *          - entirely abstract, menaing every method is naturally an abstract method. 
 *          - uses implements keyword
 */
abstract class Shape {

    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    // abstract methods since these will vary for each specific shape
    public abstract double calculateArea();
    public abstract double calculatePermiter();

    public void describe() {
        System.out.printf("I am a %s %s with an area of %.2f!\n", color, getClass().getSimpleName(), calculateArea());
    }

}


class Circle extends Shape implements Drawable, Resizable {

    private int radius;

    public Circle(String color, int radius) {

        super(color);
        this.radius = radius;
    }

    // METHODS FROM ABSTRACT CLASS
    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public double calculatePermiter() {
        return 2 * Math.PI * radius;
    }

    // METHODS FROM INTERFACES
    @Override
    public void resize() {
        radius *= 2;    // double the radius
    }

    @Override
    public void draw() {
       for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                // Check the circle equation x^2 + y^2 <= r^2
                if (i * i + j * j <= radius * radius) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println(); // Move to next line
        }
    }

    // since both interfaces have a default for this method, java won't know which one to use
    // so override it in this class so java can find it at run time
    @Override
    public void showInfo() {
        describe();
    }
    
}

class Rectangle extends Shape {

    private double length;
    private double width;

    public Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public double calculatePermiter() {
        return 2 * (length + width);
    }
    
}

class Square extends Rectangle {

    public Square(String color, double length) {
        super(color, length, length);   
    }
    
}

// -able to signify that there is an action that a object can perform
interface Drawable {

    // all variables in an interface are implictly public static final
    String DRAWABLE_VERSION = "1.0.0.";


    // all methods in an interface are implictly public abstract
    void draw();

    // added in Java 8, you can create "defaults" that provide a default implementation for a method
    // cannot be made static or abstract
    default void showInfo() {
        System.out.println("The drawable class is on version " + DRAWABLE_VERSION);
    }

}

interface Resizable {
    // all variables in an interface are implictly public static final
    String RESIZABLE_VERSION = "1.0.0.";


    // all methods in an interface are implictly public abstract
    void resize();

    // added in Java 8, you can create "defaults" that provide a default implementation for a method
    default void showInfo() {
        System.out.println("The resizable class is on version " + RESIZABLE_VERSION);
    }
}
