import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaypalRedComponent } from './paypal-red.component';

describe('PaypalRedComponent', () => {
  let component: PaypalRedComponent;
  let fixture: ComponentFixture<PaypalRedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaypalRedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaypalRedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
