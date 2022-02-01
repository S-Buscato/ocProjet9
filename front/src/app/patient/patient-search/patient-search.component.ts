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
  public formNotComplete: boolean;
  public error: string;


  constructor( private route: ActivatedRoute,
               private fb: FormBuilder,
               private patientRequestService: PatientRequestService,
               private router: Router,
               private patientService: PatientService) { }

  ngOnInit(): void {
    this.formNotComplete = false;
    this.patientform = this.fb.group({
      firstname: new FormControl('', [Validators.required]),
      lastname: new FormControl('', [Validators.required]),
    });
  }

  onFormSubmit(): void {
    this.error = null;
    if (this.patientform.controls.firstname.value  !== '' && this.patientform.controls.lastname.value !== '') {
      const patient: Patient = new Patient();
      patient.firstname = this.patientService.capitalize(this.patientform.controls.firstname.value);
      patient.lastname = this.patientService.capitalize(this.patientform.controls.lastname.value);
      this.formNotComplete = false;
      try {
        this.patientRequestService.searchPatient(patient).subscribe(
          data => {
          // @ts-ignore
          if (data.body.id !== 0){
            // @ts-ignore
            this.patient = data.body;
            // @ts-ignore
            this.router.navigate(['patients/' + data.body.id], { replaceUrl: this.route });
          }
        },
          error => {
            console.log('search err ?', error.error);
            this.error = error.error;
          });
      }catch (error){
        console.log('search err', error);
      }
    }else {
      this.formNotComplete = true;
    }
  }

}
