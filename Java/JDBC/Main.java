import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Main {

    static final String URL = "jdbc:postgresql://localhost:5432/customers";
    static final String USERNAME = "postgres";
    static final String PASSWORD = "root";

    public static void main(String[] args) {

        try(Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            CustomerDAO dao = CustomerDAO.getInstance(conn);


            // ----- FIND ALL -----
            List<Customer> allCustomers = dao.findAll();
            System.out.println("--- ALL CUSTOMERS: " + allCustomers.size() + " ---");
            System.out.println(allCustomers);


            // ----- FIND BY ID -----
            Optional<Customer> customer = dao.findById(60000);
            if(customer.isPresent()) {
                System.out.println("--- FOUND CUSTOMER: ---");
                System.out.println(customer.get());
            }
            else {
                System.out.println("--- NO CUSTOMER FOUND ---");
            }

            // ----- FIND BY COUNTRY AS INTENDED -----
            List<Customer> usaCustomers = dao.findByCountryUnsafe("USA");
            System.out.println("--- USA CUSTOMERS: " + usaCustomers.size() + " ---");
            System.out.println(usaCustomers);

            // ----- FIND BY COUNTRY W/ SQL INJECTION -----
            List<Customer> usaCustomersUnsafe = dao.findByCountryUnsafe("' OR '1'='1");    // example SQL injection
            System.out.println("--- USA CUSTOMERS UNSAFE: " + usaCustomersUnsafe.size() + " ---");
            System.out.println(usaCustomersUnsafe);


            // ----- INSERT CUSTOMER -----
            Customer customerToInsert = new Customer();
            customerToInsert.setFirstName("Gina");
            customerToInsert.setLastName("Peng");
            customerToInsert.setEmail("gpeng@skillstorm.com");
            customerToInsert.setCountry("USA");
            customerToInsert.setCity("Pearland");
            
            Customer createdCustomer = dao.insert(customerToInsert);
            System.out.println("--- CREATED CUSTOMER: ---");
            System.out.println(createdCustomer);

            // ----- UPDATE CUSTOMER -----
            Customer existingCustomer = new Customer();
            existingCustomer.setCustomerId(26);
            existingCustomer.setFirstName("Abdullah");
            existingCustomer.setLastName("Ahmad");
            existingCustomer.setEmail("aahmad@skillstorm.com");
            existingCustomer.setCountry("USA");
            existingCustomer.setCity("Auburn Hills");
            
            int updatedRows = dao.update(existingCustomer);
            System.out.println("--- ROWS UPDATED: " + updatedRows + " ---");

            // ----- DELETE CUSTOMER -----
            int deletedRows = dao.delete(30);
            System.out.println("--- ROWS DELETED: " + deletedRows + " ---");

        } catch(SQLException e) {
            System.out.println("Something went wrong. " + e.getMessage());
        }


    }
}
