import { Component, OnInit } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { Curso } from './cursos.model';
import { CommonModule } from '@angular/common';
import { CursosService } from './cursos.service';
@Component({
  selector: 'app-cursos',
  standalone: true,
  imports: [AlumnosComponent, CommonModule],
  templateUrl: './cursos.component.html',
  styleUrl: './cursos.component.css'
})
export class CursosComponent implements OnInit  {
  cursos: Curso[] = [];

  constructor(private cursosService: CursosService) {
    this.cursos = [];
  }

  ngOnInit(): void {
    this.obtenerCursos();
  }

  obtenerCursos(): void {
    this.cursosService.obtenerCursos()
      .subscribe(cursos => this.cursos = cursos);
  }
}
