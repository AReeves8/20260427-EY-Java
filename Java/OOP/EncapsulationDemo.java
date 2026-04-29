public class EncapsulationDemo {

    public static void main(String[] args) {

        System.out.println("--- BEFORE ANY TRANSACTIONS ---");
        System.out.println(BankAccount.getTransactions());


        BankAccount account1 = new BankAccount("Austin", 0);
        System.out.println(account1.getTransactions());
        account1.deposit(32.45);
        account1.withdraw(2.45);
        System.out.println("ACCOUNT 1 NUMBER:" + account1.getAccountNumber());


        BankAccount account2 = new BankAccount("Austin", 0);
        System.out.println(account2.getTransactions());
        account2.deposit(32.45);
        account2.withdraw(2.45);
        System.out.println("ACCOUNT 2 NUMBER:" + account2.getAccountNumber());

        System.out.println("--- AFTER ALL TRANSACTIONS ---");
        System.out.println(BankAccount.getTransactions());

    }

}





/**
 * ENCAPSULATION
 *      - separation of data
 *      - security purposes (private, protected, etc.)
 *          - hiding information from the public
 *          - getters and setters
 * 
 *  -------------------------------------------------------------------------------------- 
 * 
 *      - wrap related state and behavior up into a single location
 *          - separation of concerns
 * 
 *      - getters and setters are used to control how data is accessed and manipulated
 * 
 *      - access modifiers:
 *          - private: only this specific class can use the property or method
 *          - protected: only this class, a subclass, or any class in the same package can use the property or method
 *              - what if a subclass was in a different package? - yes!
 *          - public: any class that imports this class can use the properties or methods
 *          - default (package-private): can be used by classes within the same package
 *              - what if a subclass was in a different package? - no! 
 * 
 *      
 *          - static: all instances have the same location in memory. 
 *              - when the class is loaded into memory, so are ALL of the static members
 *              - a variable will have the same value on every instance since they all reference the same location in memory
 *          - final: once the value is set, it cannot be changed
 *              - a TRUE constant property should be static and final
 *              - without static, each instance could have a different value, depending on how it is initialized
 * 
 */
class BankAccount {

    private String owner;
    private double balance;
    private static int transactionCount;

    // you need to give them a value when they're declared or in EVERY constructor
    private final int accountNumber;        


    // STATIC INITIALIZATION BLOCK
    static {
        /**
         * Run once when the class is loaded into memory
         *      - usually used to perform some calculations or execute some code before the class is instantiated
         */
        
        transactionCount++;

        // cannot reference non-static properties inside ANY type of static block
        //accountNumber = 12;
    }


    // PARAMETIERIZED CONSTRUCTOR
    public BankAccount(String owner, double initialbalance) {
        this.owner = owner;

        if(initialbalance >= 0) {
            this.balance = initialbalance;
        }
        else {
            this.balance = 0;
        }

        accountNumber = transactionCount * 2;
    }

    // GETTER METHODS
    public String getOwner() {
        return owner;
    }
    public double getBalance() {
        return balance;
    }
    public int getAccountNumber() {
        return accountNumber;
    }

    // we can control HOW the balance in the account is changed
    public void deposit(double amount) {
       
        // only increase balance if amount is positive
        if(amount > 0) {
            balance += amount;
        }      
        transactionCount++;
    }

    public void withdraw(double amount) {
        // only increase balance if amount is positive
        if(amount > 0 && amount <= balance) {
            balance -= amount;
        } 
        transactionCount++;
    }


    public static int getTransactions() {
        return transactionCount;
    }

}





