import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Profesor } from './profesores.model';

@Injectable({
  providedIn: 'root'
})
export class ProfesoresService {

  private apiUrl = 'http://localhost:8085/profesores';

  constructor(private http: HttpClient) { }

  obtenerProfesores(): Observable<Profesor[]> {
    return this.http.get<Profesor[]>(this.apiUrl);
  }

  crearProfesor(profesor: Profesor): Observable<any> {
    return this.http.post<any>(this.apiUrl, profesor);
  }
}
