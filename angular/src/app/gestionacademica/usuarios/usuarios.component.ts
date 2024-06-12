import { Component, OnInit, ViewChild } from '@angular/core';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuariosService } from './usuarios.service';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { AsociarModalComponent } from './asociar-modal/asociar-modal.component';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule, NotificationcComponent, AlumnosComponent, AsociarModalComponent],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css'
})
export class UsuariosComponent implements OnInit {
  @ViewChild(NotificationcComponent) notification: NotificationcComponent | null = null;
  usuarios: any[] = [];
  modalVisible = false;
  isEdit = false;
  usuarioToEdit: any = null;
  newUsuario = {
    name: '',
    email: '',
    password: '',
    role: 'USER'
  };
  roles = ['USER', 'ADMIN', 'TEACHER'];
  alumnosSinUsuario: any[] = [];
  profesoresSinUsuario: any[] = [];
  asociarModalVisible = false;
  usuarioIdToAsociar: number | null = null;

  constructor(private usuarioService: UsuariosService) {}

  ngOnInit() {
    this.loadUsuarios();
  }

  loadUsuarios() {
    this.usuarioService.getUsuarios().subscribe(data => {
      this.usuarios = data;
    });
  }
  getImageUrl(nombreImagen: string): string {
    return `http://localhost:8085/usuarios/uploads/${nombreImagen}`;
  }

  showModal(editing = false, usuario: any = null) {
    this.modalVisible = true;
    this.isEdit = editing;
    if (editing && usuario) {
      this.usuarioToEdit = usuario;
      this.newUsuario = {
        name: usuario.name,
        email: usuario.email,
        password: '',
        role: usuario.role
      };
    } else {
      this.resetNewUsuario();
    }
  }

  closeModal() {
    this.modalVisible = false;
  }

  hideModal() {
    this.modalVisible = false;
    this.resetNewUsuario();
  }

  resetNewUsuario() {
    this.newUsuario = {
      name: '',
      email: '',
      password: '',
      role: 'USER'
    };
    this.usuarioToEdit = null;
    this.isEdit = false;
  }

  createOrEditUsuario() {
    if (this.isEdit && this.usuarioToEdit) {
      this.usuarioService.updateUsuario(this.usuarioToEdit.id, this.newUsuario).subscribe(() => {
        this.loadUsuarios();
        this.closeModal();
        if (this.notification) {
          this.notification.show('Usuario actualizado con éxito');
        }
      });
    } else {
      this.usuarioService.createUsuario(this.newUsuario).subscribe(() => {
        this.loadUsuarios();
        this.closeModal();
        if (this.notification) {
          this.notification.show('Usuario creado con éxito');
        }
      });
    }
  }

  deleteUsuario(id: number) {
    this.usuarioService.deleteUsuario(id).subscribe(() => {
      this.loadUsuarios();
      if (this.notification) {
        this.notification.show('Usuario eliminado con éxito');
      }
    }, error => {
      console.error('Error eliminando el usuario', error);
    });
  }

  editUsuario(usuario: any) {
    this.showModal(true, usuario);
  }

  asociarAlumnoOProfesor(id: number) {
    this.usuarioIdToAsociar = id;
    this.usuarioService.getAlumnosSinUsuario().subscribe(alumnos => {
      this.alumnosSinUsuario = alumnos;
      this.usuarioService.getProfesoresSinUsuario().subscribe(profesores => {
        this.profesoresSinUsuario = profesores;
        this.asociarModalVisible = true;
      });
    });
  }

  onSelectAlumno(alumnoId: number) {
    if (this.usuarioIdToAsociar !== null) {
      this.usuarioService.asociarAlumno(this.usuarioIdToAsociar, alumnoId).subscribe(() => {
        this.loadUsuarios();
        this.asociarModalVisible = false;
        this.usuarioIdToAsociar = null;
        if (this.notification) {
          this.notification.show('Alumno asociado con éxito');
        }
      });
    }
  }

  onSelectProfesor(profesorId: number) {
    if (this.usuarioIdToAsociar !== null) {
      this.usuarioService.asociarProfesor(this.usuarioIdToAsociar, profesorId).subscribe(() => {
        this.loadUsuarios();
        this.asociarModalVisible = false;
        this.usuarioIdToAsociar = null;
        if (this.notification) {
          this.notification.show('Profesor asociado con éxito');
        }
      });
    }
  }

  onCloseModal() {
    this.asociarModalVisible = false;
    this.usuarioIdToAsociar = null;
  }

}
