import { TestBed } from '@angular/core/testing';

import { EstudiantesServiceService } from './estudiantes-service.service';

describe('EstudiantesServiceService', () => {
  let service: EstudiantesServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstudiantesServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
