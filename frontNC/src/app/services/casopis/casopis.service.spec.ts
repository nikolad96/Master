import { TestBed } from '@angular/core/testing';

import { CasopisService } from './casopis.service';

describe('CasopisService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CasopisService = TestBed.get(CasopisService);
    expect(service).toBeTruthy();
  });
});
