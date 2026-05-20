public record Product(
    String name, 
    String category, 
    double price, 
    int stock
) {


    public void badFunction() throws Exception {
        throw new Exception();
    }

}
