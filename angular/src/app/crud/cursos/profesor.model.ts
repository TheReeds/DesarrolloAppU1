export interface Profesor {
  id: number;
  nombre: string;
  dni: string;
  especialidad: string;
  telefono: string;
  usuarioId: number | null;
  usuarioDto: {
    id: number;
    name: string;
    email: string;
    role: string;
  } | null;
}
