import { TestBed } from '@angular/core/testing';

import { PaypalServiceService } from './paypal-service.service';

describe('PaypalServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PaypalServiceService = TestBed.get(PaypalServiceService);
    expect(service).toBeTruthy();
  });
});
