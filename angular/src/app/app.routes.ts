import { Routes } from '@angular/router';
import { AlumnosComponent } from './alumnos/alumnos.component';
import { LoginComponent } from './auth/login/login.component';
import { authGuard } from './auth.guard';
import { CursosComponent } from './crud/cursos/cursos.component';
import { EstudiantesComponent } from './crud/estudiantes/estudiantes.component';
import { ProfesoresComponent } from './crud/profesores/profesores.component';
import { InicioComponent } from './crud/inicio/inicio.component';
import { RegisterComponent } from './auth/register/register.component';
import { AnuncioComponent } from './subcomponentes/anuncio/anuncio.component';
import { MatriculasComponent } from './gestionmatriculas/matriculas/matriculas.component';
import { AulasComponent } from './gestionmatriculas/aulas/aulas.component';
import { GradosComponent } from './gestionacademica/grados/grados.component';
import { UsuariosComponent } from './gestionacademica/usuarios/usuarios.component';
import { NotasComponent } from './gestionmatriculas/notas/notas.component';
import { AsistenciasComponent } from './gestionmatriculas/asistencias/asistencias.component';
import { OpcionesComponent } from './subcomponentes/opciones/opciones.component';
import { SidebarComponent } from './estudiante/sidebar/sidebar.component';
import { InicioEstudianteComponent } from './estudiante/inicio-estudiante/inicio-estudiante.component';
import { AulasEstudianteComponent } from './estudiante/aulas-estudiante/aulas-estudiante.component';
import { NotasEstudianteComponent } from './estudiante/notas-estudiante/notas-estudiante.component';
import { OpcionesEstudianteComponent } from './estudiante/opciones-estudiante/opciones-estudiante.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'casa', component: AlumnosComponent, canActivate: [authGuard]},
  { path: 'cursos', component: CursosComponent, canActivate: [authGuard]},
  { path: 'estudiantes', component: EstudiantesComponent, canActivate: [authGuard]},
  { path: 'profesores', component: ProfesoresComponent, canActivate: [authGuard]},
  { path: 'inicio', component: InicioComponent, canActivate: [authGuard]},
  { path: 'anuncios', component: AnuncioComponent, canActivate: [authGuard]},
  { path: 'matriculas', component: MatriculasComponent, canActivate: [authGuard]},
  { path: 'aulas', component: AulasComponent, canActivate: [authGuard]},
  { path: 'grados', component: GradosComponent, canActivate: [authGuard]},
  { path: 'usuarios', component: UsuariosComponent, canActivate: [authGuard]},
  { path: 'notas', component: NotasComponent, canActivate: [authGuard]},
  { path: 'asistencias', component: AsistenciasComponent, canActivate: [authGuard]},
  { path: 'opciones', component: OpcionesComponent, canActivate: [authGuard]},

  { path: 'estudiante', component: SidebarComponent, canActivate: [authGuard]},
  { path: 'estudiante/inicio', component: InicioEstudianteComponent, canActivate: [authGuard]},
  { path: 'estudiante/aulas', component: AulasEstudianteComponent, canActivate: [authGuard]},
  { path: 'estudiante/notas', component: NotasEstudianteComponent, canActivate: [authGuard]},
  { path: 'estudiante/opciones', component: OpcionesEstudianteComponent, canActivate: [authGuard]},



  { path: '', redirectTo: '/login', pathMatch: 'full'}
];
