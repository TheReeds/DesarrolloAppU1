import { TestBed } from '@angular/core/testing';

import { AulasEstudianteService } from './aulas-estudiante.service';

describe('AulasEstudianteService', () => {
  let service: AulasEstudianteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AulasEstudianteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
