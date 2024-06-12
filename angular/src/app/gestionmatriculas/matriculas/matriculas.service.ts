import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MatriculasService {
  private baseUrl = 'http://localhost:8085/matriculas';
  private cursosUrl = 'http://localhost:8085/cursos';
  private alumnosUrl = 'http://localhost:8085/alumno';
  private baseUrl2 = "http://localhost:8085";

  constructor(private http: HttpClient) {}


  getMatriculas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }

  createMatricula(matricula: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}`, matricula);
  }

  deleteMatricula(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }

  generatePdf(id: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/constancia/${id}`, { responseType: 'blob' });
  }

  generatePdfAll(): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/pdf`, { responseType: 'blob' });
  }

  getCursos(): Observable<any[]> {
    return this.http.get<any[]>(this.cursosUrl);
  }

  getAlumnos(): Observable<any[]> {
    return this.http.get<any[]>(this.alumnosUrl);
  }

  getGrados(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl2}/grado`);
  }

  getProfesores(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl2}/profesores`);
  }
  getAlumnosPorCurso(cursoId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/curso/${cursoId}/alumnos`);
  }
}
