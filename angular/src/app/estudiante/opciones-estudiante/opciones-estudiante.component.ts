import { Component, ViewChild } from '@angular/core';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';
import { OpcionesEstudianteService } from './opciones-estudiante.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SidebarComponent } from '../sidebar/sidebar.component';

@Component({
  selector: 'app-opciones-estudiante',
  standalone: true,
  imports: [NotificationcComponent, CommonModule, FormsModule, SidebarComponent],
  templateUrl: './opciones-estudiante.component.html',
  styleUrl: './opciones-estudiante.component.css'
})
export class OpcionesEstudianteComponent {
  @ViewChild(NotificationcComponent) notification: NotificationcComponent | null = null;
  perfil: any = {
    name: '',
    email: '',
    profileImageUrl: '',
    alumnoDto: null,
    profesorDto: null
  };
  image: File | undefined = undefined;

  constructor(private opcionesService: OpcionesEstudianteService) { }

  ngOnInit(): void {
    this.obtenerPerfil();
  }

  obtenerPerfil(): void {
    this.opcionesService.obtenerPerfil().subscribe((data: any) => {
      this.perfil = data;
    });
  }

  onFileSelected(event: any): void {
    this.image = event.target.files[0];
  }

  actualizarPerfil(): void {
    this.opcionesService.actualizarPerfil(this.perfil.name, this.perfil.email, this.image).subscribe(() => {
      this.obtenerPerfil();
      if (this.notification) {
        this.notification.show('Perfil actualizado con éxito');
      }
    });
  }
}
