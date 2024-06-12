import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from './auth.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FormsModule, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  providers: [AuthService]
})


export class AppComponent {
  title = 'angular';
  email: string;
  password: string;

    constructor(private authService: AuthService, private router: Router) {
      this.email = '';
      this.password = '';
    }

    onSubmit() {
      console.log('Email:', this.email);
      console.log('Password:', this.password);
      this.authService.login(this.email, this.password)
        .subscribe(
          (response: any) => { // Asegúrate de ajustar el tipo de respuesta según lo que devuelve tu servicio
            // Manejar la respuesta de éxito
            const token = response.token; // Asumiendo que tu API devuelve un objeto con el token
            localStorage.setItem('accessToken', token); // Guardar el token en el almacenamiento local
            console.log('Login exitoso');

            // Redirigir al usuario a la página principal o hacer lo que necesites
            this.router.navigate(['/inicio']);
          },
          error => {
            // Manejar el error de inicio de sesión
            console.error('Error de inicio de sesión:', error);
          }
        );
    }
}
