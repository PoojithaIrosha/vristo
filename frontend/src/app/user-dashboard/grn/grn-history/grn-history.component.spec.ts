import {ComponentFixture, TestBed} from '@angular/core/testing';

import {GrnHistoryComponent} from './grn-history.component';

describe('GrnHistoryComponent', () => {
    let component: GrnHistoryComponent;
    let fixture: ComponentFixture<GrnHistoryComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [GrnHistoryComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(GrnHistoryComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
