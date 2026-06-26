import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { Movie } from './types/Movie';
import { Genre } from './types/Genre';
import { Movies } from "./pages/movies/movies";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ButtonModule, MenubarModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('Movies App');

  constructor(
    private router: Router
  ) {}

  // menu items used in the Menubar component
  navItems: MenuItem[] = [
    {label: "Movies", command: () => this.router.navigate(["/movies"])},
    {label: "Directors", command: () => this.router.navigate(["/directors"])},
  ]
  

}
