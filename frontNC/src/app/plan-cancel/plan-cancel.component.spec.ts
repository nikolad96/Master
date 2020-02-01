import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanCancelComponent } from './plan-cancel.component';

describe('PlanCancelComponent', () => {
  let component: PlanCancelComponent;
  let fixture: ComponentFixture<PlanCancelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlanCancelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanCancelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
