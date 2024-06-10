// anuncios.component.ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { AnuncioService } from './anuncio.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NotificationcComponent } from '../../alerts/notificationc/notificationc.component';

@Component({
  selector: 'app-anuncio',
  standalone: true,
  imports: [AlumnosComponent, CommonModule, FormsModule, NotificationcComponent],
  templateUrl: './anuncio.component.html',
  styleUrls: ['./anuncio.component.css']
})
export class AnuncioComponent implements OnInit {
  @ViewChild(NotificationcComponent) notification: NotificationcComponent | null = null;
  anuncios: any[] = [];
  modalVisible = false;
  isEdit = false;
  anuncioToEdit: any = null;
  newAnuncio = {
    titulo: '',
    descripcion: '',
    file: null
  };

  constructor(private anuncioService: AnuncioService) {}

  ngOnInit() {
    this.loadAnuncios();
  }

  loadAnuncios() {
    this.anuncioService.getAnuncios().subscribe(data => {
      this.anuncios = data;
    });
  }

  getImageUrl(nombreImagen: string): string {
    return `http://localhost:8085/anuncios/uploads/${nombreImagen}`;
  }

  showModal(editing = false, anuncio: any = null) {
    this.modalVisible = true;
    this.isEdit = editing;
    if (editing && anuncio) {
      this.anuncioToEdit = anuncio;
      this.newAnuncio = {
        titulo: anuncio.titulo,
        descripcion: anuncio.descripcion,
        file: null
      };
    } else {
      this.resetNewAnuncio();
    }
  }
  closeModal() {
    this.modalVisible = false;
  }

  hideModal() {
    this.modalVisible = false;
    this.resetNewAnuncio();
  }

  resetNewAnuncio() {
    this.newAnuncio = {
      titulo: '',
      descripcion: '',
      file: null
    };
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.newAnuncio.file = file;
  }

  createOrEditAnuncio() {
    const formData = new FormData();
    formData.append('titulo', this.newAnuncio.titulo);
    formData.append('descripcion', this.newAnuncio.descripcion);
    if (this.newAnuncio.file) {
      formData.append('file', this.newAnuncio.file);
    }

    if (this.isEdit && this.anuncioToEdit) {
      this.anuncioService.updateAnuncio(this.anuncioToEdit.id, formData).subscribe(() => {
        this.loadAnuncios();
        this.closeModal();
        if (this.notification) {
          this.notification.show('Anuncio actualizado con éxito');
        }
      });
    } else {
      this.anuncioService.createAnuncio(formData).subscribe(() => {
        this.loadAnuncios();
        this.closeModal();
        if (this.notification) {
          this.notification.show('Anuncio creado con éxito');
        }
      });
    }
  }
  deleteAnuncio(id: number) {
    this.anuncioService.deleteAnuncio(id).subscribe(() => {
      this.loadAnuncios();
      if (this.notification) {
        this.notification.show('Anuncio eliminado con éxito');
      }
    });
  }

  editAnuncio(anuncio: any) {
    this.showModal(true, anuncio);
  }
}
