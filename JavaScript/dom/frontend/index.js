const BACKEND_URL = "http://localhost:8080/api/v1/movies";
const allMovies = [];



/**
 * DOM - Document Object Model
 *      - represents the make up of your HTML page
 *      - like a tree, where each nested HTML tag is a new branch on the tree
 * 
 *      - in JS, there is a 'document' object that we can use to manipulate the DOM programatically
 */

/**
 * EVENT LISTENERS
 *      - event: anything that can happen on an HTML page (click, hover, etc.)
 *      - event listeners will react (aka, do something) when an event occurs
 * 
 *      - in JS, you can create your own events and fire them off (similar to pub/sub model)
 *          - these you fire off and listen to manually
 *          - for today, we will focus on built-in DOM events
 * 
 *      - .addEventListener
 *          - 2 params: name of event to listen for, and callback frunction to execute when the event happens
 */

// DOMContentLoaded - fires off as soon as the page is fully loaded and ready to go
document.addEventListener("DOMContentLoaded", () => {
    // send an HTTP request to get data as soon as the page is loaded

    /**
     * AJAX - Asynchronous JavaScript and XML
     *      - works with the XmlHttpRequest (XHRs)
     *          - readystates to desxcribe the current state of the HTTP request
     *              - 0 - unsent
     *              - 1 - opened
     *              - 2 - headers received
     *              - 3 - loading
     *              - 4 - done, request completed -> this is the main one we care about
     */
    let xhr = new XMLHttpRequest();     // readyState 0

    // creating a new callback function to run when the readystate changes
    xhr.onreadystatechange = () => {

        // === is strict equality check value AND data type
        if(xhr.readyState === 4) {

            // request is complete, we can get our data (aka, the returned JSON)
            let movies = JSON.parse(xhr.responseText);      // .parse(): json string -> js object

            console.log(movies);

            // dynamically add each movie to the table
            movies.forEach(movie => addMovieToTable(movie));
        }
    };

    // create the request with open() - add data to request body, manipulate headers, etc.
    xhr.open("GET", BACKEND_URL);         // moves to readyState 1

    // send the request
    xhr.send();

});

// HANDLE NEW MOVIES BEING CREATED WITH THE FORM
document.getElementById("new-movie-form").addEventListener("submit", (eventInfo) => {

    // prevents any default actions from occuring - tells the DOM that this event is explicitly handled
    eventInfo.preventDefault();

    /**
     * FormData
     *      - makes it WAY easier to grab data out of forms
     *      - automatically map data inside of input fields to a JS object
     *      - easily retrieve values from the form using .get() with the name of the input field
     * 
     *      - otherwise, you'd have to do document.getElementById("some-id").value() on EACH input field
     */
    let inputData = new FormData(document.getElementById("new-movie-form"));

    // IMPORTANT: make sure your object property names match to what your backend is expecting
    const newMovie = {
        title: inputData.get("new-movie-title"),    
        rating: inputData.get("new-movie-rating"),
        genre: inputData.get("new-movie-genre"),
        director: {
            firstName: inputData.get("new-director-firstname"),
            lastName: inputData.get("new-director-lastname")
        }
    }
    
    /**
     * FETCH
     *      - easier way to send HTTP requests
     *      - built on AJAX
     *      - return a Promise
     *          - async/await
     *          - .then()
     */
    fetch(BACKEND_URL, {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(newMovie)      // JSON stringify: js object -> json string
    })
    .then((httpResponse) => {
        // .then() runs for ALL status codes in the 200s

        if(httpResponse.status === 201) {

            // .json() returns ANOTHER promise that needs to be handled
            return httpResponse.json();
        }
        return null;
    })
    .then((movie) => {
        console.log(movie);
        addMovieToTable(movie);
    })
    .catch((error) => {
        // .catch() runs for ALL status codes in the 400s and 500s

        // show an error message to the user, with a toast or alert

        // BE VERY CAREFUL ABOUT SHOWING ERROR MESSAGES TO USERS
        console.error("ERROR OCCURED: " + error);
    })


});


///// HELPER FUNCTIONS /////

const addMovieToTable = (newMovie) => {

    // use document object to manually create HTML elements and then manually add them to the DOM

    // creates <tr></tr> for table row
    let tr = document.createElement("tr");    
    
    // creates <td></td> for table cells
    let titleTD = document.createElement("td");
    let genreTD = document.createElement("td");
    let ratingTD = document.createElement("td");
    let directorTD = document.createElement("td");
    let editBtnTD = document.createElement("td");
    let delBtnTD = document.createElement("td");

    // filling each cell with data
    titleTD.innerText = newMovie.title;
    genreTD.innerText = newMovie.genre;
    ratingTD.innerText = newMovie.rating;
    directorTD.innerText = newMovie.director.firstName + " " + newMovie.director.lastName;

    editBtnTD.innerHTML = `<button class="btn btn-primary p-1" id="EDIT-${newMovie.id}">Edit</button>`;
    delBtnTD.innerHTML = `<button class="btn btn-danger p-1" id="DEL-${newMovie.id}">Delete</button>`;

    // put all the TDs into the TR
    tr.appendChild(titleTD);
    tr.appendChild(genreTD);
    tr.appendChild(ratingTD);
    tr.appendChild(directorTD);
    tr.appendChild(editBtnTD);
    tr.appendChild(delBtnTD);

    // ensuring each TR is GUARENTEED to be unique
    tr.setAttribute("id", `TR-${newMovie.id}`);

    // add TR to table body
    let tableBody = document.getElementById("movie-table-body");
    tableBody.appendChild(tr);

    // adding newMovie to global movies list
    allMovies.push(newMovie);
}