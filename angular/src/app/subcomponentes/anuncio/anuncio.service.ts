import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnuncioService {
  private apiUrl = 'http://localhost:8085/anuncios';

  constructor(private http: HttpClient) { }

  getAnuncios(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createAnuncio(formData: FormData): Observable<any> {
    return this.http.post<any>(this.apiUrl, formData);
  }

  deleteAnuncio(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  editAnuncio(id: number, formData: FormData): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, formData);
  }
}
