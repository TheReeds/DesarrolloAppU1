import { Component, OnInit, ViewChild } from '@angular/core';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { GradosService } from './grados.service';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { WebSocketService } from '../../web-socket.service';

@Component({
  selector: 'app-grados',
  standalone: true,
  imports: [CommonModule, FormsModule, NotificationcComponent, AlumnosComponent],
  templateUrl: './grados.component.html',
  styleUrl: './grados.component.css'
})
export class GradosComponent implements OnInit {
  @ViewChild(NotificationcComponent) notification: NotificationcComponent | null = null;
  grados: any[] = [];
  modalVisible = false;
  isEdit = false;
  gradoToEdit: any = null;
  newGrado = {
    nombre: '',
    turno: '',
    nivel: ''
  };

  constructor(private gradoService: GradosService, private webSocketService: WebSocketService) {}

  ngOnInit() {
    this.loadGrados();
    // Suscribirse a los mensajes del WebSocket
    this.webSocketService.connect().subscribe(
      (message) => {
        console.log('Mensaje recibido:', message);
        this.handleWebSocketMessage(message);
      },
      (error) => {
        console.error('Error en el WebSocket:', error);
      }
    );
  }

  loadGrados() {
    this.gradoService.getGrados().subscribe(data => {
      this.grados = data;
    });
  }

  showModal(editing = false, grado: any = null) {
    this.modalVisible = true;
    this.isEdit = editing;
    if (editing && grado) {
      this.gradoToEdit = grado;
      this.newGrado = {
        nombre: grado.nombre,
        turno: grado.turno,
        nivel: grado.nivel
      };
    } else {
      this.resetNewGrado();
    }
  }

  closeModal() {
    this.modalVisible = false;
  }

  hideModal() {
    this.modalVisible = false;
    this.resetNewGrado();
  }

  resetNewGrado() {
    this.newGrado = {
      nombre: '',
      turno: '',
      nivel: ''
    };
  }

  createOrEditGrado() {
    if (this.isEdit && this.gradoToEdit) {
      this.gradoService.updateGrado(this.gradoToEdit.id, this.newGrado).subscribe(() => {
        this.loadGrados();
        this.closeModal();
        if (this.notification) {
          this.notification.show('Grado actualizado con éxito');
        }
      });
    } else {
      this.gradoService.createGrado(this.newGrado).subscribe(() => {
        this.loadGrados();
        this.closeModal();
        if (this.notification) {
          this.notification.show('Grado creado con éxito');
        }
      });
    }
  }

  deleteGrado(id: number) {
    this.gradoService.deleteGrado(id).subscribe(() => {
      this.loadGrados();
      if (this.notification) {
        this.notification.show('Grado eliminado con éxito');
      }
    });
  }

  editGrado(grado: any) {
    this.showModal(true, grado);
  }
  handleWebSocketMessage(message: any) {
    // Aquí puedes manejar los mensajes recibidos del WebSocket
    // Por ejemplo, podrías recargar la lista de grados o mostrar una notificación
    this.loadGrados(); // Recargar la lista de grados en este caso
    if (this.notification) {
      this.notification.show(`Mensaje WebSocket: ${message}`);
    }
  }
}
