import { Pipe, PipeTransform } from "@angular/core";


@Pipe({
    name: "ratingStars",
    standalone: true,
    pure: true
})
export class RatingStarsPipe implements PipeTransform {

    transform(value: number, outOf: number = 5) {
        
        // ratings are out of 10 so make them out of 5 instead
        const compressedValue = value / 2; 

        // round compressed value to nearest integer
        const rounded = Math.round(compressedValue);

        // create string of filled in and unfilled stars
        const filled = "★".repeat(rounded);
        const unfilled = "☆".repeat(outOf - rounded);

        return filled + unfilled;
    }
}