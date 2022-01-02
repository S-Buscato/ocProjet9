import {Component, Input, OnInit} from '@angular/core';

import {Patient} from '../../model/patient';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {PatientRequestService} from '../../repositories/patient-request.service';
import {PatientService} from '../patient.service';

@Component({
  selector: 'app-patient-search',
  templateUrl: './patient-search.component.html',
  styleUrls: ['./patient-search.component.css']
})
export class PatientSearchComponent implements OnInit {
  @Input()
  patientform: FormGroup;

  public patient: Patient;
  public patientNotFound: boolean;
  public formNotComplete: boolean;

  constructor( private route: ActivatedRoute,
               private fb: FormBuilder,
               private patientRequestService: PatientRequestService,
               private router: Router,
               private patientService: PatientService) { }

  ngOnInit(): void {
    this.patientNotFound = false;
    this.formNotComplete = false;
    this.patientform = this.fb.group({
      firstname: new FormControl('', [Validators.required]),
      lastname: new FormControl('', [Validators.required]),
    });
  }

  onFormSubmit(): void {
    if (this.patientform.controls.firstname.value  !== '' && this.patientform.controls.lastname.value !== '') {
      this.patientNotFound = false;
      const patient: Patient = new Patient();
      patient.firstname = this.patientService.capitalize(this.patientform.controls.firstname.value);
      patient.lastname = this.patientService.capitalize(this.patientform.controls.lastname.value);
      console.log('search patient : ', patient);
      this.formNotComplete = false;
      try {
        this.patientRequestService.searchPatient(patient).subscribe( data => {
          // @ts-ignore
          // tslint:disable-next-line:triple-equals
          console.log(' data search ', data);
          if (data.id !== 0){
            // @ts-ignore
            this.patient = data;
            // @ts-ignore
            this.router.navigate(['patients/' + data.id], { replaceUrl: this.route });
          }else{
            this.patientNotFound = true;
          }
        });
      }catch (error){
        console.log('save err', error);
      }
    }else {
      this.formNotComplete = true;
    }
  }

}
