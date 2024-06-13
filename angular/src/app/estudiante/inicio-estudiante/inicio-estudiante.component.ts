import { Component } from '@angular/core';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { AnuncioService } from '../../subcomponentes/anuncio/anuncio.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inicio-estudiante',
  standalone: true,
  imports: [SidebarComponent, CommonModule],
  templateUrl: './inicio-estudiante.component.html',
  styleUrl: './inicio-estudiante.component.css'
})
export class InicioEstudianteComponent {
  anuncios: any[] = [];

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
}
