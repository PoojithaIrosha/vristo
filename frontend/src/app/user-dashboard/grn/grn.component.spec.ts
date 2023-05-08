import {ComponentFixture, TestBed} from '@angular/core/testing';

import {GrnComponent} from './grn.component';

describe('GrnComponent', () => {
    let component: GrnComponent;
    let fixture: ComponentFixture<GrnComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [GrnComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(GrnComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
