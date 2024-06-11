export interface Curso {
  id: number;
  nombre: string;
  descripcion: string;
  duracion: number;
  numeroDePeriodos: number;
  profesoresDto: ProfesoresDto;

}
export interface ProfesoresDto {
  id: number;
  nombre: string;
  dni: string;
  especialidad: string;
}

