import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BitcoinNewCustomerComponent } from './bitcoin-new-customer.component';

describe('BitcoinNewCustomerComponent', () => {
  let component: BitcoinNewCustomerComponent;
  let fixture: ComponentFixture<BitcoinNewCustomerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BitcoinNewCustomerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BitcoinNewCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
