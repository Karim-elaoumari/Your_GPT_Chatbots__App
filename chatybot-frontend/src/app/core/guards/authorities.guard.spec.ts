import { TestBed } from '@angular/core/testing';

import { AuthoritiesGuard } from './authorities.guard';

describe('AuthoritiesGuard', () => {
  let guard: AuthoritiesGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AuthoritiesGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
