import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotasEstudianteComponent } from './notas-estudiante.component';

describe('NotasEstudianteComponent', () => {
  let component: NotasEstudianteComponent;
  let fixture: ComponentFixture<NotasEstudianteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotasEstudianteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotasEstudianteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
