import { Component, OnInit } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { Profesor } from './profesores.model';
import { ProfesoresService } from './profesores.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';

@Component({
  selector: 'app-profesores',
  standalone: true,
  imports: [AlumnosComponent, CommonModule, FormsModule, NotificationcComponent],
  templateUrl: './profesores.component.html',
  styleUrl: './profesores.component.css'
})
export class ProfesoresComponent implements OnInit {
  profesores: Profesor[] = [];
  nuevoProfesor: Profesor = {
    id: 0,
    nombre: '',
    dni: '',
    especialidad: '',
    telefono: '',
    usuarioId: null,
    usuarioDto: null
  };
  modalVisible: boolean = false;
  editando: boolean = false;
  profesorSeleccionado: Profesor | null = null;

  constructor(private profesoresService: ProfesoresService) { }

  ngOnInit(): void {
    this.obtenerProfesores();
  }

  obtenerProfesores(): void {
    this.profesoresService.obtenerProfesores().subscribe((data: Profesor[]) => {
      this.profesores = data;
    });
  }

  crearProfesor(): void {
    this.profesoresService.crearProfesor(this.nuevoProfesor).subscribe((profesor: Profesor) => {
      this.profesores.push(profesor);
      this.cerrarModal();
    });
  }

  actualizarProfesor(): void {
    if (this.profesorSeleccionado) {
      this.profesoresService.actualizarProfesor(this.profesorSeleccionado.id, this.nuevoProfesor).subscribe(() => {
        this.obtenerProfesores();
        this.cerrarModal();
      });
    }
  }

  eliminarProfesor(id: number): void {
    this.profesoresService.eliminarProfesor(id).subscribe(() => {
      this.obtenerProfesores();
    });
  }

  abrirModal(editando: boolean = false, profesor?: Profesor): void {
    this.editando = editando;
    if (editando && profesor) {
      this.profesorSeleccionado = profesor;
      this.nuevoProfesor = { ...profesor };
    } else {
      this.nuevoProfesor = {
        id: 0,
        nombre: '',
        dni: '',
        especialidad: '',
        telefono: '',
        usuarioId: null,
        usuarioDto: null
      };
    }
    this.modalVisible = true;
  }

  cerrarModal(): void {
    this.modalVisible = false;
    this.profesorSeleccionado = null;
  }
}
