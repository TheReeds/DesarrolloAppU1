import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Estudiante } from './estudiante.model';
import { Grado } from './grado.model';

@Injectable({
  providedIn: 'root'
})
export class EstudiantesServiceService {
  private apiUrl = 'http://localhost:8085/alumno';
  private gradosUrl = 'http://localhost:8085/grado';

  constructor(private http: HttpClient) { }

  obtenerEstudiantes(): Observable<Estudiante[]> {
    return this.http.get<Estudiante[]>(this.apiUrl);
  }

  registrarEstudiante(estudiante: Estudiante): Observable<Estudiante> {
    return this.http.post<Estudiante>(this.apiUrl, estudiante);
  }

  actualizarEstudiante(id: number, estudiante: Estudiante): Observable<Estudiante> {
    return this.http.put<Estudiante>(`${this.apiUrl}/${id}`, estudiante);
  }

  eliminarEstudiante(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  obtenerGrados(): Observable<Grado[]> {
    return this.http.get<Grado[]>(this.gradosUrl);
  }
}
