import { Component, ViewChild } from '@angular/core';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatriculasService } from '../matriculas/matriculas.service';
import { AsistenciasService } from './asistencias.service';
import { AlumnosComponent } from '../../alumnos/alumnos.component';

@Component({
  selector: 'app-asistencias',
  standalone: true,
  imports: [ NotificationcComponent, CommonModule, FormsModule, AlumnosComponent],
  templateUrl: './asistencias.component.html',
  styleUrl: './asistencias.component.css'
})
export class AsistenciasComponent {
  @ViewChild(NotificationcComponent) notification: NotificationcComponent | null = null;
  aulas: any[] = [];
  alumnos: any[] = [];
  asistencias: { [key: number]: { presente: boolean } } = {};
  modalVisible = false;
  selectedAula: any = null;

  constructor(private asistenciaService: AsistenciasService, private matriculaService: MatriculasService) {}

  ngOnInit() {
    this.loadAulas();
  }

  loadAulas() {
    this.asistenciaService.getAulas().subscribe(data => {
      this.aulas = data;
    });
  }

  showModal() {
    this.modalVisible = true;
  }

  hideModal() {
    this.modalVisible = false;
  }

  selectAula(aulaId: number): void {
    this.asistenciaService.getAula(aulaId).subscribe(data => {
      this.selectedAula = data;
      this.alumnos = this.selectedAula.alumnos;
      this.initializeAsistencias();
      this.showModal();
    },
    error => {
      console.error('Error fetching aula details', error);
    });
  }

  initializeAsistencias(): void {
    this.asistencias = {};
    this.alumnos.forEach(alumno => {
      this.asistencias[alumno.id] = { presente: false };
    });
  }

  saveAsistencias() {
    const asistenciaData = this.alumnos.map(alumno => ({
      alumnoId: alumno.id,
      cursoId: this.selectedAula.curso.id,
      fecha: new Date().toISOString(), // Asegúrate de que el formato de la fecha sea correcto
      presente: this.asistencias[alumno.id].presente
    }));

    this.asistenciaService.registrarVariasAsistencias(asistenciaData).subscribe(
      () => {
        this.notification?.show('Asistencias registradas correctamente');
        this.hideModal();
      },
      error => {
        console.error('Error registrando asistencias', error);
        this.notification?.show('Error al registrar asistencias');
      }
    );
  }

}
