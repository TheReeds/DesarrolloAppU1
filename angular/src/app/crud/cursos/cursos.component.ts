import { Component, OnInit } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { Curso } from './cursos.model';
import { Profesor } from './profesor.model';
import { CommonModule } from '@angular/common';
import { CursosService } from './cursos.service';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-cursos',
  standalone: true,
  imports: [AlumnosComponent, CommonModule, NotificationcComponent, FormsModule],
  templateUrl: './cursos.component.html',
  styleUrl: './cursos.component.css'
})
export class CursosComponent implements OnInit  {
  cursos: Curso[] = [];
  profesores: Profesor[] = [];
  nuevoCurso: Curso = {
    id: 0,
    nombre: '',
    descripcion: '',
    duracion: '',
    numeroDePeriodos: 1,
    profesorId: 0
  };
  modalVisible: boolean = false;
  editando: boolean = false;
  cursoSeleccionado: Curso | null = null;

  constructor(private cursosService: CursosService) { }

  ngOnInit(): void {
    this.obtenerCursos();
    this.obtenerProfesores();
  }

  obtenerCursos(): void {
    this.cursosService.obtenerCursos()
      .subscribe(cursos => this.cursos = cursos);
  }

  obtenerProfesores(): void {
    this.cursosService.obtenerProfesores()
      .subscribe(profesores => this.profesores = profesores);
  }

  registrarCurso(): void {
    this.cursosService.registrarCurso(this.nuevoCurso)
      .subscribe(curso => {
        this.cursos.push(curso);
        this.cerrarModal();
      });
  }

  actualizarCurso(): void {
    if (this.cursoSeleccionado) {
      this.cursosService.actualizarCurso(this.cursoSeleccionado.id, this.nuevoCurso)
        .subscribe(() => {
          this.obtenerCursos();
          this.cerrarModal();
        });
    }
  }

  eliminarCurso(id: number): void {
    this.cursosService.eliminarCurso(id)
      .subscribe(() => this.obtenerCursos());
  }

  abrirModal(editando: boolean = false, curso?: Curso): void {
    this.editando = editando;
    if (editando && curso) {
      this.cursoSeleccionado = curso;
      this.nuevoCurso = { ...curso };
    } else {
      this.nuevoCurso = {
        id: 0,
        nombre: '',
        descripcion: '',
        duracion: '',
        numeroDePeriodos: 1,
        profesorId: 0
      };
    }
    this.modalVisible = true;
  }

  cerrarModal(): void {
    this.modalVisible = false;
    this.cursoSeleccionado = null;
  }
}
