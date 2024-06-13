export interface Estudiante {
  id: number;
  nombre: string;
  apellidos: string;
  telefono: string;
  dni: string;
  estado: boolean;
  grado: {
    id: number;
    nombre?: string;
    turno?: string;
    nivel?: string;
  };
  usuarioId: number | null;
  usuarioDto: {
    id: number;
    name: string;
    email: string;
    role: string;
  } | null;
}
