package Java.StringsAndControlFlow;

public class Strings {

    public static void main(String[] args) {

        System.out.println("----- STRING POOL -----");


        /**
         * String Pool
         *      - all String literals are placed into the Pool
         *      - if you instantiate multiple literals with the same text, they will reference the same place in memory
         * 
         *      - new String(), this will bypass the String Pool entirely
         */
        String password1 = "superSecretPassword@12345";
        String password2 = "superSecretPassword@12345";
        String password3 = new String("superSecretPassword@12345");


        // for strings, == checks memory locations, not text
        if(password1 == password2){
            System.out.println("The passwords are the same object in memory.");
        }

        // false - password3 is not in the String pool and thus not in the same memory location despite having the same text
        if(password1 == password3){
            System.out.println("Password 1 and 3 are the same object in memory.");
        }

        // .equals() to check if ANY two strings have the same text
        if(password1.equals(password3)) {
            System.out.println("Password 1 and 3 have the same text.");
        }

        // false - changing the string in an existing reference can create a new String in the String Pool
        if(password1 == password2 + " Hello!"){
            System.out.println("changed password 2");
        }


        String existingRole = "manager";
        String newRole = "MANAGER".toLowerCase();   // ALL string methods return new String() objects

        // false
        if(existingRole == newRole) {
            System.out.println("ROLES ARE THE SAME OBJECT");
        }

        // true
        if(existingRole.equals(newRole)) {
            System.out.println("ROLES ARE THE SAME TEXT");
        }


        System.out.println("----- STRING METHODS -----");

        /**
         * STRING METHODS
         *      - since strings are immutable, every method returns a new String()
         *          - the original string is left alone
         */

        String sku = "      sku-12341234-1234       abcd   ";

        String trimmedSku = sku.trim();         // removes excess whitespace at beginnning and end (not in the middle)
        String upperSku = sku.toUpperCase();    // turns all lowercase chars to uppercase

        System.out.println("Original SKU: " + sku);         //  original string remains unchanged
        System.out.println("Trimmed SKU: " + trimmedSku);
        System.out.println("Upper SKU: " + upperSku);


        // String indexes start at 0 so count up from there
        String realSku = "SKU-BLU42-2001";
        System.out.println("Length: " + realSku.length());                           // 14
        System.out.println("First '-' Location: " + realSku.indexOf("-"));      // 3
        System.out.println("Last '-' Location: " + realSku.lastIndexOf("-"));   // 9
        System.out.println("First 'z' Location: " + realSku.indexOf("z"));      // -1 is returned when the char is not found
        System.out.println("Char At Index 7: " + realSku.charAt(7));          // 4


        String str1 = "123456789";
        System.out.println("Beg. Index Only: " + str1.substring(3));                  // 456789
        System.out.println("Beg. & End Index: " + str1.substring(3, 6));    // 456 - ending index is EXCLUSIVE


        String str2 = str1.replace("456", "ABC");
        System.out.println("After Replace: " + str2.substring(3, 6));       // ABC


        System.out.println("----- STRING BUILDER -----");

        /**
         * STRING BUILDER
         *      - more string methods for building out larger, more complex strings
         * 
         *      - generally treated as objects by Java, not necessarily as Strings
         * 
         */

        String[] items = {"Laptop", "Mouse", "Keyboard", "Monitor"};
        double[] prices = {999.99, 29.99, 49.99, 149.99};

        double total = 0;
        StringBuilder receipt = new StringBuilder();
        receipt.append("=== BEGIN RECEIPT ===\n");      // StringBuilder methods DO modify the original string
        for(int i = 0; i < items.length; i++) {

            // adding each item to the receipt
            receipt.append(String.format("%-10s $%7.2f%n", items[i], prices[i]));
            total += prices[i];
        }

        // many StringBuilder methods return "this" and thus can be chained together
        receipt.insert(21, "\nORDER #ORD-1234-5678\n")
            .append("=====================")
            .append(String.format("\n%-10s $%7.2f%n", "TOTAL", total));


        System.out.println(receipt);            // call to toString is implicit so this will print the String



        StringBuilder sb1 = new StringBuilder("superSecretPassword@12345");
        StringBuilder sb2 = new StringBuilder("superSecretPassword@12345");

        // false
        if(sb1 == sb2) {
            System.out.println("STRING BUILDERS ARE THE SAME OBJECT");
        }

        // false
        if(sb1.equals(sb2)) {
            //System.out.println("STRING BUILDERS ARE THE SAME TEXT");    // doesn't actually check text
            System.out.println("STRING BUILDERS ARE THE SAME OBJECT");
        }

        // .equals() is a method that comes from Object
        // StringBulder DOES NOT override that method, leaving it at default Object behavior
        // need to manually check the strings if you want to compare String Builder text
        if(sb1.toString().equals(sb2.toString())) {
            System.out.println("STRING BUILDERS ARE THE SAME TEXT");    
        }
    }

}
