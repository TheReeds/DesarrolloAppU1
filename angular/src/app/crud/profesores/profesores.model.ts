export interface Profesor {
  id: number;
  nombre: string;
  dni: string;
  especialidad: string;
  telefono: string;
  cursoDto: CursoDto;
}

export interface CursoDto {
  id: number;
  nombre: string;
  descripcion: string;
  duracion: string;
}
