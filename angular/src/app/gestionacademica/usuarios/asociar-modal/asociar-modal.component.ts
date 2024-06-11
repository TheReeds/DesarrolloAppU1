import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-asociar-modal',
  standalone: true,
  imports: [ CommonModule],
  templateUrl: './asociar-modal.component.html',
  styleUrl: './asociar-modal.component.css'
})
export class AsociarModalComponent {
  @Input() alumnos: any[] = [];
  @Input() profesores: any[] = [];
  @Output() selectAlumno = new EventEmitter<number>();
  @Output() selectProfesor = new EventEmitter<number>();
  @Output() closeModal = new EventEmitter<void>();

  onSelectAlumno(alumnoId: number) {
    this.selectAlumno.emit(alumnoId);
  }

  onSelectProfesor(profesorId: number) {
    this.selectProfesor.emit(profesorId);
  }

  onClose() {
    this.closeModal.emit();
  }
}
