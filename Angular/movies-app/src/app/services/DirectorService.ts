import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../../environments/environments";
import { catchError, Observable, throwError } from "rxjs";
import { Director } from "../types/Director";
import { DirectorPage } from "../types/DirectorPage";


@Injectable({providedIn: "root"})
export class DirectorService {

    private readonly URL = `${environment.baseApiUrl}/directors`;

    constructor(private http: HttpClient){}

    // GET request for all directors
    getAllDirectors(): Observable<Director[]> {
        return this.http.get<Director[]>(this.URL)
            .pipe(
                catchError(() => throwError(() => new Error("Failed to load directors")))
            )
    }

    getDirectorsPage(page: number, size: number, sortBy?: string): Observable<DirectorPage> {
        const params = new HttpParams()
            .set("page", page)
            .set("size", size)
            .set("sortBy", sortBy ?? "");
        
        return this.http.get<DirectorPage>(this.URL + "/page", {params})
            .pipe(
                catchError(() => throwError(() => new Error("Failed to load directors")))
            )
    }

}