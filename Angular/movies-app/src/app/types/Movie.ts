import { Director } from "./Director";
import { Genre } from "./Genre";

/**
 * interface would be better to use in this scenario
 *      - just wanted to show syntax for using "type"
 */
export type Movie = {
    id?: number;
    title: string;
    rating: number;
    genre: Genre;
    director: Director
}