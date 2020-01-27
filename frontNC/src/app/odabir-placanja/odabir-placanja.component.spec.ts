import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OdabirPlacanjaComponent } from './odabir-placanja.component';

describe('OdabirPlacanjaComponent', () => {
  let component: OdabirPlacanjaComponent;
  let fixture: ComponentFixture<OdabirPlacanjaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OdabirPlacanjaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OdabirPlacanjaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
