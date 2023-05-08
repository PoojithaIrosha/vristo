import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AllBrandsComponent} from './all-brands.component';

describe('AllBrandsComponent', () => {
    let component: AllBrandsComponent;
    let fixture: ComponentFixture<AllBrandsComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [AllBrandsComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(AllBrandsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
