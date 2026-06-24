import { Component, signal } from '@angular/core';
import { Movie } from '../../types/Movie';
import { MovieService } from '../../services/MovieService';

@Component({
  selector: 'app-movies',
  imports: [],
  templateUrl: './movies.html',
  styleUrl: './movies.css',
})
export class Movies {

  /**
   * signal - introduced in v21
   *    - control state in your TS as DOM changes
   *        - better faciliate DOM updates as state changes
   */
  allMovies = signal<Movie[]>([]);

  constructor(
    private movieService: MovieService
  ){}


  /**
   * ngOnInit() - runs as soon as the component is mounted to the dom
   */
  ngOnInit(): void {
    this.loadMovies();
  }


  loadMovies(): void {

    // subscribing to the Observable
    this.movieService.getMovies().subscribe({

      // next specifices what to do when the observable stream has data inside of it
      next: (data) => {

        // setting all of the movies - signal setter is what notifies Angular when data changes
        this.allMovies.set(data);
      },
      error: (err) => {
        console.error(err);
      }
    });

  }

}
