import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaypalCancelComponent } from './paypal-cancel.component';

describe('PaypalCancelComponent', () => {
  let component: PaypalCancelComponent;
  let fixture: ComponentFixture<PaypalCancelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaypalCancelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaypalCancelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
