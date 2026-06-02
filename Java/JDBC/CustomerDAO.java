import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO - pronounced "dow")
 *      - centralize all the database code into one location
 *      - DAO interacts with the datbase, and THAT IS IT
 *          - does not handle ANY business logic
 * 
 *      CRUD Operations
 *          Create
 *          Read
 *          Update
 *          Delete
 */
public class CustomerDAO {


    private final Connection conn;
    private static CustomerDAO instance;

    private CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    // making the DAO as a singleton so every instance has the same database connection
    public static CustomerDAO getInstance(Connection conn) {
        if(instance == null) {
            return new CustomerDAO(conn);
        }
        return instance;
    }


    public List<Customer> findAll() {

        String query = "SELECT * FROM customer";
        List<Customer> results = new ArrayList<>();
        
        /**
         * STATEMENT
         *      - executes a SQL query
         *          
         * RESULT SET
         *      - cursor object with all results from the query
         */
        try(Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while(resultSet.next()) {

                // mapping each row of the ResultSet to a Customer object
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customer_id"));
                customer.setCity(resultSet.getString("city"));
                customer.setCountry(resultSet.getString("country"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));

                // adding each object to the results
                results.add(customer);
            }

        } catch(SQLException e) {

            // return an empty list if the statement could not be executed
            System.out.println("Query could not be executed: findAll()");
            return new ArrayList<>();
        }

        return results;

    } 

    public Optional<Customer> findById(int id) {


        /**
         * Statement requires something like this:
         *      String query = "SELECT * FROM customer WHERE id = " + id;
         *      
         *      Statement is suseptible to SQL Injection
         *          imagine if the user input was: "5; SELECT * FROM USERS;" or "12; DROP TABLE *;"
         * 
         * PREPARED STATEMENTS
         *      designed to parse out placeholders in a string and sanitize ALL inputs
         *      String query = "SELECT * FROM customer WHERE id = ?";
         *      
         */
        String query = "SELECT * FROM customer WHERE customer_id = ?";
        
        try(PreparedStatement preppedStatement = conn.prepareStatement(query)) {

            // fill in placeholders based on index - indexes are 1-based
            preppedStatement.setInt(1, id);

            try(ResultSet resultSet = preppedStatement.executeQuery()) {

                // resultSet.next() will be false if there is no value found
                if(resultSet.next()) {
                    // mapping the ResultSet to a Customer object
                    Customer customer = new Customer();
                    customer.setCustomerId(resultSet.getInt("customer_id"));
                    customer.setCity(resultSet.getString("city"));
                    customer.setCountry(resultSet.getString("country"));
                    customer.setFirstName(resultSet.getString("first_name"));
                    customer.setLastName(resultSet.getString("last_name"));
                    customer.setEmail(resultSet.getString("email"));

                    // returning the optional with the returned data
                    return Optional.of(customer);
                }
                return Optional.empty();
            }

        } catch(SQLException e) {

            // return an empty Optional if the statement could not be executed
            System.out.println("Query could not be executed: findById()");
            return Optional.empty();
        }
    }

    /**
     * uses Statement instead of PreparedStatement to simulate SQL injection
     */
    public List<Customer> findByCountryUnsafe(String country) {

        String query = "SELECT * FROM customer WHERE country = '" + country + "'";
        List<Customer> results = new ArrayList<>();
        
        try(Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while(resultSet.next()) {

                // mapping each row of the ResultSet to a Customer object
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customer_id"));
                customer.setCity(resultSet.getString("city"));
                customer.setCountry(resultSet.getString("country"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));

                // adding each object to the results
                results.add(customer);
            }
            return results;

        } catch(SQLException e) {

            // return an empty list if the statement could not be executed
            System.out.println("Query could not be executed: findByCountry()");
            return new ArrayList<>();
        }

        
    }


    public Customer insert(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, email, city, country) VALUES(?,?,?,?,?)";

        try(PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getCity());
            preparedStatement.setString(5, customer.getCountry());

            preparedStatement.executeUpdate();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {

                // getting the generated ID and adding it to the customer object before returning it
                resultSet.next();   // need to call .next() to get the data out of the ResultSet
                customer.setCustomerId(resultSet.getInt(1));
                return customer;
            }
            
        } catch (SQLException e) {
            System.out.println("Query could not be executed: insert()");
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * TRANSACTION MANAGEMENT
     *      - commit() - fully commits data to database
     *      - rollback() - remove any database operations that are not fully committed
     * 
     *      - getAutoCommit() - returns a boolean for if automatic transaction management is enabled
     *          - true: sql queries will commit automatically
     *          - false: sql queries need to be manulaly comitted
     */
    public int update(Customer customer) throws SQLException{
        String sql = "UPDATE customer SET first_name = ?, last_name = ?, email = ?, city = ?, country = ? WHERE customer_id = ?";
        boolean manageTransaction = false;
        int rowsUpdated = 0;

        /**
         * Auto-Commit is managed on the connection
         *      - once it changes commit modes (manual vs auto), it will be like that everywhere the connection is used
         */
        try {
            // if true, then transaction is not being managed elsewhere, and WE should so so here
            manageTransaction = conn.getAutoCommit();
            if(manageTransaction) {
                conn.setAutoCommit(false);
            }

            try(PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, customer.getFirstName());
                preparedStatement.setString(2, customer.getLastName());
                preparedStatement.setString(3, customer.getEmail());
                preparedStatement.setString(4, customer.getCity());
                preparedStatement.setString(5, customer.getCountry());
                preparedStatement.setInt(6, customer.getCustomerId());

                rowsUpdated = preparedStatement.executeUpdate();
                if(manageTransaction) {
                    conn.commit();
                }
            }
        } catch (SQLException e) {

            // remove any non-committed data if an error occurs
            if(manageTransaction) {
                System.out.println("Query could not be executed: update(). Transaction being rolled back.");
                conn.rollback();
            }
        } finally {
            // make sure connection's transaction managtement settings are back to where they were before this method ran
            conn.setAutoCommit(manageTransaction);
        }

        return rowsUpdated;

    }


    public int delete(int id) {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        int deletedRows = 0;

        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            deletedRows = preparedStatement.executeUpdate();

            return deletedRows;
        } catch (SQLException e) {
            System.out.println("Query could not be executed: delete()");
            return deletedRows;
        }
    }
}
