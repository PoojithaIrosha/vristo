import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SupplierModalComponent} from './supplier-modal.component';

describe('SupplierModalComponent', () => {
    let component: SupplierModalComponent;
    let fixture: ComponentFixture<SupplierModalComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [SupplierModalComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(SupplierModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
