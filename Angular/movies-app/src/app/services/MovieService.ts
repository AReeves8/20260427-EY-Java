import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Movie } from "../types/Movie";
import { catchError, Observable, throwError } from "rxjs";


/**
 * INJECTABLE
 *      - register the class with the Angular DI container
 *      - create a singleton instance
 *      
 *      - specify where it's provided from
 *          - root:  provides the service at your project root
 *          - platform: higher than root. only useful when you have multiple angular apps in the same project. (microfrontend)
 *          - any: deprecated in v16. creates each instance anew, rather than them being singletons
 */
@Injectable({providedIn: "root"})
export class MovieService {

    /**
     * SERVICES
     *      - handle the business logic of your application
     *      - primarilly used for logic that will be reused across components
     * 
     *      - biggest example: HTTP requests
     *          - one central location for all your related requests
     */

    private readonly URL = "http://localhost:8080/api/v1/movies"


    // constructor injection - getting an instance of HttpClient from DI container
    constructor(private http: HttpClient){}

    /**
     * OBSERVABLE
     *      - from Reactive Expressions for JavaScript (RxJS)
     *      - handle async operations in Angular
     *          - similar to Promises, but they're better
     *              - Promises: fire once and get a single response
     *              - Observables: streams of events
     *  
     *      - pub/sub
     *          - Publisher -> queue -> Subscribers
     *          - Observables are the queue in this example
     *              - OBSERVABLES NEED A SUBSCIBER
     *                  - your components will SUBSCRIBE to your functions that return observables
     *                  - if there's not subscribers, then there's no data.
     */
    getMovies(rating?: number): Observable<Movie[]> {
        let params = new HttpParams();

        // set the rating param if a value was given
        if(rating != null) {
            params = params.set("rating", rating);
        }

        return this.http.get<Movie[]>(this.URL, {params})
            
            /**
             * .pipe() allows you to chain multiple Observable methods together
             *      - "pipe" the result of one function into the next in the chain
             */
            .pipe(
                // catchError - observable method that allows for graceful error handling
                catchError(
                    () => throwError(
                        () => new Error("Failed to load Movies.")
                    )
                )
            );
    }


}