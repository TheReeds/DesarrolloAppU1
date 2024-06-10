import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationcComponent } from './notificationc.component';

describe('NotificationcComponent', () => {
  let component: NotificationcComponent;
  let fixture: ComponentFixture<NotificationcComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotificationcComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotificationcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
