import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from '../../auth.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterOutlet, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})


export class LoginComponent implements OnInit {
  title = 'angular';
  email: string;
  password: string;

  constructor(private authService: AuthService, private router: Router) {
    this.email = '';
    this.password = '';
  }

  ngOnInit(): void {
    const isLoggedIn = this.authService.isAuth();
    if (isLoggedIn) {
      this.router.navigate(['/inicio']);
    }
  }

  onSubmit() {
    console.log('Email:', this.email);
    console.log('Password:', this.password);
    this.authService.login(this.email, this.password)
      .pipe(
        switchMap(() => this.authService.getUserInfo())
      )
      .subscribe(
        userInfo => {
          console.log('Login exitoso, usuario:', userInfo);
          const role = userInfo.role;
          if (role === 'USER') {
            this.router.navigate(['/estudiante/inicio']);
          } else {
            this.router.navigate(['/inicio']);
          }
        },
        error => {
          console.error('Error de inicio de sesión:', error);
        }
      );
  }
}
