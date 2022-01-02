import { Component, OnInit, Input } from '@angular/core';
import { FormControl } from '@angular/forms';
import { PatientRequestService } from 'src/app/repositories/patient-request.service';


@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {
  public patientList = [];

  constructor(private patientRequest: PatientRequestService) {}

  ngOnInit(): void {
    this.patientRequest.getAllPatient().subscribe(
      data => {
        this.patientList = data;
        console.log(this.patientList);
      }
    );
  }





}
