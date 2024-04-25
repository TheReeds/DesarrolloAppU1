import { Component } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';

@Component({
  selector: 'app-profesores',
  standalone: true,
  imports: [AlumnosComponent],
  templateUrl: './profesores.component.html',
  styleUrl: './profesores.component.css'
})
export class ProfesoresComponent {

}
