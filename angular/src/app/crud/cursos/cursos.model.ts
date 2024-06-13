import { Profesor } from "./profesor.model";

export interface Curso {
  id: number;
  nombre: string;
  descripcion: string;
  duracion: string;
  numeroDePeriodos: number;
  profesorId: number;
  profesoresDto?: Profesor;
}
