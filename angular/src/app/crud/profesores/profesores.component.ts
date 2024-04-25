import { Component, OnInit } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { Profesor } from './profesores.model';
import { ProfesoresService } from './profesores.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profesores',
  standalone: true,
  imports: [AlumnosComponent, CommonModule, FormsModule],
  templateUrl: './profesores.component.html',
  styleUrl: './profesores.component.css'
})
export class ProfesoresComponent implements OnInit {
  profesores: Profesor[] = [];
  nuevoProfesor: Profesor = { id: 0, nombre: '', dni: '', especialidad: '', telefono: '' };
  showForm: boolean = false;

  constructor(private profesoresService: ProfesoresService) { }

  ngOnInit(): void {
    this.obtenerProfesores();
  }

  obtenerProfesores(): void {
    this.profesoresService.obtenerProfesores()
      .subscribe(profesores => this.profesores = profesores);
  }

  crearProfesor(): void {
    this.profesoresService.crearProfesor(this.nuevoProfesor)
      .subscribe(() => {
        this.obtenerProfesores();
        this.nuevoProfesor = { id: 0, nombre: '', dni: '', especialidad: '', telefono: '' };
        this.showForm = false;
      });
  }

  cancelarCrear(): void {
    this.nuevoProfesor = { id: 0, nombre: '', dni: '', especialidad: '', telefono: '' };
    this.showForm = false;
  }

  mostrarFormulario(): void {
    this.showForm = true;
  }
}
