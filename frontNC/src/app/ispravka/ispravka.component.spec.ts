import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IspravkaComponent } from './ispravka.component';

describe('IspravkaComponent', () => {
  let component: IspravkaComponent;
  let fixture: ComponentFixture<IspravkaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IspravkaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IspravkaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
