import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GradosService {
  private baseUrl = 'http://localhost:8085/grado';

  constructor(private http: HttpClient) {}

  getGrados(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }

  createGrado(grado: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}`, grado);
  }

  deleteGrado(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }

  updateGrado(id: number, grado: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, grado);
  }
}
