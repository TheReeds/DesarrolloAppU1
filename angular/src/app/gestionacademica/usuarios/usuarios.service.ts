import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {
  private baseUrl = 'http://localhost:8085/usuarios';
  private baseUrl2 = 'http://localhost:8085';

  constructor(private http: HttpClient) {}

  getUsuarios(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  createUsuario(usuario: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/auth/register`, usuario);
  }

  deleteUsuario(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }

  updateUsuario(id: number, usuario: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}/role`, { role: usuario.role });
  }
  getAlumnosSinUsuario(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl2}/alumno/sin-usuario`);
  }

  getProfesoresSinUsuario(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl2}/profesores/sin-usuario`);
  }

  asociarAlumno(usuarioId: number, alumnoId: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${usuarioId}/asociar?alumnoId=${alumnoId}`, {});
  }

  asociarProfesor(usuarioId: number, profesorId: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${usuarioId}/asociar?profesorId=${profesorId}`, {});
  }
}
