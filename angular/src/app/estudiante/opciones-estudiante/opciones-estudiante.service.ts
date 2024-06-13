import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OpcionesEstudianteService {
  private perfilUrl = 'http://localhost:8085/usuarios/perfil';
  private actualizarPerfilUrl = 'http://localhost:8085/usuarios/perfil';

  constructor(private http: HttpClient) { }

  obtenerPerfil(): Observable<any> {
    const token = localStorage.getItem('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.perfilUrl, { headers });
  }

  actualizarPerfil(name: string, email: string, image: File | undefined): Observable<any> {
    const token = localStorage.getItem('accessToken');
    const headers = new HttpHeaders()
      .set('Authorization', `Bearer ${token}`);

    const formData = new FormData();
    formData.append('name', name);
    formData.append('email', email);
    if (image) {
      formData.append('image', image);
    }

    return this.http.put<any>(this.actualizarPerfilUrl, formData, { headers });
  }
}
