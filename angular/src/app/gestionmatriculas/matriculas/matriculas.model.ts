export interface Curso {
  id: number;
  nombre: string;
  descripcion: string;
  duracion: string;
  profesorId: number;
  numeroDePeriodos: number;
  notas: any[];
  selected?: boolean; // Añadimos la propiedad optional selected para el checkbox
}

export interface Alumno {
  id: number;
  nombre: string;
  apellidos: string;
  telefono: string;
  dni: string;
  estado: boolean;
  grado: any;
}

export interface Matricula {
  fechaMatriculacion: string;
  alumnoId: number;
  estado: boolean;
  cursos: { cursoId: number }[];
}
