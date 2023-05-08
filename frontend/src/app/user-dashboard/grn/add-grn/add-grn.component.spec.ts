import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddGrnComponent} from './add-grn.component';

describe('AddGrnComponent', () => {
    let component: AddGrnComponent;
    let fixture: ComponentFixture<AddGrnComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [AddGrnComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(AddGrnComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
