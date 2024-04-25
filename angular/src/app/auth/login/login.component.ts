import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from '../../auth.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

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
      // Verifica si hay datos en el almacenamiento local al cargar el componente
      const isLoggedIn = localStorage.length>0; // Suponiendo que 'usuario' es la clave utilizada para almacenar los datos del usuario

      if (isLoggedIn) {
        // Si hay datos en el almacenamiento local, redirige al usuario al componente de menú
        this.router.navigate(['/casa']);
      }
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
