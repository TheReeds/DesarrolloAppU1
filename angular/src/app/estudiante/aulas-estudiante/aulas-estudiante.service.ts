import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AulasEstudianteService {

  private baseUrl = 'http://localhost:8085/aulas';
  private userInfoUrl = 'http://localhost:8085/usuarios/perfil';

  constructor(private http: HttpClient) { }

  getAulasByAlumnoId(alumnoId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/alumno/${alumnoId}`);
  }

  getPerfil(): Observable<any> {
    const token = localStorage.getItem('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(this.userInfoUrl, { headers });
  }

  getAlumnoId(): Observable<number | null> {
    return this.getPerfil().pipe(
      map((perfil: any) => perfil.alumnoId)
    );
  }
}
