/**
 * EcmaScript 6 (ES6)
 *      - JavaScript is built on EcmaScript
 *      - released in 2015
 */

// arrow functions
const myFunc = () => {
    console.log("Hello world!");
}

// objects
const person = {
    age: 26,
    name: {
        firstName: "Austin",
        lastName: "Reeves"
    },
    favColor: "gray"
}

// const makes an object constant, BUT NOT the properties within the object
person.favColor = "blue";
console.log(person);

// can add properties to objects as you need
person.favDayOfTheWeek = "Friday";
console.log(person);

// can make your objects fully read-only
Object.freeze(person);

// no errors thrown, changes are just ignored
person.favColor = "red";    
person.location = "Dallas";
console.log(person);

/**
 * Object metadata
 *      writable - can modify properties
 *      enumerable - can enumerate over the properties
 *      configurable - can change the metadata of the properties
 */
const animal = {
    specicies: "dog",
    name: "Penelope"
}

// can add a new property or modify an existing with defineProperty
Object.defineProperty(animal, "name", {
    writable: false,
    configurable: true,
    enumerable: false
})

animal.name = "Ruby";
console.log(animal);



/**
 * ENUMERATRION VS ITERATION
 *      objects are enumerable NOT iterable
 *      arrays are iterable
 * 
 *      enumeration: for(... in ...)
 *          - skips over any properties that have enumerable: false
 *      iteration: for(... of ...)
 */
for(property in animal) {
    // prints the keys of an object's properties
    console.log(property); 

    // square bracket notation to get the value of a key on an object
    console.log(animal[property]);
}



/**
 * DESTRUCTURING
 *      - works on both objects and arrays
 *          - objects: {}
 *          - arrays: []
 *      
 *      - this allows you to pull out individual values from an object or array
 */

const {name, specicies} = animal;

console.log(`NAME: ${name} vs ANIMAL.NAME: ${animal.name}`);

const nums = [1,2,3,4,5];

// rest operator condenses all remaining indexes into a single array
const [num1, num2, ...everythingElse] = nums;

console.log(num1);
console.log(num2);
console.log(everythingElse);

/**
 * SPREAD vs REST operator
 *      - ...
 * 
 *      spread operator takes the values in an array/object and spreads them out
 *      rest operator takes individual values and condenses them into an array/object
 */

function add(num1, num2) {
    return num1 + num2;
}
// using spread operator to spread array values into the individual function params
console.log(add(...everythingElse));


// using rest operator to copy object properties
const user = {
    id: 1,
    email: "areeves@skillstorm.com",
    password: "password"
}

// copies values from user to user 2
const user2 = {
    id: 2,
    ...user     // you have to be careful about how you copy.. this will override the id we just set
}
console.log(user2);

const user3 = {
    ...user,
    id: 3
}
console.log(user3);




/**
 * NULLISH COALESCING OPERATOR
 *      - ??
 * 
 *      - one line null check
 *          - if a value is null, give it a value
 */

let ageValue = null;

const person2 = {
    name: "Austin",
    age: ageValue ?? 25
}
console.log(person2);


/**
 * OPTIONAL CHAINING
 *      - ?.
 * 
 *      - checks if an object is null before accessing its property
 *          - avoid null access errors
 */

person2.home = {
    street: "123 House Ln",
    city: "Dallas",
    state: "TX"
}

person2.home = null;

// automatically check if home is null before trying to access the value of street
// will be undefined if home was null
console.log(person2.home?.street);