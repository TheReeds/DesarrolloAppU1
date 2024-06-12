import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotasService {

  private baseUrl = 'http://localhost:8085';

  constructor(private http: HttpClient) { }

  getAulas(): Observable<any> {
    return this.http.get(`${this.baseUrl}/aulas`);
  }

  getAulaById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/aulas/${id}`);
  }

  getCursoById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/cursos/${id}`);
  }
  getNotasByCurso(cursoId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/notas/curso/${cursoId}`);
  }

  updateNota(alumnoId: number, periodo: number, valor: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/notas`, { alumnoId, periodo, valor });
  }

  bulkUpdateNotas(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/notas/bulk-update`, data);
  }
  getDatosPorAula(aulaId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/notas/aula/${aulaId}`);
  }
  exportNotasToPdf(aulaId: number): Observable<Blob> {
    const headers = new HttpHeaders({ 'Accept': 'application/pdf' });
    return this.http.get(`${this.baseUrl}/notas/aula/${aulaId}/pdf`, { headers, responseType: 'blob' });
  }
}
