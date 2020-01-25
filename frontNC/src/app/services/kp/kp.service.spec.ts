import { TestBed } from '@angular/core/testing';

import { KpService } from './kp.service';

describe('KpService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: KpService = TestBed.get(KpService);
    expect(service).toBeTruthy();
  });
});
