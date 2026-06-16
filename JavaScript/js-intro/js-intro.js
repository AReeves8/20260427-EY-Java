/**
 * JavaScript is related to Java the way a carpet is related to a car
 * 
 * Mocha -> JavaScript
 * 
 * 
 * Run JS on command line:
 *      cd into your workspace directory
 *      node <app-name>
 */

console.log("Hello World!");

/**
 * VARIABLES
 *      var - global or function scoped (pretty much... NEVER use it)
 *      let - block scoped  
 *      const - block scoped, declares a constant
 */

const NUM = 8;          // not declared in a block, so it is global scoped

if(NUM > 5) {
    const NUM2 = 12;    // only available in this if-statement
    var num3 = 88;      // still global scoped to the entire file
}

console.log("NUM1: " + NUM);
//console.log("NUM2: " + NUM2);     // ReferenceError: NUM2 is not defined
console.log("NUM3: " + num3);

function myFunction(param) {

    // js will let you re-declare a variable in a stricter scope
    let num3 = 89;                  
    console.log(num3);

    let x = 10;    
    return x * param;
}

console.log(myFunction(5));


/**
 * DATA TYPES
 *      - js will inference your data types
 * 
 *      Primitives:
 *          - number, string, boolean, undefined, null, BigInt (huge numbers declared with 'n'), Symbol (more used as a JS identifier) 
 * 
 *      Non-primitives:
 *          - objects, arrays, functions, etc.
 * 
 * 
 *      typeof - keyword used to get the data type of something
 */
let y;
console.log(typeof y);      // undefined - never given a value

/**
 * TYPE COERCION
 *      - JS tries to automatically convert your data types
 */
let number = 3;
let fakeNum = "5";
let sum1 = number + fakeNum;    // number is converted to a String by Type Coercion
console.log("SUM1: " + sum1);

let result1 = myFunction("5");  // string is converted to a number because it's doing multiplication (there's no string operation for *)
console.log("RESULT1: " + result1);

let result2 = myFunction("five");   // string is converted to a number because it's doing multiplication (there's no string operation for *)
console.log("RESULT2: " + result2); // NaN (not a number)

// strings cannot be multiplied, so always NaN
console.log(10 * "hello");
console.log("hello" * 5);

result1 *= "hello";
console.log("RESULT1: " + result1);

let str = "hello";
str *= 3;
console.log(str);



/**
 * TRUTHY VS FALSEY
 * 
 *      truthy: js will take a variable with this value and convert it to true
 *          - {} any object, even an empty one
 *          - [] any array, even an empty one
 *          - true
 *          - "false" - strings are usually true
 *          - "0"
 *          - " "
 *          - any thing that isn't listed as falsey
 * 
 *      falsey: js will take a variable with this value and convert it to false
 *          - false
 *          - "" (empty string)
 *          - 0, 0n, -0 (any version of zero)
 *          - null
 *          - undefined
 *          - NaN
 */


const person = {};

if(person) {
    // person is not null OR undefined
    console.log(person);
}


let dec = 123.456;
console.log(dec * 12);

// BigInts cannot be decimals (it has int in the name)
let bigNum = 12309042815012n;
//let bigNum2 = 1234.3245n;


/**
 * JS FUNCTIONS
 *      - functions are treated as first class citizens
 * 
 *      - Higher Order Functions (HOFs)
 *          - any function that takes in a function as a parameter OR returns a function
 * 
 *          - Callback Functions
 *              - functions that are invoked at a later point in time
 */

function greeterGenerator(name) {

    return function () {
        // CLOSURE - idea that functions in JS retain access to their outer lexical environment
        console.log("Hello, " + name);
    }
}

const greetGina = greeterGenerator("Gina");
greetGina();

// can automatically call a returned function
greeterGenerator("Andrew")();


const fruits = ["apple", "orange", "banana", "mango"];
fruits.forEach(function (itr, index, copyOfFruitsArray) {

    // template literal - store the string EXACTLY as they are typed
    const message = 
`----------
Current Item: ${itr}
Index: ${index}
Array Copy: ${copyOfFruitsArray}
`;
    
    console.log(message);
});


// arrow syntax - shorthand for writing function (same syntax as in Java)
fruits.forEach((itr, index, copyOfFruitsArray) => {

    // template literal - store the string EXACTLY as they are typed
    const message = 
`----------
Current Item: ${itr}
Index: ${index}
Array Copy: ${copyOfFruitsArray}
`;
    
    console.log(message);
});