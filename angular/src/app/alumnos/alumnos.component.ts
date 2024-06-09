import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { SidebarService } from './sidebar.service';
@Component({
  selector: 'app-alumnos',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './alumnos.component.html',
  styleUrl: './alumnos.component.css'
})
export class AlumnosComponent {

  constructor (private router: Router, private sidebarService: SidebarService){}

  sidebarOpen = false;
  sidebarOpen2 = false;
  sidebarOpen3 = false;
  userName: string | null = null;
  userRole: string | null = null;

  ngOnInit() {
    this.loadSidebarState();
  }

  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
    this.saveSidebarState();
  }

  toggleSidebar2() {
    this.sidebarOpen2 = !this.sidebarOpen2;
    this.saveSidebarState();
  }

  toggleSidebar3() {
    this.sidebarOpen3 = !this.sidebarOpen3;
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
}
