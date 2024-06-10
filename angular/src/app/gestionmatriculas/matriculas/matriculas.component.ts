import { Component, OnInit, ViewChild } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';
import { MatriculasService } from './matriculas.service';
import { Curso, Alumno, Matricula } from './matriculas.model';

@Component({
  selector: 'app-matriculas',
  standalone: true,
  imports: [AlumnosComponent, CommonModule, FormsModule, NotificationcComponent],
  templateUrl: './matriculas.component.html',
  styleUrl: './matriculas.component.css'
})
export class MatriculasComponent implements OnInit  {
  @ViewChild(NotificationcComponent) notification: NotificationcComponent | null = null;
  matriculas: any[] = [];
  alumnos: any[] = [];
  cursos: any[] = [];
  modalVisible = false;
  newMatricula = {
    fechaMatriculacion: '',
    alumnoId: 0, // Cambiar a número
    cursos: [] as { cursoId: number }[]
  };

  constructor(private matriculaService: MatriculasService) {}

  ngOnInit() {
    this.loadMatriculas();
    this.loadAlumnos();
    this.loadCursos();
  }

  loadMatriculas() {
    this.matriculaService.getMatriculas().subscribe(data => {
      this.matriculas = data;
    });
  }

  loadAlumnos() {
    this.matriculaService.getAlumnos().subscribe(data => {
      this.alumnos = data.filter(alumno => !alumno.estado);
    });
  }


  loadCursos() {
    this.matriculaService.getCursos().subscribe(data => {
      this.cursos = data.map((curso: any) => ({
        ...curso,
        selected: false
      }));
    });
  }

  getAlumnoNombre(alumnoId: number, matricula: any): string {
    if (matricula.alumnoDto) {
      return `${matricula.alumnoDto.nombre} ${matricula.alumnoDto.apellidos}`;
    }
    const alumno = this.alumnos.find(a => a.id === alumnoId);
    return alumno ? `${alumno.nombre} ${alumno.apellidos}` : 'Desconocido';
  }


  getCursoNombre(cursoId: number): string {
    const curso = this.cursos.find(c => c.id === cursoId);
    return curso ? curso.nombre : 'Desconocido';
  }

  showModal() {
    this.modalVisible = true;
  }

  hideModal() {
    this.modalVisible = false;
    this.resetNewMatricula();
  }

  resetNewMatricula() {
    this.newMatricula = {
      fechaMatriculacion: '',
      alumnoId: 0, // Cambiar a número
      cursos: []
    };
    this.cursos.forEach(curso => curso.selected = false);
  }

  createMatricula() {
    this.newMatricula.cursos = this.cursos.filter(curso => curso.selected).map(curso => ({ cursoId: curso.id }));
    this.matriculaService.createMatricula(this.newMatricula).subscribe(() => {
      this.loadMatriculas();
      this.hideModal();
      if (this.notification) {
        this.notification.show('Matrícula creada con éxito');
      }
    });
  }

  deleteMatricula(id: number) {
    this.matriculaService.deleteMatricula(id).subscribe(() => {
      this.loadMatriculas();
      if (this.notification) {
        this.notification.show('Matrícula eliminada con éxito');
      }
    });
  }

  generatePdf(id: number) {
    this.matriculaService.generatePdf(id).subscribe(response => {
      const blob = new Blob([response], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `matricula_${id}.pdf`;
      link.click();
      if (this.notification) {
        this.notification.show('PDF generado con éxito');
      }
    });
  }

  generatePdfAll() {
    this.matriculaService.generatePdfAll().subscribe(response => {
      const blob = new Blob([response], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `todas_matriculas.pdf`;
      link.click();
      if (this.notification) {
        this.notification.show('PDF de todas las matrículas generado con éxito');
      }
    });
  }

}
