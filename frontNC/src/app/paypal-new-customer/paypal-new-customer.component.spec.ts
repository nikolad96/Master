import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaypalNewCustomerComponent } from './paypal-new-customer.component';

describe('PaypalNewCustomerComponent', () => {
  let component: PaypalNewCustomerComponent;
  let fixture: ComponentFixture<PaypalNewCustomerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaypalNewCustomerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaypalNewCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
