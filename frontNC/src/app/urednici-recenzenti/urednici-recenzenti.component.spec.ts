import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UredniciRecenzentiComponent } from './urednici-recenzenti.component';

describe('UredniciRecenzentiComponent', () => {
  let component: UredniciRecenzentiComponent;
  let fixture: ComponentFixture<UredniciRecenzentiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UredniciRecenzentiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UredniciRecenzentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
