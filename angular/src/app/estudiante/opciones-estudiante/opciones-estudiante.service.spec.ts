import { TestBed } from '@angular/core/testing';

import { OpcionesEstudianteService } from './opciones-estudiante.service';

describe('OpcionesEstudianteService', () => {
  let service: OpcionesEstudianteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OpcionesEstudianteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
