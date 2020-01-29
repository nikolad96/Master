import { TestBed } from '@angular/core/testing';

import { BankServiceService } from './bank-service.service';

describe('BankServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BankServiceService = TestBed.get(BankServiceService);
    expect(service).toBeTruthy();
  });
});
