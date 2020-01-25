import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CasopisiComponent } from './casopisi.component';

describe('CasopisiComponent', () => {
  let component: CasopisiComponent;
  let fixture: ComponentFixture<CasopisiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CasopisiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CasopisiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
