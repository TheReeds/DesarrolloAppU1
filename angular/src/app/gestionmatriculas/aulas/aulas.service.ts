import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AulasService {
  private baseUrl = 'http://localhost:8085/aulas';

  constructor(private http: HttpClient) {}

  getAulas(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  getAula(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  createAula(aula: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, aula);
  }

  deleteAula(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${id}`);
  }
}
