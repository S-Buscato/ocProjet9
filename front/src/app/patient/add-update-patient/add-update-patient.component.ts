import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Patient } from 'src/app/model/patient';
import {PatientRequestService} from '../../repositories/patient-request.service';


@Component({
  selector: 'app-add-update-patient',
  templateUrl: './add-update-patient.component.html',
  styleUrls: ['./add-update-patient.component.css']
})
export class AddUpdatePatientComponent implements OnInit {
  @Input()
  patientform: FormGroup;
  newPatient: Patient;

  constructor(private fb: FormBuilder, private patientRequestService: PatientRequestService) { }



  ngOnInit(): void {
    this.patientform = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      dob: ['', Validators.required],
      sex: ['', Validators.required],
      phone: [''],
      address: ['']
     });
  }

  onFormSubmit(): void {
    const patient: Patient = new Patient();
    patient.firstname = this.patientform.controls.firstname.value;
    patient.lastname = this.patientform.controls.lastname.value;
    patient.dob = this.patientform.controls.dob.value;
    patient.sex = this.patientform.controls.sex.value;
    patient.address = this.patientform.controls.address.value;
    patient.phone = this.patientform.controls.phone.value;
    this.patientRequestService.addNewPatient(patient).subscribe( data => console.log(data));
    console.log('Name:' + patient.firstname);
}


}
