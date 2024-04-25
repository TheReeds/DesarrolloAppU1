import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Estudiante } from './estudiante.model';

@Injectable({
  providedIn: 'root'
})
export class EstudiantesServiceService {
  private apiUrl = 'http://localhost:8085/alumno';

  constructor(private http: HttpClient) { }
  obtenerEstudiantes(): Observable<Estudiante[]> {
    return this.http.get<Estudiante[]>(this.apiUrl);
  }
}
