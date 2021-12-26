import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {PatientRequestService} from '../../repositories/patient-request.service';


@Component({
  selector: 'app-patient-detail',
  templateUrl: './patient-detail.component.html',
  styleUrls: ['./patient-detail.component.css']
})
export class PatientDetailComponent implements OnInit {

  constructor(private route: ActivatedRoute, private patientRequestService: PatientRequestService) { }
  // @ts-ignore
  public patient: Patient;

  ngOnInit(): void {
    const id: number = this.route.snapshot.params.id;
    this.patientRequestService.getPatient(id).subscribe(
      data => {
        this.patient = data;
        console.log(this.patient);
      }
    );
  }
}
