import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpcionesEstudianteComponent } from './opciones-estudiante.component';

describe('OpcionesEstudianteComponent', () => {
  let component: OpcionesEstudianteComponent;
  let fixture: ComponentFixture<OpcionesEstudianteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OpcionesEstudianteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OpcionesEstudianteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
