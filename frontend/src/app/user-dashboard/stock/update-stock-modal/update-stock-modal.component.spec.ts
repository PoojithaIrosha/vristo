import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateStockModalComponent} from './update-stock-modal.component';

describe('UpdateStockModalComponent', () => {
    let component: UpdateStockModalComponent;
    let fixture: ComponentFixture<UpdateStockModalComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [UpdateStockModalComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(UpdateStockModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
