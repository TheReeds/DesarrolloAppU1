import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AsistenciasService {
  private baseUrl = 'http://localhost:8085/asistencias';
  private aulaUrl = 'http://localhost:8085/aulas';

  constructor(private http: HttpClient) {}

  registrarAsistencia(asistencia: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, asistencia);
  }

  registrarVariasAsistencias(asistencias: any[]): Observable<any> {
    return this.http.post(`${this.baseUrl}/bulk`, asistencias);
  }
  getAula(id: number): Observable<any> {
    return this.http.get<any>(`${this.aulaUrl}/${id}`);
  }
  getAulas(): Observable<any[]> {
    return this.http.get<any[]>(this.aulaUrl);
  }
}
