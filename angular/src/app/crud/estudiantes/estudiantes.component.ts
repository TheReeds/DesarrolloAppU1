import { Component, OnInit } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { Estudiante } from './estudiante.model';
import { EstudiantesServiceService } from './estudiantes-service.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Grado } from './grado.model';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';
@Component({
  selector: 'app-estudiantes',
  standalone: true,
  imports: [AlumnosComponent, CommonModule, NotificationcComponent, FormsModule],
  templateUrl: './estudiantes.component.html',
  styleUrl: './estudiantes.component.css'
})
export class EstudiantesComponent implements OnInit {
  estudiantes: Estudiante[] = [];
  grados: Grado[] = [];
  nuevoEstudiante: Estudiante = {
    id: 0,
    nombre: '',
    apellidos: '',
    telefono: '',
    dni: '',
    estado: true,
    grado: {
      id: 0
    },
    usuarioId: null,
    usuarioDto: null
  };
  modalVisible: boolean = false;
  editando: boolean = false;
  estudianteSeleccionado: Estudiante | null = null;

  constructor(private servicioEstudiantes: EstudiantesServiceService) { }

  ngOnInit(): void {
    this.obtenerEstudiantes();
    this.obtenerGrados();
  }

  obtenerEstudiantes(): void {
    this.servicioEstudiantes.obtenerEstudiantes()
      .subscribe(estudiantes => this.estudiantes = estudiantes);
  }

  obtenerGrados(): void {
    this.servicioEstudiantes.obtenerGrados()
      .subscribe(grados => this.grados = grados);
  }

  registrarEstudiante(): void {
    this.servicioEstudiantes.registrarEstudiante(this.nuevoEstudiante)
      .subscribe(estudiante => {
        this.estudiantes.push(estudiante);
        this.cerrarModal();
      });
  }

  actualizarEstudiante(): void {
    if (this.estudianteSeleccionado) {
      this.servicioEstudiantes.actualizarEstudiante(this.estudianteSeleccionado.id, this.nuevoEstudiante)
        .subscribe(() => {
          this.obtenerEstudiantes();
          this.cerrarModal();
        });
    }
  }

  eliminarEstudiante(id: number): void {
    this.servicioEstudiantes.eliminarEstudiante(id)
      .subscribe(() => this.obtenerEstudiantes());
  }

  abrirModal(editando: boolean = false, estudiante?: Estudiante): void {
    this.editando = editando;
    if (editando && estudiante) {
      this.estudianteSeleccionado = estudiante;
      this.nuevoEstudiante = { ...estudiante };
    } else {
      this.nuevoEstudiante = {
        id: 0,
        nombre: '',
        apellidos: '',
        telefono: '',
        dni: '',
        estado: false,
        grado: {
          id: 0
        },
        usuarioId: null,
        usuarioDto: null
      };
    }
    this.modalVisible = true;
  }

  cerrarModal(): void {
    this.modalVisible = false;
    this.estudianteSeleccionado = null;
  }

}
