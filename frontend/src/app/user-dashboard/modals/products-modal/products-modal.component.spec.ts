import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProductsModalComponent} from './products-modal.component';

describe('ProductsModalComponent', () => {
    let component: ProductsModalComponent;
    let fixture: ComponentFixture<ProductsModalComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ProductsModalComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(ProductsModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
