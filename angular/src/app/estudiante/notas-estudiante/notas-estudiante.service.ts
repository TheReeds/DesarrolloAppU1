import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotasEstudianteService {
  constructor(private http: HttpClient) {}

  getNotas(alumnoId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8085/notas/alumno/${alumnoId}`);
  }
}
