import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUpdatePatientComponent } from './add-update-patient.component';

describe('AddUpdatePatientComponent', () => {
  let component: AddUpdatePatientComponent;
  let fixture: ComponentFixture<AddUpdatePatientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddUpdatePatientComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUpdatePatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
