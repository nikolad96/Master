import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubReturnComponent } from './sub-return.component';

describe('SubReturnComponent', () => {
  let component: SubReturnComponent;
  let fixture: ComponentFixture<SubReturnComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubReturnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubReturnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
