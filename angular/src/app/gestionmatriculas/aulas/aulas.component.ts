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
  styleUrl: './aulas.component.css'
})
export class AulasComponent {
  @ViewChild(NotificationcComponent) notification: NotificationcComponent | null = null;
  aulas: any[] = [];
  alumnos: any[] = [];
  cursos: any[] = [];
  modalVisible = false;
  viewModalVisible = false;
  selectedAula: any = null;
  newAula = {
    nombre: '',
    cursoId: null,
    alumnoIds: [] as number[]
  };

  constructor(private aulasService: AulasService, private matriculaService: MatriculasService) {}

  ngOnInit() {
    this.loadAulas();
    this.loadAlumnos();
    this.loadCursos();
  }

  loadAulas() {
    this.aulasService.getAulas().subscribe(data => {
      this.aulas = data;
    });
  }

  loadAlumnos() {
    this.matriculaService.getAlumnos().subscribe(data => {
      this.alumnos = data.filter(alumno => alumno.estado);
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
      cursoId: null,
      alumnoIds: []
    };
  }

  createAula() {
    this.newAula.alumnoIds = this.alumnos.filter(alumno => alumno.selected).map(alumno => alumno.id);
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
