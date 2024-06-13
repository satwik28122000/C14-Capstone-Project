import { TestBed } from '@angular/core/testing';
import { CanActivateChildFn } from '@angular/router';

import { childAuthsGuard } from './child-auths.guard';

describe('childAuthsGuard', () => {
  const executeGuard: CanActivateChildFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => childAuthsGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
