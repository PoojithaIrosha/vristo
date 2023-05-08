import {TestBed} from '@angular/core/testing';

import {GrnService} from './grn.service';

describe('GrnService', () => {
    let service: GrnService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(GrnService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
