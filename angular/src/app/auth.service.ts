import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8085/auth/authenticate';
  private userInfoUrl = 'http://localhost:8085/usuarios/info';

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<{ token: string }> {
    const body = { email, password };
    return this.http.post<{ token: string }>(this.apiUrl, body).pipe(
      tap(response => {
        const token = response.token;
        if (token) {
          localStorage.setItem('accessToken', token);
        }
      })
    );
  }

  getUserInfo(): Observable<any> {
    const token = localStorage.getItem('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(this.userInfoUrl, { headers });
  }

  isAuth(): boolean {
    return localStorage.getItem('accessToken') !== null;
  }

  logout(): void {
    localStorage.removeItem('accessToken');
  }
}
