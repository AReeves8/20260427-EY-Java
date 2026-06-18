/**
 * ASYNC and AWAIT
 *      - alternative way to handle promises or any sort of asynchronous JS
 *          - .then().catch().finally() is the other way
 * 
 *      - async keyword to define asynchronous functions
 * 
 *      - await keyword to tell JS that something needs to be waited on to finish
 *          - pauses the surrounding code block
 *              - IT CAN ONLY BE USED INSIDE ASYNC FUNCTIONS
 */

// SyntaxError: await is only valid in async functions and the top level bodies of modules
// function syncFunc() {
//     await console.log("waiting...");
// }

async function myAsyncFunc() {
    return Promise.resolve("This is a promise");
}

async function awaitFunc() {

    // await acts like your .then()
    const msg = await myAsyncFunc();
    console.log(msg);
}

function thenFunc() {

    // alternative to above
    myAsyncFunc().then((msg) => {
        console.log(msg);
    });
}

awaitFunc();
thenFunc();


/**
 * JS EVENT LOOP
 *      - js is a single-threaded language
 *      - runtime model for JS and it allows for asynchronous operations
 * 
 *      - this determines which subtasks to execute and in which order
 *          - microtask queue (ex: Promises)
 *          - macrotask queue (ex: setTimeout, setInterval)
 *          - execution stack (ex: regular code)
 * 
 *          execution stack > microtask queue > macrotask queue
 */

console.log("1");
setTimeout(() => console.log("2"), 0);
setTimeout(() => console.log("3"), 0);
Promise.resolve("4").then(data => console.log(data));
console.log("5");

// SUGGESTIONS:
// 1,5,2,3,4
// 2,3,1,5,4
// 1,4,5,2,3

// REAL ANSWER: 1,5,4,2,3




/**
 * PYRAMID OF DOOM (Callback Hell)
 *      - when asynchronous operations are nested inside of each other using callbacks
 *      - the code indentation grows to the right more and more, creating a pyramid
 * 
 *      - problem:
 *          - REALLY difficult to debug errors
 *          - need to do error handling at each level, further inflating the code
 *          - hard to read and follow, thus hard to maintain
 * 
 *      - solution: Promises instead of callbacks, and using .then()
 */

// Simulating async operations with setTimeout callbacks
function getUser(id, callback) {
    setTimeout(() => callback(null, { id, name: 'Alice' }), 100);
}

function getOrders(_user, callback) {
    setTimeout(() => callback(null, [{ orderId: 1 }, { orderId: 2 }]), 100);
}

function getOrderDetails(order, callback) {
    setTimeout(() => callback(null, { ...order, item: 'Widget', price: 9.99 }), 100);
}

function processPayment(details, callback) {
    setTimeout(() => callback(null, { status: 'paid', details }), 100);
}

// The pyramid of doom — each step nests deeper
getUser(42, (err, user) => {
    if (err) return console.error(err);
    getOrders(user, (err, orders) => {
        if (err) return console.error(err);
        getOrderDetails(orders[0], (err, details) => {
            if (err) return console.error(err);
            processPayment(details, (err, receipt) => {
                if (err) return console.error(err);
                console.log('Payment complete:', receipt);  // finally here, 4 levels deep
            });
        });
    });
});
