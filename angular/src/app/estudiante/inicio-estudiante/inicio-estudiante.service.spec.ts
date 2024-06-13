import { TestBed } from '@angular/core/testing';

import { InicioEstudianteService } from './inicio-estudiante.service';

describe('InicioEstudianteService', () => {
  let service: InicioEstudianteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InicioEstudianteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
