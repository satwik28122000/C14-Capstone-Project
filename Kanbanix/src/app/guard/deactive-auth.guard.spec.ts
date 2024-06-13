import { TestBed } from '@angular/core/testing';
import { CanDeactivateFn } from '@angular/router';

import { deactiveAuthGuard } from './deactive-auth.guard';

describe('deactiveAuthGuard', () => {
  const executeGuard: CanDeactivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => deactiveAuthGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
