import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Movies } from './movies';
import { Director } from '../../types/Director';
import { Movie } from '../../types/Movie';
import { Genre } from '../../types/Genre';
import { MovieService } from '../../services/MovieService';
import { DirectorService } from '../../services/DirectorService';
import { of } from 'rxjs';




/**
 * VITEST
 *    - new angular default testing framework (replacing Jasmine + Karma)
 *    - logic based unit tests
 *    - UI based unit tests
 * 
 *    - based on Jest testing - nearly same syntax as Jest
 * 
 *    - keywords:
 *      - describe() - create a test group
 *        - can nest describes to create groups for more specific features
 *      - it() / test() - individual tests
 *      - expect - assertions for confirming test results
 *      - beforeEach() / afterEach() - lifecycle hooks that run before/after each individual test
 * 
 *      - vi - vitest object with lots of built-in functions
 */

const mockDirector: Director = {
  id: 1, 
  firstName: "Christopher",
  lastName: "Nolan"
} 

const mockMovies: Movie[] = [
  {id: 1, title: "Inception", genre: Genre.SCIENCE_FICTION, rating: 9, director: mockDirector},
  {id: 2, title: "The Odyssey", genre: Genre.ACTION, rating: 6, director: mockDirector},
  {id: 3, title: "Interstellar", genre: Genre.SCIENCE_FICTION, rating: 10, director: mockDirector}
];


/**
 * Mock Functions - simulate service functions returning with some fixed values
 *    - vi.fn() creates a blank dummy function
 */
const mockMovieService = {
  getMovies: vi.fn(),
  createNewMovie: vi.fn(),
  updateMovie: vi.fn(),
  deleteMovie: vi.fn(),
}

const mockDirectorService = {
  getAllDirectors: vi.fn()
}


describe('Movies', () => {
  let component: Movies;
  let fixture: ComponentFixture<Movies>;

  beforeEach(async () => {

    // restore each mock to their default values between each test
    vi.clearAllMocks();

    // setting the return data of getMovies before each test 
    // of() - wraps the data in an Observable
    mockMovieService.getMovies.mockReturnValue(of(mockMovies));
    mockDirectorService.getAllDirectors.mockReturnValue(of([mockDirector]));

    /**
     * configureTestingModule - compiles the component the same way the component is compiled for production
     *    - gives a place to inject mocks as needed
     */
    await TestBed.configureTestingModule({
      imports: [Movies],

      // injecting mocked service classes
      providers: [
        {provide: MovieService, useValue: mockMovieService},
        {provide: DirectorService, useValue: mockDirectorService},
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Movies);
    component = fixture.componentInstance;

    // detectChanges() - will run our ngOnInit function and force Angular to update the DOM
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should render one card per movie', () => {

    // find all HTML elements with "movie-card" as a class
    const nodes: NodeList = fixture.nativeElement.querySelectorAll(".movie-card");

    // make sure it's the same number as movies we have
    expect(nodes.length).toBe(mockMovies.length);
  });

  it('should render error message when there are no movies', () => {

    // making sure allMovies is empty
    component.allMovies.set([]);

    // telling Angular that the component has changed since we changed the signal above
    fixture.detectChanges();

    // find all HTML elements with "movie-card" as a class
    const nodes: NodeList = fixture.nativeElement.querySelectorAll(".movie-card");

    // there shouldn't be any movie cards
    expect(nodes.length).toBe(0);

    // finding the specific paragrpah tag that has the error message
    const errorMesasgeNode : Node = fixture.nativeElement.querySelector("p.text-3xl");
    
    // checks that the node exists
    expect(errorMesasgeNode).toBeTruthy();

    // checks that the inner text is what it should be
    expect(errorMesasgeNode.textContent).toBe("No Movies at this time.");


  });


});
