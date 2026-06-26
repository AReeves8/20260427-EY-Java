import { Routes } from '@angular/router';
import { Movies } from './pages/movies/movies';
import { Directors } from './pages/directors/directors';

export const routes: Routes = [
    {path: "movies", component: Movies},
    {path: "directors", component: Directors},
    {path: "", component: Movies, pathMatch: "full"}
];
