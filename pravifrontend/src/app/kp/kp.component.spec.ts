import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KPComponent } from './kp.component';

describe('KPComponent', () => {
  let component: KPComponent;
  let fixture: ComponentFixture<KPComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KPComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KPComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
