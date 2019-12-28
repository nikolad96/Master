import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BankPageComponent } from './bank-page.component';

describe('BankPageComponent', () => {
  let component: BankPageComponent;
  let fixture: ComponentFixture<BankPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BankPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BankPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
