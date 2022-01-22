import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PatientRequestService} from '../../repositories/patient-request.service';
import {NoteRequestService} from '../../repositories/note-request.service';
import {Note} from '../../model/note';


@Component({
  selector: 'app-patient-detail',
  templateUrl: './patient-detail.component.html',
  styleUrls: ['./patient-detail.component.css']
})
export class PatientDetailComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private patientRequestService: PatientRequestService,
    private noteRequestService: NoteRequestService,
    private router: Router
  ) { }
  // @ts-ignore
  public patient: Patient;
  public notes: Note[];
  public id: number;
  public age: number;
  public risk: string;

  ngOnInit(): void {
    this.id = this.route.snapshot.params.id;
    this.patientRequestService.getPatient(this.id).subscribe(
      data => {
        this.patient = data;
      }
    );

    this.patientRequestService.getAgePatient(this.id).subscribe(
      data => {
        this.age = data;
      }
    );
  }

  // tslint:disable-next-line:typedef
  public calculPatientRisk() {
      this.patientRequestService.getCalculatePatientRisk(this.patient.id).subscribe(
        data => {
          if (data) {
            this.risk = data;
          }
        }
      );
  }

  public deletePatient() {
    if (confirm("voulez-vous vraiment supprimer cette fiche patient et tout son historique ?")) {
      // @ts-ignore
      this.patientRequestService.deletePatient(this.patient.id).subscribe(
        data => {
          if (data) {
            this.noteRequestService.deleteAllPatientNote(this.id).subscribe(
              deldata => {
                if (deldata) {
                  // @ts-ignore
                  this.router.navigate(['patients'], { replaceUrl: this.route });
                }
              }
            );
          }
        }
      );
    }
  }
}
