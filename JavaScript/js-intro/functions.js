
function myFunction() {

    x = 4;              // ReferenceError: Cannot access 'x' before initialization

    /**
     * VARIABLE HOISTING
     *      - JS will move your variable declarations to the TOP of their scope
     *      - let/const will still be moved, but you can't do anything with the variable until it has been initialized
     *          - variables put into the "Temporal Dead Zone" until initialization
     *      - var on the other hand, WILL let you access it before it is initialized
     *          - variable will just be undefined
     */
    console.log(y);     // ReferenceError: y is not defined
    console.log(x);     // ReferenceError: Cannot access 'x' before initialization
    console.log(z);     // undefined (but no error)

    let x = 12;
    var z = 15;
}
//myFunction();


function mySecondFunction(param1, param2, param3, param4) {
    return param1 + param2 + param3 + param4;
}

// no error thrown for invalid function calls
// function is still called with the values it has been given, or can fit into it's params
console.log(mySecondFunction(10, 20));   
console.log(mySecondFunction(10, 20, 30, 40, 50, 60));

// can give params default values to use if they aren't provided any
function myThirdFunction(param1, param2, param3 = 30, param4 = 40) {
    return param1 + param2 + param3 + param4;
}
console.log(myThirdFunction(10, 20));  

// you can use REST operator to condense additional params into an array
function myFourthFunction(param1, param2, ...param3) {

    let sum = 0;
    if(param3.length > 0) {
        for(let num of param3) {
            sum += num;
        }
    }
    return sum + param1 + param2;
}
console.log(myFourthFunction(10, 20, 30, 40, 50, 60));



/**
 * 
 * Arrays have TONS of built-in Higher Order Functions
 * 
 */
const fruits = ["mango", "banana", "strawberry", "apple", "orange", "blueberry", "raspberry", "blackberry"];

// .map() - maps each value in the array to a new value, and returns it is part of a NEW list
const allCaps = fruits.map(fruit => fruit.toUpperCase());
console.log(allCaps);

// .filter() - return a new list with only filtered out elements
const oopsAllBerries = fruits.filter(fruit => fruit.includes("berry"));
console.log(oopsAllBerries);


/**
 * CALLBACK FUNCTIONS
 *      - functions that are invoked at a later point in time
 *          - usually seen as functions passed in as parameters to a function, or returned from a function
 */

// execute the callback function after the given number of milliseconds has passed
setTimeout(() => {
    console.log("It has been 3 seconds!");
}, 3000);

// execute the callback at each interval of the given time. clearInterval() to stop it - memory leaks otherwise
let count = 10;
const countdown = setInterval(() => {
    if(count <= 0) {
        console.log("LIFTOFF!!!!!!!!");
        clearInterval(countdown);           // stops the interval AFTER the current iteration finishes
    }

    console.log(count);
    count--;
}, 1000); 