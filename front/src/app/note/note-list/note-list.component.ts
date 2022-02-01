import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PatientRequestService} from '../../repositories/patient-request.service';
import {NoteRequestService} from '../../repositories/note-request.service';
import {Note} from '../../model/note';

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.css']
})
export class NoteListComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private patientRequestService: PatientRequestService,
    private noteRequestService: NoteRequestService,
    private router: Router
  ) { }

  public notes: Note[];
  public id: number;

  ngOnInit(): void {
    this.id = this.route.snapshot.params.id;
    this.noteRequestService.getPatientNote(this.id).subscribe(
      noteData => {
        this.notes = noteData.body;
      }
    );
  }

}
