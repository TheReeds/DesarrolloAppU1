import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AulasEstudianteComponent } from './aulas-estudiante.component';

describe('AulasEstudianteComponent', () => {
  let component: AulasEstudianteComponent;
  let fixture: ComponentFixture<AulasEstudianteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AulasEstudianteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AulasEstudianteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
