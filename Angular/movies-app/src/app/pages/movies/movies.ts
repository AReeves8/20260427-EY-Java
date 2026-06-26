import { Component, signal } from '@angular/core';
import { Movie } from '../../types/Movie';
import { MovieService } from '../../services/MovieService';
import { DataViewModule } from 'primeng/dataview';
import { SelectButtonModule } from 'primeng/selectbutton';
import { TagModule } from 'primeng/tag';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from "primeng/button";
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { InputNumberModule } from 'primeng/inputnumber';
import { IftaLabelModule } from 'primeng/iftalabel';
import { Genre } from '../../types/Genre';
import { Director } from '../../types/Director';
import { DirectorService } from '../../services/DirectorService';
import { DeleteConfirmationModal } from "../../components/delete-confirmation-modal/delete-confirmation-modal";
import { DirectorNamePipe } from '../../../pipes/director-name-pipe';
import { RatingStarsPipe } from '../../../pipes/rating-stars-pipe';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-movies',
  imports: [DataViewModule, SelectButtonModule, TagModule, FormsModule, ButtonModule,
    DialogModule, InputTextModule, SelectModule, ReactiveFormsModule, InputNumberModule,
    IftaLabelModule, DeleteConfirmationModal, DirectorNamePipe, RatingStarsPipe, CardModule],
  templateUrl: './movies.html',
  styleUrl: './movies.css',
})
export class Movies {

  /**
   * signal - introduced in v21
   *    - control state in your TS as DOM changes
   *        - better faciliate DOM updates as state changes
   */
  allMovies = signal<Movie[]>([]);              // all movies from server
  allDirectors = signal<Director[]>([]);        // all directors from server
  selectedMovie = signal<Movie | null>(null);   // the movie to be updated/deleted by the user
  showDialog = signal<boolean>(false);          // toggle for showing and closing form dialog
  showDeleteDialog = signal<boolean>(false);    // togle for showing and closing delete dialog
  dialogTitle = signal<string>("");             // title used in form dialog


  genreOptions = Object.values(Genre);          // creating a list of the values from Genre enum
  form! : FormGroup;                            // FormGroup captures data from an HTML form

  constructor(
    private formBuilder: FormBuilder,
    private movieService: MovieService,
    private directorService: DirectorService
  ){}

  /**
   * ngOnInit() - runs as soon as the component is mounted to the dom
   */
  ngOnInit(): void {
    this.loadMovies();
    this.loadDirectors();

    // using form builder to create the form group 
    // form builder lets you initialize form with default values and validators
    this.form = this.formBuilder.group({
      title: ["", [Validators.required, Validators.minLength(2)]],
      genre: [null, [Validators.required]],
      rating: [0, [Validators.required, Validators.max(10), Validators.min(1)]],
      director: [null, [Validators.required]]
    });

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

        // better to display Toast message
        console.error(err);
      }
    });
  }

  loadDirectors(): void {
    this.directorService.getAllDirectors().subscribe({
      next: (data) => {
        this.allDirectors.set(data);
      },
      error: (err) => {
        // better to display Toast message
        console.error(err);
      }
    })
  }

  saveMovie(){

    // early exit if form is invalid
    if(this.form.invalid) {
      return;
    }

    // using destructuring to grab form values
    const {title, genre, rating, director} = this.form.value;

    // turn enum value back into the key
    const genreKey = Object.entries(Genre).find(([, val]) => val === genre)?.[0];

    const payload: Movie = {
      title, 
      genre: genreKey as Genre, 
      rating,
      director
    }

    if(this.selectedMovie() === null) {
      this.movieService.createMovie(payload).subscribe({
        next: (data) => {
          // update function gives you access to the current state of the signal
          // instead of pushing onto the list, we copy everything over and add in the new data
          this.allMovies.update((currentList) => [...currentList, data])

          // closing the form dialog
          this.showDialog.set(false);
        },
        error: (err) => {
          // better to display Toast message
          console.error(err);

          // closing the form dialog
          this.showDialog.set(false);
        }
      })
    }
    else {
      payload.id = this.selectedMovie()!.id;
      this.movieService.updateMovie(payload!.id!, payload).subscribe({
        next: (data) => {
          this.allMovies.update((currentList) => currentList.map(movie => movie.id === data.id ? data : movie))
          
          // closing the form dialog
          this.showDialog.set(false);
        },
        error: (err) => {
          // better to display Toast message
          console.error(err);

          // closing the form dialog
          this.showDialog.set(false);
        }
      })
    }
  }

  handleCreateMovie() {
    
    this.dialogTitle.set("Create Movie");

    // make sure form is empty before showing dialog
    this.form.setValue({
      title: "",
      genre: null,
      rating: 0,
      director: null
    })

    // open form dialog
    this.showDialog.set(true);
  }

  handleUpdateMovie(movie: Movie) {

    this.dialogTitle.set("Update Movie");
    this.selectedMovie.set(movie);

    // prefill form with existing values
    this.form.setValue({
      title: movie.title,
      genre: Genre[movie.genre as string as keyof typeof Genre],
      rating: movie.rating,
      director: movie.director
    })

    // open form dialog
    this.showDialog.set(true);
  }

  handleDeleteMovie(movie: Movie) {

    console.log(movie);

    this.selectedMovie.set(movie);
    this.showDeleteDialog.set(true);
  }

  deleteMovie() {

    const movieId = this.selectedMovie()!.id!;

    this.movieService.deleteMovie(movieId).subscribe({
      next: (data) => {
          this.allMovies.update((currentList) => currentList.filter(movie => movie.id !== movieId))
          
          // closing the form dialog
          this.showDeleteDialog.set(false);
        },
        error: (err) => {
          // better to display Toast message
          console.error(err);

          // closing the form dialog
          this.showDeleteDialog.set(false);
        }
    })
  }

}
