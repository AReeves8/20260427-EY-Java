package main.java.com.skillstorm.notification_status;

/**
 * - enums implicitly marked as final so they cannot be extended
 * 
 * - enums implicitly extend java.lang.Enum, and therefore cannot extend anything else
 * - enums CAN implement interfaces
 * 
 * - enums can be compared with both == and .equals()
 *      - only ever one instance of an enum constant so there's no harm in using ==
 * 
 * - enums CAN have abstract methods, but they have to be implemented by the constant
 *  - EX:
 *          LOW(1) {
 *               @Override
 *               boolean someMethod() {
 *                   return true;
 *               }
 *           }
 * 
 *  - BUILT IN METHODS FROM JAVA.LANG.ENUM
 *      - values() - array of all enum constants (Priority[])
 *      - valueOf() - takes in string of enum constant name, returns the enum constant ("LOW" -> LOW)
 *      - ordinal() - int index based on the listed constants
 *          - NEVER store ordinals anywhere. if the order of the constants change, so will their ordinals
 *      - name() - return the constant as a String (LOW -> "LOW")
 *      - toString() - unless you override it, it's the same as name()
 */


public enum Priority {

    // your enum constants will call the constructor
    // constructor is called once per constant when the class is loaded
    LOW(1),
    MEDIUM(3),
    HIGH(7),
    CRITICAL(10);

    private final int level;

    // constructors are ALWAYS private - cannot be declared as anything else and will be implicitly private if not defined
    Priority(int level) {
        this.level = level;
    }

    // could have multiple constructors... not encouraged
    Priority(int level, boolean someValue) {
        this.level = level;
    }

    // getter for the value
    public int getLevel() {
        return level;
    }

    // method to convert a value -> enum constant
    public static Priority fromLevel(int level) {
        for(Priority p : values()) {
            if(p.level == level) {
                return p;
            }
        }
        throw new IllegalArgumentException("No Priority with level: " + level);
    }


}
