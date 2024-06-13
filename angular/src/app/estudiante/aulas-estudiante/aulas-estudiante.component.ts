import { Component, OnInit } from '@angular/core';
import { AulasEstudianteService } from './aulas-estudiante.service';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-aulas-estudiante',
  standalone: true,
  imports: [SidebarComponent, CommonModule],
  templateUrl: './aulas-estudiante.component.html',
  styleUrl: './aulas-estudiante.component.css'
})
export class AulasEstudianteComponent implements OnInit {
  aulas: any[] = [];

  constructor(private aulasEstudianteService: AulasEstudianteService) {}

  ngOnInit() {
    this.aulasEstudianteService.getAlumnoId().subscribe(alumnoId => {
      if (alumnoId) {
        this.loadAulas(alumnoId);
      }
    });
  }

  loadAulas(alumnoId: number) {
    this.aulasEstudianteService.getAulasByAlumnoId(alumnoId).subscribe(data => {
      this.aulas = data;
    });
  }
}
