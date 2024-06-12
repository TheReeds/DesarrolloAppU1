import { Component, ViewChild } from '@angular/core';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { CommonModule } from '@angular/common';
import { NotasService } from './notas.service';
import { MatriculasService } from '../matriculas/matriculas.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-notas',
  standalone: true,
  imports: [NotificationcComponent, AlumnosComponent, CommonModule, FormsModule],
  templateUrl: './notas.component.html',
  styleUrl: './notas.component.css'
})
export class NotasComponent {
  @ViewChild(NotificationcComponent) notification: NotificationcComponent | null = null;
  aulas: any[] = [];
  alumnos: any[] = [];
  cursos: any[] = [];
  grados: any[] = [];
  profesores: any[] = [];
  periods: number[] = [];  // Inicializar como array vacío
  notas: any = {};
  modalVisible = false;
  viewModalVisible = false;
  selectedAula: any = null;

  constructor(private notasService: NotasService, private matriculaService: MatriculasService) {}

  ngOnInit() {
    this.loadAulas();
    this.loadCursos();
    this.loadGrados();
  }

  loadAulas() {
    this.notasService.getAulas().subscribe(data => {
      this.aulas = data;
    });
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

  showModal() {
    this.modalVisible = true;
  }

  hideModal() {
    this.modalVisible = false;
  }

  selectAula(aulaId: number): void {
    this.notasService.getDatosPorAula(aulaId).subscribe(
      data => {
        this.selectedAula = data.aula;
        this.periods = Array.from({ length: data.curso.numeroDePeriodos }, (_, i) => i + 1);
        this.initializeNotas(data.notas);
        this.showModal();
      },
      error => {
        console.error('Error fetching aula details', error);
      }
    );
  }

  loadNotas(cursoId: number): void {
    this.notasService.getNotasByCurso(cursoId).subscribe(
      data => {
        this.initializeNotas(data);
      },
      error => {
        console.error('Error fetching notas', error);
      }
    );
  }

  initializeNotas(notas: any[]): void {
    this.notas = {};
    this.selectedAula.alumnos.forEach((alumno: any) => {
      this.notas[alumno.id] = {};
      this.periods.forEach(period => {
        this.notas[alumno.id][period] = this.getNota(notas, alumno.id, period);
      });
    });
  }

  getNota(notas: any[], alumnoId: number, periodo: number): number {
    const nota = notas.find(n => n.alumnoId === alumnoId && n.periodo === periodo);
    return nota ? nota.valor : 0;
  }

  saveNotas() {
    const notasAlumnos = Object.keys(this.notas).map(alumnoId => ({
      alumnoId: +alumnoId,
      notasPeriodos: this.periods.map(period => ({
        periodo: period,
        valor: this.notas[alumnoId][period]
      }))
    }));
    const payload = {
      cursoId: this.selectedAula.curso.id,
      notasAlumnos
    };
    this.notasService.bulkUpdateNotas(payload).subscribe(
      () => {
        this.notification?.show('Notas actualizadas correctamente');
        this.hideModal();
      },
      error => {
        console.error('Error updating notas', error);
        this.notification?.show('Error al actualizar notas');
      }
    );
  }
  exportNotasToPdf(): void {
    if (!this.selectedAula || !this.selectedAula.id) {
      console.error('No se ha seleccionado ninguna aula.');
      return;
    }

    this.notasService.exportNotasToPdf(this.selectedAula.id).subscribe(response => {
      const blob = new Blob([response], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `notas_aula_${this.selectedAula.id}.pdf`;
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);
    }, error => {
      console.error('Error al exportar las notas a PDF:', error);
    });
  }

}
