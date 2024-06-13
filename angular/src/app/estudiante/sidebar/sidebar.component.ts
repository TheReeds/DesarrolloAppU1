import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { SidebarService } from './sidebar.service';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  sidebarOpen = false;
  userName: string | null = null;
  userRole: string | null = null;
  userProfileImage: string | null = null;
  userAdditionalInfo: string | null = null;
  isLoading = false;
  errorMessage: string | null = null;
  currentRoute = 'Inicio';

  constructor(private router: Router, private sidebarService: SidebarService, private authService: AuthService) {
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
  }

  ngOnInit() {
    this.loadSidebarState();
    this.loadUserInfo();
  }

  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
    this.saveSidebarState();
  }

  // Guarda el estado de los submenús en localStorage
  saveSidebarState() {
    const state = {
      sidebarOpen: this.sidebarOpen
    };
    this.sidebarService.setSidebarState(state);
  }

  // Carga el estado de los submenús desde localStorage
  loadSidebarState() {
    const state = this.sidebarService.getSidebarState();
    this.sidebarOpen = state.sidebarOpen || false;
  }

  isMenuOpen = true;

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  cerrarSesion() {
    localStorage.clear();
    this.router.navigate(['/login']);
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
