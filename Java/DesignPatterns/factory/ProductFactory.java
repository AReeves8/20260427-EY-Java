package factory;

public class ProductFactory {

    // private constructor to make sure it cannot be instantiated
    private ProductFactory() {}

    public static Product create(String type, int id, String name, double price) {

        String formattedType = type.toLowerCase().trim();

        return switch (formattedType) {
            case "hardware" -> new HardwareProduct(id, name, price);
            case "software" -> new SoftwareProduct(id, name, price);
            default -> {
                throw new IllegalArgumentException("Invalid type. Could not create product.");
            }
        };
    }

}
