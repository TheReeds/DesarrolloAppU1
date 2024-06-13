import { Component, ViewChild } from '@angular/core';
import { OpcionesService } from './opciones.service';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AlumnosComponent } from '../../alumnos/alumnos.component';

@Component({
  selector: 'app-opciones',
  standalone: true,
  imports: [CommonModule, FormsModule, NotificationcComponent, AlumnosComponent],
  templateUrl: './opciones.component.html',
  styleUrl: './opciones.component.css'
})
export class OpcionesComponent {
  @ViewChild(NotificationcComponent) notification: NotificationcComponent | null = null;
  perfil: any = {
    name: '',
    email: '',
    profileImageUrl: '',
    alumnoDto: null,
    profesorDto: null
  };
  image: File | undefined = undefined;

  constructor(private opcionesService: OpcionesService) { }

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
