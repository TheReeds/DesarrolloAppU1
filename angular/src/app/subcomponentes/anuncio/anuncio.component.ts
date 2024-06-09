import { Component, OnInit } from '@angular/core';
import { AlumnosComponent } from '../../alumnos/alumnos.component';
import { AnuncioService } from './anuncio.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-anuncio',
  standalone: true,
  imports: [AlumnosComponent, CommonModule, FormsModule],
  templateUrl: './anuncio.component.html',
  styleUrl: './anuncio.component.css'
})
export class AnuncioComponent implements OnInit{
  anuncios: any[] = [];
  modalVisible = true;
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
  showModal() {
    this.modalVisible = true;
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

  createAnuncio() {
    const formData = new FormData();
    formData.append('titulo', this.newAnuncio.titulo);
    formData.append('descripcion', this.newAnuncio.descripcion);
    if (this.newAnuncio.file) {
      formData.append('file', this.newAnuncio.file);
    }

    this.anuncioService.createAnuncio(formData).subscribe(() => {
      this.loadAnuncios();
      this.hideModal();
    });
  }

  deleteAnuncio(id: number) {
    this.anuncioService.deleteAnuncio(id).subscribe(() => {
      this.loadAnuncios();
    });
  }

  editAnuncio(anuncio: any) {
    // Aquí puedes implementar la lógica para editar un anuncio
    // por ejemplo, abrir otro modal para editar el anuncio seleccionado.
  }
}
