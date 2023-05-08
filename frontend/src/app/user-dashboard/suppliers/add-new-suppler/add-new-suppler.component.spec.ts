import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddNewSupplerComponent} from './add-new-suppler.component';

describe('AddNewSupplerComponent', () => {
    let component: AddNewSupplerComponent;
    let fixture: ComponentFixture<AddNewSupplerComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [AddNewSupplerComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(AddNewSupplerComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
