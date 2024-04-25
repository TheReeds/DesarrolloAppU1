import { Component, OnInit } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { Estudiante } from './estudiante.model';
import { EstudiantesServiceService } from './estudiantes-service.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-estudiantes',
  standalone: true,
  imports: [AlumnosComponent, CommonModule],
  templateUrl: './estudiantes.component.html',
  styleUrl: './estudiantes.component.css'
})
export class EstudiantesComponent implements OnInit {
  estudiantes: Estudiante[] = [];

  constructor(private servicioEstudiantes: EstudiantesServiceService) {
    this.estudiantes = [];
  }

  ngOnInit(): void {
    this.obtenerEstudiantes();
  }

  obtenerEstudiantes(): void {
    this.servicioEstudiantes.obtenerEstudiantes()
      .subscribe(estudiantes => this.estudiantes = estudiantes);
  }

}
