import { Component, signal } from '@angular/core';
import { DirectorService } from '../../services/DirectorService';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { Director } from '../../types/Director';
import { Button } from "primeng/button";

@Component({
  selector: 'app-directors',
  imports: [TableModule, Button],
  templateUrl: './directors.html',
  styleUrl: './directors.css',
})
export class Directors {

  directors = signal<Director[]>([]);
  totalRecords = signal<number>(0);
  loading = signal<boolean>(false);


  constructor(
    private directorService: DirectorService
  ){}


  ngOnInit() {
    this.loadDirectors();
  }

  loadDirectors(event? : TableLazyLoadEvent) {
    
    const page = event ? event?.first! / event?.rows! : 0;
    const size = event ? event?.rows! : 2;

    // show loading spinner while request to backend is being made
    this.loading.set(true);

    this.directorService.getDirectorsPage(page, size).subscribe({
      next: (data) => {
        this.directors.set(data.content);
        this.totalRecords.set(data.totalElements);
        this.loading.set(false);
      },
      error: (err) => {
        console.error(err);
      }
    })

  }

}
