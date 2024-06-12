import { Component, OnInit, ViewChild } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';
import { AulasService } from './aulas.service';
import { MatriculasService } from '../matriculas/matriculas.service';

@Component({
  selector: 'app-aulas',
  standalone: true,
  imports: [AlumnosComponent, CommonModule, FormsModule, NotificationcComponent],
  templateUrl: './aulas.component.html',
  styleUrls: ['./aulas.component.css']
})
export class AulasComponent implements OnInit {
  @ViewChild(NotificationcComponent) notification: NotificationcComponent | null = null;
  aulas: any[] = [];
  alumnos: any[] = [];
  cursos: any[] = [];
  grados: any[] = [];
  profesores: any[] = [];
  modalVisible = false;
  viewModalVisible = false;
  selectedAula: any = null;
  newAula = {
    nombre: '',
    grado: { id: null },
    curso: { id: null },
    profesor: { id: null },
    alumnos: [] as { id: number }[]
  };

  constructor(private aulasService: AulasService, private matriculaService: MatriculasService) {}

  ngOnInit() {
    this.loadAulas();
    this.loadCursos();
    this.loadGrados();
    this.loadProfesores();
  }

  loadAulas() {
    this.aulasService.getAulas().subscribe(data => {
      this.aulas = data;
    });
  }

  loadAlumnosPorCurso(cursoId: number | null) {
    if (cursoId !== null) {
      this.matriculaService.getAlumnosPorCurso(cursoId).subscribe(data => {
        this.alumnos = data.map(alumno => ({ ...alumno, selected: false }));
      });
    } else {
      this.alumnos = [];
    }
  }

  loadCursos() {
    this.matriculaService.getCursos().subscribe(data => {
      this.cursos = data;
    });
  }

  loadGrados() {
    this.matriculaService.getGrados().subscribe(data => {
      this.grados = data;
    });
  }

  loadProfesores() {
    this.matriculaService.getProfesores().subscribe(data => {
      this.profesores = data;
    });
  }

  showModal() {
    this.modalVisible = true;
  }

  hideModal() {
    this.modalVisible = false;
    this.resetNewAula();
  }

  hideViewModal() {
    this.viewModalVisible = false;
    this.selectedAula = null;
  }

  resetNewAula() {
    this.newAula = {
      nombre: '',
      grado: { id: null },
      curso: { id: null },
      profesor: { id: null },
      alumnos: []
    };
  }

  createAula() {
    this.newAula.alumnos = this.alumnos.filter(alumno => alumno.selected).map(alumno => ({ id: alumno.id }));
    this.aulasService.createAula(this.newAula).subscribe(() => {
      this.loadAulas();
      this.hideModal();
      if (this.notification) {
        this.notification.show('Aula creada con éxito');
      }
    });
  }

  deleteAula(id: number) {
    this.aulasService.deleteAula(id).subscribe(() => {
      this.loadAulas();
      if (this.notification) {
        this.notification.show('Aula eliminada con éxito');
      }
    });
  }

  showAlumnos(id: number) {
    this.aulasService.getAula(id).subscribe(data => {
      this.selectedAula = data;
      this.viewModalVisible = true;
    });
  }
}
