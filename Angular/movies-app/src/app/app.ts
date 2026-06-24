import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { Movie } from './types/Movie';
import { Genre } from './types/Genre';
import { Movies } from "./pages/movies/movies";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ButtonModule, Movies],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('movies-app');


  newMovie: Movie = {
    title: "Example",
    rating: 10,
    genre: Genre.SCIENCE_FICTION,
    director: {
      id: 12,
      firstName: "Austin",
      lastName: "Reeves"
    }
  }

}
