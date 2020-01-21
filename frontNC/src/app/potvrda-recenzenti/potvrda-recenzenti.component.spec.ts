import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PotvrdaRecenzentiComponent } from './potvrda-recenzenti.component';

describe('PotvrdaRecenzentiComponent', () => {
  let component: PotvrdaRecenzentiComponent;
  let fixture: ComponentFixture<PotvrdaRecenzentiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PotvrdaRecenzentiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PotvrdaRecenzentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
