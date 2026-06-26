import { Pipe, PipeTransform } from "@angular/core";
import { Director } from "../app/types/Director";



/**
 * PIPES
 *      - transforming data in some consistent way in your HTML
 *      - {{ someValue | pipeFunction }}
 *              - transforms the value however you need
 */
@Pipe({
    name: "directorName",

    // standalone pipes don't need to be apart of a Module, they can be imported directly into a component
    standalone: true,       
    
    // pure pipes only re-run when inputs change - better performance
    pure: true
})
export class DirectorNamePipe implements PipeTransform {


    transform(director: Director, format: "first-last" | "last-first" = 'first-last'): string {
        
        const first = director.firstName.trim();
        const last = director.lastName.trim();

        if(format === "first-last") {
            return first + " " + last;
        }
        else {
            return last + ", " + first;
        }

    }

}