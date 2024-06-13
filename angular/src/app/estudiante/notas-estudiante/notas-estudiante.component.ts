import { Component } from '@angular/core';
import { NotasEstudianteService } from './notas-estudiante.service';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from '../sidebar/sidebar.component';

@Component({
  selector: 'app-notas-estudiante',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './notas-estudiante.component.html',
  styleUrl: './notas-estudiante.component.css'
})
export class NotasEstudianteComponent {
  notas: any[] = [];
  cursos: any[] = [];
  alumnoId: number | null = null;

  constructor(private notasService: NotasEstudianteService, private http: HttpClient) {}

  ngOnInit() {
    this.obtenerAlumnoId();
    this.cargarNotas();
  }

  obtenerAlumnoId() {
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.http.get<any>('http://localhost:8085/usuarios/perfil', {
        headers: { Authorization: `Bearer ${token}` }
      }).subscribe(perfil => {
        this.alumnoId = perfil.alumnoId;
        this.cargarNotas();
      });
    }
  }

  cargarNotas() {
    if (this.alumnoId !== null) {
      this.notasService.getNotas(this.alumnoId).subscribe(notas => {
        this.notas = notas;
        this.cargarCursos();
      });
    }
  }

  cargarCursos() {
    this.http.get<any[]>('http://localhost:8085/cursos').subscribe(cursos => {
      this.cursos = cursos;
    });
  }

  obtenerNombreCurso(cursoId: number): string {
    const curso = this.cursos.find(c => c.id === cursoId);
    return curso ? curso.nombre : '';
  }
}
