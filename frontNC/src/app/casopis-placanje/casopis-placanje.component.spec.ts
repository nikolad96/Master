import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CasopisPlacanjeComponent } from './casopis-placanje.component';

describe('CasopisPlacanjeComponent', () => {
  let component: CasopisPlacanjeComponent;
  let fixture: ComponentFixture<CasopisPlacanjeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CasopisPlacanjeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CasopisPlacanjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
