import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CasopisAdminComponent } from './casopis-admin.component';

describe('CasopisAdminComponent', () => {
  let component: CasopisAdminComponent;
  let fixture: ComponentFixture<CasopisAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CasopisAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CasopisAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
