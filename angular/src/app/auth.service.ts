import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8085/auth/authenticate';
  private userInfoUrl = 'http://localhost:8085/usuarios/info'; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post(`${this.apiUrl}`, body);
  }

  getUserInfo(): Observable<any> {
    const token = localStorage.getItem('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(this.userInfoUrl, { headers });
  }

  isAuth(): boolean {
    return localStorage.getItem('accessToken') !== null;
  }
}
