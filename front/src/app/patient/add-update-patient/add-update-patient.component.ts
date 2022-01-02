import { Component, Input, OnInit } from '@angular/core';
import {FormGroup, FormBuilder, Validators, FormControl} from '@angular/forms';
import {Patient } from 'src/app/model/patient';
import {PatientRequestService} from '../../repositories/patient-request.service';
import {Router, ActivatedRoute} from '@angular/router';
import {PatientService} from '../patient.service';



@Component({
  selector: 'app-add-update-patient',
  templateUrl: './add-update-patient.component.html',
  styleUrls: ['./add-update-patient.component.css']
})
export class AddUpdatePatientComponent implements OnInit {
  // @ts-ignore
  @Input()
  patientform: FormGroup;

  public patient: Patient;
  public id: number;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private patientRequestService: PatientRequestService,
    private router: Router,
    private patientService: PatientService,
  ) { }

  ngOnInit(): void {
    if (this.route.snapshot.params.id != null) {
      this.id = this.route.snapshot.params.id;
      this.patientRequestService.getPatient(this.id).subscribe(
        data => {
          this.patient = data;
          this.patientform = this.fb.group({
            firstname: new FormControl(this.patientService.capitalize(this.patient.firstname), Validators.required),
            lastname: new FormControl(this.patientService.capitalize(this.patient.lastname), Validators.required),
            dob: new FormControl(this.patient.dob, Validators.required),
            sex: new FormControl(this.patient.sex, Validators.required),
            phone: new FormControl(this.patient.phone ),
            address: new FormControl(this.patient.address)
          });
        }
      );
    } else {
      this.patientform = this.fb.group({
        firstname: new FormControl('', Validators.required),
        lastname: new FormControl('', Validators.required),
        dob: new FormControl('', Validators.required),
        sex: new FormControl('', Validators.required),
        phone: new FormControl(''),
        address: new FormControl('')
      });
    }
  }

  // @ts-ignore
  onFormSubmit(): void {
    const patient: Patient = new Patient();
    patient.id = this.id || 0 ;
    patient.firstname = this.patientService.capitalize(this.patientform.controls.firstname.value);
    patient.lastname = this.patientService.capitalize(this.patientform.controls.lastname.value);
    patient.dob = this.patientform.controls.dob.value;
    patient.sex = this.patientform.controls.sex.value;
    patient.address = this.patientform.controls.address.value;
    patient.phone = this.patientform.controls.phone.value;
    console.log('patient : ', patient);
    try {
      this.patientRequestService.addNewPatient(patient).subscribe( data => {
        // @ts-ignore
        if (data){
          // @ts-ignore
          console.log(' save data ', data);
          // @ts-ignore
          this.router.navigate(['patients/' + data.id], { replaceUrl: this.route });
        }
      });
      }catch (error){
        console.log('save err', error);
      }
  }
}
