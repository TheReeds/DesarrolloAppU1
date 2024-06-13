import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { SidebarService } from './sidebar.service';
import { AuthService } from '../auth.service';
@Component({
  selector: 'app-alumnos',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './alumnos.component.html',
  styleUrl: './alumnos.component.css'
})
export class AlumnosComponent {
  sidebarOpen = false;
  sidebarOpen2 = false;
  sidebarOpen3 = false;
  userName: string | null = null;
  userRole: string | null = null;
  userProfileImage: string | null = null;
  userAdditionalInfo: string | null = null;
  isLoading = false; // Nuevo estado de carga
  errorMessage: string | null = null;

  currentRoute = 'Inicio';

  constructor (private router: Router, private sidebarService: SidebarService, private authService: AuthService){
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.updateCurrentRoute();
      }
    });
  }
  goBack() {
    this.router.navigate(['../']);
  }

  updateCurrentRoute() {
    const url = this.router.url;
    const urlSegments = url.split('/');
    this.currentRoute = urlSegments[urlSegments.length - 1] || 'Inicio';

    // Verificar si alguna de las rutas corresponde a las del sidebar
    this.sidebarOpen = url.includes('estudiantes') || url.includes('cursos') || url.includes('profesores'); // Ajusta según tus rutas
    this.sidebarOpen2 = url.includes('usuarios') || url.includes('grados') || url.includes('horarios');
    this.sidebarOpen3 = url.includes('aulas') || url.includes('notas') || url.includes('asistencias');
  }

  ngOnInit() {
    this.loadSidebarState();
    this.loadUserInfo();
  }

  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
    if (this.sidebarOpen) {
      this.sidebarOpen2 = false;
      this.sidebarOpen3 = false;
    }
    this.saveSidebarState();
  }

  toggleSidebar2() {
    this.sidebarOpen2 = !this.sidebarOpen2;
    if (this.sidebarOpen2) {
      this.sidebarOpen = false;
      this.sidebarOpen3 = false;
    }
    this.saveSidebarState();
  }

  toggleSidebar3() {
    this.sidebarOpen3 = !this.sidebarOpen3;
    if (this.sidebarOpen3) {
      this.sidebarOpen = false;
      this.sidebarOpen2 = false;
    }
    this.saveSidebarState();
  }

  // Guarda el estado de los submenús en localStorage
  saveSidebarState() {
    const state = {
      sidebarOpen: this.sidebarOpen,
      sidebarOpen2: this.sidebarOpen2,
      sidebarOpen3: this.sidebarOpen3
    };
    this.sidebarService.setSidebarState(state);
  }

  // Carga el estado de los submenús desde localStorage
  loadSidebarState() {
    const state = this.sidebarService.getSidebarState();
    this.sidebarOpen = state.sidebarOpen || false;
    this.sidebarOpen2 = state.sidebarOpen2 || false;
    this.sidebarOpen3 = state.sidebarOpen3 || false;
  }



  isMenuOpen = true;


  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
    // Puedes agregar más lógica aquí, como emitir un evento, cambiar clases, etc.
  }

  cerrarSesion() {
    localStorage.clear();
    this.router.navigate(['/login'])
  }
  loadUserInfo() {
    this.isLoading = true;
    this.errorMessage = null;

    this.authService.getUserInfo().subscribe({
      next: (user) => {
        console.log('User data received:', user); // Log para depuración
        this.userName = user.alumnoDto ? `${user.alumnoDto.nombre} ${user.alumnoDto.apellidos}` :
                        user.profesorDto ? `${user.profesorDto.nombre} ${user.profesorDto.especialidad}` :
                        user.name;
        this.userRole = user.role;
        this.userProfileImage = `http://localhost:8085/usuarios/uploads/${user.profileImageUrl}`;
        this.userAdditionalInfo = user.alumnoDto ? 'Alumno' : user.profesorDto ? 'Profesor' : 'Director';
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading user info:', error); // Log para depuración
        this.errorMessage = 'Error loading user info. Please try again later.';
        this.isLoading = false;
      }
    });
  }

}
