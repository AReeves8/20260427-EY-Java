import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';

// Component Decorator manages the content of this Angular Component
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('angular-intro');


  // TS is good at inferencing your data types
  message = "Hello, Austin!";
  isHighlighted = false;
  count = 0;
  searchText = "donuts";

  allMovies = [
    {
      id: 1, 
      title: "The Hunger Games",
      genre: "Sci-Fi",
      rating: 7, 
      director: {
        id: 1, 
        firstName: "Susanne", 
        lastName: "Collins"
      } 
    },
    {
      id: 2, 
      title: "Catching Fire",
      genre: "Sci-Fi",
      rating: 8, 
      director: {
        id: 1, 
        firstName: "Susanne", 
        lastName: "Collins"
      } 
    },
    {
      id: 3, 
      title: "Mockingjay",
      genre: "Sci-Fi",
      rating: 8, 
      director: {
        id: 1, 
        firstName: "Susanne", 
        lastName: "Collins"
      } 
    }
  ];


  increment() {
    this.count++;

    if(this.count > 5) {
      this.isHighlighted = true;
      this.message = "Lots of clicks!"
    }

  }

}
