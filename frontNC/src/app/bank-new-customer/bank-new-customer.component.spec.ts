import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BankNewCustomerComponent } from './bank-new-customer.component';

describe('BankNewCustomerComponent', () => {
  let component: BankNewCustomerComponent;
  let fixture: ComponentFixture<BankNewCustomerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BankNewCustomerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BankNewCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
