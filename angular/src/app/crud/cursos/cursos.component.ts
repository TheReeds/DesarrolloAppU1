import { Component } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';

@Component({
  selector: 'app-cursos',
  standalone: true,
  imports: [AlumnosComponent],
  templateUrl: './cursos.component.html',
  styleUrl: './cursos.component.css'
})
export class CursosComponent {

}
