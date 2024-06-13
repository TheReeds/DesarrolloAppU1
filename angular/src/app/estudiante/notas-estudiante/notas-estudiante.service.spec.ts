import { TestBed } from '@angular/core/testing';

import { NotasEstudianteService } from './notas-estudiante.service';

describe('NotasEstudianteService', () => {
  let service: NotasEstudianteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotasEstudianteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
