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


  { path: '', redirectTo: '/login', pathMatch: 'full'}
];
