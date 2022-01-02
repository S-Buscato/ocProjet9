import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PatientRequestService} from '../../repositories/patient-request.service';
import {FormControl, Validators} from '@angular/forms';


@Component({
  selector: 'app-patient-detail',
  templateUrl: './patient-detail.component.html',
  styleUrls: ['./patient-detail.component.css']
})
export class PatientDetailComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private patientRequestService: PatientRequestService,
    private router: Router
  ) { }
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

  // tslint:disable-next-line:typedef
  public deletePatient() {
    if (confirm("voulez-vous vraiment supprimer cette fiche patient ?")) {
      // @ts-ignore
      this.patientRequestService.deletePatient(this.patient).subscribe(
        data => {
          if (data) {
            // @ts-ignore
            this.router.navigate(['patients'], { replaceUrl: this.route });
          }
        }
      );
    }
  }
}
