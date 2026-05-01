package Java.StringsAndControlFlow;

public class ControlFlow {

    public static void main(String[] args) {


        /**
         * CONTROL FLOW
         *      - changing the natural top-down order of Java
         *          - branching lets you skip blocks of code
         *              - if/else if statements
         *              - ternary operators
         *              - switch statements
         *              - enhanced switches
         *          - loops let you run the same block of code over and over again
         *              - while/do-while
         *              - for
         *              - enhanced for (for-each)
         * 
         *          - break: exit the current loop 
         *          - continue: brings you back to the start of the current loop you are in
         */

        System.out.println("----- IF/ELSE IF/ELSE & TERNARY -----");

        double total = 6.87;
        double shippingCost = 0;

        // depending on the total, we change the shipping cost: branching means only one of these blocks will run
        if(total > 100) {
            shippingCost = 5.99;
        }
        else if (total > 50){
            shippingCost = 4.99;
        }
        else if (total > 25){
            shippingCost = 3.99;
        }
        else {
            shippingCost = 2.99;
        }
        System.out.println("Your total with shipping is $" + (total + shippingCost));
        
        // ternary - one line if-statement
        //                boolean statement ?    value_if_true     :   value_if_false
        String message = shippingCost > 4.0 ? "High shipping cost" : "Low shipping cost";
        System.out.println(message);


        // nested ternarys - read the inner most first
        int stockLevel = 3;
        String stockStatus = stockLevel == 0 ? "Out of Stock" : stockLevel <= 5 ? "Low Stock" : "In Stock";

        // above is same as below:
        if(stockLevel == 0) {
            stockStatus = "Out of Stock";
        }
        else {
            if(stockLevel <= 5) {
                stockStatus = "Low Stock";
            }
            else {
                stockStatus = "In Stock";
            }
        }


        System.out.println("----- SWITCH & ENHANCED SWITCH -----");

        // ENHANCED SWITCH WAS ADDED IN JAVA 14 - THEREFORE IT WON'T BE ON OCA. 
        // OCA IS ONLY JAVA 8 AND BEFORE


        String ticketPriority = "LOW";

        switch(ticketPriority) {
            case "CRITICAL":
                System.out.println("Ticket needs to be done ASAP!");
                break;      // break will prevent fall-through
            case "HIGH":
                System.out.println("Ticket needs to be completed soon.");
                break;
            case "MEDIUM":
                System.out.println("Ticket needs to be worked on.");
                break;
            
            // In Java 8, if you want multiple cases executing the same thing, you purposefully let them fall-through
            case "LOW":
            case "TRIVIAL":                
                System.out.println("Ticket is not of priority.");
                break;
            //case "LOW", "TRIVIAL":      // multiple conditions on the same case was not allowed until Java 14
            default: 
                System.out.println("INVALID PRIORITY.");
        }

        /**
         * Enhanced Switch - Java 14+
         *      - returns a value rather than executing a block
         *      - no fall-through
         *          - don't need break
         *      - arrow syntax
         */
        String priorityMessage = switch(ticketPriority) {
            case "CRITICAL" ->
                "Ticket needs to be done ASAP!";
            case "HIGH"->
                "Ticket needs to be completed soon.";
            case "MEDIUM"->
                "Ticket needs to be worked on.";
            case "LOW", "TRIVIAL"->                
                "Ticket is not of priority.";
            default-> 
                "INVALID PRIORITY.";
        };



        System.out.println("----- LOOPS -----");

        double[] prices = {120.56, 23.54, 98.76, 1.67, 32.09, 88.88};
        double runningTotal = 0;

        // standard for loop
        for(int i = 0; i < prices.length; i++) {
            runningTotal += prices[i];
            System.out.println("NORMAL LOOP RUNNING TOTAL: " + runningTotal);
        }

        // OCA could leave out one or more parts of the standard for loop..
        int i = 0;   
        for(; i < prices.length; i++) {     // no issue, AS LONG AS the increment variable is declared before the loop
            runningTotal += prices[i];
            System.out.println("WEIRD LOOP 1 RUNNING TOTAL: " + runningTotal);
        }

        i = 0;
        for(; ; i++) {                      // no issue, AS LONG AS there is some logic to break the loop inside
            if(i >= prices.length) {
                break;                      // no break -> infinite loop
            }
            runningTotal += prices[i];
            
            System.out.println("WEIRD LOOP 2 RUNNING TOTAL: " + runningTotal);
        }

        i = 0;
        // this is basically equivalent to a while(true) loop
        for(;;) {                           // no issue, AS LONG AS there is some logic to change the increment operator in the loop
            
            if(i >= prices.length) {
                break;
            }
            runningTotal += prices[i];
            i++;
    
            System.out.println("WEIRD LOOP 3 RUNNING TOTAL: " + runningTotal);
        }

        System.out.println("NESTED FOR LOOP QUESTION:");
        for(int j = 0; j < 5; j++) {
            for(int k = 0; k < 5; k++) {
                System.out.print((j+k) + " ");

                if(j+k > 4) {
                    break;      // breaks out of the INNER loop, not the OUTER
                }
            }
            System.out.println();
        }
        // What will be the final print out?
        /**
         * 0 1 2 3 4 
         * 1 2 3 4 5 
         * 2 3 4 5 
         * 3 4 5 
         * 4 5
         */

        // enhanced for-loop (for each). they do work on regular arrays
        for(double price : prices) {
            System.out.println(price);
        }

        // compilation error: strings are not iterable
        // String temp = "Some string";
        // for(char c : temp) {        
        //     System.out.println(temp);
        // }


        // while loops check some condition FIRST and then loop
        while(runningTotal > 1000) {
            runningTotal += 100;
            System.out.println("Total is going up up up");
        }

        // do-while loops run code first THEN check condition at the END
        do {
            runningTotal += 100;
            System.out.println("Total is going up up up");      // will always run at least once
        } while(runningTotal > 1000);

        // code block is unreachable -> compilation error
        // unreachable code is ALWAYS a compilation error
        // while(false) {
        //     // do something
        // }

        // code block CAN be reached -> NO compilation error
        do {
            // do something
        } while(false);
    }

}
