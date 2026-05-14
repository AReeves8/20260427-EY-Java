package factory;

public class Main {
    public static void main(String[] args) {

        // product factory gives us any product we want - Main is only coupled to ProductFactory
        Product computer = ProductFactory.create("HARDWARE", 1, "Computer", 1999.99);
        Product apiLicense = ProductFactory.create("software", 2, "API License", 99.99);

        System.out.println(computer.getName());
        System.out.println(apiLicense.getName());

        // each object can be any type of product thanks to covariance
        computer = ProductFactory.create("soFtWaRe", 3, "Virtual Machine", 9.99);
        System.out.println(computer.getName());

    }
}
