import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsociarModalComponent } from './asociar-modal.component';

describe('AsociarModalComponent', () => {
  let component: AsociarModalComponent;
  let fixture: ComponentFixture<AsociarModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AsociarModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AsociarModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
