import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CasopisComponent } from './casopis.component';

describe('CasopisComponent', () => {
  let component: CasopisComponent;
  let fixture: ComponentFixture<CasopisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CasopisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CasopisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
